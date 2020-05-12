package com.example.capston;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginTask extends AsyncTask <String,Void,String> {
    String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings){
        try{
            //안드로이드 <->JSP 통신
            String str;
            URL url = new URL("http://192.168.0.26:8081/white1/login.jsp"); //JSP연동 URL http://나의IP 주소:톰캣포트번호/이클립스프로젝트명/사용할 JSP파일
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.connect();

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            //안드로이드에서 입력한 내용을 JSP로 전달함
            sendMsg = "userID="+strings[0]+"&userPassword="+strings[1]+"&type="+strings[2];
            osw.write(sendMsg);
            osw.flush();
            osw.close();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                //JSP 리턴 값을 안드로이드가 받음
                receiveMsg = buffer.toString().trim();

            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
        }
    }

