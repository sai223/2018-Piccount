package com.example.intae.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {
    String id, pw, name, birthday, phoneNumber;
    EditText idInput, pwInput, nameInput, birthdayInput, phoneNumberInput;
    HttpConnection httpConn = HttpConnection.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button signupDoneButton = (Button)findViewById(R.id.signupDoneButton);
        idInput = (EditText)findViewById(R.id.id);
        pwInput = (EditText)findViewById(R.id.password);
        nameInput = (EditText)findViewById(R.id.name);
        birthdayInput = (EditText)findViewById(R.id.birthday);
        phoneNumberInput = (EditText)findViewById(R.id.phoneNumber);

        id = idInput.getText().toString();
        pw = pwInput.getText().toString();
        name = nameInput.getText().toString();
        birthday = birthdayInput.getText().toString();
        phoneNumber = phoneNumberInput.getText().toString();

        signupDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSignupData();
            }
        });
    }
    private void sendSignupData(){
        new Thread(){
            public void run(){
                httpConn.requestSignupWebServer(id,pw,name,birthday,phoneNumber,callback);
            }
        }.start();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("TEST", "실패: "+e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("TEST", "성공: " + body);
        }
    };
}
