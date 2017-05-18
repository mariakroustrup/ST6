package info.androidhive.loginandregistration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import info.androidhive.loginandregistration.R;


public class MenuController extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);



        Button traening = (Button) findViewById(R.id.traening);
        traening.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), TilpasningController.class);
                startActivity(myIntent);
            }
        });

        Button resultater = (Button) findViewById(R.id.resultater);
        resultater.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Intent myIntent = new Intent(view.getContext(), ResultaterController.class);
                Intent myIntent = new Intent(view.getContext(), grafController.class);
                startActivity(myIntent);
            }
        });

        Button venneliste = (Button) findViewById(R.id.Venneliste);
        venneliste.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), VennelisteController.class);
                startActivity(myIntent);
            }
        });

        Button redigering = (Button) findViewById(R.id.RedigerAdgangskode);
        redigering.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), RedigeringController.class);
                startActivity(myIntent);
            }
        });

        Button logud = (Button) findViewById(R.id.btnLogout);
        logud.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuController.this, LogUdController.class));
            }
        });
    }



}