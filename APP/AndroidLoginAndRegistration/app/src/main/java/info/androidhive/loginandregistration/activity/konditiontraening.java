package info.androidhive.loginandregistration.activity;

import android.app.Activity;
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



public class konditiontraening extends Activity {

    private String kondi_type;
    private int helbredstilstand;
    private String tid;
    private int afstand;
    private int evaluering;

    public static String URL_kondi= "http://172.31.159.63/android_login_api/konditiontraening.php";
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

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getAfstand() {
        return afstand;
    }

    public void setAfstand(int afstand) {
        this.afstand = afstand;
    }

    public int getEvaluering() {
        return evaluering;
    }

    public void setEvaluering(int evaluering) {
        this.evaluering = evaluering;
    }



    public boolean gemTraening(final String medlemsid,final String kondi_type, final int helbredstilstand, final String tid) {
        String tag_string_req = "req_kondi";
        boolean error = false;
        // Viser at kategoriseringen er ved at blive gemt
        //pDialog.setMessage("Gemmer træningsparametre");
        //showDialog();

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
                        error = false; //Toast.makeText(getApplicationContext(), "aaaaaaaaa", Toast.LENGTH_LONG).show();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                       error = true; //Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    //Toast.makeText(getApplicationContext(), "Helbredstilstand er gemt", Toast.LENGTH_LONG).show();
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
                params.put("tid",tid);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    return error;
    }
}
