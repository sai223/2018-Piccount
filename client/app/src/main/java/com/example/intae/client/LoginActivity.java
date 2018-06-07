package com.example.intae.client;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {
    HttpConnection httpConn = HttpConnection.getInstance();
    EditText idInput, pwInput;
    String id, password;
    static String validID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idInput = (EditText)findViewById(R.id.idInput);
        pwInput = (EditText)findViewById(R.id.passwordInput);
        Button logInButton = (Button)findViewById(R.id.loginButton);
        Button signupPageButton = (Button)findViewById(R.id.signupPageButton);


        logInButton.setOnClickListener(new View.OnClickListener() {//버튼을 클릭하면, 로그인 정보를 서버로 전달
            @Override
            public void onClick(View v) {
               // id = idInput.getText().toString();
                //password = pwInput.getText().toString();
                //new UserInfo().setValidID(id);
                //sendIDData();
                Intent intent = new Intent(getApplicationContext(),ListCheckActivity.class);
                startActivity(intent);
            }
        });
        signupPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Sign up 페이지로 이동
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
    }
    /******************서버로 로그인 정보 전송*************************/
    private void sendIDData(){
        new Thread(){
            public void run(){
                //@TEST: 로그인 정보가 제대로 전송되는지
                httpConn.requestWebServer(id,password,callback);
            }
        }.start();
    }
    /****************************Start of Callback******************************************/
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("TEST", "실패: "+e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            //@TEST: response로 json오브젝트를 받아오는지
            String jsonData = response.body().string();
            try {
                JSONObject jObject = new JSONObject(jsonData);
                if(jObject.getBoolean("success")){//로그인 정보가 유효할 경우, 메인 액티비티로 전환
                    //new UserInfo().setValidID(id);
                    Intent intent = new Intent(getApplicationContext(),ListCheckActivity.class);
                    startActivity(intent);
                }else{//로그인 정보가 유효하지 않을 경우, 로그인 정보가 틀리다는 팝업창을 띄움
                    loginFailureShow();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    /******************************로그인 실패 팝업창********************************/
    void loginFailureShow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("로그인 정보가 일치하지 않습니다.");
        builder.setPositiveButton("닫기",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }
}
