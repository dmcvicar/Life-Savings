package lifesavings.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dave on 4/2/15.
 */

public class MoneySQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_MONEY = "money";
    public static final String COLUMN_ID = "rowid";
    public static final String COLUMN_EXCERCISE = "excercise";
    public static final String COLUMN_MONEY = "money";


    private static final String DATABASE_NAME = "money.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_MONEY
                    + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_EXCERCISE + " TEXT NOT NULL,"
                    + COLUMN_MONEY + " REAL NOT NULL);";

    public MoneySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MoneySQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONEY);
        onCreate(db);
    }
}