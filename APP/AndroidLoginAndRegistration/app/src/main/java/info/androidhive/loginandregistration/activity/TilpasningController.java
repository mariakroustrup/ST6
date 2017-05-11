package info.androidhive.loginandregistration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.helper.SQLiteHandler;


public class TilpasningController extends AppCompatActivity {

    static konditiontraening kondi = new konditiontraening();
    int helbredstilstand;
    Button btnKondi;
    Button btnStyke;
    Button btnVejr;
    Button btnVidereForm;
    Button btnGaa;
    Button btnLoeb;
    Button btnCykel;
    Button btnVidereType;



    private SQLiteHandler db;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traeningsform); // viser layoutet for tilpasning

        //Buttons
        btnKondi = (Button) findViewById(R.id.btnKondi);
        btnStyke = (Button) findViewById(R.id.btnStyrke);
        btnVejr = (Button) findViewById(R.id.btnVejr);
        btnVidereForm = (Button) findViewById(R.id.btnVidereForm);
        btnGaa = (Button) findViewById(R.id.btnGaa);
        btnLoeb = (Button) findViewById(R.id.btnLoeb);
        btnCykel = (Button) findViewById(R.id.btnCykle);
        btnVidereType = (Button) findViewById(R.id.btnVidereType);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from SQLite
        user = db.getUserDetails();

        btnKondi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //**** HER KUNNE DET VÆRE OPLAGT AT OBJEKTET FOR KONDI BLEV OPRETTET! ****//
                setContentView(R.layout.kondi);
                finish();
            }
        });
        btnGaa.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view1){
                kondi.setKondi_type("gaa");
                setContentView(R.layout.helbred);
            }
        });



        //btnVidereForm;
    }
    /*
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

    // ***** Hvis man vælger konditræning****** //
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


    // ***** Hvis man vælger styrketræning****** //
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
    // ***** Hvis man vælger vejrtrækningsøvelser****** //
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
    */

    // Daglig helbredstilstand
    public void ButtonOnClick3(View v) {
        helbredstilstand = 0;
        switch (v.getId()) {
            case R.id.enhelbred:
                helbredstilstand = 1;
                OnGone3(v);
                startanbefaling(1);
                break;
            case R.id.tohelbred:
                helbredstilstand = 2;
                OnGone3(v);
                startanbefaling(2);
                break;
            case R.id.trehelbred:
                helbredstilstand = 3;
                OnGone3(v);
                startanbefaling(3);
                break;
            case R.id.firehelbred:
                helbredstilstand = 4;
                OnGone3(v);
                startanbefaling(4);
                break;
            case R.id.femhelbred:
                helbredstilstand = 5;
                OnGone3(v);
                startanbefaling(5);
                break;
        }
        kondi.setHelbredstilstand(helbredstilstand);
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

    /*
    // ******** Her gemmes helbredstilstanden ********* //
    final ArrayList<Integer> list = new ArrayList<Integer>();
    public void beregnhelbred(int i) {
        int HELBRED = 0;
        if (helbredstilstand == 1) {
            HELBRED = 1;
        } else if (helbredstilstand == 2) {
            HELBRED = 2;
        } else if (helbredstilstand == 3) {
            HELBRED = 3;
        } else if (helbredstilstand == 4) {
            HELBRED = 4;
        } else if (helbredstilstand == 5) {
            HELBRED = 5;
        }
    }
    */
    public void startanbefaling (int i) {
        Button Videre = (Button) findViewById(R.id.Viderehelbred);
        Videre.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                setContentView(R.layout.anbefaling);
                //beregnhelbred(0);
                starttraening(0);
            }
        });}


    public void starttraening (int i) {
        Button OKanbefal = (Button) findViewById(R.id.OKanbefal);
        OKanbefal.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Intent myIntent = new Intent(TilpasningController.this, TraeningController.class);
                startActivity(myIntent);
            }
        });}
}