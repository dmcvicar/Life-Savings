package lifesavings.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.sql.SQLException;

import lifesavings.db.User;
import lifesavings.db.UserDataSource;


public class EditProfileActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "com.example.cs480.lifesavings";

    private EditText userName;
    private EditText userAge;
    private  User currentUser;
    private RadioButton userMale;
    private RadioButton userFemale;
    private EditText userWeight;
    private EditText userHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent userInfo = getIntent();

        // Restore preferences

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        userName = (EditText) findViewById(R.id.name_edit_text);
        userAge = (EditText) findViewById(R.id.age_edit_text);
        userMale = (RadioButton) findViewById(R.id.male_gender_radbutton);
        userFemale = (RadioButton) findViewById(R.id.female_gender_radbutton);
        userHeight = (EditText) findViewById(R.id.height_edit_text);
        userWeight = (EditText) findViewById(R.id.weight_edit_text);

        if(userInfo.hasExtra("USER")) {
            currentUser = (User) userInfo.getSerializableExtra("USER");
            userName.setText(currentUser.getName());
            userAge.setText(currentUser.getAge());
            if (currentUser.getGender().equalsIgnoreCase("Male"))
            {
                userMale.setChecked(true);
                userFemale.setChecked(false);
            }
            else
            {
                userMale.setChecked(false);
                userFemale.setChecked(true);
            }
            userWeight.setText(currentUser.getWeight());
            userHeight.setText(currentUser.getHeight());
        }else {
            userName.setText(settings.getString("Name", ""));
            userAge.setText(settings.getString("Age", ""));
            userMale.setChecked(settings.getBoolean("Male", true));
            userFemale.setChecked(settings.getBoolean("Female", false));
            userWeight.setText(settings.getString("Weight", ""));
            userHeight.setText(settings.getString("Height", ""));
        }

    }

    public void infoSave(View view){
        Toast.makeText(getApplicationContext(), "Saved",
                Toast.LENGTH_LONG).show();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("Name",userName.getText().toString());
        editor.putString("Age",userAge.getText().toString());
        editor.putString("Weight",userWeight.getText().toString());
        editor.putString("Height",userHeight.getText().toString());

        //Radio buttons save
        editor.putBoolean("Male",userMale.isChecked());
        editor.putBoolean("Female",userFemale.isChecked());

        editor.commit();
        UserDataSource oldDB = new UserDataSource(this);
        try {
            oldDB.open();
            String gender;
            gender = "female";
            if(userMale.isChecked())
            {
                gender = "male";
            }
            oldDB.updateUser(currentUser.getUserid(),userName.getText().toString(),
                    Integer.parseInt(userAge.getText().toString()),Double.parseDouble(userWeight.getText().toString()),
                    Double.parseDouble(userHeight.getText().toString()),gender);

        }catch (SQLException e){
            String error =  e.getMessage().toString();
        }

    }

    public void infoCancel(View view){
        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
