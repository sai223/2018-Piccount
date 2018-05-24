package com.example.intae.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button ListCheckButton = (Button)findViewById(R.id.ListCheckButton);
        Button AddListButton = (Button)findViewById(R.id.AddListButton);
        Button ReportCheckButton = (Button)findViewById(R.id.ReportCheckButton);

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

        ReportCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReportCheckActivity.class);
                startActivity(intent);
            }
        });
    }
}
