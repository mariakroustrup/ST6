package info.androidhive.loginandregistration.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;



public class konditiontraening extends Activity {

    private String kondi_type;
    private int helbredstilstand;
    private ProgressDialog pDialog;
    public static String URL_kondi= "http://172.25.147.84/android_login_api/konditiontraening.php";
    private static final String TAG = konditiontraening.class.getSimpleName();
    private SQLiteHandler db;
    String medlemsid;
    HashMap<String, String> user;


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










/*

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

*/

}
