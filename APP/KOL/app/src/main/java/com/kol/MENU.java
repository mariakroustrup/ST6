package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Maria on 04/05/2017.
 */

public class MENU extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button traening = (Button) findViewById(R.id.traening);
        traening.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), TILPASNING.class);
                startActivity(myIntent);
            }
        });

        Button resultater = (Button) findViewById(R.id.resultater);
        resultater.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), RESULTATER.class);
                startActivity(myIntent);
            }
        });

        Button venneliste = (Button) findViewById(R.id.Venneliste);
        venneliste.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), VENNELISTE.class);
                startActivity(myIntent);
            }
        });

        Button redigering = (Button) findViewById(R.id.RedigerAdgangskode);
        redigering.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), REDIGERING.class);
                startActivity(myIntent);
            }
        });

        Button logud = (Button) findViewById(R.id.LogUd);
        logud.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MENU.this, POP.class));
            }
        });
    }

}
