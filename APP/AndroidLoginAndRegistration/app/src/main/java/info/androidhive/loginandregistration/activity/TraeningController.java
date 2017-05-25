package info.androidhive.loginandregistration.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import info.androidhive.loginandregistration.R;
import static info.androidhive.loginandregistration.activity.TilpasningController.kondi;


public class TraeningController extends AppCompatActivity {
    // det her er til timer
    TextView textViewTimer, textViewKm;
    Button start, stop;// reset;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    int Time, Seconds, Minutes, MilliSeconds;
    String medlemsid;
    Handler handler;
    PopupWindow myPopUp;
    RelativeLayout positionOfPopup;

    // det her er til GPS
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
        Time = Minutes /60;

        //textViewTimer.setText(" " +Time + ":" + "" + Minutes + ":" + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
            Long m = getMinutes();

//
      //MediaPlayer mediaPlayer = new MediaPlayer();


            if (Minutes == m && Seconds == 0){
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.beep);
         mediaPlayer.start();
         }
//           mediaPlayer.start();
//            MediaPlayer song = MediaPlayer.create(TraeningController.this, R.raw.beep);
//            if (UpdateTime == 2){
//                song.start();
//            }

        }
    };

    long mm;
    public long getMinutes(){
        return minutter;
    }
    public void setMinutes(long newminutter){
        minutter = newminutter;
    }
    long minutter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traening);
        long min = getIntent().getLongExtra("Value", mm);
        Log.d(String.valueOf(min),"hej");
        medlemsid = getIntent().getStringExtra("medlemsid");
        //MediaPlayer mediaPlayer = new MediaPlayer();
        //mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.beep);
        //minutter = Long.parseLong(min);

        setMinutes(min);



        // MediaPlayer song = MediaPlayer.create(TraeningController.this, R.raw.beep);
        // if (UpdateTime == 2){
        //    song.start();
        //}

        textViewTimer = (TextView) findViewById(R.id.Timer);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        textViewKm = (TextView) findViewById(R.id.km);

        handler = new Handler();


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnVisible(view);

                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);

                locationManager.removeUpdates(locationListener);

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
                        OnVisible1(v);
                        myPopUp.dismiss();
                    }

                });
                Button closePopUp1 = (Button) customView.findViewById(R.id.Bekræft);
                closePopUp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //********* Her gemmer vi tid og afstand ********

                        kondi.setTid((int) (UpdateTime/1000));
                        kondi.setAfstand(getnyDist());

                        myPopUp.dismiss();
                        // if(locationManager != null){
                        locationManager.removeUpdates(locationListener);
                        //    locationManager = null;}
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
        public void onLocationChanged(Location location) {

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

   /* public void onPause(){
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }*/


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
            public void onClick(View view) {
                OnGone(view);
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                StopNotifikation();

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



        if (listLat.size() > 1) {
            double Lng_last = listLng.get(listLng.size() - 1);
            double Lat_last = listLat.get(listLat.size() - 1);
            double Lng_nl = listLng.get(listLng.size() - 2);
            double Lat_nl = listLat.get(listLat.size() - 2);
            distance(Lat_nl, Lng_nl, Lat_last, Lng_last);

        }

    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double gammelDist = getnyDist();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = ((dist * 60 * 1.1515) * 1609.344 / 1000);
        double nyDist = gammelDist + dist;
        textViewKm.setText(String.format("%.2f", nyDist) + " km");
        setDist(dist);
        setnyDist(nyDist);
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    double distance = 0;
    double distance1 = 0;

    private void setDist(double newDist){
        distance = newDist;
    }

    public double getDist(){
        return distance;
    }

    private void setnyDist(double newDist){
        distance1 = newDist;
    }

    public double getnyDist(){
        return distance1;
    }


    // her begynder evalueringen
    int evaluering;

    public void ButtonOnClickSur(View view) {
        switch (view.getId()) {
            case R.id.smiley_Sur:
                evaluering = 1;
                OnGone1(view);
                startmenu(1);
                kondi.setEvaluering(evaluering);
                break;
        }
    }

    public void ButtonOnClickMiddel(View view) {
        switch (view.getId()) {
            case R.id.smiley_Middel:
                evaluering = 2;
                OnGone1(view);
                startmenu(1);
                kondi.setEvaluering(evaluering);
                break;
        }
    }

    public void ButtonOnClickGlad(View view) {
        switch (view.getId()) {
            case R.id.smiley_glad:
                evaluering = 3;
                OnGone1(view);
                startmenu(1);
                kondi.setEvaluering(evaluering);
                break;
       }
    }

    public void OnGone1(View view) {
        ImageView en = (ImageView) findViewById(R.id.smiley_glad);
        en.setEnabled(false);
        en.setClickable(false);
        ImageView to = (ImageView) findViewById(R.id.smiley_Middel);
        to.setEnabled(false);
        to.setClickable(false);
        ImageView tre = (ImageView) findViewById(R.id.smiley_Sur);
        tre.setEnabled(false);
        tre.setClickable(false);
        Button videre = (Button) findViewById(R.id.Videreeval);
        videre.setVisibility(view.VISIBLE);
        videre.setEnabled(true);
        videre.setClickable(true);
    }

    public void startmenu(int i) {
        Button videre = (Button) findViewById(R.id.Videreeval);
        videre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // scheduleNotification(getNotification("Har du husket at træne i dag?"));


                String temp1 = kondi.getKondi_type();
                int temp2 = kondi.getHelbredstilstand();
                int temp3 = kondi.getTid();
                int temp4 = kondi.getEvaluering();
                double temp5 = kondi.getAfstand();

                boolean error = kondi.gemTraening(medlemsid, temp1, temp2, temp3, temp4, temp5);
                if(error == false){
                    Toast.makeText(getApplicationContext(), "Træningen er gemt!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Træningen kunne ikke gemmes!", Toast.LENGTH_LONG).show();
                }

                Intent myIntent = new Intent(TraeningController.this, MenuController.class);
                startActivity(myIntent);
                kondi.hent_b_resultater(medlemsid);
                finish();
                scheduleNotification(getNotification("Har du husket at træne i dag?"));
            }
        });
    }


    // Gør de andre knapper synlige igen og videre knappen usynlig
    public void OnGone(View view) {
        Button start = (Button) findViewById(R.id.start);
        start.setVisibility(view.VISIBLE);
        start.setEnabled(true);
        start.setClickable(true);
        Button stop = (Button) findViewById(R.id.stop);
        stop.setVisibility(view.VISIBLE);
        stop.setEnabled(true);
        stop.setClickable(true);
        Button start1 = (Button) findViewById(R.id.start);
        start1.setVisibility(view.INVISIBLE);
        start1.setEnabled(true);
        start1.setClickable(true);
    }


    public void OnVisible(View view) {
        Button start = (Button) findViewById(R.id.start);
        start.setVisibility(view.INVISIBLE);
        start.setEnabled(false);
        start.setClickable(false);
        Button stop = (Button) findViewById(R.id.stop);
        stop.setVisibility(view.VISIBLE);
        stop.setEnabled(true);
        stop.setClickable(true);
    }

    public void OnVisible1(View view) {
        Button start = (Button) findViewById(R.id.start);
        start.setVisibility(view.VISIBLE);
        start.setEnabled(true);
        start.setClickable(true);
        Button stop = (Button) findViewById(R.id.stop);
        stop.setVisibility(view.INVISIBLE);
        stop.setEnabled(true);
        stop.setClickable(true);
    }

    private void scheduleNotification(Notification notification){
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra("NOTIFICATION_ID",1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    Calendar calendar = Calendar.getInstance();
    Calendar now = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 15);
    calendar.set(Calendar.MINUTE, 00);
    calendar.set(Calendar.SECOND, 00);
        if(now.after(calendar)){
        calendar.add(Calendar.DATE,1);
    }

        long tiden = calendar.getTimeInMillis();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tiden, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification (String content){
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("KOL APP");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.notification);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        return builder.build();
    }

    public void StopNotifikation(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

}











//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.media.MediaPlayer;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.SystemClock;
//import android.provider.Settings;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import info.androidhive.loginandregistration.R;
//
//
//public class TraeningController extends AppCompatActivity {
//
//    //Button btnFortryd, btnBekræft;
//
//
//
//    TextView textViewTimer, textViewKm;
//    Button start, stop;// reset;
//    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
//    int Seconds, Minutes, MilliSeconds;
//    Handler handler;
//    PopupWindow myPopUp;
//    RelativeLayout positionOfPopup;
//
//    // det her er til GPS
//    private LocationManager locationManager;
//    private LocationListener locationListener;
//    double lat2;
//    double lng2;
//
//    ArrayList<Double> listLat = new ArrayList<Double>();
//    ArrayList<Double> listLng = new ArrayList<Double>();
//
//
//    // her starter timer
//    public Runnable runnable = new Runnable() {
//
//        public void run() {
//            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
//            UpdateTime = TimeBuff + MillisecondTime;
//            Seconds = (int) (UpdateTime / 1000);
//            Minutes = Seconds / 60;
//            Seconds = Seconds % 60;
//            MilliSeconds = (int) (UpdateTime % 1000);
//
//            textViewTimer.setText("" + Minutes + ":" + String.format("%02d", Seconds));
//
//            handler.postDelayed(this, 0);
//
//
////            Long m = getMinutes();
////            MediaPlayer player = MediaPlayer.create(TraeningController.this, R.raw.beep);
////            if (Minutes == m && Seconds == 0) {
////                player.start();
////            }
//
//        }
//
//    };
//
//
////    long mm;
////    long minutter;
////
////    public long getMinutes(){
////        return minutter;
////    }
////
////    public void setMinutes(long newminutter){
////        minutter = newminutter;
////    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.traening);
////        long min = getIntent().getLongExtra("Value", mm);
////        setMinutes(min);
//
//
//        textViewTimer = (TextView) findViewById(R.id.Timer);
//        start = (Button) findViewById(R.id.start);
//        stop = (Button) findViewById(R.id.stop);
//        textViewKm = (TextView) findViewById(R.id.km);
//
//        handler = new Handler();
//
//
//        stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnVisible(view);
//                TimeBuff += MillisecondTime;
//                handler.removeCallbacks(runnable);
//                setContentView(R.layout.evaluering);
//
////                positionOfPopup = (RelativeLayout) findViewById(R.id.popUp_position);
////                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
////                View customView = inflater.inflate(R.layout.popupwindow1, null);
//
////                myPopUp = new PopupWindow(
////                        customView,
////                        RelativeLayout.LayoutParams.WRAP_CONTENT,
////                        RelativeLayout.LayoutParams.WRAP_CONTENT
////                );
//
//                Button btnFortryd =(Button)findViewById(R.id.Fortryd);
//                btnFortryd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        OnVisible1(v);
//                       // myPopUp.dismiss();
//                    }
//
//                });
//                Button btnBekræft =(Button)findViewById(R.id.Bekræft);
//                btnBekræft.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                      //  myPopUp.dismiss();
//                        locationManager.removeUpdates(locationListener);
//                        setContentView(R.layout.evaluering);
//                    }
//
//                });
//                myPopUp.showAtLocation(positionOfPopup, Gravity.CENTER, 0, 0);
//
//            }
//        });
//
//        // her starter GPS
//
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) { //Kaldes nÂr lokation opdateres.
//
//              /*  textView.append("\n " + location.getLatitude() + " " + location.getLongitude());*/
//
//
//                lat2 = location.getLatitude();
//                lng2 = location.getLongitude();
//
//                setLat2(lat2);
//                setLng2(lng2);
//
//            }
//
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) { // Tjekker om GPS er slukket.
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//            }
//        };
//
//        configureButton();
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 10:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                    configureButton();
//                break;
//            default:
//                break;
//        }
//    }
//
//
//    private void configureButton() {
//        // first check for permissions
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
//                        , 10);
//            }
//            return;
//        }
//
//        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnGone(view);
//                //noinspection MissingPermission
//                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
//                StartTime = SystemClock.uptimeMillis();
//                handler.postDelayed(runnable, 0);
//
//            }
//        });
//    }
//
//
//    public void setLat2(double newLat) {
//        listLat.add(newLat);
//    }
//
//    public void setLng2(double newLng) {
//        listLng.add(newLng);
//        //textView10.setText("F¯rste long koordinat er: " + listLng.get(0));
//        dis();
//    }
//
//    public void dis() {
//
//        double Lng_last = listLng.get(listLng.size() - 1);
//        double Lat_last = listLat.get(listLat.size() - 1);
//
//
//        if (listLat.size() > 1) {
//            double Lng_nl = listLng.get(listLng.size() - 2);
//            double Lat_nl = listLat.get(listLat.size() - 2);
//            distance(Lat_nl, Lng_nl, Lat_last, Lng_last);
//
//        }
//    }
//
//
//    private double distance(double lat1, double lon1, double lat2, double lon2) {
//        double gammelDist = getDist();
//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1))
//                * Math.sin(deg2rad(lat2))
//                + Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2))
//                * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = ((dist * 60 * 1.1515) * 1609.344 / 1000);
//        double nyDist = gammelDist + dist;
//        textViewKm.setText(String.format("%.2f", nyDist) + " km");
//        setDist(dist);
//        return (dist);
//    }
//
//
//    private double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//
//    private double rad2deg(double rad) {
//        return (rad * 180.0 / Math.PI);
//    }
//
//    double distance = 0;
//
//    private void setDist(double newDist){
//        distance = newDist;
//    }
//
//    public double getDist(){
//        return distance;
//    }
//
//
//
//
//
//
//    // her begynder evalueringen
//    int result;
//
//    public void ButtonOnClick(View view) {
//
//        switch (view.getId()) {
//            case R.id.smiley_1:
//                result = 0;
//                OnGone1(view);
//                startmenu(0);
//                break;
//            case R.id.smiley_2:
//                result = 1;
//                OnGone1(view);
//                startmenu(1);
//                break;
//            case R.id.smiley_3:
//                result = 2;
//                OnGone1(view);
//                startmenu(2);
//                break;
//
//        }}
//
//    public void OnGone1(View view) {
//        ImageView en = (ImageView) findViewById(R.id.smiley_1);
//        en.setEnabled(false);
//        en.setClickable(false);
//        ImageView to = (ImageView) findViewById(R.id.smiley_2);
//        to.setEnabled(false);
//        to.setClickable(false);
//        ImageView tre = (ImageView) findViewById(R.id.smiley_3);
//        tre.setEnabled(false);
//        tre.setClickable(false);
//        Button videre= (Button) findViewById(R.id.Videreeval);
//        videre.setVisibility(view.VISIBLE);
//        videre.setEnabled(true);
//        videre.setClickable(true);
//    }
//
//    public void startmenu(int i){
//        Button videre = (Button) findViewById(R.id.Videreeval);
//        videre.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent myIntent = new Intent(TraeningController.this, MenuController.class);
//                startActivity(myIntent); }});}
//
//    public void OnGone(View view) {
//        Button start = (Button) findViewById(R.id.start);
//        start.setVisibility(view.VISIBLE);
//        start.setEnabled(true);
//        start.setClickable(true);
//        Button stop = (Button) findViewById(R.id.stop);
//        stop.setVisibility(view.VISIBLE);
//        stop.setEnabled(true);
//        stop.setClickable(true);
//        Button start1 = (Button) findViewById(R.id.start);
//        start1.setVisibility(view.INVISIBLE);
//        start1.setEnabled(true);
//        start1.setClickable(true);
//    }
//
//    public void OnVisible(View view) {
//        Button start = (Button) findViewById(R.id.start);
//        start.setVisibility(view.INVISIBLE);
//        start.setEnabled(true);
//        start.setClickable(true);
//        Button stop = (Button) findViewById(R.id.stop);
//        stop.setVisibility(view.VISIBLE);
//        stop.setEnabled(true);
//        stop.setClickable(true);
//    }
//
//    public void OnVisible1(View view) {
//        Button start = (Button) findViewById(R.id.start);
//        start.setVisibility(view.VISIBLE);
//        start.setEnabled(true);
//        start.setClickable(true);
//        Button stop = (Button) findViewById(R.id.stop);
//        stop.setVisibility(view.INVISIBLE);
//        stop.setEnabled(true);
//        stop.setClickable(true);
//    }
//
//}
//



