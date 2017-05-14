package info.androidhive.loginandregistration.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;



public class LogUdController extends AppCompatActivity{

    private SQLiteHandler db;
    private SessionManager session;
    private ProgressDialog pDialog;
    private static final String TAG = LogUdController.class.getSimpleName();
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        // Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // Fetching user details from SQLite
        final HashMap<String, String> user = db.getUserDetails();

        final String medlemsid = user.get("medlemsid");

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
                Intent myIntent = new Intent(view.getContext(), LoginController.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myIntent);

            }
        });

        Button Fortryd= (Button) findViewById(R.id.Fortryd);
        Fortryd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MenuController.class);
                startActivity(myIntent);
            }
        });


    }




    public void OnBackPressed(){

    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        //  startActivity(intent);
        //  finish();
    }


}
