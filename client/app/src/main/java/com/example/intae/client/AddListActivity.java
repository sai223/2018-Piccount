package com.example.intae.client;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        Button ListCheckButton = (Button)findViewById(R.id.ListCheckButton);
        Button ReportCheckButton = (Button)findViewById(R.id.ReportCheckButton);
        Button SettingsButton = (Button)findViewById(R.id.SettingsButton);

        ListCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListCheckActivity.class);
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

        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}
