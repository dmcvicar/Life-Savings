package lifesavings.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import lifesavings.db.UserDataSource;
import lifesavings.db.User;


public class EditProfileActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "com.example.cs480.lifesavings";

    private EditText userName;
    private EditText userAge;
    private RadioButton userMale;
    private RadioButton userFemale;
    private EditText userWeight;
    private EditText userHeight;
    private UserDataSource oldDB;
    private User primary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        oldDB = new UserDataSource(this);
        try {
            oldDB.open();
        }catch(Exception e){}

        userName = (EditText) findViewById(R.id.name_edit_text);
        userAge = (EditText) findViewById(R.id.age_edit_text);
        userMale = (RadioButton) findViewById(R.id.male_gender_radbutton);
        userFemale = (RadioButton) findViewById(R.id.female_gender_radbutton);
        userHeight = (EditText) findViewById(R.id.height_edit_text);
        userWeight = (EditText) findViewById(R.id.weight_edit_text);

        if(getIntent().hasExtra("USER")) {
            primary =  User.fromArrayList(getIntent().getStringArrayListExtra("USER"));
            userName.setText(primary.getName());
            userAge.setText("" + primary.getAge());
            if (primary.getGender().equalsIgnoreCase("male")) {
                userMale.setChecked(true);
                userFemale.setChecked(false);
            } else {
                userMale.setChecked(false);
                userFemale.setChecked(true);
            }
            userWeight.setText("" + primary.getWeight());
            userHeight.setText("" + primary.getHeight());
        }else {
            userName.setText("");
            userAge.setText("");
            userMale.setChecked(false);
            userFemale.setChecked(true);
            userWeight.setText("");
            userHeight.setText("");
        }
    }

    public void infoSave(View view){
        User currentUser = new User();
        Intent intent = new Intent(this,HomeActivity.class);
        if(getIntent().hasExtra("USER")) {
            currentUser = getUserFromInput(User.fromArrayList(getIntent().getStringArrayListExtra("USER")).getUserid());
            oldDB.updateUser(currentUser);
            intent.putStringArrayListExtra("USER",currentUser.toArrayList());
        }else{
            currentUser = getUserFromInput(-1);
            currentUser = oldDB.createUser(currentUser);
            intent.putStringArrayListExtra("USER",currentUser.toArrayList());
        }
        startActivity(intent);
    }

    public void infoCancel(View view){
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(primary != null)
            getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }*/
        if (id == R.id.home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putStringArrayListExtra("USER",primary.toArrayList());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private User getUserFromInput(int userId) {
        String gender;
        gender = "female";
        if(userMale.isChecked())
        {
            gender = "male";
        }
        User user = new User(userId,
                userName.getText().toString(),
                gender,
                Integer.parseInt(userWeight.getText().toString()),
                Integer.parseInt(userHeight.getText().toString()),
                Integer.parseInt(userAge.getText().toString()));
        return user;
    }
}
