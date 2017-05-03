package com.kol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Maria on 02/05/2017.
 */

public class ABCD extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abcd);
}


        int sum;

        void setSum (int s){
            s = sum;
            TextView output = (TextView) findViewById(R.id.ABCD);
            output.setText(s);
        }
        int getSum(){
            return sum;
        }

}
