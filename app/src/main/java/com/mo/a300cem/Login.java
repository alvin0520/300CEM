package com.mo.a300cem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    String StrUser, StrPassword;
    private EditText user,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void LoginAc(View view){

        user = (EditText)findViewById(R.id.TxtAc);
        password = (EditText)findViewById(R.id.TxtPw);
        StrUser = user.getText().toString();
        StrPassword = password.getText().toString();

        if(TextUtils.isEmpty(StrUser)) {
            user.setError("Please input your user name");
            return;
        }
        if(TextUtils.isEmpty(StrPassword)) {
            password.setError("Please input your password");
            return;
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    boolean success = jsonObj.getBoolean("success");
                    if (success) {
                        Toast.makeText(Login.this,"Login Success",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Login.this, MainActivity.class);
                        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                        pref.edit()
                                .putString("user", StrUser)
                                .commit();
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Login.this,"Login Fail",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Login.this,"Login Fail",Toast.LENGTH_LONG).show();
                }
            }
        };

        LoginRequest LoginRequest = new LoginRequest(StrUser, StrPassword, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        queue.add(LoginRequest);
    }
    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }

    public void register(View view){
        //Intent i = new Intent(this, Register.class);
        //startActivity(i);
    }
}
