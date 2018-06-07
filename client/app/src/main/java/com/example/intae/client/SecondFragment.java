package com.example.intae.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

public class SecondFragment extends Fragment {

    public SecondFragment(){
        // empty public constructor가 있어야 함.
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_second, container, false);

        final DBHandler expenseDB = new DBHandler(
                getActivity().getApplicationContext(),
                "Expense.db", null, 1);

        final EditText expense_data_store_name = (EditText) v.findViewById(R.id.store_name);
        //EditText expense_data_upjong = (EditText) v.findViewById(R.id.upjong);
        final EditText expense_data_date_year = (EditText) v.findViewById(R.id.date_year);
        final EditText expense_data_date_month = (EditText) v.findViewById(R.id.date_month);
        final EditText expense_data_date_day = (EditText) v.findViewById(R.id.date_day);
        final EditText expense_data_total_price = (EditText) v.findViewById(R.id.total_price);
        final TextView result = (TextView) v.findViewById(R.id.result);

        Button insert_expense = (Button) v.findViewById(R.id.commit);
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

                List<Data_Expense> tmptmp = expenseDB.get_expense_data(
                        0, 0, 0,
                        10000, 10000, 10000,
                        "temp_user_id"
                );

                for(int i = 0;i < tmptmp.size();i++) {
                    String ttt = tmptmp.get(i).store_name + " "
                            + tmptmp.get(i).date_year + "년 "
                            + tmptmp.get(i).date_month + "월 "
                            + tmptmp.get(i).date_day + "일 "
                            + tmptmp.get(i).upjong + " "
                            + tmptmp.get(i).total_price;
                    result.append(ttt);
                }
            }
        });

        return v;
    }
}
