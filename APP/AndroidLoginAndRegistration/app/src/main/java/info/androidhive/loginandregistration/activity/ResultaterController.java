package info.androidhive.loginandregistration.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
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


public class ResultaterController extends AppCompatActivity {

    private SQLiteHandler db;
    private ProgressDialog pDialog;

    // products JSONArray
    JSONArray beloenninger_array = null;

    // Arrayliste til at lagre resultater i
    ArrayList<HashMap<String, String>> ResultatListe = new ArrayList<HashMap<String, String>>();

    // URL's
    public static String URL_BELOENNINGER = "http://172.31.159.63/android_login_api/beloenninger.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beloeninger);

        //**************** HER HENTER VI MEDLEMSIDET *******************
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();
        // Vises i den respektive TextView
        final String medlemsid = user.get("medlemsid");
        // *************************************************************

        // Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        
        hentbeloenninger(medlemsid);

    }
    private void hentbeloenninger(final String medlemsid) {
        // Tag used to cancel the request
        String tag_string_req = "req_beloenninger";

        pDialog.setMessage("Henter resultater ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_BELOENNINGER, new Response.Listener<String>() {

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
                        stjerner(b_afstand, b_tid, b_antal, b_kondi);

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


    // *************** METODE TIL AT VISE BELØNNINGER ***********//
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
}


    /*    chart = (BarChart) findViewById(R.id.chart1);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY();

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY,"");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(3000);



  /*  public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(2f, 0));
        BARENTRY.add(new BarEntry(4f, 1));
        BARENTRY.add(new BarEntry(6f, 2));
        BARENTRY.add(new BarEntry(8f, 3));
        BARENTRY.add(new BarEntry(7f, 4));
        BARENTRY.add(new BarEntry(3f, 5));
        BARENTRY.add(new BarEntry(3f, 6));

    }

    public void AddValuesToBarEntryLabels() {

        BarEntryLabels.add("Mandag");
        BarEntryLabels.add("Tirsdag");
        BarEntryLabels.add("Onsdag");
        BarEntryLabels.add("Torsdag");
        BarEntryLabels.add("Fredag");
        BarEntryLabels.add("Lørdag");
        BarEntryLabels.add("Sørdag");

    }
    public void start (int i){
        Button kalender = (Button) findViewById(R.id.Kalender);
        kalender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.kalender);

            }
        });
        Button beloen = (Button) findViewById(R.id.Beloenniger);
        beloen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.beloeninger);

            }
        });

    } */
