package lifesavings.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 4/2/15.
 */
public class ExerciseDataSource {

    private SQLiteDatabase database;
    private ExerciseSQLHelper helper;
    private String[] allColumns = { ExerciseSQLHelper.COLUMN_ID, ExerciseSQLHelper.COLUMN_USERID, ExerciseSQLHelper.COLUMN_TIME, ExerciseSQLHelper.COLUMN_DURATION, ExerciseSQLHelper.COLUMN_EXCERCISE};

    public ExerciseDataSource(Context context) {
        helper = new ExerciseSQLHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public Exercise createExcercise(int userid, int time, double duration, String excercise) {
        ContentValues values = new ContentValues();
        values.put(ExerciseSQLHelper.COLUMN_USERID, userid);
        values.put(ExerciseSQLHelper.COLUMN_TIME,time);
        values.put(ExerciseSQLHelper.COLUMN_DURATION,duration);
        values.put(ExerciseSQLHelper.COLUMN_EXCERCISE,excercise);

        long insertId = database.insert(ExerciseSQLHelper.TABLE_EXCERCISE, null, values);
        Cursor cursor = database.query(ExerciseSQLHelper.TABLE_EXCERCISE, allColumns, ExerciseSQLHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Exercise newExercise = cursorToExcercise(cursor);
        cursor.close();
        return newExercise;
    }

    public void deleteExcercise(Exercise Exercise) {
        long id = Exercise.getUserid();
        database.delete(ExerciseSQLHelper.TABLE_EXCERCISE, ExerciseSQLHelper.COLUMN_USERID
                + " = " + id, null);
    }

    public List<Exercise> getAllExcercises() {
        List<Exercise> exercises = new ArrayList<Exercise>();

        Cursor cursor = database.query(ExerciseSQLHelper.TABLE_EXCERCISE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise Exercise = cursorToExcercise(cursor);
            exercises.add(Exercise);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return exercises;
    }

    private Exercise cursorToExcercise(Cursor cursor) {
        Exercise Exercise = new Exercise();
        Exercise.setId(cursor.getInt(0));
        Exercise.setUserid(cursor.getInt(1));
        Exercise.setTime(cursor.getInt(2));
        Exercise.setDuration(cursor.getDouble(3));
        Exercise.setExcercise(cursor.getString(4));
        return Exercise;
    }

}
