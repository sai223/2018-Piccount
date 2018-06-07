package com.example.intae.client;

public class Data_Detail_Expense {
    int detail_expense_id;
    String item;
    int expense_id;
    int quantity;
    int price;

    Data_Detail_Expense(
            String _item,
            int _expense_id,
            int _quantity,
            int _price
    ){
        item = _item;
        expense_id = _expense_id;
        quantity = _quantity;
        price = _price;
    }

    Data_Detail_Expense(
            int _detail_expense_id,
            String _item,
            int _expense_id,
            int _quantity,
            int _price
    ){
        detail_expense_id = _detail_expense_id;
        item = _item;
        expense_id = _expense_id;
        quantity = _quantity;
        price = _price;
    }
}
