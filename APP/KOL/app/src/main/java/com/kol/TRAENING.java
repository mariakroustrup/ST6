package com.kol;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.width;

/**
 * Created by Maria on 05/05/2017.
 */

public class TRAENING extends AppCompatActivity {

    TextView textViewTimer, textViewKm;
    Button start, stop;// reset;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    int Seconds, Minutes, MilliSeconds;
    Handler handler;
    PopupWindow myPopUp;
    RelativeLayout positionOfPopup;

    // det her er til GPS
    private Button button;
    private TextView textView, textView10, textView11;
    private LocationManager locationManager;
    private LocationListener locationListener;
    double lat2;
    double lng2;


    ArrayList<Double> listLat = new ArrayList<Double>();
    ArrayList<Double> listLng = new ArrayList<Double>();


    // her starter timer
    public Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + Minutes + ":" + String.format("%02d", Seconds) + ":" + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traening);


        textViewTimer = (TextView) findViewById(R.id.Timer);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        textViewKm = (TextView) findViewById(R.id.km);

        handler = new Handler();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

            }

        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);

                positionOfPopup = (RelativeLayout) findViewById(R.id.popUp_position);
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.popupwindow1, null);

                myPopUp = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                Button closePopUp = (Button) customView.findViewById(R.id.Fortryd);
                closePopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myPopUp.dismiss();
                    }

                });
                Button closePopUp1 = (Button) customView.findViewById(R.id.Bekræft);
                closePopUp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myPopUp.dismiss();
                        setContentView(R.layout.evaluering);
                    }

                });
                myPopUp.showAtLocation(positionOfPopup, Gravity.CENTER, 0, 0);

            }
        });

        // her starter GPS

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) { //Kaldes nÂr lokation opdateres.

              /*  textView.append("\n " + location.getLatitude() + " " + location.getLongitude());*/


                lat2 = location.getLatitude();
                lng2 = location.getLongitude();

                setLat2(lat2);
                setLng2(lng2);

            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {


            }

            @Override
            public void onProviderDisabled(String provider) { // Tjekker om GPS er slukket.
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        configureButton();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                break;
            default:
                break;
        }
    }


    private void configureButton() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }

        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
    start.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                //noinspection MissingPermission
               locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

            }
        });
    }


    public void setLat2(double newLat) {
        listLat.add(newLat);
    }

    public void setLng2(double newLng) {
        listLng.add(newLng);
        //textView10.setText("F¯rste long koordinat er: " + listLng.get(0));
        dis();
    }

    public void dis() {
        double Lng_first = listLng.get(0);
        double Lat_first = listLat.get(0);

        double Lng_last = listLng.get(listLng.size() - 1);
        double Lat_last = listLat.get(listLat.size() - 1);


        //textView10.setText("F¯rste long koordinat er: ");
        distance(Lat_first, Lng_first, Lat_last, Lng_last);
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = ((dist * 60 * 1.1515) * 1609.344/1000);
        textViewKm.setText(String.format("%.3f",dist) + " km");
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }





    // her begynder evalueringen
    int result;

    public void ButtonOnClick(View view) {

        switch (view.getId()) {
            case R.id.smiley_1:
                result = 0;
                OnGone1(view);
                startmenu(0);
                break;
            case R.id.smiley_2:
                result = 1;
                OnGone1(view);
                startmenu(1);
                break;
            case R.id.smiley_3:
                result = 2;
                OnGone1(view);
                startmenu(2);
                break;

        }}

    public void OnGone1(View view) {
        ImageView en = (ImageView) findViewById(R.id.smiley_1);
        en.setEnabled(false);
        en.setClickable(false);
        ImageView to = (ImageView) findViewById(R.id.smiley_2);
        to.setEnabled(false);
        to.setClickable(false);
        ImageView tre = (ImageView) findViewById(R.id.smiley_3);
        tre.setEnabled(false);
        tre.setClickable(false);
        Button videre= (Button) findViewById(R.id.Videreeval);
        videre.setVisibility(view.VISIBLE);
        videre.setEnabled(true);
        videre.setClickable(true);
    }

    public void startmenu(int i){
        Button videre = (Button) findViewById(R.id.Videreeval);
        videre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(TRAENING.this, MENU.class);
                startActivity(myIntent); }});}

}










