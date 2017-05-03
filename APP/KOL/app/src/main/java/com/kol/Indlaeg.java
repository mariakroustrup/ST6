package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maria on 02/05/2017.
 */

public class Indlaeg extends AppCompatActivity {
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indlaeg);

        Button indlaegVidere = (Button) findViewById(R.id.indlaegVidere);
        indlaegVidere.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent myIntent = new Intent(Indlaeg.this, ABCD.class);
                startActivity(myIntent);
            }
        });}


    final ArrayList<Integer> list = new ArrayList<Integer>();

    public void ButtonOnClick(View view) {


                TextView output = (TextView) findViewById(R.id.prove);

               switch (view.getId()) {
                   case R.id.mindre:
                        result = 0;
                        beregnIndlaeg(0);
                        break;
                   case R.id.stoerre:
                        result = 1;
                       beregnIndlaeg(0);
                        break;
                }
            }

    public void beregnIndlaeg (int i) {
        list.add(i);

    }
}

