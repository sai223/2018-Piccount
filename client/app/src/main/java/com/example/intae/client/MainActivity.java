package com.example.intae.client;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

// HTTP 쓰기 위한 import
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    TabLayout tabLayout;

    HttpConnection httpConn = HttpConnection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sendData(); // 웹 서버로 데이터 전송

        // get the reference of FrameLayout and TabLayout
        frameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);

        // 내역보기 탭 생성
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setIcon(R.drawable.tab1_see_records);

        // 내역추가 탭 생성
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setIcon(R.drawable.tab2_add_records);

        // 리포트 탭 생성
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setIcon(R.drawable.tab3_report);

        // 환경설정 탭 생성
        TabLayout.Tab fourthTab = tabLayout.newTab();
        fourthTab.setIcon(R.drawable.tab4_setting);

        // 생성한 각 탭을 레이아웃에 추가
        tabLayout.addTab(firstTab);
        tabLayout.addTab(secondTab);
        tabLayout.addTab(thirdTab);
        tabLayout.addTab(fourthTab);

        /*
        // 테스트용으로 데이터 추가
        final DBHandler testDB = new DBHandler(
                getApplicationContext(), "Expense.db", null, 1
        );
        testDB.test_data();
        testDB.clear_data();
        */

        // TabLayout의 각 탭을 누를 때 발생하는 Event 설정
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 선택된 탭으로 화면을 바꾼다.
                Fragment fragment = null;
                switch(tab.getPosition()){
                    case 0:
                        fragment = new FirstFragment();
                        break;
                    case 1:
                        fragment = new SecondFragment();
                        break;
                    case 2:
                        fragment = new ThirdFragment();
                        break;
                    case 3:
                        fragment = new FourthFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 딱히 기능 없음
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 딱히 기능 없음
            }
        });

        {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.simpleFrameLayout, new FirstFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }

        /*
        final DBHandler userDB = new DBHandler(getApplicationContext(), "User.db", null, 1);
        final DBHandler expenseDB = new DBHandler(getApplicationContext(), "Expense.db", null, 1);
        final DBHandler detail_expenseDB = new DBHandler(getApplicationContext(), "Detail_Expense.db", null, 1);
        final DBHandler incomeDB = new DBHandler(getApplicationContext(), "Income.db", null, 1);

        final EditText expense_data_store_name = (EditText) findViewById(R.id.store_name);
        //EditText expense_data_upjong = (EditText) findViewById(R.id.upjong);
        final EditText expense_data_date_year = (EditText) findViewById(R.id.date_year);
        final EditText expense_data_date_month = (EditText) findViewById(R.id.date_month);
        final EditText expense_data_date_day = (EditText) findViewById(R.id.date_day);
        final EditText expense_data_total_price = (EditText) findViewById(R.id.total_price);
        final TextView result = (TextView) findViewById(R.id.result);

        // DB에 Expense 데이터 넣기
        Button insert_expense = (Button) findViewById(R.id.commit);
        insert_expense.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String store_name = expense_data_store_name.getText().toString();
                int date_year = Integer.parseInt(expense_data_date_year.getText().toString());
                int date_month = Integer.parseInt(expense_data_date_month.getText().toString());
                int date_day = Integer.parseInt(expense_data_date_day.getText().toString());
                int total_price = Integer.parseInt(expense_data_total_price.getText().toString());

                Data_Expense tmp = new Data_Expense(
                        "temp_user_id",
                        store_name,
                        date_year,
                        date_month,
                        date_day,
                        "temp_upjong",
                        total_price
                );

                expenseDB.add_expense_data(tmp);

                Vector<Data_Expense> tmptmp = expenseDB.get_expense_data(
                        0, 0, 0,
                        10000, 10000, 10000,
                        "temp_user_id"
                );

                for(int i = 0;i < tmptmp.size();i++) {
                    String ttt = tmptmp.elementAt(i).store_name + " "
                            + tmptmp.elementAt(i).date_year + "년 "
                            + tmptmp.elementAt(i).date_month + "월 "
                            + tmptmp.elementAt(i).date_day + "일 "
                            + tmptmp.elementAt(i).upjong + " "
                            + tmptmp.elementAt(i).total_price;
                    result.setText(ttt);
                }
            }
        });
        */

    }// end of onCreate()

    private void sendData(){
        new Thread(){
            public void run(){
                httpConn.requestWebServer("intae", "123", callback);
            }
        }.start();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("TEST", "실패: "+e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("TEST", "성공: " + body);
        }
    };
}
