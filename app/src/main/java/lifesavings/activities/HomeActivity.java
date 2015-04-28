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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    private int totalStepCount; //total steps taken since last reboot.
    private Sensor stepCounter;
    private boolean isPaused;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        isPaused = false;

        currentUser = User.fromArrayList(getIntent().getStringArrayListExtra("USER"));
        setTitle("User: " + currentUser.getName());

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

    public void onStartRecord(View view){
        stepCount = 0;

        TextView stepView = (TextView) findViewById(R.id.step_count);
        stepView.setText(Integer.toString(stepCount));
        setRecordButtons(1);
        //set sensorManager

        if (stepCounter != null)
        {
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI);

        }
        else
        {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    public void onPauseRecord(View view){
        if(isPaused) {
            isPaused = false;
            setRecordButtons(3);
        }
        else {
            isPaused = true;
            setRecordButtons(2);
        }

    }

    public void onStopRecord(View view)throws SQLException{
        sensorManager.unregisterListener(this, stepCounter);
        setRecordButtons(4);

        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);


        StringBuilder date_time = new StringBuilder();


        date_time.append(dp.getMonth() + "/");
        date_time.append(dp.getDayOfMonth() + "/");
        date_time.append(dp.getYear() + " ");
        date_time.append(tp.getCurrentHour() + ":");
        date_time.append(tp.getCurrentMinute());

        String dt = date_time.toString();

        ExerciseDataSource dataSource = new ExerciseDataSource(this);
        dataSource.open();
        dataSource.createExcercise(currentUser.getUserid(),dt,-1,"TODO: Implement Accelerometer",stepCount);
        dataSource.close();

        Toast.makeText(this, "Exercise Recorded", Toast.LENGTH_LONG).show();

    }


    private void setRecordButtons(int option)
    {
        Button record= (Button) findViewById(R.id.Record);
        Button pause= (Button) findViewById(R.id.Pause);
        Button stop= (Button) findViewById(R.id.Stop);

        switch(option){
            case 1:
                record.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
                break;
            case 2:
                record.setVisibility(View.GONE);
                pause.setText(R.string.resume);
                stop.setVisibility(View.GONE);
                break;
            case 3:
                record.setVisibility(View.GONE);
                pause.setText(R.string.pause_exercise);
                stop.setVisibility(View.VISIBLE);
                break;
            case 4:
                record.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                stop.setVisibility(View.GONE);
                break;
        }
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
        EditText sel_duration = (EditText) findViewById(R.id.duration);
        EditText sel_exertion=(EditText) findViewById(R.id.exertion);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        double duration;
        int exertion;


        StringBuilder date_time = new StringBuilder();


        date_time.append(dp.getMonth() + "/");
        date_time.append(dp.getDayOfMonth() + "/");
        date_time.append(dp.getYear() + " ");
        date_time.append(tp.getCurrentHour() + ":");
        date_time.append(tp.getCurrentMinute());

        String dt = date_time.toString();
        String exercise = spinner.getSelectedItem().toString();

        duration = Double.parseDouble(sel_duration.getText().toString());
        exertion = Integer.parseInt(sel_exertion.getText().toString());

        ExerciseDataSource dataSource = new ExerciseDataSource(this);
        dataSource.open();
        dataSource.createExcercise(currentUser.getUserid(),dt,duration,exercise,exertion);
        dataSource.close();


        Toast.makeText(this, "Exercise Saved", Toast.LENGTH_LONG).show();
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
        if(!isPaused) {
            TextView stepView = (TextView) findViewById(R.id.step_count);
            stepView.setText(Integer.toString(++stepCount));
        }
    }


        @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }



}
