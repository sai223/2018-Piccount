package com.example.intae.client;

import android.app.Application;

public class UserInfo extends Application{
    private String validID;
    public String getValidID(){
        return validID;
    }
    public void setValidID(String idInput){
        this.validID = idInput;
    }
}
