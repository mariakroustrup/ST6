package info.androidhive.loginandregistration.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

import static info.androidhive.loginandregistration.activity.TilpasningController.kondi;


public class LogUdController extends AppCompatActivity{

    private SQLiteHandler db;
    private SessionManager session;
    private ProgressDialog pDialog;
    public static String URL_kondi= "http://192.168.1.149/android_login_api/konditiontraening.php";
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
        final int heldbredstilstand = kondi.getHelbredstilstand();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        Button Bekræft= (Button) findViewById(R.id.Bekræft);
        Bekræft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //***********************

                gemHelbredstilstand(String.valueOf(heldbredstilstand),medlemsid);


                //*************************
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

    //********************* METODE ***********//
    public void gemHelbredstilstand(final String helbredstilstand, final String medlemsid) {
        String tag_string_req = "req_kondi";

        // Viser at kategoriseringen er ved at blive gemt
        pDialog.setMessage("Gemmer helbredstilstand");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_kondi, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Helbredstilstand Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(), "aaaaaaaaa", Toast.LENGTH_LONG).show();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Helbredstilstand er gemt", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Helbredstilstand Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("helbredstilstand", helbredstilstand);
                params.put("medlemsid", medlemsid);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
