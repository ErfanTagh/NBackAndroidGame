package se.kth.anderslm.ttt;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 * Information on the current settings, like visual or audio, value of n, time between events and
 * number of events in a round.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        // Inflate the layout for this fragment
        Spinner spinCountry;
        spinCountry= (Spinner) view.findViewById(R.id.spinner);//fetch the spinner from layout file

        EditText nValue = (EditText) view.findViewById(R.id.n_value);
        EditText timeEvents = (EditText) view.findViewById(R.id.time_events);
        EditText playNumber = (EditText) view.findViewById(R.id.play_number_edit_text);






        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.list_array));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCountry.setAdapter(adapter);
//if you want to set any action you can do in this listener
        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        //Clean project when you have R.id errors as shown bellow
        Button applyButton = (Button) view.findViewById(R.id.apply_btn);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor editor = preferences.edit();
                int nValueInt =  Integer.parseInt(nValue.getText().toString());
                int timeEventsInt =  Integer.parseInt(timeEvents.getText().toString());
                int playNumInt =  Integer.parseInt(playNumber.getText().toString());

                //Either N-back by audio or position
                String gameType = spinCountry.getSelectedItem().toString();

                editor.putString("gametype", gameType);
                editor.putInt("nvalue", nValueInt);
                editor.putInt("timeevents", timeEventsInt);
                editor.putInt("playnum", playNumInt);
                editor.apply();

                //To check if settings were applied
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Setting changes applied", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        return view;
    }
}