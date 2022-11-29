package se.kth.anderslm.ttt.utils;


import static se.kth.anderslm.ttt.model.TicLogic.SIZE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import se.kth.anderslm.ttt.R;
import se.kth.anderslm.ttt.utils.TextToSpeechUtil;

public class RunTimer {

    private Timer msgTimer;
    private Handler handler;

    private int noOfMsgs;

    private TextView msgView;


    /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msgView = (TextView) findViewById(R.id.msg_view);

        msgTimer = null;
        handler = new Handler();
    }

    /* // cancel timer and pending tasks when user leaves this activity
    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
    } */

    // the task to execute periodically
    private class MsgTimerTask extends TimerTask {
        private Handler handler;
        private int events;
        private Context context;
        private String gameType;
        private TextToSpeechUtil textToSpeechUtil;
        private MsgTimerTask(Context context, int events, String gameType) {
            this.context = context;
            textToSpeechUtil = new TextToSpeechUtil();
            textToSpeechUtil.initialize(this.context);
            handler = new Handler();
            this.events = events;
            this.gameType = gameType;
        }


        public void run() {
            noOfMsgs++;
            ImageView imageView = loadReferencesToImageViews();

            String msg = "Messge number " + noOfMsgs;
            Log.i("MsgTask", msg);
            // post message to main thread
            String[] array = this.context.getResources().getStringArray(R.array.letters);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(Objects.equals(gameType, "Audio")) {
                    String randomStr = array[new Random().nextInt(array.length)];
                    Log.e("Sound","Played");
                    textToSpeechUtil.speakNow(randomStr); }
                    else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cross));
                    new CountDownTimer(2000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            // do something after 1s
                        }

                        @Override
                        public void onFinish() {

                            imageView.setImageDrawable(null);
                        }

                    }.start(); }

                }
            });
            if (noOfMsgs > events) {
                cancelTimer();
            }
        }
        // load references to, and add listener on, all image views
        private ImageView loadReferencesToImageViews() {
            // well, it would probably be easier (for a larger matrix) to create
            // the views in Java code and then add them to the appropriate layout
            ImageView[] imgViews = new ImageView[SIZE * SIZE];
            imgViews[0] = (ImageView) ((Activity) context).findViewById(R.id.imageView0);
            imgViews[1] = (ImageView) ((Activity) context).findViewById(R.id.imageView1);
            imgViews[2] = (ImageView) ((Activity) context).findViewById(R.id.imageView2);
            imgViews[3] = (ImageView) ((Activity) context).findViewById(R.id.imageView3);
            imgViews[4] = (ImageView) ((Activity) context).findViewById(R.id.imageView4);
            imgViews[5] = (ImageView) ((Activity) context).findViewById(R.id.imageView5);
            imgViews[6] = (ImageView) ((Activity) context).findViewById(R.id.imageView6);
            imgViews[7] = (ImageView) ((Activity) context).findViewById(R.id.imageView7);
            imgViews[8] = (ImageView) ((Activity) context).findViewById(R.id.imageView8);

            Random random = new Random();
            int randPosition = random.nextInt(9 ) ;
            return imgViews[randPosition];

        }
    }



    public boolean startTimer(Context context, int sec, int events, int nValue, String gameType) {

        if (msgTimer == null) { // first, check if task is already running
            noOfMsgs = 0;
            msgTimer = new Timer();
            // schedule a new task: task , delay, period (milliseconds)
            msgTimer.schedule(new MsgTimerTask(context, events, gameType), 1000, sec * 1000);
            return true; // new task started
        }
        return false;
    }

    public boolean cancelTimer() {
        if (msgTimer != null) {
            msgTimer.cancel();
            msgTimer = null;
            Log.i("MsgTask", "timer canceled");
            return true; // task canceled
        }
        return false;
    }

    /*

    // start a new timer and task on button clicked
    public void onStartButtonClicked(View view) {
        boolean started = startTimer();
        if (!started) {
            Toast.makeText(this, "Task already running", Toast.LENGTH_SHORT).show();
        }
    } */
    public void onStopButtonClicked(View view) {
        cancelTimer();
    }
}