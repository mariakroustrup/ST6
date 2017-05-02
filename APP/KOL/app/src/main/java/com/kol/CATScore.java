package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Created by Maria on 02/05/2017.
 */

public class CATScore extends AppCompatActivity {

    TextView myTextView;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catscore);

        Button CATVidere1= (Button) findViewById(R.id.CATvidere1);
        CATVidere1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (counter < 1) {
                    myTextView = (TextView) findViewById(R.id.aldrig1);
                    myTextView.setText("Jeg har ingen slim i lungerne");
                    myTextView = (TextView) findViewById(R.id.altid1);
                    myTextView.setText("Jeg har altid slim i lungerne");
                    counter++;
                } else if (counter == 1) {
                    myTextView = (TextView) findViewById(R.id.aldrig1);
                    myTextView.setText("Jeg har ingen trykken for brystet");
                    myTextView = (TextView) findViewById(R.id.altid1);
                    myTextView.setText("Jeg har trykken for brystet");
                    counter++;
                } else if (counter == 2) {
                    myTextView = (TextView) findViewById(R.id.aldrig1);
                    myTextView.setText("Når jeg går op ad bakken eller går én etage op, bliver jeg ikke forpustet");
                    myTextView = (TextView) findViewById(R.id.altid1);
                    myTextView.setText("Når jeg går op ad bakken eller går én etage op, bliver jeg meget forpustet");
                    counter++;
                } else if (counter == 3) {
                    myTextView = (TextView) findViewById(R.id.aldrig1);
                    myTextView.setText("Jeg er ikke begrænset i nogen aktiviteter i hjemmet");
                    myTextView = (TextView) findViewById(R.id.altid1);
                    myTextView.setText("Jeg er meget begrænset i alle aktiviteter i hjemmet");
                    counter++;
                } else if (counter == 4) {
                    myTextView = (TextView) findViewById(R.id.aldrig1);
                    myTextView.setText("Jeg er tryg ved at forlade mit hjem");
                    myTextView = (TextView) findViewById(R.id.altid1);
                    myTextView.setText("Jeg er ikke tryg ved at forlade mit hjem");
                    counter++;
                } else if (counter == 5){
                    myTextView = (TextView) findViewById(R.id.aldrig1);
                    myTextView.setText("Jeg sover dybt");
                    myTextView = (TextView) findViewById(R.id.altid1);
                    myTextView.setText("Jeg sover ikke dybt");
                    counter++;
                 } else  {
                    myTextView = (TextView) findViewById(R.id.aldrig1);
                    myTextView.setText("Jeg har masser af energi");
                    myTextView = (TextView) findViewById(R.id.altid1);
                    myTextView.setText("Jeg har slet ingen energi ");
                 }
        }
        });
    }
}