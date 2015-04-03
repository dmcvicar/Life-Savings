package lifesavings.activities;

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


public class EditProfileActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "com.example.cs480.lifesavings";

    private EditText userName;
    private EditText userAge;

    private RadioButton userMale;
    private RadioButton userFemale;
    private EditText userWeight;
    private EditText userHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        userName = (EditText) findViewById(R.id.name_edit_text);
        userAge = (EditText) findViewById(R.id.age_edit_text);
        userMale = (RadioButton) findViewById(R.id.male_gender_radbutton);
        userFemale = (RadioButton) findViewById(R.id.female_gender_radbutton);
        userHeight = (EditText) findViewById(R.id.height_edit_text);
        userWeight = (EditText) findViewById(R.id.weight_edit_text);

        userName.setText(settings.getString("Name",""));
        userAge.setText(settings.getString("Age",""));
        userMale.setChecked(settings.getBoolean("Male",true));
        userFemale.setChecked(settings.getBoolean("Female",false));
        userWeight.setText(settings.getString("Weight",""));
        userHeight.setText(settings.getString("Height",""));


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

    }

    public void infoCancel(View view){
        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.home) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
