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

import java.util.ArrayList;

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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.edit_profile) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
