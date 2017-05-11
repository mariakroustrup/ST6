package info.androidhive.loginandregistration.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;*/

import java.util.ArrayList;

import info.androidhive.loginandregistration.R;


/**
 * Created by Maria on 05/05/2017.
 */


public class ResultaterController extends AppCompatActivity {

   /* BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beloeninger);

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
        start(0);*/
    }


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

    } */ }
