package info.androidhive.loginandregistration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.helper.SQLiteHandler;


public class TilpasningController extends AppCompatActivity {

    static konditiontraening kondi = new konditiontraening();
    int helbredstilstand;
    String kondi_type;
    String medlemsid;

    // Buttons
    Button btnKondi, btnStyrke, btnVejr;
    Button btnVidereForm;
    Button btnGaa, btnLoeb, btnCykel;
    Button btnVidereType;
    Button btnEn, btnTo, btnTre, btnFire, btnFem;
    Button btnVidereHelbred;
    Button btnOKanbefal;
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
        btnCykel = (Button) findViewById(R.id.btnCykel);
        btnVidereType = (Button) findViewById(R.id.btnVidereType);
        btnEn = (Button) findViewById(R.id.enhelbred);
        btnTo = (Button) findViewById(R.id.tohelbred);
        btnTre = (Button) findViewById(R.id.trehelbred);
        btnFire = (Button) findViewById(R.id.firehelbred);
        btnFem = (Button) findViewById(R.id.femhelbred);
        btnVidereHelbred = (Button)findViewById(R.id.btnVidereHelbred);
        TVAnbefaling = (TextView)findViewById(R.id.TVanbefaling);
        btnOKanbefal = (Button)findViewById(R.id.btnOKanbefal);


    // SqLite database handler
       db = new SQLiteHandler(getApplicationContext());

        //Fetching user details from SQLite
        user = db.getUserDetails();
        medlemsid = user.get("medlemsid");
    }

    int counter = 0;
    TextView myTextView;
   // android.widget.Button Button;

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
        btnVidereForm = (Button) findViewById(R.id.btnVidereForm);
            btnVidereForm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setContentView(R.layout.traeningstype);
}});}


        int resultat;
public void ButtonOnClick5 (View v){

        switch (v.getId()) {
        case R.id.btnGaa:
        resultat = 0;
        OnGone5(v);
        starthelbred(0);
        break;
        case R.id.btnLoeb:
        resultat = 1;
        OnGone5(v);
        starthelbred(1);
        break;
        case R.id.btnCykel:
        resultat = 2;
        OnGone5(v);
        starthelbred(2);
        break;
        }}

public void OnGone5(View view) {
        btnGaa = (Button) findViewById(R.id.btnGaa);
        btnGaa.setEnabled(false);
        btnGaa.setClickable(false);
        btnLoeb = (Button) findViewById(R.id.btnLoeb);
        btnLoeb.setEnabled(true);
        btnLoeb.setClickable(true);
        btnCykel = (Button) findViewById(R.id.btnCykel);
        btnCykel.setEnabled(true);
        btnCykel.setClickable(true);
        Button btnVidereType = (Button)findViewById(R.id.btnVidereType);
        btnVidereType.setVisibility(view.VISIBLE);
        btnVidereType.setEnabled(true);
        btnVidereType.setClickable(true);
        }

public void beregntraeningsform (int i){ // det er de her data der skal sendes til databasen
        String.valueOf(i);
        if (resultat == 0){
        kondi_type = "Gaa";
        } if (resultat == 1){
        kondi_type = "Loeb";
        } if(resultat == 2){
        kondi_type = "Cykel";
        }
        kondi.setKondi_type(kondi_type);
        }


public void starthelbred(int i){
        btnVidereType = (Button) findViewById(R.id.btnVidereType);
        btnVidereType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.helbred);
                beregntraeningsform(resultat);
            }
        });
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
        btnEn = (Button) findViewById(R.id.enhelbred);
        btnEn.setEnabled(false);
        btnEn.setClickable(false);
        btnTo = (Button) findViewById(R.id.tohelbred);
        btnTo.setEnabled(false);
        btnTo.setClickable(false);
        btnTre = (Button) findViewById(R.id.trehelbred);
        btnTre.setEnabled(false);
        btnTre.setClickable(false);
        btnFire = (Button) findViewById(R.id.firehelbred);
        btnFire.setEnabled(false);
        btnFire.setClickable(false);
        btnFem = (Button) findViewById(R.id.femhelbred);
        btnFem.setEnabled(false);
        btnFem.setClickable(false);
        Button btnVidereHelbred= (Button)findViewById(R.id.btnVidereHelbred);
        btnVidereHelbred.setVisibility(view.VISIBLE);
        btnVidereHelbred.setEnabled(true);
        btnVidereHelbred.setClickable(true);
    }

    public void beregnanbefaling(int i) {

        int evalueringgaa = 0;
        int helbred = 1;
        if (evalueringgaa == 0 && evalueringloeb == 0 && evalueringcykel == 0 ) {
            if (helbred == 1) {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 10 minutter");
                setMinutter(1);
            } else if (helbred == 2) {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 20 minutter");
                setMinutter(20);
            } else if (helbred == 3) {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 30 minutter");
                setMinutter(30);
            } else if (helbred == 4) {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 40 minutter");
                setMinutter(40);
            } else if (helbred == 5) {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 50 minutter");
                setMinutter(50);
            }
        }
        else if (evalueringgaa != 0 || evalueringloeb != 0 ||evalueringcykel != 0 ){
            if (evalueringgaa == 1 && helbred == 1){
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 20 minutter");
                setMinutter(1);
            } else if (evalueringgaa == 1 && helbred == 2)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 30 minutter");
                setMinutter(30);
            }  else if (evalueringgaa == 1 && helbred == 3)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 40 minutter");
                setMinutter(40);
            } else if (evalueringgaa == 1 && helbred == 4)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 50 minutter");
                setMinutter(50);
            } else if (evalueringgaa == 1 && helbred == 5)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 60 minutter");
                setMinutter(60);
            } else if (evalueringgaa == 2 && helbred == 1){
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 10 minutter");
                setMinutter(10);
            } else if (evalueringgaa == 2 && helbred == 2)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 20 minutter");
                setMinutter(20);
            } else if (evalueringgaa == 2 && helbred == 3)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 30 minutter");
                setMinutter(30);
            } else if (evalueringgaa == 2 && helbred == 4)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 40 minutter");
                setMinutter(40);
            } else if (evalueringgaa == 2 && helbred == 5)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
               TVAnbefaling.setText("Den anbefalede træningstid er 50 minutter");
                setMinutter(50);
            } else if (evalueringgaa == 3 && helbred == 1){
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 5 minutter");
                setMinutter(5);}
            else if (evalueringgaa == 3 && helbred == 2)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 10 minutter");
                setMinutter(10);
            } else if (evalueringgaa == 3 && helbred == 3)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
               TVAnbefaling.setText("Den anbefalede træningstid er 20 minutter");
                setMinutter(20);
            } else if (evalueringgaa == 3 && helbred == 4)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 30 minutter");
                setMinutter(30);
            } else if (evalueringgaa == 3 && helbred == 5)  {
                TextView TVAnbefaling = (TextView) findViewById(R.id.TVanbefaling);
                TVAnbefaling.setText("Den anbefalede træningstid er 40 minutter");
                setMinutter(40);
            }
        }}


    public void setMinutter(long newMinutter){
        mm = newMinutter;
    }

    public void startanbefaling (int i) {
        Button btnVidereHelbred= (Button)findViewById(R.id.btnVidereHelbred);
        btnVidereHelbred.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                setContentView(R.layout.anbefaling);
                beregnanbefaling(0);
                starttraening(0);
            }
        });
    }

// *********** KNAP DER STARTER TRÆNING ************ //
    public void starttraening (int i) {
        Button btnOKanbefal = (Button) findViewById(R.id.btnOKanbefal);
        btnOKanbefal.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                Intent myIntent = new Intent(TilpasningController.this, TraeningController.class);
                myIntent.putExtra("medlemsid",medlemsid);
                myIntent.putExtra("Value", mm);
                startActivity(myIntent);
            }
        });
    }
}