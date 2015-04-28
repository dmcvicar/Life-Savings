package lifesavings.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dave on 4/2/15.
 */

public class ExerciseSQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXCERCISE = "exercise";
    public static final String COLUMN_ID = "rowid";
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_EXCERCISE = "excercise";
    public static final String COLUMN_EXERTION = "exertion";


    private static final String DATABASE_NAME = "exercise.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_EXCERCISE
                    + " (" + COLUMN_ID + "INTEGER PRIMARY KEY, "
                    + COLUMN_USERID + " INTEGER NOT NULL,"
                    + COLUMN_TIME + " TEXT NOT NULL,"
                    + COLUMN_DURATION + " INTEGER NOT NULL, "
                    + COLUMN_EXCERCISE + " TEXT NOT NULL,"
                    + COLUMN_EXERTION + " INTEGER NOT NULL);";

    public ExerciseSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ExerciseSQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXCERCISE);
        onCreate(db);
    }
}