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
public class MoneyDataSource {

    private SQLiteDatabase database;
    private MoneySQLHelper helper;
    private String[] allColumns = { MoneySQLHelper.COLUMN_ID, MoneySQLHelper.COLUMN_EXCERCISE, MoneySQLHelper.COLUMN_MONEY};

    public MoneyDataSource(Context context) {
        helper = new MoneySQLHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public Money createMoney(int excercise, int money) {
        ContentValues values = new ContentValues();
        values.put(MoneySQLHelper.COLUMN_EXCERCISE, excercise);
        values.put(MoneySQLHelper.COLUMN_MONEY,money);

        long insertId = database.insert(MoneySQLHelper.TABLE_MONEY, null, values);
        Cursor cursor = database.query(MoneySQLHelper.TABLE_MONEY, allColumns, MoneySQLHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Money newMoney = cursorToMoney(cursor);
        cursor.close();
        return newMoney;
    }

    public void deleteMoney(Money Money) {
        int id = Money.getId();
        database.delete(MoneySQLHelper.TABLE_MONEY, MoneySQLHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Money> getAllMoneys() {
        List<Money> Moneys = new ArrayList<Money>();

        Cursor cursor = database.query(MoneySQLHelper.TABLE_MONEY,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Money Money = cursorToMoney(cursor);
            Moneys.add(Money);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return Moneys;
    }

    private Money cursorToMoney(Cursor cursor) {
        Money Money = new Money();
        Money.setId(cursor.getInt(0));
        Money.setExcercise(cursor.getString(1));
        Money.setMoney(cursor.getInt(2));
        return Money;
    }

}
