package info.androidhive.loginandregistration.activity;

/**
 * Created by Maria on 09/05/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import info.androidhive.loginandregistration.R;

public class REDIGERING extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redigering);

        Button gem = (Button) findViewById(R.id.btnNyAdgangskode);
        gem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MENU.class);
                startActivity(myIntent);
            }
        });
    }
}
