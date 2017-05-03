package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Maria on 02/05/2017.
 */

public class CATScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catscore);
    }

    int result;

    final ArrayList<Integer> list = new ArrayList<Integer>();

    public void ButtonOnClick1(View v) {

        switch (v.getId()) {
            case R.id.nul:
                result = 0;
                beregnCAT(0);
                OnGone(v);
                break;
            case R.id.en:
                result = 1;
                beregnCAT(1);
                OnGone(v);
                break;
            case R.id.to:
                result = 2;
                beregnCAT(2);
                OnGone(v);
                break;
            case R.id.tre:
                result = 3;
                beregnCAT(3);
                OnGone(v);
                break;
            case R.id.fire:
                result = 4;
                beregnCAT(4);
                OnGone(v);
                break;
            case R.id.fem:
                result = 5;
                beregnCAT(5);
                OnGone(v);
                break;
        }
    }

    TextView myTextView;
    int counter = 0;

    public int sum(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum = sum + i;
        }
        return sum;
    }

    public void OnGone (View view){
        Button nul = (Button) findViewById(R.id.nul);
        nul.setEnabled(false);
        nul.setClickable(false);
        Button en = (Button) findViewById(R.id.en);
        en.setEnabled(false);
        en.setClickable(false);
        Button to = (Button) findViewById(R.id.to);
        to.setEnabled(false);
        to.setClickable(false);
        Button tre = (Button) findViewById(R.id.tre);
        tre.setEnabled(false);
        tre.setClickable(false);
        Button fire = (Button) findViewById(R.id.fire);
        fire.setEnabled(false);
        fire.setClickable(false);
        Button fem = (Button) findViewById(R.id.fem);
        fem.setEnabled(false);
        fem.setClickable(false);
        Button CATVidere1 = (Button)findViewById(R.id.CATvidere1);
        CATVidere1.setVisibility(view.VISIBLE);
        CATVidere1.setEnabled(true);
        CATVidere1.setClickable(true);
    }

    public void OnVisible (View view){
        Button nul = (Button) findViewById(R.id.nul);
        nul.setEnabled(true);
        nul.setClickable(true);
        Button en = (Button) findViewById(R.id.en);
        en.setEnabled(true);
        en.setClickable(true);
        Button to = (Button) findViewById(R.id.to);
        to.setEnabled(true);
        to.setClickable(true);
        Button tre = (Button) findViewById(R.id.tre);
        tre.setEnabled(true);
        tre.setClickable(true);
        Button fire = (Button) findViewById(R.id.fire);
        fire.setEnabled(true);
        fire.setClickable(true);
        Button fem = (Button) findViewById(R.id.fem);
        fem.setEnabled(true);
        fem.setClickable(true);
        Button CATVidere1 = (Button)findViewById(R.id.CATvidere1);
        CATVidere1.setVisibility(view.INVISIBLE);
        CATVidere1.setEnabled(false);
        CATVidere1.setClickable(false);
    }

    public void beregnCAT (int i) {
        int resultat = i + 1;
        list.add(i);
        int res = sum(list);

        if (resultat >=1);

        {
            Button CATVidere1= (Button) findViewById(R.id.CATvidere1);
            CATVidere1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    OnVisible(v);
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
                    } else if (counter == 5) {
                        myTextView = (TextView) findViewById(R.id.aldrig1);
                        myTextView.setText("Jeg sover dybt");
                        myTextView = (TextView) findViewById(R.id.altid1);
                        myTextView.setText("Jeg sover ikke dybt");
                        counter++;
                    } else  if (counter == 6){
                        myTextView = (TextView) findViewById(R.id.aldrig1);
                        myTextView.setText("Jeg har masser af energi");
                        myTextView = (TextView) findViewById(R.id.altid1);
                        myTextView.setText("Jeg har slet ingen energi ");
                        counter++;
                    } else if (counter == 7){
                    Intent myIntent = new Intent(v.getContext(), Indlaeg.class);
                    startActivity(myIntent);
                    }
                }
            }); }}

}