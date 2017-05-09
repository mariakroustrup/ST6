package info.androidhive.loginandregistration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.loginandregistration.R;

/**
 * Created by Maria on 04/05/2017.
 */

public class TILPASNING extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tilpasning); // viser layoutet for tilpasning
    }

    int counter = 0;
    TextView myTextView;
    android.widget.Button Button;

    public void ButtonOnClick2(View v) {

        switch (v.getId()) {
            case R.id.Kondi:
                OnGone(v);
                valgkondi(0);
                break;
            case R.id.Styrke:
                OnGone(v);
                valgstyrke(1);
                break;
            case R.id.vejr:
                OnGone(v);
                valgvejr(2);
                break;
        }
    }

    // Gør videre knappen usynlig og synlig når de andre er trykket på
    public void OnGone(View view) {
        Button Kondi = (Button) findViewById(R.id.Kondi);
        Kondi.setEnabled(false);
        Kondi.setClickable(false);
        Button Styrke = (Button) findViewById(R.id.Styrke);
        Styrke.setEnabled(false);
        Styrke.setClickable(false);
        Button vejr = (Button) findViewById(R.id.vejr);
        vejr.setEnabled(false);
        vejr.setClickable(false);
        Button VidereTilpasning = (Button) findViewById(R.id.VidereTilpasning);
        VidereTilpasning.setVisibility(view.VISIBLE);
        VidereTilpasning.setEnabled(true);
        VidereTilpasning.setClickable(true);

    }

    // Gør de andre knapper synlige igen og videre knappen usynlig
    public void OnVisible(View view) {
        Button Kondi = (Button) findViewById(R.id.Kondi);
        Kondi.setEnabled(true);
        Kondi.setClickable(true);
        Button Styrke = (Button) findViewById(R.id.Styrke);
        Styrke.setEnabled(true);
        Styrke.setClickable(true);
        Button vejr = (Button) findViewById(R.id.vejr);
        vejr.setEnabled(true);
        vejr.setClickable(true);
        Button VidereTilpasning = (Button) findViewById(R.id.VidereTilpasning);
        VidereTilpasning.setVisibility(view.INVISIBLE);
        VidereTilpasning.setEnabled(false);
        VidereTilpasning.setClickable(false);
    }

    public void valgkondi(int i) {
        final int resultat = i + 1;


        if (resultat >= 1) ;
        {
            Button VidereTilpasning = (Button) findViewById(R.id.VidereTilpasning);
            VidereTilpasning.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    OnVisible(v);
                    if (counter < 1) {
                        myTextView = (TextView) findViewById(R.id.tilpasningtekst);
                        myTextView.setText("Vælg træningsform");
                        Button = (Button) findViewById(R.id.Kondi);
                        Button.setText("Gå");
                        Button = (Button) findViewById(R.id.Styrke);
                        Button.setText("Løbe");
                        Button = (Button) findViewById(R.id.vejr);
                        Button.setText("Cykle");
                        counter++;
                    } else if (counter == 1) {
                        setContentView(R.layout.helbred);
                    }
                }
            });
        }
    }


    public void valgstyrke(int i) {
        final int resultat = i + 1;


        if (resultat >= 1) ;

        {
            Button VidereTilpasning = (Button) findViewById(R.id.VidereTilpasning);
            VidereTilpasning.setOnClickListener(new View.OnClickListener() {
                                                    public void onClick(View v) {
                                                        OnVisible(v);
                                                        if (counter < 1) {
                                                            myTextView = (TextView) findViewById(R.id.tilpasningtekst);
                                                            myTextView.setText("Vælg træningsform");
                                                            Button = (Button) findViewById(R.id.Kondi);
                                                            Button.setText("Type 1");
                                                            Button = (Button) findViewById(R.id.Styrke);
                                                            Button.setText("Type 2");
                                                            Button = (Button) findViewById(R.id.vejr);
                                                            Button.setText("Type 3");
                                                            counter++;
                                                        } else if (counter == 1) {
                                                            setContentView(R.layout.helbred);
                                                        }
                                                    }
                                                }
            );
        }

    }

    public void valgvejr(int i) {
        final int resultat = i + 1;


        if (resultat >= 1) ;

        {
            Button VidereTilpasning = (Button) findViewById(R.id.VidereTilpasning);
            VidereTilpasning.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    OnVisible(v);
                    if (counter < 1) {
                        myTextView = (TextView) findViewById(R.id.tilpasningtekst);
                        myTextView.setText("Vælg træningsform");
                        Button = (Button) findViewById(R.id.Kondi);
                        Button.setText("Type 1");
                        Button = (Button) findViewById(R.id.Styrke);
                        Button.setText("Type 2");
                        Button = (Button) findViewById(R.id.vejr);
                        Button.setText("Type 3");
                        counter++;
                    } else if (counter == 1) {
                        setContentView(R.layout.helbred);
                    }
                }

            });
        }
    }


    // Daglig helbredstilstand

    int result = 0;

    public void ButtonOnClick3(View v) {

        switch (v.getId()) {
            case R.id.enhelbred:
                result = 1;
                OnGone3(v);
                startanbefaling(1);
                break;
            case R.id.tohelbred:
                result = 2;
                OnGone3(v);
                startanbefaling(2);
                break;
            case R.id.trehelbred:
                result = 3;
                OnGone3(v);
                startanbefaling(3);
                break;
            case R.id.firehelbred:
                result = 4;
                OnGone3(v);
                startanbefaling(4);
                break;
            case R.id.femhelbred:
                result = 5;
                OnGone3(v);
                startanbefaling(5);
                break;
        }

    }

    public void OnGone3(View view) {
        Button en = (Button) findViewById(R.id.enhelbred);
        en.setEnabled(false);
        en.setClickable(false);
        Button to = (Button) findViewById(R.id.tohelbred);
        to.setEnabled(false);
        to.setClickable(false);
        Button tre = (Button) findViewById(R.id.trehelbred);
        tre.setEnabled(false);
        tre.setClickable(false);
        Button fire = (Button) findViewById(R.id.firehelbred);
        fire.setEnabled(false);
        fire.setClickable(false);
        Button fem = (Button) findViewById(R.id.femhelbred);
        fem.setEnabled(false);
        fem.setClickable(false);
        Button videre = (Button) findViewById(R.id.Viderehelbred);
        videre.setVisibility(view.VISIBLE);
        videre.setEnabled(true);
        videre.setClickable(true);
    }


    final ArrayList<Integer> list = new ArrayList<Integer>();

    public void beregnhelbred(int i) {
        int HELBRED = 0;
        if (result == 1) {
            HELBRED = 1;
        } else if (result == 2) {
            HELBRED = 2;
        } else if (result == 3) {
            HELBRED = 3;
        } else if (result == 4) {
            HELBRED = 4;
        }
    }



    public void startanbefaling (int i) {
        Button Videre = (Button) findViewById(R.id.Viderehelbred);
        Videre.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                setContentView(R.layout.anbefaling);
                beregnhelbred(0);
                starttraening(0);

            }
        });}

    public void starttraening (int i) {
        Button OKanbefal = (Button) findViewById(R.id.OKanbefal);
        OKanbefal.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Intent myIntent = new Intent(TILPASNING.this, TRAENING.class);
                startActivity(myIntent);
            }
        });}
}

