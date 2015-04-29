package lifesavings.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.net.UnknownServiceException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 4/2/15.
 */
public class UserDataSource {
    private static int autoId;
    private SQLiteDatabase database;
    private UserSQLHelper helper;
    private String[] allColumns = { UserSQLHelper.COLUMN_USERID, UserSQLHelper.COLUMN_NAME, UserSQLHelper.COLUMN_GENDER, UserSQLHelper.COLUMN_WEIGHT, UserSQLHelper.COLUMN_HEIGHT, UserSQLHelper.COLUMN_BMI, UserSQLHelper.COLUMN_CATEGORY, UserSQLHelper.COLUMN_BFP};

    public UserDataSource(Context context) {
        helper = new UserSQLHelper(context);
        autoId = 0;
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public User createUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserSQLHelper.COLUMN_NAME,user.getName());
        values.put(UserSQLHelper.COLUMN_AGE, user.getAge());
        values.put(UserSQLHelper.COLUMN_GENDER,user.getGender());
        values.put(UserSQLHelper.COLUMN_WEIGHT,user.getWeight());
        values.put(UserSQLHelper.COLUMN_HEIGHT,user.getHeight());
        values.put(UserSQLHelper.COLUMN_BMI,user.getBmi());
        values.put(UserSQLHelper.COLUMN_CATEGORY,user.getCategory());
        values.put(UserSQLHelper.COLUMN_BFP,user.getBfp());
        values.put(UserSQLHelper.COLUMN_ICON_PATH,user.getIconPath());

        long insertId = database.insert(UserSQLHelper.TABLE_USERS, null, values);
        Cursor cursor = database.query(UserSQLHelper.TABLE_USERS, allColumns, UserSQLHelper.COLUMN_USERID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        User newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;
    }

    public void updateUser(User user){
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("name", user.getName());
        dataToInsert.put("age", user.getAge());
        dataToInsert.put("weight", user.getWeight());
        dataToInsert.put("height", user.getHeight());
        dataToInsert.put("gender", user.getGender());
        String where = "userid=?";
        String[] whereArgs = new String[] {String.valueOf(user.getUserid())};
        try{
            database.update(UserSQLHelper.TABLE_USERS, dataToInsert,where, whereArgs);
        }
        catch (Exception e){
            String error =  e.getMessage().toString();
        }
    }
    public void deleteUser(User User) {
        long id = User.getUserid();
        database.delete(UserSQLHelper.TABLE_USERS, UserSQLHelper.COLUMN_USERID
                + " = " + id, null);

    }
    public List<User> getAllUsers() {
        List<User> Users = new ArrayList<User>();

        Cursor cursor = database.query(UserSQLHelper.TABLE_USERS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User User = cursorToUser(cursor);
            Users.add(User);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return Users;
    }

    private User cursorToUser(Cursor cursor) {
        User User = new User();
        User.setUserid(cursor.getInt(0));
        User.setName(cursor.getString(1));
        User.setGender(cursor.getString(2));
        User.setWeight(cursor.getInt(3));
        User.setHeight(cursor.getInt(4));
        User.setBmi(cursor.getInt(5));
        User.setCategory(cursor.getInt(6));
        User.setBfp(cursor.getDouble(7));
        return User;
    }

}
