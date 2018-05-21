package com.example.intae.client;

public class Data_Expense {
    int expense_id;
    String user_id;
    String store_name;
    int date_year;
    int date_month;
    int date_day;
    String upjong;
    int total_price;

    Data_Expense(
            String _user_id,
            String _store_name,
            int _date_year,
            int _date_month,
            int _date_day,
            String _upjong,
            int _total_price
    ){
        // expense_id는 DB에 저장될 때 AUTO_INCREMENT로 자동 할당됨.
        user_id = _user_id;
        store_name = _store_name;
        date_year = _date_year;
        date_month = _date_month;
        date_day = _date_day;
        upjong = _upjong;
        total_price = _total_price;
    }

    Data_Expense(
            int _expense_id,
            String _user_id,
            String _store_name,
            int _date_year,
            int _date_month,
            int _date_day,
            String _upjong,
            int _total_price
    ){
        expense_id = _expense_id;
        user_id = _user_id;
        store_name = _store_name;
        date_year = _date_year;
        date_month = _date_month;
        date_day = _date_day;
        upjong = _upjong;
        total_price = _total_price;
    }
}
