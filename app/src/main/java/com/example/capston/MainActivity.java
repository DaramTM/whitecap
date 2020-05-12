package com.example.capston;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText userId,userPwd;
    Button loginBtn,joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = (EditText) findViewById(R.id.EditId);
        userPwd = (EditText) findViewById(R.id.EditPw);
        loginBtn = (Button) findViewById(R.id.LoginBtn);
        joinBtn = (Button) findViewById(R.id.RegisterBtn);

        //로그인 버튼 이벤트
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginid = userId.getText().toString();
                String loginpwd = userPwd.getText().toString();
                try {
                    String result = new LoginTask().execute(loginid,loginpwd,"login").get();
                    if (result.equals("true")) {
                        Toast.makeText(MainActivity.this, "로그인", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),test.class);
                        intent.putExtra("logtest", userId.getText().toString());
                        startActivity(intent);
                        userId.setText("");
                        userPwd.setText("");
                    } else if (result.equals("false")) {
                        Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 틀렸음", Toast.LENGTH_SHORT).show();
                        userId.setText("");
                        userPwd.setText("");
                    }
                } catch (Exception e) {
                }
            }
        });
        //회원가입 버튼 이벤트
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent joinupintent = new Intent(MainActivity.this,Signup.class);
                MainActivity.this.startActivity(joinupintent);
            }
        });

    }
}