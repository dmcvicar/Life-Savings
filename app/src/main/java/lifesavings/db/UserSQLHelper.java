package lifesavings.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dave on 4/2/15.
 */

public class UserSQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_BMI = "bmi";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_BFP = "bfp";



    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_USERS
                    + "(" + COLUMN_USERID + " INTEGER PRIMARY KEY, "
                    + COLUMN_NAME + " TEXT NOT NULL, "
                    + COLUMN_GENDER + " TEXT NOT NULL, "
                    + COLUMN_WEIGHT + " INTEGER NOT NULL, "
                    + COLUMN_HEIGHT + " INTEGER NOT NULL,"
                    + COLUMN_BMI + " INTEGER NOT NULL,"
                    + COLUMN_CATEGORY + " INTEGER NOT NULL,"
                    + COLUMN_BFP + " REAL NOT NULL);";

    public UserSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(UserSQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}