package com.example.capston;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Signup extends Activity {


    final int REQ_CODE_SELECT_IMAGE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        final EditText signupid, signuppwd, signupemail, signuppnumber;
        final Button signupok, signupexit,piclog;


        signupid = (EditText) findViewById(R.id.SignupId);
        signuppwd = (EditText) findViewById(R.id.SignupPw);
        signupemail = (EditText) findViewById(R.id.SignupEmail);
        signuppnumber = (EditText) findViewById(R.id.SingupPNumber);

        signupok = (Button) findViewById(R.id.SingupBtn);
        signupexit = (Button) findViewById(R.id.ExitBtn);
        piclog = (Button)findViewById(R.id.SingupPhoto);


        //이미지 첨부 업로드 클릭 이벤트 구현중
        piclog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAlbum();

            }
        });

        //회원가입 버튼 클릭 이벤트
        signupok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String signupId = signupid.getText().toString();
                String signupPwd = signuppwd.getText().toString();
                String signupEmail = signupemail.getText().toString();
                String signupNumber = signuppnumber.getText().toString();
                if (signupid.getText().toString().equals("") || signuppwd.getText().toString().equals("")
                        ||signupemail.getText().toString().equals("") || signuppnumber.getText().toString().equals(""))
                {
                    Toast.makeText(Signup.this, "입력란에 공백이 있습니다.", Toast.LENGTH_SHORT).show();
                    signupid.setText("");
                    signuppwd.setText("");
                    signupemail.setText("");
                    signuppnumber.setText("");
                } else {
                    try {
                        String result = new SignupTask().execute(signupId, signupPwd, signupEmail, signupNumber, "join").get();
                        if (result.equals("unvalidid")) {
                            Toast.makeText(Signup.this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                            signupid.setText("");
                        } else if (result.equals("ok")) {
                            Intent signok = new Intent(Signup.this, MainActivity.class);
                            Signup.this.startActivity(signok);
                        }

                    } catch (Exception e) {
                    }

                }
            }
        });

        //뒤로버튼 클릭 이벤트
        signupexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signexit = new Intent(Signup.this,MainActivity.class);
                Signup.this.startActivity(signexit);
            }
        });
    }
    public String getImageNameToUri(Uri data){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data,proj,null,null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring((imgPath.lastIndexOf("/")+1));

        return imgName;
    }
    public void selectAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData((MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
        startActivityForResult(intent,REQ_CODE_SELECT_IMAGE);
    }
}

