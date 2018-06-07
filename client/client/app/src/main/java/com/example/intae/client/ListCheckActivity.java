package com.example.intae.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class ListCheckActivity extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_check);
        ImageButton AddListButton = (ImageButton)findViewById(R.id.AddListButton);
        ImageButton ReportCheckButton = (ImageButton)findViewById(R.id.ReportCheckButton);
        ImageButton SettingsButton = (ImageButton)findViewById(R.id.SettingsButton);
        final DBHandler expenseDB = new DBHandler(
                getApplicationContext(),
                "Expense.db", null, 1);

        Date current = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MM");
        SimpleDateFormat format3 = new SimpleDateFormat("dd");

        int current_year = Integer.parseInt(format1.format(current));
        int current_month = Integer.parseInt(format2.format(current));
        int current_day = Integer.parseInt(format3.format(current));

        List<Data_Expense> expenseData = expenseDB.get_expense_data(0,0,0,
                current_year,current_month,current_day,new UserInfo().getValidID());
        Collections.sort(expenseData,new CompareDateDesc());

        adapter = new ListViewAdapter();

        listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        for(int i =0;i<expenseData.size();i++){
            adapter.addVO(expenseData.get(i).store_name,""+expenseData.get(i).total_price,
                    ""+expenseData.get(i).date_year+"/"+expenseData.get(i).date_month+"/"+expenseData.get(i).date_day,""+expenseData.get(i).expense_id);
        }
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

        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
    static class CompareDateDesc implements Comparator<Data_Expense>{
        @Override
        public int compare(Data_Expense o1, Data_Expense o2) {
            if(o1.getDateTotal()>o2.getDateTotal()){
                return -1;
            }else{
                return 1;
            }
        }
    }
}

