package info.androidhive.loginandregistration.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private TextView ven_medlemsid;
    private TextView ven_navn;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private static final String TAG = VenController.class.getSimpleName();
    public static String URL_CHECKVENNERELATION = "http://192.168.1.149/android_login_api/checkvennerelation.php";
    public static String URL_VENNERELATION= "http://192.168.1.149/android_login_api/opretvennerelation.php";

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
            }
        });
    }

    private void checkVennerelation(final String medlemsid, final String ven_medlemsid){
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
                        //Toast.makeText(getApplicationContext(),"I er pisse meget allerede venner!",Toast.LENGTH_LONG).show();
                        btnFoelgVen.setVisibility(INVISIBLE);

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
                params.put("medlemsid", medlemsid);
                params.put("ven_medlemsid", ven_medlemsid);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}