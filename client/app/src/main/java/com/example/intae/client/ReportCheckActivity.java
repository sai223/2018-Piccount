package com.example.intae.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReportCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_check);
        Button ListCheckButton = (Button)findViewById(R.id.ListCheckButton);
        Button AddListButton = (Button)findViewById(R.id.AddListButton);
        Button SettingsButton = (Button)findViewById(R.id.SettingsButton);

        ListCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListCheckActivity.class);
                startActivity(intent);
            }
        });

        AddListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddListActivity.class);
                startActivity(intent);
            }
        });


        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
