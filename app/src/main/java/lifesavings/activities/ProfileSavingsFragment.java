package lifesavings.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import lifesavings.db.Excercise;
import lifesavings.db.ExcerciseDataSource;
import lifesavings.db.Money;
import lifesavings.db.MoneyDataSource;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileSavingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileSavingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSavingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSavings.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSavingsFragment newInstance(String param1, String param2) {
        ProfileSavingsFragment fragment = new ProfileSavingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileSavingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_savings, container,
                false);
        double total = 0;
        ExcerciseDataSource exerciseConnect;
        MoneyDataSource moneyConnect;
        ListView listView;
        TextView textView;

        listView = (ListView) rootView.findViewById(R.id.exercise_list);
        textView = (TextView) rootView.findViewById(R.id.running_total);

        exerciseConnect = new ExcerciseDataSource(getActivity());
        moneyConnect = new MoneyDataSource(getActivity());
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

        for (int i = 0; i < valuesEx.length; i++) {
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1, valuesEx);

        //Set total
        textView.setText("Your lifetime savings are: " + Double.toString(total));
        //Apply adapter
        listView.setAdapter(adapter);
        return inflater.inflate(R.layout.fragment_profile_savings, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionSavings(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteractionSavings(Uri uri);
    }

}
