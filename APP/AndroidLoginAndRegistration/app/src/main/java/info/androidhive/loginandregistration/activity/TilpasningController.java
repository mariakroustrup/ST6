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
    Button btnKondi, btnStyrke, btnVejr;
    Button btnVidereForm;
    Button btnGaa, btnLoeb, btnCykel;
    Button btnVidereType;
    Button btnEn, btnTo, btnTre, btnFire, btnFem;
    Button btnVidereHelbred;
    int evalueringgaa, evalueringloeb, evalueringcykel, helbred;
    TextView TVAnbefaling;
    long mm;


    private SQLiteHandler db;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traeningsform); // viser layoutet for tilpasning

        //Buttons
        btnKondi = (Button) findViewById(R.id.btnKondi);
        btnStyrke = (Button) findViewById(R.id.btnStyrke);
        btnVejr = (Button) findViewById(R.id.btnVejr);
        btnVidereForm = (Button) findViewById(R.id.btnVidereForm);
        btnGaa = (Button) findViewById(R.id.btnGaa);
        btnLoeb = (Button) findViewById(R.id.btnLoeb);
        btnCykel = (Button) findViewById(R.id.btnCykle);
        btnVidereType = (Button) findViewById(R.id.btnVidereType);
        btnEn = (Button) findViewById(R.id.enhelbred);
        btnTo = (Button) findViewById(R.id.tohelbred);
        btnTre = (Button) findViewById(R.id.trehelbred);
        btnFire = (Button) findViewById(R.id.firehelbred);
        btnFem = (Button) findViewById(R.id.femhelbred);
        btnVidereHelbred = (Button)findViewById(R.id.Viderehelbred);
        TVAnbefaling = (TextView)findViewById(R.id.anbefaling);


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from SQLite
        user = db.getUserDetails();
    }

    int counter = 0;
    TextView myTextView;
    android.widget.Button Button;

    public void ButtonOnClick2(View v) {

        switch (v.getId()) {
            case R.id.btnKondi:
                OnGone(v);
                valgkondi(0);
                break;
            case R.id.btnStyrke:
                break;
            case R.id.btnVejr:
                break;
        }
    }

    // Gør videre knappen usynlig og synlig når de andre er trykket på
    public void OnGone(View view) {
        btnKondi.setEnabled(false);
        btnKondi.setClickable(false);
        btnStyrke.setEnabled(true);
        btnStyrke.setClickable(true);
        btnVejr.setEnabled(true);
        btnVejr.setClickable(true);
        btnVidereForm.setVisibility(view.VISIBLE);
        btnVidereForm.setEnabled(true);
        btnVidereForm.setClickable(true);

    }

    // ***** Hvis man vælger konditræning****** //
    public void valgkondi(int i) {
            btnVidereForm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.traeningstype);
                }});}


    int resultat;
    public void ButtonOnClick (View v){
    switch (v.getId()) {
        case R.id.Gaa:
            resultat = 0;
            OnGone1(v);
            starthelbred(0);
            break;
        case R.id.Loeb:
            resultat = 1;
            OnGone1(v);
            starthelbred(1);
            break;
        case R.id.Cykel:
            resultat = 2;
            OnGone1(v);
            starthelbred(2);
            break;
    }}

    public void OnGone1(View view) {
        btnGaa.setEnabled(false);
        btnGaa.setClickable(false);
        btnLoeb.setEnabled(true);
        btnLoeb.setClickable(true);
        btnCykel.setEnabled(true);
        btnCykel.setClickable(true);
        btnVidereType.setVisibility(view.VISIBLE);
        btnVidereType.setEnabled(true);
        btnVidereType.setClickable(true);
    }

    public void starthelbred(int i){
        Button VidereTilpas1 = (Button) findViewById(R.id.VidereTilpas1);
        VidereTilpas1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.helbred);
                beregntræningsform(resultat);
            }
        });
    }

    public void beregntræningsform (int i){ // det er de her data der skal sendes til databasen
        String.valueOf(i);
        String form;
        if (resultat == 0){
            form = "Gå";
        } if (resultat == 1){
            form = "Løb";
        } if(resultat== 2){
            form = "Cykel";
        }
    }



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
        btnEn.setEnabled(false);
        btnEn.setClickable(false);
        btnTo.setEnabled(false);
        btnTo.setClickable(false);
        btnTre.setEnabled(false);
        btnTre.setClickable(false);
        btnFire.setEnabled(false);
        btnFire.setClickable(false);
        btnFem.setEnabled(false);
        btnFem.setClickable(false);
        btnVidereHelbred.setVisibility(view.VISIBLE);
        btnVidereHelbred.setEnabled(true);
        btnVidereHelbred.setClickable(true);
    }


    public void beregnanbefaling(int i) {
        if (evalueringgaa == 0 && evalueringloeb == 0 && evalueringcykel == 0 ) {
            if (helbred == 1) {
                TVAnbefaling.setText("Den anbefalede træningstid er 10 minutter");
                setMinutter(1);
            } else if (helbred == 2) {
                TVAnbefaling.setText("Den anbefalede træningstid er 20 minutter");
                setMinutter(20);
            } else if (helbred == 3) {
                TVAnbefaling.setText("Den anbefalede træningstid er 30 minutter");
                setMinutter(30);
            } else if (helbred == 4) {
                TVAnbefaling.setText("Den anbefalede træningstid er 40 minutter");
                setMinutter(40);
            } else if (helbred == 5) {
                TVAnbefaling.setText("Den anbefalede træningstid er 50 minutter");
                setMinutter(50);
            }
        }
        else if (evalueringgaa != 0 || evalueringloeb != 0 ||evalueringcykel != 0 ){
            if (evalueringgaa == 1 && helbred == 1){
                TVAnbefaling.setText("Den anbefalede træningstid er 20 minutter");
                setMinutter(1);
            } else if (evalueringgaa == 1 && helbred == 2)  {
                TVAnbefaling.setText("Den anbefalede træningstid er 30 minutter");
                setMinutter(30);
            }  else if (evalueringgaa == 1 && helbred == 3)  {
                TVAnbefaling.setText("Den anbefalede træningstid er 40 minutter");
                setMinutter(40);
            } else if (evalueringgaa == 1 && helbred == 4)  {
                TVAnbefaling.setText("Den anbefalede træningstid er 50 minutter");
                setMinutter(50);
            } else if (evalueringgaa == 1 && helbred == 5)  {
                TVAnbefaling.setText("Den anbefalede træningstid er 60 minutter");
                setMinutter(60);
            } else if (evalueringgaa == 2 && helbred == 1){
                TVAnbefaling.setText("Den anbefalede træningstid er 10 minutter");
                setMinutter(10);
            } else if (evalueringgaa == 2 && helbred == 2)  {
                TVAnbefaling.setText("Den anbefalede træningstid er 20 minutter");
                setMinutter(20);
            } else if (evalueringgaa == 2 && helbred == 3)  {
                TVAnbefaling.setText("Den anbefalede træningstid er 30 minutter");
                setMinutter(30);
            } else if (evalueringgaa == 2 && helbred == 4)  {
                TVAnbefaling.setText("Den anbefalede træningstid er40 minutter");
                setMinutter(40);
            } else if (evalueringgaa == 2 && helbred == 5)  {
               TVAnbefaling.setText("Den anbefalede træningstid er 50 minutter");
                setMinutter(50);
            } else if (evalueringgaa == 3 && helbred == 1){
                TVAnbefaling.setText("Den anbefalede træningstid er 5 minutter");
                setMinutter(5);}
            else if (evalueringgaa == 3 && helbred == 2)  {
                TVAnbefaling.setText("Den anbefalede træningstid er 10 minutter");
                setMinutter(10);
            } else if (evalueringgaa == 3 && helbred == 3)  {
               TVAnbefaling.setText("Den anbefalede træningstid er 20 minutter");
                setMinutter(20);
            } else if (evalueringgaa == 3 && helbred == 4)  {
                TVAnbefaling.setText("Den anbefalede træningstid er 30 minutter");
                setMinutter(30);
            } else if (evalueringgaa == 3 && helbred == 5)  {
                TVAnbefaling.setText("Den anbefalede træningstid er 40 minutter");
                setMinutter(40);
            }
        }}


    public void setMinutter(long newMinutter){
        mm = newMinutter;
    }
    public long getMinutes(){
        return mm;
    }

    public void startanbefaling (int i) {
        btnVidereHelbred.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                setContentView(R.layout.anbefaling);
                //beregnhelbred(0);
                setContentView(R.layout.anbefaling);
                beregnanbefaling(0);
                starttraening(0);
            }
        });}


    public void starttraening (int i) {
        Button OKanbefal = (Button) findViewById(R.id.OKanbefal);
        OKanbefal.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Intent myIntent = new Intent(TilpasningController.this, TraeningController.class);
                myIntent.putExtra("Value", mm);
                startActivity(myIntent);
            }
        });}
}