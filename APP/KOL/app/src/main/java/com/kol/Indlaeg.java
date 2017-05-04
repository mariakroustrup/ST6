package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maria on 02/05/2017.
 */

public class Indlaeg extends AppCompatActivity {
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indlaeg);
    }


    public void ButtonOnClick(View view) {

        switch (view.getId()) {
            case R.id.mindre:
                result = 0;
                OnGone(view);
                beregnIndlaeg(0);

                break;
            case R.id.stoerre:
                result = 1;
                OnGone(view);
                beregnIndlaeg(1);
                break;
        }


    }


    public void OnGone(View view) {
        Button mindre = (Button) findViewById(R.id.mindre);
        mindre.setEnabled(false);
        mindre.setClickable(false);
        Button stoerre = (Button) findViewById(R.id.stoerre);
        stoerre.setEnabled(false);
        stoerre.setClickable(false);
        Button indlaegVidere = (Button) findViewById(R.id.indlaegVidere1);
        indlaegVidere.setVisibility(view.VISIBLE);
        indlaegVidere.setEnabled(true);
        indlaegVidere.setClickable(true);
    }
    public void OnVisible (View view) {
        Button mindre = (Button) findViewById(R.id.mindre);
        mindre.setEnabled(true);
        mindre.setClickable(true);
        Button stoerre = (Button) findViewById(R.id.stoerre);
        stoerre.setEnabled(true);
        stoerre.setClickable(true);
        Button indlaegVidere = (Button) findViewById(R.id.indlaegVidere1);
        indlaegVidere.setVisibility(view.INVISIBLE);
        indlaegVidere.setEnabled(false);
        indlaegVidere.setClickable(false);
    }



    public void beregnIndlaeg(int i) {
            Button indlaegVidere = (Button) findViewById(R.id.indlaegVidere1);
            indlaegVidere.setOnClickListener(new View.OnClickListener() {
                public void onClick (View v) {
                    int ABCD = 1;
                    int CATscore = getIntent().getExtras().getInt("int_value");

                    if (CATscore < 10 && result == 0) {
                        ABCD = 1;
                    } else if (CATscore < 10 && result == 1) {
                        ABCD = 2;
                    } else if (CATscore >= 10 && result == 0) {
                        ABCD = 3;
                    } else if (CATscore >= 10 && result == 1) {
                        ABCD = 4;
                    }
                    Intent myIntent = new Intent(v.getContext(), ABCD.class);
                    myIntent.putExtra("int_value", ABCD);
                    startActivity(myIntent);
                    }
                } ); }
}








