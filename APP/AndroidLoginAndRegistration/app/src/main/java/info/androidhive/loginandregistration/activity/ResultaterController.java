package info.androidhive.loginandregistration.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import info.androidhive.loginandregistration.R;

import static info.androidhive.loginandregistration.activity.TilpasningController.kondi;


/**
 * Created by Maria on 05/05/2017.
 */


public class ResultaterController extends AppCompatActivity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.beloeninger);
                stjerner(0);
            }


            int antal;
            int kondi;
            int tid;
            int afstand;

            public void stjerner(int i){
                afstand = 3;
                tid = 20;
                antal = 3;
                kondi = 6;
                if (antal >= 1 && antal < 5 || kondi >= 1 && kondi < 5) {
                    ImageView image1 = (ImageView)findViewById(R.id.starantal);
                    image1.setImageResource(R.drawable.star1);
                    ImageView image = (ImageView)findViewById(R.id.starkondi);
                    image.setImageResource(R.drawable.star1);
                } else if (antal >= 5 && antal < 10 || kondi >= 5 && kondi < 10){
                    ImageView image1 = (ImageView)findViewById(R.id.starantal);
                    image1.setImageResource(R.drawable.star2);
                    ImageView image = (ImageView)findViewById(R.id.starkondi);
                    image.setImageResource(R.drawable.star2);
                } else if (antal >= 10 && antal < 15 || kondi >= 10 && kondi < 15){
                    ImageView image1 = (ImageView)findViewById(R.id.starantal);
                    image1.setImageResource(R.drawable.star3);
                    ImageView image = (ImageView)findViewById(R.id.starkondi);
                    image.setImageResource(R.drawable.star3);
                } else if (antal >= 15 && antal < 20 || kondi >= 15 && kondi < 20) {
                    ImageView image1 = (ImageView)findViewById(R.id.starantal);
                    image1.setImageResource(R.drawable.star4);
                    ImageView image = (ImageView)findViewById(R.id.starkondi);
                    image.setImageResource(R.drawable.star4);
                } else if(antal >= 20 && antal < 25 || kondi >= 20 && kondi <25) {
                    ImageView image1 = (ImageView)findViewById(R.id.starantal);
                    image1.setImageResource(R.drawable.star5);
                    ImageView image = (ImageView)findViewById(R.id.starkondi);
                    image.setImageResource(R.drawable.star5);
                } else  if(antal >= 25 || kondi >=25) {
                    ImageView image1 = (ImageView)findViewById(R.id.starantal);
                    image1.setImageResource(R.drawable.star6);
                    ImageView image = (ImageView)findViewById(R.id.starkondi);
                    image.setImageResource(R.drawable.star6);
                }
                // FOR TID
                if (tid >= 10 && tid < 20 ) {
                    ImageView image1 = (ImageView)findViewById(R.id.startid);
                    image1.setImageResource(R.drawable.star1);
                } else if (tid >= 20 && tid < 30) {
                    ImageView image1 = (ImageView)findViewById(R.id.startid);
                    image1.setImageResource(R.drawable.star2);
                } else if (tid >= 30 && tid < 40) {
                    ImageView image1 = (ImageView)findViewById(R.id.startid);
                    image1.setImageResource(R.drawable.star3);
                } else if (tid >= 40 && tid < 50) {
                    ImageView image1 = (ImageView)findViewById(R.id.startid);
                    image1.setImageResource(R.drawable.star4);
                } else if (tid >= 50) {
                    ImageView image1 = (ImageView)findViewById(R.id.startid);
                    image1.setImageResource(R.drawable.star5);
                }

                // For afstand
                if (afstand >= 1 && afstand < 3) {
                    ImageView image1 = (ImageView)findViewById(R.id.starafstand);
                    image1.setImageResource(R.drawable.star1);
                } else if (afstand >= 3 && afstand < 5) {
                    ImageView image1 = (ImageView)findViewById(R.id.starafstand);
                    image1.setImageResource(R.drawable.star2);
                }else if (afstand >= 5 && afstand < 7) {
                    ImageView image1 = (ImageView) findViewById(R.id.starafstand);
                    image1.setImageResource(R.drawable.star3);
                } else if (afstand >= 7 && afstand < 9) {
                    ImageView image1 = (ImageView) findViewById(R.id.starafstand);
                    image1.setImageResource(R.drawable.star4);
                } else if (afstand >= 9 && afstand < 10) {
                    ImageView image1 = (ImageView)findViewById(R.id.starafstand);
                    image1.setImageResource(R.drawable.star5);}
                else if (afstand >= 10) {
                    ImageView image1 = (ImageView)findViewById(R.id.starafstand);
                    image1.setImageResource(R.drawable.star6);}
            }

        }




    /*    chart = (BarChart) findViewById(R.id.chart1);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY,"");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(3000);



  /*  public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(2f, 0));
        BARENTRY.add(new BarEntry(4f, 1));
        BARENTRY.add(new BarEntry(6f, 2));
        BARENTRY.add(new BarEntry(8f, 3));
        BARENTRY.add(new BarEntry(7f, 4));
        BARENTRY.add(new BarEntry(3f, 5));
        BARENTRY.add(new BarEntry(3f, 6));

    }

    public void AddValuesToBarEntryLabels() {

        BarEntryLabels.add("Mandag");
        BarEntryLabels.add("Tirsdag");
        BarEntryLabels.add("Onsdag");
        BarEntryLabels.add("Torsdag");
        BarEntryLabels.add("Fredag");
        BarEntryLabels.add("Lørdag");
        BarEntryLabels.add("Sørdag");

    }
    public void start (int i){
        Button kalender = (Button) findViewById(R.id.Kalender);
        kalender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.kalender);

            }
        });
        Button beloen = (Button) findViewById(R.id.Beloenniger);
        beloen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.beloeninger);

            }
        });

    } */
