package lifesavings.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import lifesavings.activities.R;
import lifesavings.db.ExerciseDataSource;

/**
 * Created by Nick on 4/23/2015.
 */
public class MyAdapter extends ArrayAdapter<ExerciseDataSource.Exercise> {
    private final Context context;
    private final List<ExerciseDataSource.Exercise> exerciseList;

    public MyAdapter(Context context, List<ExerciseDataSource.Exercise> exerciseList) {
        super(context, R.layout.exercise_list_item, exerciseList);

        this.context = context;
        this.exerciseList = exerciseList;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View exerciseView = inflater.inflate(R.layout.exercise_list_item, parent, false);

        TextView timeView = (TextView) exerciseView.findViewById(R.id.ex_time);
        TextView typeView = (TextView) exerciseView.findViewById(R.id.ex_type);
        TextView durView = (TextView) exerciseView.findViewById(R.id.ex_duration);
        TextView exertView = (TextView) exerciseView.findViewById(R.id.ex_exertion);

        timeView.setText(exerciseList.get(position).getTime());
        typeView.append(exerciseList.get(position).getExcercise());
        durView.append(Double.toString(exerciseList.get(position).getDuration()));
        exertView.append(Integer.toString(exerciseList.get(position).getExertion()));

        return exerciseView;
    }


}
