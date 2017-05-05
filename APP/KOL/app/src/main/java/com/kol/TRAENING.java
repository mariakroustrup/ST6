package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Maria on 05/05/2017.
 */

public class TRAENING extends AppCompatActivity{

        TextView textView, textView1;
        Button start, stop;// reset;
        long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
        int Seconds, Minutes, MilliSeconds;
        Handler handler;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.traening);


            textView = (TextView)findViewById(R.id.Timer);
            textView1 = (TextView)findViewById(R.id.Tid);
            start = (Button)findViewById(R.id.start);
            stop = (Button)findViewById(R.id.stop);
            //reset = (Button)findViewById(R.id.buttonReset);
            //listView = (ListView)findViewById(R.id.Listview1);

            handler = new Handler();




            //ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        /*adapter = new ArrayAdapter<String>(traening.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );
*/
            //listView.setAdapter(adapter);


            start.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);

                    //reset.setEnabled(false);
                }

            });



            stop.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);
                    startActivity(new Intent(TRAENING.this, POP1.class));

                    //reset.setEnabled(true);

                    //textView1.setText("" + Minutes + ":" + String.format("%02d", Seconds) + ":" + String.format("%03d",MilliSeconds));

                }


            });


        }




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




    }



    /* TextView  Timer, km, bpm;
    Button start, stop;
    long MilisecondTime, StartTime, TimeBuff,UpdateTime = 0L;
    int Seconds, Minutes, MiliSeconds;
    Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traening);
        int HELBRED = getIntent().getExtras().getInt("int_value");

        Timer = (TextView)findViewById(R.id.Timer);
        km = (TextView)findViewById(R.id.km);
        bpm = (TextView)findViewById(R.id.bpm);
        start = (Button) findViewById(R.id.start);
        stop =(Button)findViewById(R.id.stop);

        handler = new Handler();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
            }
        });

        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimeBuff += MilisecondTime;
                handler.removeCallbacks(runnable);
            }
        });


    }

    public Runnable runnable = new Runnable() {
        public void run() {
            MilisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MilisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilisecondTime = (int) (UpdateTime % 1000);

            Timer.setText("" + Minutes + ":" + String.format("%02d", Seconds) + ":" + String.format("%02d", MiliSeconds));

            handler.postDelayed(this, 0);
        }
    };*/




