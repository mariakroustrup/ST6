package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Maria on 04/05/2017.
 */

public class TILPASNING extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tilpasning);
}

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

    public void OnGone (View view){
        Button Kondi = (Button) findViewById(R.id.Kondi);
        Kondi.setEnabled(false);
        Kondi.setClickable(false);
        Button Styrke = (Button) findViewById(R.id.Styrke);
        Styrke.setEnabled(false);
        Styrke.setClickable(false);
        Button vejr = (Button) findViewById(R.id.vejr);
        vejr.setEnabled(false);
        vejr.setClickable(false);
        Button VidereTilpasning = (Button)findViewById(R.id.VidereTilpasning);
        VidereTilpasning.setVisibility(view.VISIBLE);
        VidereTilpasning.setEnabled(true);
        VidereTilpasning.setClickable(true);
    }

    public void OnVisible (View view){
        Button Kondi = (Button) findViewById(R.id.Kondi);
        Kondi.setEnabled(true);
        Kondi.setClickable(true);
        Button Styrke = (Button) findViewById(R.id.Styrke);
        Styrke.setEnabled(true);
        Styrke.setClickable(true);
        Button vejr = (Button) findViewById(R.id.vejr);
        vejr.setEnabled(true);
        vejr.setClickable(true);
        Button VidereTilpasning = (Button)findViewById(R.id.VidereTilpasning);
        VidereTilpasning.setVisibility(view.INVISIBLE);
        VidereTilpasning.setEnabled(false);
        VidereTilpasning.setClickable(false);
    }

    int counter = 0;
    TextView myTextView;
    Button Button;



    public void valgkondi (int i) {
        final int resultat = i + 1;


        if (resultat >= 1) ;
        {
            Button VidereTilpasning = (Button) findViewById(R.id.VidereTilpasning);
             VidereTilpasning.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     OnVisible(v);
                     if (counter < 1){
                         myTextView = (TextView) findViewById(R.id.tilpasningtekst);
                         myTextView.setText("Vælg træningsform");
                         Button = (Button) findViewById(R.id.Kondi);
                         Button.setText("Gå");
                         Button = (Button) findViewById(R.id.Styrke);
                         Button.setText("Løbe");
                         Button = (Button) findViewById(R.id.vejr);
                         Button.setText("Cykle");
                         counter ++;
                     }else if(counter ==1){
                         Intent myIntent = new Intent(TILPASNING.this, HELBRED.class);
                         startActivity(myIntent);
                     }
                 }});
        }}


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
                        counter ++;
                    }else if(counter ==1){
                        Intent myIntent = new Intent(TILPASNING.this, HELBRED.class);
                        startActivity(myIntent);
                    }}}
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
                    }else if(counter ==1){
                    Intent myIntent = new Intent(TILPASNING.this, HELBRED.class);
                    startActivity(myIntent);
                }
                }

                });}}}


