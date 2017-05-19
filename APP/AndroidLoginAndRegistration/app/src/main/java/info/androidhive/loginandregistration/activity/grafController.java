package info.androidhive.loginandregistration.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;


public class grafController extends AppCompatActivity {

    public static String URL_ALLE_traeninger = "http://172.31.159.63/android_login_api/alletraeninger.php";
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private Button btnBeloenninger;
    BarChart barChart;
    public int x;


    // products JSONArray
    JSONArray traeninger_array = null;

    // Arrayliste til at lagre venner i
    ArrayList<HashMap<String, String>> TraeningListe = new ArrayList<HashMap<String, String>>();
    String[] days = new String[7];
    String[] daynames = new String[]{"Man", "Tirs", "Ons", "Tors", "Fre", "Loer", "Soen"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultatgraf);
        barChart = (BarChart)findViewById(R.id.bargraf);
        btnBeloenninger = (Button)findViewById(R.id.btnbeloenninger);



        //**************** HER HENTER VI MEDLEMSIDET
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        // Vises i den respektive TextView
        final int medlemsid = Integer.parseInt(user.get("medlemsid"));

        // Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);



        for (int i = 0; i < 7; i++)
        {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        henttraeninger(medlemsid);

        btnBeloenninger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ResultaterController.class);
                startActivity(myIntent);
            }
        });
    }

    //********* METODE **********//
    public void setupgraph() throws JSONException {

        //for(int j = 0; j < days.length; j++) {
        //    for (int i = 0; i < traeninger_array.length(); i++) {

        //    }
        //}

        int[] data = {0,0,0,0,0,0,0,0};

        for (int j=0; j<days.length;j++){
            for (int i = 0; i < traeninger_array.length(); i++) {
                JSONObject jsonObj = traeninger_array.getJSONObject(i);
                String tid_dato = jsonObj.getString("tid_dato");
                String dato = tid_dato.substring(0,10);
                int tid = Integer.parseInt(jsonObj.getString("kondi_tid"));
                if (days[j].equals(dato)){
                    data[j] = tid;
                }
            }
        }

       // int nr = Integer.parseInt(jsonMap.get(days[0]))/60;


        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(data[0]/60,0));
        barEntries.add(new BarEntry(data[1]/60,1));
        barEntries.add(new BarEntry(data[2]/60,2));
        barEntries.add(new BarEntry(data[3]/60,3));
        barEntries.add(new BarEntry(data[4]/60,4));
        barEntries.add(new BarEntry(data[5]/60,5));
        barEntries.add(new BarEntry(data[6]/60,6));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("Man");
        theDates.add("Tirs");
        theDates.add("Ons");
        theDates.add("Tors");
        theDates.add("Fre");
        theDates.add("Lør");
        theDates.add("Søn");

        BarData theData = new BarData(theDates,barDataSet);
        barChart.setData(theData);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.invalidate();
        barChart.refreshDrawableState();
        barChart.setTouchEnabled(false);
        barChart.setDescription(" ");
    }


    //********* METODE **********//
    public void henttraeninger (final int medlemsid){
        String tag_string_req = "req_henttraening";

        pDialog.setMessage("Henter tidligere træninger...");
        showDialog();

        final StringRequest strReq = new StringRequest(Request.Method.POST, URL_ALLE_traeninger, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject json = new JSONObject(response);
                    boolean error = json.getBoolean("error");

                    traeninger_array = json.getJSONArray("traeninger");

                    if(!error){
                        for(int i = 0; i < traeninger_array.length(); i++){
                            JSONObject c = traeninger_array.getJSONObject(i);

                            // Vi lagre det i en variable
                            String kondi_tid = c.getString("kondi_tid");
                            String tid_dato = c.getString("tid_dato");

                            //Hashmap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // Værdier i Hashmap
                            map.put("kondi_tid", kondi_tid);
                            map.put("tid_dato", tid_dato);

                            // Tilfjer HashList to ArrayList
                            TraeningListe.add(map);
                        }
                        setupgraph();
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
                params.put("medlemsid", String.valueOf(medlemsid));
                params.put("Man", days[0]);
                String temp = params.get("Man");

                 for (int i = 0; i < 7; i++)
                  {
                    params.put(daynames[i],days[i]);
                  }
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    //********* METODE **********//
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    //********** METODE **********//
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

