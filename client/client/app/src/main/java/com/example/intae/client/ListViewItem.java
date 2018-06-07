package com.example.intae.client;

public class ListViewItem {
    private String storeName;
    private String expenseAmount;
    private String expenseId;
    private String date;

    public ListViewItem(String _storeName, String _expenseAmount, String _date, String _expenseId){
        this.storeName = _storeName;
        this.expenseAmount = _expenseAmount;
        this.date = _date;
        this.expenseId = _expenseId;
    }
    public void setStoreName(String _storeName){
        this.storeName = _storeName;
    }
    public String getStoreName(){
        return this.storeName;
    }
    public void setExpenseAmount(String _expenseAmount){
        this.expenseAmount = _expenseAmount;
    }
    public String getExpenseAmount(){
        return this.expenseAmount;
    }
    public void setDate(String _date){ this.date = _date; }
    public String getDate(){return this.date;}
    public void setExpenseId(String _expenseId){
        this.expenseId = _expenseId;
    }
    public String getExpenseId(){
        return this.expenseId;
    }

}
