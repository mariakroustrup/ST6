package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import static android.R.id.list;


/**
 * Created by Maria on 02/05/2017.
 */

public class ABCD extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abcd);

       int ABCDscore = getIntent().getExtras().getInt("int_value");


        TextView textView = (TextView)findViewById(R.id.ABCD);
        textView.setText("HEJ" + ABCDscore);

}

}

