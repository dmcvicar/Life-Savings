package lifesavings.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lifesavings.db.ExerciseDataSource;
import lifesavings.db.User;
import lifesavings.view.SlidingTabLayout;


public class HomeActivity extends ActionBarActivity implements ProfileSavingsFragment.OnFragmentInteractionListener,
                                                               ExerciseEditFragment.OnFragmentInteractionListener,
                                                               ExerciseRecordFragment.OnFragmentInteractionListener,
                                                               ExerciseHistoryFragment.OnFragmentInteractionListener,
                                                               SensorEventListener {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private LifeSavingsTabsViewPagerAdapter myViewPageAdapter;
    private User currentUser;
    private SensorManager sensorManager;
    private int stepCount;
    private int lastCountSubmitted; //last step Count submitted to the database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Sensor stepCounter;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stepCount = 0;
        lastCountSubmitted = 0;

        currentUser = User.fromArrayList(getIntent().getStringArrayListExtra("USER"));
        setTitle("User: " + currentUser.getName());

        //set sensorManager
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepCounter != null)
        {
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

            //setup sliding tab
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // create a fragment list in order.
        fragments = new ArrayList<>();
        fragments.add(new ExerciseEditFragment());
        fragments.add(new ExerciseRecordFragment());
        fragments.add(new ProfileSavingsFragment());
        fragments.add(ExerciseHistoryFragment.newInstance(currentUser));

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles)
        // and ViewPager (different pages of fragment) together.
        myViewPageAdapter = new LifeSavingsTabsViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myViewPageAdapter);

        // make sure the tabs are equally spaced.
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);


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
        /*if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }*/
        if (id == R.id.edit_profile) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            intent.putStringArrayListExtra("USER",currentUser.toArrayList());
            startActivity(intent);
            return true;
        }
        else if (id == R.id.sign_out) {
            Intent intent = new Intent(this, SelectUserActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Fragment UI listeners below

    public void onSetTime(View view){
        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        if(tp.getVisibility() == View.VISIBLE)
            tp.setVisibility(View.GONE);
        else
            tp.setVisibility(View.VISIBLE);

    }

    public void onSetDate(View view){
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        if(dp.getVisibility() == View.VISIBLE)
            dp.setVisibility(View.GONE);
        else
            dp.setVisibility(View.VISIBLE);
    }

    public void onSubmitEdit(View view) throws SQLException {
        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        EditText et = (EditText) findViewById(R.id.duration);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        double duration;


        StringBuilder date_time = new StringBuilder();


        date_time.append(dp.getMonth() + "/");
        date_time.append(dp.getDayOfMonth() + "/");
        date_time.append(dp.getYear() + " ");
        date_time.append(tp.getCurrentHour() + ":");
        date_time.append(tp.getCurrentMinute());

        String dt = date_time.toString();
        String exercise = spinner.getSelectedItem().toString();
        if(useStep(exercise) == 1)
        {
            duration = stepCount - lastCountSubmitted;
            lastCountSubmitted = stepCount;
        }
        else {
            duration = Double.parseDouble(et.getText().toString());
        }

        ExerciseDataSource dataSource = new ExerciseDataSource(this);
        dataSource.open();
        dataSource.createExcercise(currentUser.getUserid(),dt,duration,exercise);
        dataSource.close();
    }

    public List<ExerciseDataSource.Exercise> grabUserExercises(int user_id)throws SQLException{
        List<ExerciseDataSource.Exercise> exercise;

        ExerciseDataSource dataSource = new ExerciseDataSource(this);
        dataSource.open();
        exercise = dataSource.getAllExcercises(user_id);
        dataSource.close();

        return exercise;
    }

    //<editor-fold desc="Implementing Interfaces">
    public void onFragmentInteractionSavings(Uri uri)
    {

    }
    public void onFragmentInteractionEdit(Uri uri)
    {

    }
    public void onFragmentInteractionRecord(Uri uri)
    {

    }
    public void onFragmentInteractionUser(String str){

    }
    //</editor-fold>

    //Exercise Handler
    //Returns
    private int useStep(String exercise){
        if(exercise.equalsIgnoreCase("walking")){
            return 1;
        }
        else if(exercise.equalsIgnoreCase("running")){
            return 1;
        }
        else if(exercise.equalsIgnoreCase("jogging")){
            return 1;
        }
        else if(exercise.equalsIgnoreCase("rowing")){
            return 0;
        }
        else if(exercise.equalsIgnoreCase("swimming")){
            return 0;
        }
        else if(exercise.equalsIgnoreCase("hiking")){
            return 0;
        }
        else if(exercise.equalsIgnoreCase("biking")){
            return 0;
        }

        return 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        stepCount = (int) event.values[0];
    }


        @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
