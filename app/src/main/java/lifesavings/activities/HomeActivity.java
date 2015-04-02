package lifesavings.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSavingsClicked(View view) {
        Intent intent = new Intent(this, SavingsProfile.class);
        startActivity(intent);
    }


    public void onEditProfileClicked(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    public void onSignOutClicked(View view) {
        Intent intent = new Intent(this, SelectUserActivity.class);
        startActivity(intent);
    }

    public void onRecordExerciseClicked(View view) {
        Intent intent = new Intent(this, RecordExerciseActivity.class);
        startActivity(intent);
    }

    public void onEditExerciseClicked(View view) {
        Intent intent = new Intent(this, EditExerciseActivity.class);
        startActivity(intent);
    }
}
