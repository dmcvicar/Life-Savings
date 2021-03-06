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
    private String[] allColumns = { ExerciseSQLHelper.COLUMN_ID, ExerciseSQLHelper.COLUMN_USERID, ExerciseSQLHelper.COLUMN_TIME, ExerciseSQLHelper.COLUMN_DURATION, ExerciseSQLHelper.COLUMN_EXCERCISE, ExerciseSQLHelper.COLUMN_EXERTION};

    public ExerciseDataSource(Context context) {
        helper = new ExerciseSQLHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public Exercise createExcercise(int userid, String time, double duration, String excercise, String exertion) {
        ContentValues values = new ContentValues();
        values.put(ExerciseSQLHelper.COLUMN_USERID, userid);
        values.put(ExerciseSQLHelper.COLUMN_TIME,time);
        values.put(ExerciseSQLHelper.COLUMN_DURATION,duration);
        values.put(ExerciseSQLHelper.COLUMN_EXCERCISE,excercise);
        values.put(ExerciseSQLHelper.COLUMN_EXERTION,exertion);

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

    public List<Exercise> getAllExcercises(int user_id) {
        List<Exercise> exercises = new ArrayList<Exercise>();

        Cursor cursor = database.query(ExerciseSQLHelper.TABLE_EXCERCISE,
                allColumns, ""+ ExerciseSQLHelper.COLUMN_USERID + " = " + user_id, null, null, null, null);

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
        Exercise.setTime(cursor.getString(2));
        Exercise.setDuration(cursor.getDouble(3));
        Exercise.setExcercise(cursor.getString(4));
        Exercise.setExertion(cursor.getString(5));
        return Exercise;
    }


    public static class Exercise{
        private int id;
        private int userid;
        private String time;
        private double duration;
        private String exercise;
        private String exertion;

        public Exercise() {

        }

        public Exercise(int rowid, int userid, String time, double duration, String excercise, String exertion) {
            this.id = rowid;
            this.userid = userid;
            this.time = time;
            this.duration = duration;
            this.exercise = excercise;
            this.exertion = exertion;
        }



        public String getExertion() {return this.exertion;}

        public void setExertion(String exertion){this.exertion = exertion;}

        public String getExcercise() {
            return exercise;
        }

        public void setExcercise(String excercise) {
            this.exercise = excercise;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getId() {
            return id;
        }

        public void setId(int rowid) {
            this.id = rowid;
        }
    }
}
