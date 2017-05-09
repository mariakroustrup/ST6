package info.androidhive.loginandregistration.activity;

/**
 * Created by Maria on 09/05/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;


public class LOGUD extends AppCompatActivity{

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        Button Bekræft= (Button) findViewById(R.id.Bekræft);
        Bekræft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                logoutUser();
                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(myIntent);
            }
        });

        Button Fortryd= (Button) findViewById(R.id.Fortryd);
        Fortryd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MENU.class);
                startActivity(myIntent);
            }
        });
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        //  Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        //  startActivity(intent);
        //  finish();
    }
}
