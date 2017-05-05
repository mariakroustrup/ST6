package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maria on 05/05/2017.
 */

public class HELBRED extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helbred); }

int result = 0;
    public void ButtonOnClick3(View v) {

        switch (v.getId()) {
            case R.id.enhelbred:
                result = 1;
                OnGone(v);
                beregnhelbred(1);
                break;
            case R.id.tohelbred:
                result = 2;
                OnGone(v);
                beregnhelbred(2);
                break;
            case R.id.trehelbred:
                result = 3;
                OnGone(v);
                beregnhelbred(3);
                break;
            case R.id.firehelbred:
                result = 4;
                OnGone(v);
                beregnhelbred(4);
                break;
            case R.id.femhelbred:
                result = 5;
                OnGone(v);
                beregnhelbred(5);
                break;
        }

    }

    public void OnGone (View view){
        Button en = (Button) findViewById(R.id.enhelbred);
        en.setEnabled(false);
        en.setClickable(false);
        Button to= (Button) findViewById(R.id.tohelbred);
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
        Button videre = (Button)findViewById(R.id.Viderehelbred);
        videre.setVisibility(view.VISIBLE);
        videre.setEnabled(true);
        videre.setClickable(true);
    }

    public void OnVisible (View view){
        Button en = (Button) findViewById(R.id.enhelbred);
        en.setEnabled(true);
        en.setClickable(true);
        Button to= (Button) findViewById(R.id.tohelbred);
        to.setEnabled(true);
        to.setClickable(true);
        Button tre = (Button) findViewById(R.id.trehelbred);
        tre.setEnabled(true);
        tre.setClickable(true);
        Button fire = (Button) findViewById(R.id.firehelbred);
        fire.setEnabled(true);
        fire.setClickable(true);
        Button fem = (Button) findViewById(R.id.femhelbred);
        fem.setEnabled(true);
        fem.setClickable(true);
        Button videre = (Button)findViewById(R.id.Viderehelbred);
        videre.setVisibility(view.INVISIBLE);
        videre.setEnabled(false);
        videre.setClickable(false);
    }


    int counter = 0;
    TextView myTextView;
    Button Button;

    final ArrayList<Integer> list = new ArrayList<Integer>();


    public void beregnhelbred (int i) {
        Button Videre = (Button) findViewById(R.id.Viderehelbred);
        Videre.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                OnVisible(v);
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
                Intent myIntent = new Intent(v.getContext(), TRAENING.class);
                myIntent.putExtra("int_value", HELBRED);
                startActivity(myIntent);
            }
        } ); }
}
