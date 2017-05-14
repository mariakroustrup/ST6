package info.androidhive.loginandregistration.activity;
import android.app.ProgressDialog;
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
    JSONArray resultater_kondi = null;

    // Arrayliste til at lagre resultater i
    ArrayList<HashMap<String, String>> ResultatListe = new ArrayList<HashMap<String, String>>();

    // URL's
    public static String URL_RESULTATER = "http://192.168.1.149/android_login_api/resultater.php";

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


        hentresultater(medlemsid);
        stjerner(0);
    }



    // ***** Metode til at hente alle resultater fra konditræning ******* //
    private void hentresultater (final String medlemsid){
        // Tag used to cancel the request
        String tag_string_req = "req_hentresultater";

        pDialog.setMessage("Henter resultater...");
        showDialog();

        final StringRequest strReq = new StringRequest(Request.Method.POST, URL_RESULTATER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject json = new JSONObject(response);
                    boolean error = json.getBoolean("error");

                    resultater_kondi = json.getJSONArray("kondi");

                    if(!error){
                        for(int i = 0; i < resultater_kondi.length(); i++){
                            JSONObject c = resultater_kondi.getJSONObject(i);

                            // Vi lagre det i en variable
                            String afstand = c.getString("afstand_km");
                            String tid = c.getString("kondi_tid");

                            //Hashmap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // Værdier i Hashmap
                            map.put("afstand", afstand);
                            map.put("tid",tid);

                            // Tilfjer HashList to ArrayList VenneListe
                            ResultatListe.add(map);
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


    // *************** METODE TIL AT UDVÆLGE BELØNNINGER ***********//
    int antal;
    int kondi;
    int tid;
    int afstand;

    public void stjerner(int i){
        afstand = 3;
        tid = 20;
        antal = 3;
        kondi = 6;
        if (antal >= 1 && antal < 5 || kondi >= 1 && kondi < 5) {
            ImageView image1 = (ImageView)findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star1);
            ImageView image = (ImageView)findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star1);
        } else if (antal >= 5 && antal < 10 || kondi >= 5 && kondi < 10){
            ImageView image1 = (ImageView)findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star2);
            ImageView image = (ImageView)findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star2);
        } else if (antal >= 10 && antal < 15 || kondi >= 10 && kondi < 15){
            ImageView image1 = (ImageView)findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star3);
            ImageView image = (ImageView)findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star3);
        } else if (antal >= 15 && antal < 20 || kondi >= 15 && kondi < 20) {
            ImageView image1 = (ImageView)findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star4);
            ImageView image = (ImageView)findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star4);
        } else if(antal >= 20 && antal < 25 || kondi >= 20 && kondi <25) {
            ImageView image1 = (ImageView)findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star5);
            ImageView image = (ImageView)findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star5);
        } else  if(antal >= 25 || kondi >=25) {
            ImageView image1 = (ImageView)findViewById(R.id.starantal);
            image1.setImageResource(R.drawable.star6);
            ImageView image = (ImageView)findViewById(R.id.starkondi);
            image.setImageResource(R.drawable.star6);
        }
        // FOR TID
        if (tid >= 10 && tid < 20 ) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star1);
        } else if (tid >= 20 && tid < 30) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star2);
        } else if (tid >= 30 && tid < 40) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star3);
        } else if (tid >= 40 && tid < 50) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star4);
        } else if (tid >= 50) {
            ImageView image1 = (ImageView)findViewById(R.id.startid);
            image1.setImageResource(R.drawable.star5);
        }

        // For afstand
        if (afstand >= 1 && afstand < 3) {
            ImageView image1 = (ImageView)findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star1);
        } else if (afstand >= 3 && afstand < 5) {
            ImageView image1 = (ImageView)findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star2);
        }else if (afstand >= 5 && afstand < 7) {
            ImageView image1 = (ImageView) findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star3);
        } else if (afstand >= 7 && afstand < 9) {
            ImageView image1 = (ImageView) findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star4);
        } else if (afstand >= 9 && afstand < 10) {
            ImageView image1 = (ImageView)findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star5);}
        else if (afstand >= 10) {
            ImageView image1 = (ImageView)findViewById(R.id.starafstand);
            image1.setImageResource(R.drawable.star6);}
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
