package com.example.intae.client;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>() ;
    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItems.size() ;
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //postion = ListView의 위치      /   첫번째면 position = 0
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView storeName= (TextView) convertView.findViewById(R.id.storeName) ;
        TextView expenseAmount = (TextView) convertView.findViewById(R.id.expenseAmount);
        TextView expenseID = (TextView)convertView.findViewById(R.id.expense_id);
        ImageButton detailButton = (ImageButton) convertView.findViewById(R.id.detailButton);
        ImageButton modificationButton = (ImageButton)convertView.findViewById(R.id.listChangeButton);

        expenseID.setVisibility(View.GONE);
        final ListViewItem listViewItem = listViewItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        date.setText(listViewItem.getDate());
        storeName.setText(listViewItem.getStoreName());
        expenseAmount.setText(listViewItem.getExpenseAmount());

        detailButton.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                            }
                                        });
        modificationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public Object getItem(int position) {
        return listViewItems.get(position) ;
    }

    // 데이터값 넣어줌
    public void addVO(String _store_name, String _expense_amount, String _date, String _expense_id) {
        ListViewItem item = new ListViewItem(_store_name,_expense_amount,_date, _expense_id);
        listViewItems.add(item);
    }
}
