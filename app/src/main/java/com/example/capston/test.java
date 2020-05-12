package com.example.capston;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class test extends AppCompatActivity {
    TextView logtest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        String log = intent.getExtras().getString("logtest");

        logtest = (TextView)findViewById(R.id.Logtest);
        logtest.setText(log);


    }
}
