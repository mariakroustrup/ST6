package com.kol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.R.attr.width;

/**
 * Created by Maria on 05/05/2017.
 */

public class TRAENING extends AppCompatActivity{

        TextView textView, textView1;
        Button start, stop;// reset;
        long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
        int Seconds, Minutes, MilliSeconds;
        Handler handler;
        PopupWindow myPopUp;
    LinearLayout positionOfPopup;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.traening);


            textView = (TextView)findViewById(R.id.Timer);
            textView1 = (TextView)findViewById(R.id.Tid);
            start = (Button)findViewById(R.id.start);
            stop = (Button)findViewById(R.id.stop);

            handler = new Handler();


            start.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                }

            });

            stop.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);

                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View customView = inflater.inflate(R.layout.popupwindow1, null);

                    myPopUp = new PopupWindow(
                            customView,
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );

                    Button closePopUp = (Button) customView.findViewById(R.id.Fortryd);
                    closePopUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myPopUp.dismiss();
                        }

                    });
                    Button closePopUp1 = (Button) customView.findViewById(R.id.Bekræft);
                    closePopUp1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myPopUp.dismiss();
                            setContentView(R.layout.evaluering);
                        }

                    });


                    myPopUp.showAtLocation(positionOfPopup, Gravity.CENTER, 0, 0);
                }});}


    public Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + Minutes + ":" + String.format("%02d", Seconds) + ":" + String.format("%03d",MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };


    int result;

    public void ButtonOnClick(View view) {

        switch (view.getId()) {
            case R.id.mindre:
                result = 0;
                OnGone1(view);
                /*start(0);*/
                break;
            case R.id.stoerre:
                result = 1;
                OnGone1(view);
                /*start(1);*/
                break;
        }}

    public void OnGone1(View view) {
        Button en = (Button) findViewById(R.id.enhelbred);
        en.setEnabled(false);
        en.setClickable(false);
        Button to = (Button) findViewById(R.id.tohelbred);
        to.setEnabled(false);
        to.setClickable(false);

        Button videre = (Button) findViewById(R.id.Viderehelbred);
        videre.setVisibility(view.VISIBLE);
        videre.setEnabled(true);
        videre.setClickable(true);
    }


        }










