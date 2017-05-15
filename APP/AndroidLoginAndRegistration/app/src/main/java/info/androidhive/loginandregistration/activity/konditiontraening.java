package info.androidhive.loginandregistration.activity;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.loginandregistration.app.AppController;



public class konditiontraening extends Activity {

    private String kondi_type;
    private int helbredstilstand;
    private int tid;
    private double afstand;
    private int evaluering;
    private int b_antal;
    private int b_afstand;
    private int b_tid;

    public static String URL_kondi = "http://192.168.1.149/android_login_api/konditiontraening.php";
    public static String URL_B_RESULTATER = "http://1192.168.1.149/android_login_api/b_resultater.php";
    public static String URL_UPDATEBELOENNINGER = "http://192.168.1.149/android_login_api/updatebeloenninger.php";
    private static final String TAG = konditiontraening.class.getSimpleName();


    public int getHelbredstilstand() {
        return helbredstilstand;
    }

    public void setHelbredstilstand(int helbredstilstand) {

        this.helbredstilstand = helbredstilstand;
    }

    public String getKondi_type() {
        return kondi_type;
    }

    public void setKondi_type(String kondi_type) {
        this.kondi_type = kondi_type;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public double getAfstand() {
        return afstand;
    }

    public void setAfstand(double afstand) {
        this.afstand = afstand;
    }

    public int getEvaluering() {
        return evaluering;
    }

    public void setEvaluering(int evaluering) {
        this.evaluering = evaluering;
    }

    public int getB_antal() {
        return b_antal;
    }

    public void setB_antal(int b_antal) {
        this.b_antal = b_antal;
    }

    public int getB_afstand() {
        return b_afstand;
    }

    public void setB_afstand(int b_afstand) {
        this.b_afstand = b_afstand;
    }

    public int getB_tid() {
        return b_tid;
    }

    public void setB_tid(int b_tid) {
        this.b_tid = b_tid;
    }


    public boolean gemTraening(final String medlemsid, final String kondi_type, final int helbredstilstand, final int tid, final int evaluering, final double afstand) {
        String tag_string_req = "req_kondi";
        boolean error = false;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_kondi, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Parametre Response: " + response.toString());
                //hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        error = false;
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        error = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Parametre Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("medlemsid", medlemsid);
                params.put("kondi_type", kondi_type);
                params.put("helbredstilstand", String.valueOf(helbredstilstand));
                params.put("tid", String.valueOf(tid));
                params.put("evaluering", String.valueOf(evaluering));
                params.put("afstand", String.valueOf(afstand));
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        return error;
    }

    //*************** HENTER RESULTATER SÅLEDES BELØNNINGER KAN BEREGNES ************//
    public void hent_b_resultater(final String medlemsid) {
        // Tag used to cancel the request
        String tag_string_req = "req_b_resultater";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_B_RESULTATER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        JSONObject b_resultater = jObj.getJSONObject("b_resultater");
                        int antal_t = b_resultater.getInt("COUNT(tid_dato)");
                        int kondi_t = antal_t;
                        int afstand_t = (int) b_resultater.getInt("SUM(afstand_km)");
                        int tid_t = b_resultater.getInt("SUM(kondi_tid)");

                        beregn_beloenninger(medlemsid, antal_t, kondi_t, afstand_t, tid_t);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    // Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());;
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


    public void beregn_beloenninger(final String medlemsid, final int antal_t, final int kondi_t, final int afstand_t, final int tid_t) {
        int b_antal = 0;
        int b_kondi = 0;
        int b_afstand = 0;
        int b_tid = 0;

        // ANTAL
        if (antal_t >= 3 && antal_t < 29){
            b_antal = 1;
        } else if (antal_t >= 30 && antal_t < 89){
            b_antal = 2;
        } else if (antal_t >= 90 && antal_t < 209){
            b_antal = 3;
        } else if (antal_t >= 210 && antal_t < 299){
            b_antal = 4;
        } else if (antal_t >= 300 && antal_t < 449){
            b_antal = 5;
        } else if (antal_t >= 450){
            b_antal = 6;

        } else {

        }

        // KONDI
        if (kondi_t >= 1 && kondi_t < 9){
            b_kondi = 1;
        } else if (kondi_t >= 10 && kondi_t < 29){
            b_kondi = 2;
        } else if (kondi_t >= 30 && kondi_t < 69){
            b_kondi = 3;
        } else if (kondi_t >= 70 && kondi_t < 99){
            b_kondi = 4;
        } else if (kondi_t >= 100 && kondi_t < 149){
            b_kondi = 5;
        } else if (kondi_t >= 150){
            b_kondi = 6;
        }

        // AFSTAND
        if (afstand_t >= 5 && afstand_t < 24) {
            b_afstand = 1;
        } else if (afstand_t >= 25 && afstand_t < 49) {
            b_afstand = 2;
        } else if (afstand_t >= 50 && afstand_t < 99) {
            b_afstand = 3;
        } else if (afstand_t >= 100 && afstand_t < 299) {
            b_afstand = 4;
        } else if (afstand_t >= 300 && afstand_t < 499) {
            b_afstand = 5;
        } else if (afstand_t >= 500) {
            b_afstand = 6;
        } else {

        }

        // TID
        if (tid_t >= 3600 && tid_t < 5399) {
            b_tid = 1;
        } else if (tid_t >= 5400 && tid_t < 7199) {
            b_tid = 2;
        } else if (tid_t >= 7200 && tid_t < 17999) {
            b_tid = 3;
        } else if (tid_t >= 18000 && tid_t < 23999) {
            b_tid = 4;
        } else if (tid_t >= 24000 && tid_t < 29999) {
            b_tid = 5;
        } else if (tid_t >= 30000) {
            b_tid = 6;
        } else {

        }
        gembeloenninger(medlemsid, b_antal, b_kondi, b_afstand, b_tid);
    }

    public void gembeloenninger(final String medlemsid, final int b_antal, final int b_kondi, final int b_afstand, final int b_tid){
        String tag_string_req = "req_beloenninger1";
        boolean error = false;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_UPDATEBELOENNINGER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Parametre Response: " + response.toString());
                //hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        error = false;
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        error = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Parametre Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("medlemsid", medlemsid);
                params.put("b_antal", String.valueOf(b_antal));
                params.put("b_kondi", String.valueOf(b_kondi));
                params.put("b_afstand", String.valueOf(b_afstand));
                params.put("b_tid", String.valueOf(b_tid));

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}


