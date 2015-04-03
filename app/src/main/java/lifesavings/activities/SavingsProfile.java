package lifesavings.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import lifesavings.db.Excercise;
import lifesavings.db.ExcerciseDataSource;
import lifesavings.db.Money;
import lifesavings.db.MoneyDataSource;


public class SavingsProfile extends ActionBarActivity {
    private ExcerciseDataSource exerciseConnect;
    private MoneyDataSource moneyConnect;
    private ListView listView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);
        double total = 0;

        listView = (ListView) findViewById(R.id.exercise_list);
        textView = (TextView) findViewById(R.id.running_total);

        exerciseConnect = new ExcerciseDataSource(this);
        moneyConnect = new MoneyDataSource(this);
        try {
            exerciseConnect.open();
            moneyConnect.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Excercise> exercises = exerciseConnect.getAllExcercises();
        List<Money> moneys = moneyConnect.getAllMoneys();

        //construct exercise strings
        String[] valuesEx = new String[exercises.size() + 1];
        valuesEx[0] = "Time\tExercise\tDuration\tMoney$aved";

        for (int i = 0; 0 < valuesEx.length; i++) {
            for (int j = 0; j < moneys.size(); j++) {
                Excercise cur = exercises.get(i);
                double cash = 0;
                if (cur.getExcercise().equals(moneys.get(j).getExcercise())) {
                    valuesEx[i + 1] = cur.getTime() + "\t" + cur.getExcercise() + "\t" + cur.getDuration();
                    cash = cur.getDuration() * moneys.get(j).getMoney();
                    valuesEx[i + 1] = valuesEx[i] + "\t" + cash + "\n";
                    total += cash;
                }
            }
        }
        //Use an adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, valuesEx);

        //Set total
        textView.setText("Your lifetime savings are: " + Double.toString(total));
        //Apply adapter
        listView.setAdapter(adapter);
    }

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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
