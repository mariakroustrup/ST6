package info.androidhive.loginandregistration.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;


public class VennelisteController extends AppCompatActivity {

    private EditText etsoegven;
    private Button btnsoegven;
    private ListView lv;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    ListAdapter adapter;

    // products JSONArray
    JSONArray venner_array = null;

    // Arrayliste til at lagre venner i
    ArrayList<HashMap<String, String>> VenneListe = new ArrayList<HashMap<String, String>>();

    // URL's
    public static String URL_SOEGVEN = "http://172.31.159.63/android_login_api/soegven.php";
    public static String URL_ALLE_VENNER = "http://172.31.159.63/android_login_api/allevenner4.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venneliste);

        //**************** HER HENTER VI MEDLEMSIDET
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        // Vises i den respektive TextView
        final String medlemsid = user.get("medlemsid");

        // Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        hentallevenner(medlemsid);
        updateList();

        btnsoegven = (Button) findViewById(R.id.btnSoegVen);
        etsoegven = (EditText) findViewById(R.id.etSoegVen);
        lv = (ListView) findViewById(R.id.list);


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


        // launching Edit Product Screen
      lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem

                //String medlemsid = lv.getItemAtPosition(position).toString(); // Den crasher her, men hvorfor???

                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(position);
                String ven_medlemsid = (String) obj.get("ven_medlemsid");
                String ven_navn = (String) obj.get("navn");

                // Starting new intent
                Intent in = new Intent(getApplicationContext(), VenController.class);
                // sending pid to next activity
                in.putExtra("EXTRA_VENS_ID", ven_medlemsid);
                in.putExtra("EXTRA_VENS_NAVN", ven_navn);

                startActivity(in);
            }
        });

    }


    private void checkVen(final String ven_medlemsid){ //SøgVen() i design
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
                        //String uid = jObj.getString("uid");

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


    //***************** METODE TIL AT HENTE ALLE VENNER TIL LISTEN
    private void hentallevenner (final String medlemsid){ //HentVenner i design
        // Tag used to cancel the request
        String tag_string_req = "req_hentvenner";

        pDialog.setMessage("Henter venner...");
        showDialog();

        final StringRequest strReq = new StringRequest(Request.Method.POST, URL_ALLE_VENNER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject json = new JSONObject(response);
                    boolean error = json.getBoolean("error");

                    venner_array = json.getJSONArray("venner");

                    if(!error){
                        for(int i = 0; i < venner_array.length(); i++){
                            JSONObject c = venner_array.getJSONObject(i);

                            // Vi lagre det i en variable
                            String navn = c.getString("navn");
                            String ven_medlemsid = c.getString("ven_medlemsid");

                            //Hashmap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // Værdier i Hashmap
                            map.put("navn", navn);
                            map.put("ven_medlemsid",ven_medlemsid);

                            // Tilfjer HashList to ArrayList VenneListe
                            VenneListe.add(map);
                        }
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = json.getString("error_msg");
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

    public void updateList(){
        runOnUiThread(new Runnable() {
            public void run() {
                /**
                 * Updating parsed JSON data into ListView
                 * */
                adapter = new SimpleAdapter(
                        VennelisteController.this, VenneListe,
                        R.layout.activity_listview, new String[] {"navn"},
                        new int[] { R.id.Navn });
                ListView listView = (ListView)findViewById(R.id.list);
                // updating listview
                listView.setAdapter(adapter);
            }
        });
    }
}



