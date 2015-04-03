package lifesavings.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.sql.SQLException;
import java.util.ArrayList;

import lifesavings.db.ExerciseDataSource;
import lifesavings.db.User;
import lifesavings.view.SlidingTabLayout;


public class HomeActivity extends ActionBarActivity implements ProfileSavingsFragment.OnFragmentInteractionListener,
                                                               ExerciseEditFragment.OnFragmentInteractionListener,
                                                               ExerciseRecordFragment.OnFragmentInteractionListener{

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private LifeSavingsTabsViewPagerAdapter myViewPageAdapter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentUser = (User)getIntent().getSerializableExtra("USER");

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // create a fragment list in order.
        fragments = new ArrayList<>();
        fragments.add(new ExerciseEditFragment());
        fragments.add(new ExerciseRecordFragment());
        fragments.add(new ProfileSavingsFragment());

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


        StringBuilder date_time = new StringBuilder();

        date_time.append(dp.getDayOfMonth());
        date_time.append(dp.getMonth());
        date_time.append(dp.getYear());
        date_time.append(tp.getCurrentHour());
        date_time.append(tp.getCurrentMinute());

        long dt = Long.parseLong(date_time.toString().trim());
        double duration = Double.parseDouble(et.getText().toString());
        String exercise = spinner.getSelectedItem().toString();

        ExerciseDataSource dataSource = new ExerciseDataSource(this);
        dataSource.open();
        dataSource.createExcercise(101101,dt,duration,exercise);
        dataSource.close();
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
    //</editor-fold>
}
