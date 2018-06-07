package com.example.intae.client;

import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConnection {
    private OkHttpClient client;
    private static HttpConnection instance = new HttpConnection();
    public static HttpConnection getInstance(){
        return instance;
    }// singleton 패턴

    private HttpConnection(){this.client = new OkHttpClient();}

    // 웹 서버로 요청을 한다.
    public void requestWebServer(String id, String password, Callback callback){
        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://13.124.74.152:3000/logIn")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
        System.out.println("request!!!!");
    }
    public void requestSignupWebServer(String id, String password, String name, String birthday, String phoneNumber, Callback callback){
        RequestBody body = new FormBody.Builder()
                .add("id",id)
                .add("password",password)
                .add("name",name)
                .add("birthday",birthday)
                .add("phoneNumber",phoneNumber)
                .build();

        Request request = new Request.Builder()
                .url("http://13.124.74.152:3000/signUp")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void sendImageToWebServer(String encodedImage, Callback callback){
        RequestBody body = new FormBody.Builder()
                .add("imgFile", encodedImage)
                .add("fileName", ""+(Math.random() * 10000000))
                .build();

        Request request = new Request.Builder()
                .url("http://13.124.74.152:3000/visionAPI")
                .post(body)
                .build();

        Log.d("@@@@@@", "서버로 출발했음");
        client.newCall(request).enqueue(callback);
    }
    public OkHttpClient getClient(){
        return this.client;
    }
}