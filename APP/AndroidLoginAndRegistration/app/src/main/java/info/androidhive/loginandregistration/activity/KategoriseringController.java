
package info.androidhive.loginandregistration.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppController;
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;


public class KategoriseringController extends AppCompatActivity{
    String ABCD;
    private ProgressDialog pDialog;
    public static final String URL_KATEGORISERING = "http://172.31.159.63/android_login_api/kategorisering.php";
    private static final String TAG = KategoriseringController.class.getSimpleName();
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategoriseringintro);

        //Progress Dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Button OKKat= (Button) findViewById(R.id.OKKat);
        OKKat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setContentView(R.layout.catscore);//Videre til layoutet for CATscore
            }
        });
    }

    // CATscore
    final ArrayList<Integer> list = new ArrayList<Integer>(); // kaldes CATscore i design

    public void ButtonOnClick1(View v) {

        switch (v.getId()) {
            case R.id.nul:
                OnGone(v);
                beregnCAT(0);
                break;
            case R.id.en:
                OnGone(v);
                beregnCAT(1);
                break;
            case R.id.to:
                OnGone(v);
                beregnCAT(2);
                break;
            case R.id.tre:
                OnGone(v);
                beregnCAT(3);
                break;
            case R.id.fire:
                OnGone(v);
                beregnCAT(4);
                break;
            case R.id.fem:
                OnGone(v);
                beregnCAT(5);
                break;
        }
    }

    TextView myTextView;
    int counter = 0;

    public int sum(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum = sum + i;
        }
        return sum;
    }

    // Gør videre knappen synlig når en af de andre knapper trykkes på
    public void OnGone (View view){
        Button nul = (Button) findViewById(R.id.nul);
        nul.setEnabled(false);
        nul.setClickable(false);
        Button en = (Button) findViewById(R.id.en);
        en.setEnabled(false);
        en.setClickable(false);
        Button to = (Button) findViewById(R.id.to);
        to.setEnabled(false);
        to.setClickable(false);
        Button tre = (Button) findViewById(R.id.tre);
        tre.setEnabled(false);
        tre.setClickable(false);
        Button fire = (Button) findViewById(R.id.fire);
        fire.setEnabled(false);
        fire.setClickable(false);
        Button fem = (Button) findViewById(R.id.fem);
        fem.setEnabled(false);
        fem.setClickable(false);
        Button CATVidere1 = (Button)findViewById(R.id.CATvidere1);
        CATVidere1.setVisibility(view.VISIBLE);
        CATVidere1.setEnabled(true);
        CATVidere1.setClickable(true);
    }

    // Gør videre knappe usynlig og de andre synlige når man går videre til et nyt spørgsmål
    public void OnVisible (View view){
        Button nul = (Button) findViewById(R.id.nul);
        nul.setEnabled(true);
        nul.setClickable(true);
        Button en = (Button) findViewById(R.id.en);
        en.setEnabled(true);
        en.setClickable(true);
        Button to = (Button) findViewById(R.id.to);
        to.setEnabled(true);
        to.setClickable(true);
        Button tre = (Button) findViewById(R.id.tre);
        tre.setEnabled(true);
        tre.setClickable(true);
        Button fire = (Button) findViewById(R.id.fire);
        fire.setEnabled(true);
        fire.setClickable(true);
        Button fem = (Button) findViewById(R.id.fem);
        fem.setEnabled(true);
        fem.setClickable(true);
        Button CATVidere1 = (Button)findViewById(R.id.CATvidere1);
        CATVidere1.setVisibility(view.INVISIBLE);
        CATVidere1.setEnabled(false);
        CATVidere1.setClickable(false);
    }

    public void beregnCAT (int i) {
        final int resultat = i + 1;
        list.add(i);
        final int sum = sum(list);


        if (resultat >= 1) ;
        {
            Button CATVidere1 = (Button) findViewById(R.id.CATvidere1);
            CATVidere1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    OnVisible(v);
                    if (counter < 1) {
                        myTextView = (TextView) findViewById(R.id.aldrig1);
                        myTextView.setText("Jeg har ingen slim i lungerne");
                        myTextView = (TextView) findViewById(R.id.altid1);
                        myTextView.setText("Jeg har altid slim i lungerne");
                        counter++;
                    } else if (counter == 1) {
                        myTextView = (TextView) findViewById(R.id.aldrig1);
                        myTextView.setText("Jeg har ingen trykken for brystet");
                        myTextView = (TextView) findViewById(R.id.altid1);
                        myTextView.setText("Jeg har trykken for brystet");
                        counter++;
                    } else if (counter == 2) {
                        myTextView = (TextView) findViewById(R.id.aldrig1);
                        myTextView.setText("Når jeg går op ad bakken eller går én etage op, bliver jeg ikke forpustet");
                        myTextView = (TextView) findViewById(R.id.altid1);
                        myTextView.setText("Når jeg går op ad bakken eller går én etage op, bliver jeg meget forpustet");
                        counter++;
                    } else if (counter == 3) {
                        myTextView = (TextView) findViewById(R.id.aldrig1);
                        myTextView.setText("Jeg er ikke begrænset i nogen aktiviteter i hjemmet");
                        myTextView = (TextView) findViewById(R.id.altid1);
                        myTextView.setText("Jeg er meget begrænset i alle aktiviteter i hjemmet");
                        counter++;
                    } else if (counter == 4) {
                        myTextView = (TextView) findViewById(R.id.aldrig1);
                        myTextView.setText("Jeg er tryg ved at forlade mit hjem");
                        myTextView = (TextView) findViewById(R.id.altid1);
                        myTextView.setText("Jeg er ikke tryg ved at forlade mit hjem");
                        counter++;
                    } else if (counter == 5) {
                        myTextView = (TextView) findViewById(R.id.aldrig1);
                        myTextView.setText("Jeg sover dybt");
                        myTextView = (TextView) findViewById(R.id.altid1);
                        myTextView.setText("Jeg sover ikke dybt");
                        counter++;
                    } else if (counter == 6) {
                        myTextView = (TextView) findViewById(R.id.aldrig1);
                        myTextView.setText("Jeg har masser af energi");
                        myTextView = (TextView) findViewById(R.id.altid1);
                        myTextView.setText("Jeg har slet ingen energi ");
                        counter++;
                    } else if (counter == 7) {
                        setContentView(R.layout.indlaeg); //Videre til layoutet for indlæggelse*/
                    }
                }
            });}
    }

    // Håndtering af indlæggelser
    int result;
    public void ButtonOnClick(View view) {

        switch (view.getId()) {
            case R.id.mindre:
                result = 0;
                OnGone1(view);
                start(0);
                break;
            case R.id.stoerre:
                result = 1;
                OnGone1(view);
                start(1);
                break;
        }
    }

    // Gør knapper videre knappen synlig når en af de andre knapper trykkes på
    public void OnGone1(View view) {
        Button mindre = (Button) findViewById(R.id.mindre);
        mindre.setEnabled(false);
        mindre.setClickable(false);
        Button stoerre = (Button) findViewById(R.id.stoerre);
        stoerre.setEnabled(false);
        stoerre.setClickable(false);
        Button indlaegVidere = (Button) findViewById(R.id.indlaegVidere1);
        indlaegVidere.setVisibility(view.VISIBLE);
        indlaegVidere.setEnabled(true);
        indlaegVidere.setClickable(true);
    }

    public void start(int i){
        Button indlaegVidere = (Button) findViewById(R.id.indlaegVidere1);
        indlaegVidere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.abcd);
                beregnIndlaeg(0);
            }
        });}

    public void beregnIndlaeg(int i) {

        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // Fetching user details from SQLite
        final HashMap<String, String> user = db.getUserDetails();

        // Vises i den respektive TextView
        final String medlemsid = user.get("medlemsid");

        int CATscore = sum(list);

        if (CATscore < 10 && result == 0) {
            ABCD = "A";
            TextView textView = (TextView) findViewById(R.id.ABCD);
            textView.setText("A");
        } else if (CATscore < 10 && result == 1) {
            ABCD = "C";
            TextView textView = (TextView) findViewById(R.id.ABCD);
            textView.setText("C");
        } else if (CATscore >= 10 && result == 0) {
            ABCD = "B";
            TextView textView = (TextView) findViewById(R.id.ABCD);
            textView.setText("B");
        } else if (CATscore >= 10 && result == 1) {
            ABCD = "D";
            TextView textView = (TextView) findViewById(R.id.ABCD);
            textView.setText("D");
        }   Button ABCDVidere= (Button) findViewById(R.id.ABCDVidere);
        ABCDVidere.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                gemKategoriseringen(ABCD,medlemsid);
                db.updateKat(medlemsid,ABCD);
                Intent myIntent = new Intent(KategoriseringController.this, MenuController.class);
                startActivity(myIntent); }});}



    private void gemKategoriseringen (final String ABCD, final String medlemsid) { //Kaldes SendKategorisering i design
        String tag_string_req = "req_kategorisering";

        // Viser at kategoriseringen er ved at blive gemt
        pDialog.setMessage("Gemmer kategoriseringen");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_KATEGORISERING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Kategorisering Response: " + response.toString());
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
                    Toast.makeText(getApplicationContext(), "Kategoriseringen er gemt", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){


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
                params.put("ABCD", ABCD);
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
}

