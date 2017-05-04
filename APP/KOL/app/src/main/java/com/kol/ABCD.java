package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



/**
 * Created by Maria on 02/05/2017.
 */

public class ABCD extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abcd);
        BeregnABCD(0);
        Button ABCDVidere= (Button) findViewById(R.id.ABCDVidere);
        ABCDVidere.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MENU.class);
                startActivity(myIntent);
            }
        });
    }

    public void BeregnABCD (int i) {
        int ABCDscore = getIntent().getExtras().getInt("int_value");
        if (ABCDscore == 1) {
            TextView textView = (TextView) findViewById(R.id.ABCD);
            textView.setText("A");
        } else if (ABCDscore == 2) {
            TextView textView = (TextView) findViewById(R.id.ABCD);
            textView.setText("B");
        } else if (ABCDscore == 3) {
            TextView textView = (TextView) findViewById(R.id.ABCD);
            textView.setText("C");
        } else if (ABCDscore == 4) {
            TextView textView = (TextView) findViewById(R.id.ABCD);
            textView.setText("D");
        }
    }

}
