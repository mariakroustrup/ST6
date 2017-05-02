package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * Created by Maria on 02/05/2017.
 */

public class Kategorisering extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.katintro);

        Button OKKat= (Button) findViewById(R.id.OKKat);
        OKKat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CATScore.class);
                startActivity(myIntent);
            }
        });
}






}