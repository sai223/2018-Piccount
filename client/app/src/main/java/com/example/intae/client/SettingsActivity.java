package com.example.intae.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ImageButton ListCheckButton = (ImageButton)findViewById(R.id.ListCheckButton);
        ImageButton AddListButton = (ImageButton)findViewById(R.id.AddListButton);
        ImageButton ReportCheckButton = (ImageButton)findViewById(R.id.ReportCheckButton);

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

        Button insertBtn = (Button)findViewById(R.id.setting_insert_btn);
        Button clearBtn = (Button)findViewById(R.id.setting_clear_btn);

        final DBHandler expenseDB = new DBHandler(
                getApplicationContext(),
                "Expense.db", null, 1);

        insertBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Data_Expense tmp1 = new Data_Expense(new UserInfo().getValidID(), "맛구단", 2018, 5, 15, "음식", 13000);
                Data_Expense tmp2 = new Data_Expense(new UserInfo().getValidID(), "GS25", 2018, 5, 17, "소매", 2000);
                Data_Expense tmp3 = new Data_Expense(new UserInfo().getValidID(), "GS25", 2018, 5, 17, "소매", 1300);
                Data_Expense tmp4 = new Data_Expense(new UserInfo().getValidID(), "하나투어", 2018, 5, 16, "관광/여가/오락", 200000);
                Data_Expense tmp5 = new Data_Expense(new UserInfo().getValidID(), "만코쿠", 2018, 5, 20, "음식", 8000);
                Data_Expense tmp6 = new Data_Expense(new UserInfo().getValidID(), "아주대 병원", 2018, 5, 21, "의료", 15000);
                Data_Expense tmp7 = new Data_Expense(new UserInfo().getValidID(), "아주탑치과", 2018, 5, 22, "의료", 20000);
                Data_Expense tmp8 = new Data_Expense(new UserInfo().getValidID(), "고씨네", 2018, 5, 23, "음식", 13000);
                Data_Expense tmp9 = new Data_Expense(new UserInfo().getValidID(), "CU", 2018, 5, 24, "소매", 7000);
                Data_Expense tmp10= new Data_Expense(new UserInfo().getValidID(), "수원 월드컵 경기장", 2018, 5, 25, "스포츠", 70000);
                Data_Expense tmp11 = new Data_Expense(new UserInfo().getValidID(), "메가스터디", 2018, 5, 27, "학문", 150000);
                Data_Expense tmp12 = new Data_Expense(new UserInfo().getValidID(), "세븐일레븐", 2018, 5, 28, "소매", 4000);
                Data_Expense tmp13 = new Data_Expense(new UserInfo().getValidID(), "일등 부동산", 2018, 5, 29, "부동산", 30000);
                Data_Expense tmp14 = new Data_Expense(new UserInfo().getValidID(), "GS25", 2018, 5, 29, "소매", 13000);
                Data_Expense tmp15 = new Data_Expense(new UserInfo().getValidID(), "어처구니", 2018, 5, 30, "음식", 13000);
                Data_Expense tmp16 = new Data_Expense(new UserInfo().getValidID(), "GS25", 2018, 5, 30, "음식", 4500);
                Data_Expense tmp17 = new Data_Expense(new UserInfo().getValidID(), "Cafe 027", 2018, 6,1, "음식", 3000);
                Data_Expense tmp18 = new Data_Expense(new UserInfo().getValidID(), "아주대 병원", 2018, 6, 1, "의료", 13000);
                Data_Expense tmp19 = new Data_Expense(new UserInfo().getValidID(), "GS25", 2018, 6, 2, "소매", 3000);

                expenseDB.add_expense_data(tmp1);
                expenseDB.add_expense_data(tmp2);
                expenseDB.add_expense_data(tmp3);
                expenseDB.add_expense_data(tmp4);
                expenseDB.add_expense_data(tmp5);
                expenseDB.add_expense_data(tmp6);
                expenseDB.add_expense_data(tmp7);
                expenseDB.add_expense_data(tmp8);
                expenseDB.add_expense_data(tmp9);
                expenseDB.add_expense_data(tmp10);
                expenseDB.add_expense_data(tmp11);
                expenseDB.add_expense_data(tmp12);
                expenseDB.add_expense_data(tmp13);
                expenseDB.add_expense_data(tmp14);
                expenseDB.add_expense_data(tmp15);
                expenseDB.add_expense_data(tmp16);
                expenseDB.add_expense_data(tmp17);
                expenseDB.add_expense_data(tmp18);
                expenseDB.add_expense_data(tmp19);
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                expenseDB.clear_data();
            }
        });
    }
}