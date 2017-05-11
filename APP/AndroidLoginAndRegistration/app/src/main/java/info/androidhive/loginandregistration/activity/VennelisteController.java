package info.androidhive.loginandregistration.activity;

/**
 * Created by Maria on 09/05/2017.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class VennelisteController extends AppCompatActivity {

    private EditText etsoegven;
    private Button btnsoegven;
    private ProgressDialog pDialog;
    public static String URL_SOEGVEN = "http://172.31.159.63/android_login_api/soegven.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venneliste);

        // Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        btnsoegven = (Button) findViewById(R.id.btnSoegVen);
        etsoegven = (EditText) findViewById(R.id.etSoegVen);

        btnsoegven.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String ven_medlemsid = etsoegven.getText().toString().trim();

                if (!ven_medlemsid.isEmpty()) {
                    checkVen(ven_medlemsid);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Indtast medlemsid på ven", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }


    private void checkVen(final String ven_medlemsid){
        // Tag used to cancel the request
        String tag_string_req = "req_soegven";

        pDialog.setMessage("Søger ven");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_SOEGVEN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String ven_navn = user.getString("navn");
                        String ven_medlemsid = user.getString("medlemsid");

                        // ********
                        Intent intent = new Intent(VennelisteController.this, VenController.class);
                        intent.putExtra("EXTRA_VENS_ID", ven_medlemsid);
                        intent.putExtra("EXTRA_VENS_NAVN", ven_navn);
                        startActivity(intent);
                        // *******

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}


