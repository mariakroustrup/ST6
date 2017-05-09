package info.androidhive.loginandregistration.activity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import info.androidhive.loginandregistration.helper.SessionManager;


public class RedigeringController extends Activity {
    private TextView Medlemsid;
    private TextView Navn;
    private TextView Kategorisering;
    private EditText Adgangskode;
    private EditText GenAdgangskode;
    private Button btnAdgangskode;
    private SQLiteHandler db;
    private SessionManager session;
    private ProgressDialog pDialog;
    public static String URL_ADGANGSKODE = "http://172.31.159.63/android_login_api/register2.php";
    private static final String TAG = RedigeringController.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redigering);

        // Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Medlemsid = (TextView) findViewById(R.id.medlemsid);
        Navn = (TextView) findViewById(R.id.navn);
        Kategorisering = (TextView) findViewById(R.id.kategorisering);
        Adgangskode = (EditText) findViewById(R.id.adgangskode);
        GenAdgangskode = (EditText) findViewById(R.id.genadgangskode);
        btnAdgangskode = (Button) findViewById(R.id.btnNyAdgangskode);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());


        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        // Vises i den respektive TextView
        final String medlemsid = user.get("medlemsid");
        String navn = user.get("navn");
        String kategorisering = user.get("kategorisering");


        // Displaying the user details on the screen
        Medlemsid.setText(medlemsid);
        Navn.setText(navn);
        Kategorisering.setText(kategorisering);

        btnAdgangskode.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String adgangskode = Adgangskode.getText().toString().trim();
                String genadgangskode = GenAdgangskode.getText().toString().trim();


                //Tjekker om tekstfeltet er tom
                if(!adgangskode.isEmpty() && !genadgangskode.isEmpty()){

                    if(adgangskode.equals(genadgangskode)) {

                        //Tjekker om adgangskoden er min 10 karakter lang
                        if (adgangskode.length() >= 10) {
                            gemAdgangskode(adgangskode, medlemsid);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Adgangskoden skal minimum være 10 karakterer!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "Adgangskoderne er ikke identiske",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Begge felter skal udfyldes",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void gemAdgangskode(final String adgangskode, final String medlemsid){
        // Tag used to cancel the request
        String tag_string_req = "req_register2";

        pDialog.setMessage("Gemmer adgangskode");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_ADGANGSKODE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        //String uid = jObj.getString("uid");
                        /*
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);
                        */
                        Toast.makeText(getApplicationContext(), "Adgangskoden er gemt", Toast.LENGTH_LONG).show();

                        /*
                        // Launch login activity
                        Intent intent = new Intent(RedigeringController.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                        */
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Adgangskoden er gemt", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

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
                params.put("adgangskode", adgangskode);
                params.put("medlemsid", medlemsid);
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