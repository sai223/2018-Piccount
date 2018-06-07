package com.example.intae.client;

public class Data_Income {
    int income_id;
    String user_id;
    int date_year;
    int date_month;
    int date_day;
    int money;

    Data_Income(
            String _user_id,
            int _date_year,
            int _date_month,
            int _date_day,
            int _money
    ){
        // income_id는 DB에 저장될 때 AUTO_INCREMENT로 자동 할당됨.
        user_id = _user_id;
        date_year = _date_year;
        date_month = _date_month;
        date_day = _date_day;
        money = _money;
    }

    Data_Income(
            int _income_id,
            String _user_id,
            int _date_year,
            int _date_month,
            int _date_day,
            int _money
    ){
        income_id = _income_id;
        user_id = _user_id;
        date_year = _date_year;
        date_month = _date_month;
        date_day = _date_day;
        money = _money;
    }
}
