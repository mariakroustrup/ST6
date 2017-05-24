package info.androidhive.loginandregistration.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import static android.view.View.INVISIBLE;

public class VenController extends AppCompatActivity {

    private Button btnFoelgVen;
    private Button btnFjernVen;
    private TextView ven_medlemsid;
    private TextView ven_navn;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private static final String TAG = VenController.class.getSimpleName();
    public static String URL_CHECKVENNERELATION = "http://172.31.159.63/android_login_api/checkvennerelation.php";
    public static String URL_VENNERELATION= "http://172.31.159.63/android_login_api/opretvennerelation.php";
    public static String URL_VENBELOENNINGER= "http://172.31.159.63/android_login_api/venbeloenninger.php";
    public static String URL_FJERNVENNERELATION= "http://172.31.159.63/android_login_api/fjernven.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soegven);

        // Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        // Henter medlemsid på bruger der er logget ind
        final String medlemsid = user.get("medlemsid");

        btnFoelgVen = (Button) findViewById(R.id.btnFoelgVen);
        btnFjernVen = (Button) findViewById(R.id.btnFjernVen);
        ven_medlemsid = (TextView)findViewById(R.id.venMedlemsid);
        ven_navn = (TextView)findViewById(R.id.venNavn);

        final String input_ven_medlemsid = getIntent().getStringExtra("EXTRA_VENS_ID");
        String input_ven_navn = getIntent().getStringExtra("EXTRA_VENS_NAVN");

        checkVennerelation(medlemsid, input_ven_medlemsid);

        ven_medlemsid.setText(input_ven_medlemsid);
        ven_navn.setText(input_ven_navn);

        btnFoelgVen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                opretvennerelation(medlemsid, input_ven_medlemsid);
                btnFoelgVen.setVisibility(INVISIBLE);
                btnFjernVen.setVisibility(View.VISIBLE);
            }
        });

        btnFjernVen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fjernvennerelation(medlemsid, input_ven_medlemsid);
                btnFjernVen.setVisibility(INVISIBLE);
                btnFoelgVen.setVisibility(View.VISIBLE);
            }
        });
    }

    private void checkVennerelation(final String medlemsid, final String ven_medlemsid){ //ValiderVen i design
        // Tag used to cancel the request
        String tag_string_req = "req_checkvennerelation";

        pDialog.setMessage("Checker");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_CHECKVENNERELATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        btnFoelgVen.setVisibility(INVISIBLE);
                        hentvenbeloenninger(ven_medlemsid);

                    } else {
                        // Error in login. Get the error message
                       // String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                        btnFjernVen.setVisibility(INVISIBLE);
                        hentvenbeloenninger(ven_medlemsid);

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("medlemsid", medlemsid);
                params.put("ven_medlemsid", ven_medlemsid);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void hentvenbeloenninger(final String ven_medlemsid){
        String tag_string_req = "req_venbeloenninger";

        pDialog.setMessage("Henter resultater ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_VENBELOENNINGER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        JSONObject beloenninger = jObj.getJSONObject("beloenninger");
                        int b_afstand = beloenninger.getInt("b_afstand");
                        int b_tid = beloenninger.getInt("b_tid");
                        int b_antal = beloenninger.getInt("b_antal");
                        int b_kondi = beloenninger.getInt("b_kondi");
                        stjerner(b_afstand,b_tid,b_antal,b_kondi);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("ven_medlemsid", ven_medlemsid);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void stjerner(final int b_afstand, final int b_tid, final int b_antal, final int b_kondi){

        // Afstand
        if (b_afstand == 1) {
            ImageView image1 = (ImageView)findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star1);
        } else if (b_afstand == 2) {
            ImageView image1 = (ImageView)findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star2);
        } else if (b_afstand == 3) {
            ImageView image1 = (ImageView) findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star3);
        } else if (b_afstand == 4) {
            ImageView image1 = (ImageView) findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star4);
        } else if (b_afstand == 5) {
            ImageView image1 = (ImageView) findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star5);
        } else if (b_afstand == 6) {
            ImageView image1 = (ImageView) findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star6);
        } else {

        }

        // tid
        if (b_tid == 1) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star1);
        } else if (b_tid == 2) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star2);
        } else if (b_tid == 3) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star3);
        } else if (b_tid == 4) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star4);
        } else if (b_tid == 5) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star5);
        } else if (b_tid == 6) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star6);
        } else {

        }

        // antal
        if (b_antal ==1){
            ImageView image1 = (ImageView)findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star1);
        } else if(b_antal==2){
            ImageView image1 = (ImageView) findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star2);
        } else if(b_antal==3){
            ImageView image1 = (ImageView) findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star3);
        } else if(b_antal==4){
            ImageView image1 = (ImageView) findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star4);
        } else if (b_antal ==5){
            ImageView image1 = (ImageView) findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star5);
        } else if (b_antal ==6){
            ImageView image1 = (ImageView) findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star5);
        } else{

        }


        // KONDI
        if (b_kondi == 1) {
            ImageView image = (ImageView)findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star1);
        } else if (b_kondi == 2) {
            ImageView image = (ImageView) findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star2);
        } else if (b_kondi == 3) {
            ImageView image = (ImageView) findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star3);
        } else if (b_kondi == 4) {
            ImageView image = (ImageView) findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star4);
        } else if (b_kondi == 5) {
            ImageView image = (ImageView) findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star5);
        } else if (b_kondi == 6) {
            ImageView image = (ImageView) findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star5);
        } else {

        }
    }


    private void opretvennerelation(final String medlemsid, final String ven_medlemsid){
        String tag_string_req = "req_vennerelation";

        // Viser at kategoriseringen er ved at blive gemt
        pDialog.setMessage("Gemmer vennerelation");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_VENNERELATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Vennerelation Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Ven er tilføjet til din venneliste", Toast.LENGTH_LONG).show();
                        //updateList();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Ven tilføjet til venneliste", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("medlemsid", medlemsid);
                params.put("ven_medlemsid", ven_medlemsid);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void Fjernvennerelation(final String medlemsid, final String ven_medlemsid){
        String tag_string_req = "req_fjernvennerelation";

        // Viser at kategoriseringen er ved at blive gemt
        pDialog.setMessage("Fjerner vennerelation");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FJERNVENNERELATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Vennerelation Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        //Toast.makeText(getApplicationContext(), "Ven er fjernet fra din venneliste", Toast.LENGTH_LONG).show();
                        //updateList();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                   Toast.makeText(getApplicationContext(), "Ven er fjernet fra din venneliste", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("medlemsid", medlemsid);
                params.put("ven_medlemsid", ven_medlemsid);
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