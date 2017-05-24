
package info.androidhive.loginandregistration.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;


public class LoginController extends Activity {
    private Button btnLogin;
    private EditText inputMedlemsID;
    private EditText inputAdgangskode;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    public static String URL_LOGIN = "http://172.31.159.63/android_login_api/login.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //notifikation


        // Edit Text
        inputMedlemsID = (EditText) findViewById(R.id.medlemsid);
        inputAdgangskode = (EditText) findViewById(R.id.adgangskode);

        // Buttons
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginController.this, MenuController.class);
            startActivity(intent);
            finish();
        }

        // Log ind knap listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String medlemsid = inputMedlemsID.getText().toString().trim();
                String adgangskode = inputAdgangskode.getText().toString().trim();

                // Check for empty data in the form
                if (!medlemsid.isEmpty() && !adgangskode.isEmpty()) {
                    // login user
                    checkLogin(medlemsid, adgangskode);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Angiv log ind informationer", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }



    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String medlemsid, final String adgangskode) { //HentLogind() i design i rapport
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String navn = user.getString("navn");
                        String medlemsid = user.getString("medlemsid");
                        String kategorisering = user.getString("kategorisering");

                        // Inserting row in users table
                        db.addUser(navn, medlemsid, uid, kategorisering);

                        // Launch main activity
                        if(kategorisering.equals("F")){
                            Intent intent = new Intent(LoginController.this, KategoriseringController.class);
                            String v_Msg = jObj.getString("v_msg");
                            Toast.makeText(getApplicationContext(), v_Msg, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            //finish();
                        }
                        else{
                            String v_Msg = jObj.getString("v_msg");
                            Toast.makeText(getApplicationContext(), v_Msg, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginController.this, MenuController.class);
                            startActivity(intent);
                            //finish();
                        }
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
                params.put("adgangskode", adgangskode);
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

//    private void scheduleNotification(Notification notification){
//        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID,1);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 15);
//        calendar.set(Calendar.MINUTE, 00);
//        calendar.set(Calendar.SECOND, 00);
//
//        long futureInMillis = calendar.getTimeInMillis();
//        final AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    private Notification getNotification (String content){
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentTitle("KOL APP");
//        builder.setContentText(content);
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        builder.setSound(alarmSound);
//        builder.setSmallIcon(R.drawable.notification);
//        return builder.build();
//    }
}

