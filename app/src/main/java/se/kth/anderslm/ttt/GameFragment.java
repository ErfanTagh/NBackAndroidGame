package se.kth.anderslm.ttt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;
import java.util.Timer;

import android.speech.tts.TextToSpeech;


import se.kth.anderslm.ttt.utils.RunTimer;
import se.kth.anderslm.ttt.utils.TextToSpeechUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Timer msgTimer;
    private Handler handler;
    private int btnPressed;
    private int btnMustBePressed;
    private static GameFragment instance;
    public boolean btnShouldBePressed = false;
    TextToSpeech textToSpeech;

    public GameFragment() {
        // Required empty public constructor
    }


  /*  @Override
    public void onResume() {
        super.onResume();

    } */
    @Override
    public void onPause() {
        super.onPause();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static GameFragment getInstance(){
        return instance;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        btnPressed = 0;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void changeScoreStatus(){

        if(btnShouldBePressed){

            btnPressed++;
        }

    }

    public void checkClickedBtn(){

        btnMustBePressed += 1;

        btnShouldBePressed = true;

    }

    public void openResultsActivity(){

        Log.e( "btnpresssedes: ", btnPressed+" btn mussed be: " + btnMustBePressed);
        float f = (float) btnPressed / btnMustBePressed;
        Intent intent = new Intent(getActivity(), MainActivity2.class).putExtra("userScore", f * 100);
        startActivity(intent);

    }


    public void regenerateBtnShouldBePressed(){

        btnShouldBePressed=false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);

        instance = this;
        btnPressed = 0;
        btnMustBePressed = 0;
        btnShouldBePressed=false;
        Button button = (Button) view.findViewById(R.id.startBtn);
        Button soundButton = (Button) view.findViewById(R.id.soundBtn);
            // well, it would probably be easier (for a larger matrix) to create
            // the views in Java code and then add them to the appropriate layout
            ImageView[] imgViews = new ImageView[9];
            imgViews[0] = view.findViewById(R.id.imageView0);
            imgViews[1] = view.findViewById(R.id.imageView1);
            imgViews[2] = view.findViewById(R.id.imageView2);
            imgViews[3] = view.findViewById(R.id.imageView3);
            imgViews[4] = view.findViewById(R.id.imageView4);
            imgViews[5] = view.findViewById(R.id.imageView5);
            imgViews[6] = view.findViewById(R.id.imageView6);
            imgViews[7] = view.findViewById(R.id.imageView7);
            imgViews[8] = view.findViewById(R.id.imageView8);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String name = preferences.getString("gametype", "Audio");
        Log.e("gametypevalue: ", name );
        soundButton.setText(name);
        int nValue = preferences.getInt("nvalue", 1);
        Log.e( "nValue: ",nValue + "" );


        int timeEvents = preferences.getInt("timeevents", 3);
        int playNum = preferences.getInt("playnum", 10);

        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });


        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                changeScoreStatus();


            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  String[] array = getActivity().getResources().getStringArray(R.array.letters); */
                RunTimer runTimer = new RunTimer();
                runTimer.startTimer(view.getContext(), timeEvents, playNum, nValue, name);



                /*  handler.postDelayed(runnable = new Runnable() {
                    public void run() {
                        String randomStr = array[new Random().nextInt(array.length)];
                        Log.e("Dila","Erfan");
                        textToSpeechUtil.speakNow(randomStr);
                        handler.postDelayed(runnable, delay);
                    }
                }, delay); */


            }
        });
        return view;
    }
}