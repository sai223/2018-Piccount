package com.example.intae.client;

public class Data_User {
    String user_id;
    String password;
    String name;
    int birth_date_year;
    int birth_date_month;
    int birth_date_day;
    String phone_number;
    int balance;
    int backup_date_year;
    int backup_date_month;
    int backup_date_day;

    Data_User(
            String _user_id,
            String _password,
            String _name,
            int _birth_date_year,
            int _birth_date_month,
            int _birth_date_day,
            String _phone_number,
            int _balance,
            int _backup_date_year,
            int _backup_date_month,
            int _backup_date_day
    ){
        user_id = _user_id;
        password = _password;
        name = _name;
        birth_date_year = _birth_date_year;
        birth_date_month = _birth_date_month;
        birth_date_day = _birth_date_day;
        phone_number = _phone_number;
        balance = _balance;
        backup_date_year = _backup_date_year;
        backup_date_month = _backup_date_month;
        backup_date_day = _backup_date_day;
    }
}
