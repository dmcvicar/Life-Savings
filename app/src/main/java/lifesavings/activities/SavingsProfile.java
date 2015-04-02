package lifesavings.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SavingsProfile extends ActionBarActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings_profile);

        listView = (ListView) findViewById(R.id.exercise_list);

        //Dumby values for the list
        String[] saved = new String[]{"Exercise performed this week:\n",
                "Steps taken: 57   Money Saved:$0.03",
                "Steps walked: 30   Money Saved:$0.01",
                "Steps ran: 27   Money Saved:$0.02",
                "Reps with 50lb weights: 75\nMoney saved:$0.10"
        };

        //Define an adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,saved);

        //assign the adapter
        listView.setAdapter(adapter);

        //Assign adapter to ListView
        listView.setAdapter(adapter);

        //We can add on click listeners to these btdubs
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_stats, menu);
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
    }*/
}
