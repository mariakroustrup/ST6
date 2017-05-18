package info.androidhive.loginandregistration.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import info.androidhive.loginandregistration.R;

public class grafController extends AppCompatActivity {

    BarChart barChart;
    public int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultatgraf);



        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] days = new String[7];
        for (int i = 0; i < 7; i++)
        {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }



        barChart = (BarChart)findViewById(R.id.bargraf);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        x=3;
        barEntries.add(new BarEntry(x,0));
        barEntries.add(new BarEntry(14f,1));
        barEntries.add(new BarEntry(44,2));
        barEntries.add(new BarEntry(74f,3));
        barEntries.add(new BarEntry(44f,4));
        barEntries.add(new BarEntry(34f,5));
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


    }

}
