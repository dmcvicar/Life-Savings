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
public class ExcerciseDataSource {

    private SQLiteDatabase database;
    private ExcerciseSQLHelper helper;
    private String[] allColumns = { ExcerciseSQLHelper.COLUMN_ID, ExcerciseSQLHelper.COLUMN_USERID, ExcerciseSQLHelper.COLUMN_TIME, ExcerciseSQLHelper.COLUMN_DURATION, ExcerciseSQLHelper.COLUMN_EXCERCISE};

    public ExcerciseDataSource(Context context) {
        helper = new ExcerciseSQLHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public Excercise createExcercise(int userid, int time, double duration, String excercise) {
        ContentValues values = new ContentValues();
        values.put(ExcerciseSQLHelper.COLUMN_USERID, userid);
        values.put(ExcerciseSQLHelper.COLUMN_TIME,time);
        values.put(ExcerciseSQLHelper.COLUMN_DURATION,duration);
        values.put(ExcerciseSQLHelper.COLUMN_EXCERCISE,excercise);

        long insertId = database.insert(ExcerciseSQLHelper.TABLE_EXCERCISE, null, values);
        Cursor cursor = database.query(ExcerciseSQLHelper.TABLE_EXCERCISE, allColumns, ExcerciseSQLHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Excercise newExcercise = cursorToExcercise(cursor);
        cursor.close();
        return newExcercise;
    }

    public void deleteExcercise(Excercise Excercise) {
        long id = Excercise.getUserid();
        database.delete(ExcerciseSQLHelper.TABLE_EXCERCISE, ExcerciseSQLHelper.COLUMN_USERID
                + " = " + id, null);
    }

    public List<Excercise> getAllExcercises() {
        List<Excercise> Excercises = new ArrayList<Excercise>();

        Cursor cursor = database.query(ExcerciseSQLHelper.TABLE_EXCERCISE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Excercise Excercise = cursorToExcercise(cursor);
            Excercises.add(Excercise);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return Excercises;
    }

    private Excercise cursorToExcercise(Cursor cursor) {
        Excercise Excercise = new Excercise();
        Excercise.setId(cursor.getInt(0));
        Excercise.setUserid(cursor.getInt(1));
        Excercise.setTime(cursor.getInt(2));
        Excercise.setDuration(cursor.getDouble(3));
        Excercise.setExcercise(cursor.getString(4));
        return Excercise;
    }

}
