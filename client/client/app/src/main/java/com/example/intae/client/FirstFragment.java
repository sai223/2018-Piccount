package com.example.intae.client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;


public class FirstFragment extends Fragment {

    public FirstFragment(){
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_first, container, false);

        final CalendarView element1 = (CalendarView) v.findViewById(R.id.contentCalendar);
        final TextView element2 = (TextView) v.findViewById(R.id.contentList);

        Button button1 = (Button) v.findViewById(R.id.calendarButton);
        Button button2 = (Button) v.findViewById(R.id.listButton);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                element1.setVisibility(View.VISIBLE);
                element2.setVisibility(View.GONE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                element1.setVisibility(View.GONE);
                element2.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }
}















