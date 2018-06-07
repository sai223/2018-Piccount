package com.example.intae.client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class ReportCheckActivity extends AppCompatActivity {
    PieChart pieChart;
    int i;
    int startYear;
    int startMonth;
    int startDay;
    private Spinner spinner_start_year;
    private Spinner spinner_end_year;
    private Spinner spinner_start_month;
    private Spinner spinner_end_month;
    private Spinner spinner_start_day;
    private Spinner spinner_end_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_check);


        Date current = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MM");
        SimpleDateFormat format3 = new SimpleDateFormat("dd");
        int current_year = Integer.parseInt(format1.format(current));
        int current_month = Integer.parseInt(format2.format(current));
        int current_day = Integer.parseInt(format3.format(current));
        int endYear=current_year;
        int endMonth = current_month;
        int endDay = current_day;
        setPreDate(endYear,endMonth,endDay);
        ImageButton ListCheckButton = (ImageButton)findViewById(R.id.ListCheckButton);
        ImageButton AddListButton = (ImageButton)findViewById(R.id.AddListButton);
        ImageButton SettingsButton = (ImageButton)findViewById(R.id.SettingsButton);
        Button RefreshButton = (Button)findViewById(R.id.reportCheckRefreshButton);

        float sumOfRetail;
        float sumOfFood;
        float sumOfEntertainment;
        float sumOfService;
        float sumOfRealty;
        float sumOfHealth;
        float sumOfEducation;
        float sumOfSports;

        final ArrayList<String> yearList = new ArrayList<>();
        final ArrayList<String> monthList = new ArrayList<>();
        final ArrayList<String> dayList1 = new ArrayList<>();
        final ArrayList<String> dayList2 = new ArrayList<>();
        final ArrayList<String> dayList3 = new ArrayList<>();
        for(i = 1999;i<=2018;i++){
            yearList.add(""+i);
        }
        for(i=1;i<=12;i++){
            monthList.add(""+i);
        }
        for(i=1;i<=29;i++){
            dayList1.add(""+i);
        }
        for(i=1;i<=30;i++){
            dayList2.add(""+i);
        }
        for(i=1;i<=31;i++){
            dayList3.add(""+i);
        }
        /**
         spinner_start_year = (Spinner)findViewById(R.id.start_year);
         spinner_start_month = (Spinner)findViewById(R.id.start_month);
         spinner_start_day = (Spinner)findViewById(R.id.start_day);
         spinner_end_year = (Spinner)findViewById(R.id.end_year);
         spinner_end_month = (Spinner)findViewById(R.id.end_month);
         spinner_end_day = (Spinner)findViewById(R.id.end_day);
         ArrayAdapter yearAdapter1 = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,yearList);
         ArrayAdapter yearAdapter2 = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,yearList);
         ArrayAdapter monthAdapter1 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,monthList);
         ArrayAdapter monthAdapter2 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,monthList);
         final ArrayAdapter dayAdapter1 = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,dayList1);
         final ArrayAdapter dayAdapter2 = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,dayList2);
         final ArrayAdapter dayAdapter3 = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,dayList3);
         final ArrayAdapter dayAdapter4 = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,dayList1);
         final ArrayAdapter dayAdapter5 = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,dayList2);
         final ArrayAdapter dayAdapter6 = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,dayList3);

         spinner_start_year.setAdapter(yearAdapter1);
         spinner_start_month.setAdapter(monthAdapter1);
         spinner_start_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinner_start_month.getSelectedItem().equals("1")||
        spinner_start_month.getSelectedItem().equals("3")||
        spinner_start_month.getSelectedItem().equals("5")||
        spinner_start_month.getSelectedItem().equals("7")||
        spinner_start_month.getSelectedItem().equals("8")||
        spinner_start_month.getSelectedItem().equals("10")||
        spinner_start_month.getSelectedItem().equals("12")) {
        spinner_start_day.setAdapter(dayAdapter3);
        }else if(spinner_start_month.getSelectedItem().equals("2")){
        spinner_start_day.setAdapter(dayAdapter1);
        }else{
        spinner_start_day.setAdapter(dayAdapter2);
        }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
        });
         spinner_end_year.setAdapter(yearAdapter2);
         spinner_end_month.setAdapter(monthAdapter2);
         spinner_end_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinner_end_month.getSelectedItem().equals("1")||
        spinner_end_month.getSelectedItem().equals("3")||
        spinner_end_month.getSelectedItem().equals("5")||
        spinner_end_month.getSelectedItem().equals("7")||
        spinner_end_month.getSelectedItem().equals("8")||
        spinner_end_month.getSelectedItem().equals("10")||
        spinner_end_month.getSelectedItem().equals("12")) {
        spinner_end_day.setAdapter(dayAdapter6);
        }else if(spinner_end_month.getSelectedItem().equals("2")){
        spinner_end_day.setAdapter(dayAdapter4);
        }else{
        spinner_end_day.setAdapter(dayAdapter5);
        }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
        });
         **/
        /******************************************/
        final DBHandler expenseDB = new DBHandler(
                getApplicationContext(),
                "Expense.db", null, 1);
        // expenseDB.clear_data();
        /*****************************************************/
        //spinner_start_year.setSelection(10);
        //spinner_start_month.setSelection(10);
        //spinner_start_day.setSelection(10);

        List<Data_Expense> expenseData = expenseDB.get_expense_data(startYear,startMonth,startDay,
                endYear,endMonth,endDay,new UserInfo().getValidID());
        Log.d("Size",""+expenseData.size());

        sumOfRetail = sumOfUpjong("소매",expenseData);
        sumOfFood = sumOfUpjong("음식",expenseData);
        sumOfEntertainment = sumOfUpjong("관광/여가/오락",expenseData);
        sumOfService = sumOfUpjong("생활서비스",expenseData);
        sumOfRealty = sumOfUpjong("부동산",expenseData);
        sumOfSports = sumOfUpjong("의료",expenseData);
        sumOfHealth = sumOfUpjong("학문/교육",expenseData);
        sumOfEducation = sumOfUpjong("스포츠",expenseData);
        Log.d("소매",""+sumOfRetail);
        Log.d("food",""+sumOfFood);

        /******************************************/
        pieChart = (PieChart)findViewById(R.id.report);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setEntryLabelColor(Color.BLACK);
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();


        if(sumOfRetail!=0){
            yValues.add(new PieEntry(sumOfRetail,"소매"));
        }
        if(sumOfFood!=0){
            yValues.add(new PieEntry(sumOfFood,"음식"));
        }
        if(sumOfEntertainment!=0){
            yValues.add(new PieEntry(sumOfEntertainment,"오락"));
        }
        if(sumOfService!=0){
            yValues.add(new PieEntry(sumOfService,"서비스"));
        }
        if(sumOfRealty!=0){
            yValues.add(new PieEntry(sumOfRealty,"부동산"));
        }
        if(sumOfHealth!=0){
            yValues.add(new PieEntry(sumOfHealth,"의료"));
        }
        if(sumOfEducation!=0){
            yValues.add(new PieEntry(sumOfEducation,"교육"));
        }
        if(sumOfSports!=0){
            yValues.add(new PieEntry(sumOfSports,"스포츠"));
        }
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"지출 카테고리");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(10f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

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
        RefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    public float sumOfUpjong(String upjong, List<Data_Expense> expenseData){
        float sum=0;
        int vectorSize = expenseData.size();
        for(int i=0;i<vectorSize;i++) {
            if(expenseData.get(i).upjong.equals(upjong)){
                sum +=expenseData.get(i).total_price;
            }
        }
        return sum;
    }
    public void setPreDate(int _endYear, int _endMonth, int _endDay){
        if(_endMonth == 1){
            startYear = _endYear-1;
            startMonth = 12;
            if(_endDay==31){
                startDay = 29;
            }else{
                startDay = _endDay;
            }

        }else{
            startYear = _endYear;
            startMonth = _endMonth-1;
            if(_endDay==31){
                startDay = 29;
            }else{
                startDay = _endDay;
            }
        }
    }

}