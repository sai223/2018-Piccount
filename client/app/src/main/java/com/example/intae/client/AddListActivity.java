package com.example.intae.client;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddListActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    int number_of_detail = 1;

    private void sendTakePhotoIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        ImageButton ListCheckButton = (ImageButton)findViewById(R.id.ListCheckButton);
        ImageButton AddListButton = (ImageButton)findViewById(R.id.AddListButton);
        ImageButton ReportCheckButton = (ImageButton)findViewById(R.id.ReportCheckButton);
        ImageButton SettingsButton = (ImageButton)findViewById(R.id.SettingsButton);
        ImageButton gotoCameraButton = (ImageButton)findViewById(R.id.gotoCameraButton);
        ImageButton addDetailButton = (ImageButton)findViewById(R.id.addDetailButton);

        final Spinner spinnerUpjong = (Spinner) findViewById(R.id.addlistUpjong);
        final ArrayList<String> upjongList = new ArrayList<>();
        upjongList.add("소매");
        upjongList.add("음식");
        upjongList.add("관광/여가/오락");
        upjongList.add("생활서비스");
        upjongList.add("부동산");
        upjongList.add("의료");
        upjongList.add("학문/교육");
        upjongList.add("스포츠");
        ArrayAdapter upjongAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, upjongList);
        spinnerUpjong.setAdapter(upjongAdapter);

        addDetailButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 아직 미구현
                number_of_detail++;
                LinearLayout LL = new LinearLayout(getApplicationContext());
                LL.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                LL.setId(R.id.detail_LinearLayout_2);
            }
        });

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

        gotoCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(intent);
            }
        });

        final DBHandler thisDB = new DBHandler(getApplicationContext(), "Expense.db", null, 1);

        ((EditText)findViewById(R.id.detail_productName_1)).setText("");
        ((EditText)findViewById(R.id.detail_productPrice_1)).setText("0");
        ((EditText)findViewById(R.id.detail_productName_2)).setText("");
        ((EditText)findViewById(R.id.detail_productPrice_2)).setText("0");

        Intent get_intent = getIntent();

        if(get_intent.getStringExtra("shop") != null){
            ((EditText)findViewById(R.id.store_name)).setText(get_intent.getStringExtra("shop"));

            String category = get_intent.getStringExtra("category");
            switch(category){
                case "소매":
                    ((Spinner)findViewById(R.id.addlistUpjong)).setSelection(0);
                    break;
                case "음식":
                    ((Spinner)findViewById(R.id.addlistUpjong)).setSelection(1);
                    break;
                case "관광/여가/오락":
                    ((Spinner)findViewById(R.id.addlistUpjong)).setSelection(2);
                    break;
                case "생활서비스":
                    ((Spinner)findViewById(R.id.addlistUpjong)).setSelection(3);
                    break;
                case "부동산":
                    ((Spinner)findViewById(R.id.addlistUpjong)).setSelection(4);
                    break;
                case "의료":
                    ((Spinner)findViewById(R.id.addlistUpjong)).setSelection(5);
                    break;
                case "학문/교육":
                    ((Spinner)findViewById(R.id.addlistUpjong)).setSelection(6);
                    break;
                case "스포츠":
                    ((Spinner)findViewById(R.id.addlistUpjong)).setSelection(7);
                    break;
            }

            ((EditText)findViewById(R.id.date_year)).setText(get_intent.getIntExtra("date_year", 0)+"");
            ((EditText)findViewById(R.id.date_month)).setText(get_intent.getIntExtra("date_month", 0)+"");
            ((EditText)findViewById(R.id.date_day)).setText(get_intent.getIntExtra("date_day", 0)+"");
            ((EditText)findViewById(R.id.total_price)).setText(get_intent.getIntExtra("totalPrice", 0)+"");

            String[] itemArray = get_intent.getStringArrayExtra("itemArray");
            String[] priceArray = get_intent.getStringArrayExtra("priceArray");

            if(itemArray.length == 2){
                ((EditText)findViewById(R.id.detail_productName_2)).setText(itemArray[1] + "");
                ((EditText)findViewById(R.id.detail_productPrice_2)).setText(priceArray[1] + "");
            }
            ((EditText)findViewById(R.id.detail_productName_1)).setText(itemArray[0] + "");
            ((EditText)findViewById(R.id.detail_productPrice_1)).setText(priceArray[0] + "");
        }

        Button insert_expense = (Button) findViewById(R.id.commit);
        insert_expense.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String store_name = ((EditText) findViewById(R.id.store_name)).getText().toString();
                String upjong = spinnerUpjong.getSelectedItem().toString();
                int date_year = Integer.parseInt(((EditText) findViewById(R.id.date_year)).getText().toString());
                int date_month = Integer.parseInt(((EditText) findViewById(R.id.date_month)).getText().toString());
                int date_day = Integer.parseInt(((EditText) findViewById(R.id.date_day)).getText().toString());
                int total_price = Integer.parseInt(((EditText) findViewById(R.id.total_price)).getText().toString());

                String productName1 = ((EditText) findViewById(R.id.detail_productName_1)).getText().toString();
                int productPrice1 = Integer.parseInt(((EditText)findViewById(R.id.detail_productPrice_1)).getText().toString());

                String productName2 = ((EditText) findViewById(R.id.detail_productName_2)).getText().toString();
                int productPrice2 = Integer.parseInt(((EditText)findViewById(R.id.detail_productPrice_2)).getText().toString());

                int randomID = (int)(Math.random() * 10000000);

                Data_Expense tmp = new Data_Expense(
                        randomID,
                        new UserInfo().getValidID(),
                        store_name,
                        date_year,
                        date_month,
                        date_day,
                        upjong,
                        total_price
                );
                List<Data_Expense> expenseData = thisDB.get_expense_data(1999,1,1,
                        2018,1,1,new UserInfo().getValidID());

                System.out.println("Presize: "+expenseData.size());
                thisDB.add_expense_data(tmp);
                System.out.println("Postsize: "+expenseData.size());
                System.out.println(tmp.upjong+"/"+tmp.store_name+"/"+tmp.total_price);
                Data_Detail_Expense data1 = new Data_Detail_Expense(
                        productName1,
                        randomID,
                        1,
                        productPrice1
                );
                thisDB.add_detail_expense_date(data1);
                if(productName2 != ""){
                    Data_Detail_Expense data2 = new Data_Detail_Expense(
                            productName2,
                            randomID,
                            1,
                            productPrice2
                    );
                    thisDB.add_detail_expense_date(data2);
                }
            }
        });
    }// end of onCreate()
}
