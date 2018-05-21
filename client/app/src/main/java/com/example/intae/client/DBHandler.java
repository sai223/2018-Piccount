package com.example.intae.client;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

public class DBHandler extends SQLiteOpenHelper {
    // DBHandler 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    // DB를 초기에 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db){
        // USER 테이블 생성
        db.execSQL("CREATE TABLE USER " +
                "(user_id  TEXT PRIMARY KEY, " +
                "password TEXT, " +
                "name TEXT, " +
                "birth_date_year INTEGER, " +
                "birth_date_month INTEGER, " +
                "birth_date_day INTEGER, " +
                "phone_number TEXT, " +
                "balance INTEGER, " +
                "backup_date_year INTEGER, " +
                "backup_date_month INTEGER, " +
                "backup_date_day INTEGER)");

        // EXPENSE 테이블 생성
        db.execSQL("CREATE TABLE EXPENSE " +
                "(expense_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id TEXT REFERENCES USER(user_id), " +
                "store_name TEXT, " +
                "date_year INTEGER, " +
                "date_month INTEGER, " +
                "date_day INTEGER, " +
                "upjong TEXT, " +
                "total_price INTEGER)");

        // DETAIL_EXPENSE 테이블 생성
        db.execSQL("CREATE TABLE DETAIL_EXPENSE (" +
                "detail_expense_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "item TEXT, " +
                "expense_id INTEGER REFERENCES EXPENSE(expense_id), " +
                "quantity INTEGER, " +
                "price INTEGER)");

        // INCOME 테이블 생성
        db.execSQL("CREATE TABLE INCOME (" +
                "income_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id TEXT REFERENCES USER(user_id), " +
                "date_year INTEGER, " +
                "date_month INTEGER, " +
                "date_day INTEGER, " +
                "money INTEGER)");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // DB restore할 때 이거 사용/??
    }
    //////////////////////////////////////
    // Methods of USER Table
    //////////////////////////////////////

    // 1) User 데이터를 DB에 저장
    public void add_user_data(Data_User data){
        String query = "INSERT INTO USER VALUES('" + data.user_id + "','"
                + data.password + "','" + data.name + "'," + data.birth_date_year + ","
                + data.birth_date_month + "," + data.birth_date_day + ",'"
                + data.phone_number + "'," + data.balance + "," + data.backup_date_year + ","
                + data.backup_date_month + "," + data.backup_date_day + ");";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    //////////////////////////////////////
    // Methods of EXPENSE Table
    //////////////////////////////////////

    // 1) Expense 데이터를 DB에 저장
    public void add_expense_data(Data_Expense data){
        String query = "INSERT INTO EXPENSE VALUES(NULL,'" + data.user_id + "','"
                + data.store_name + "'," + data.date_year + "," + data.date_month + ","
                + data.date_day + ",'" + data.upjong + "'," + data.total_price + ");";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    // 2) (Expense 데이터의 date값 + User 데이터의 user_id값)을 기준으로 검색
    // => 해당하는 Expense 데이터 배열을 리턴
    public Vector<Data_Expense> get_expense_data(
            int start_date_year,
            int start_date_month,
            int start_date_day,
            int end_date_year,
            int end_date_month,
            int end_date_day,
            String user_id
    ){
        SQLiteDatabase db = getReadableDatabase();
        Vector<Data_Expense> result = new Vector<Data_Expense>(3);

        String query = "SELECT * FROM EXPENSE WHERE user_id = '" + user_id
                + "' AND (date_year >= " + start_date_year + " AND date_year <= " + end_date_year + ")"
                + " AND (date_month >= " + start_date_month + " AND date_month <= " + end_date_month + ")"
                + " AND (date_day >= " + start_date_day + " AND date_day <= " + end_date_day + ");";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Data_Expense tmp = new Data_Expense(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getInt(7)
            );
            result.add(tmp);
        }
        return result;
    }

    // 3) Expense 데이터의 expense_id값을 기준으로 검색
    // => 해당하는 Expense 데이터를 수정
    public void update_expense_data(Data_Expense data){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE EXPENSE SET store_name='" + data.store_name + "',"
                + "date_year=" + data.date_day + ",date_month=" + data.date_month
                + ",date_day=" + data.date_day + ",upjong='" + data.upjong + "',"
                + "total_price=" + data.total_price
                + " WHERE expense_id=" + data.expense_id + ";";
        db.execSQL(query);
        db.close();
    }

    // 4) Expense 데이터의 expense_id값을 기준으로 검색
    // => 해당하는 Expense 데이터를 삭제
    public void delete_expense_data(int _expense_id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM EXPENSE WHERE expense_id=" + _expense_id + "';";
        db.execSQL(query);
        db.close();
    }

    //////////////////////////////////////
    // Methods of DETAIL_EXPENSE Table
    //////////////////////////////////////

    // 1) Detail_Expense 데이터를 DB에 저장
    public void add_detail_expense_date(Data_Detail_Expense data){
        String query = "INSERT INTO DETAIL_EXPENSE VALUES(NULL,'" + data.item + "',"
                + data.expense_id + "," + data.quantity + "," + data.price + ");";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    // 2) Expense 데이터의 expense_id값을 기준으로 검색
    // => 해당하는 Detail_Expense 데이터 배열을 리턴
    public Vector<Data_Detail_Expense> get_detail_expense_data(
            int _expense_id
    ){
        SQLiteDatabase db = getReadableDatabase();
        Vector<Data_Detail_Expense> result = new Vector<Data_Detail_Expense>(3);
        String query = "SELECT * FROM DETAIL_EXPENSE WHERE expense_id=" + _expense_id + ";";

        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Data_Detail_Expense tmp = new Data_Detail_Expense(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            );
            result.add(tmp);
        }
        return result;
    }
    // 3) Detail_Expense 데이터의 detail_expense_id를 기준으로 검색
    // => 해당하는 Detail_Expense 데이터를 수정
    public void update_detail_expense_data(Data_Detail_Expense data){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE DETAIL_EXPENSE SET item='" + data.item
                + "',quantity=" + data.quantity + ",price" + data.price
                + " WHERE detail_expense_id=" + data.detail_expense_id + ";";
        db.execSQL(query);
        db.close();
    }

    // 4) Detail_Expense 데이터의 detail_expense_id를 기준으로 검색
    // => 해당하는 Detail_Expense 데이터를 삭제
    public void delete_detail_expense_data(int _detail_expense_id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM DETAIL_EXPENSE WHERE " +
                "detail_expense_id=" + _detail_expense_id + ";";
        db.execSQL(query);
        db.close();
    }

    //////////////////////////////////////
    // Methods of INCOME Table
    //////////////////////////////////////

    // 1) Income 데이터를 DB에 저장
    public void add_income_data(Data_Income data){
        String query = "INSERT INTO INCOME VALUES(NULL,'" + data.user_id + "',"
                + data.date_year + "," + data.date_month + "," + data.date_day + ","
                + data.money + ");";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    // 2) (Income 데이터의 date값 + User 데이터의 user_id값)을 기준으로 검색
    // => 해당하는 Income 데이터 배열을 리턴
    public Vector<Data_Income> get_income_data(
            int start_date_year,
            int start_date_month,
            int start_date_day,
            int end_date_year,
            int end_date_month,
            int end_date_day,
            String user_id
    ){
        SQLiteDatabase db = getReadableDatabase();
        Vector<Data_Income> result = new Vector<Data_Income>(3);

        String query = "SELECT * FROM INCOME WHERE user_id='" + user_id
                + "' AND (date_year >= " + start_date_year + " AND date_year <= " + end_date_year + ")"
                + " AND (date_month >= " + start_date_month + " AND date_month <= " + end_date_month + ")"
                + " AND (date_day >= " + start_date_day + " AND date_day <= " + end_date_day + ");";

        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Data_Income tmp = new Data_Income(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            );
            result.add(tmp);
        }
        return result;
    }

    // 3) Income 데이터의 income_id값을 기준으로 검색
    // => 해당하는 Income 데이터를 수정/삭제
    public void update_income_data(Data_Income data){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE INCOME SET date_year=" + data.date_year
                + ",date_month=" + data.date_month + ",date_day=" + data.date_day
                + ",money=" + data.money + " WHERE income_id=" + data.income_id + ";";
        db.execSQL(query);
        db.close();
    }

    // 4) Income 데이터의 income_id값을 기준으로 검색
    // => 해당하는 Income 데이터를 삭제
    public void delete_income_data(int _income_id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM INCOME WHERE income_id=" + _income_id + ";";
        db.execSQL(query);
        db.close();
    }
}
















