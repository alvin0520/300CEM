package com.mo.a300cem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {

    EditText id,pw,pw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void Reg (View view) {
        //get text from the edittext and change to string
        id = findViewById(R.id.txtId);
        pw = findViewById(R.id.txtPw);
        pw2 = findViewById(R.id.txtPw2);
        String Sid,Spw,Spw2;
        Sid = id.getText().toString();
        Spw = pw.getText().toString();
        Spw2  = pw2.getText().toString();


        //Check the user and password is empty or not, also the password has typed twice with same
        if(TextUtils.isEmpty(Sid)) {
            id.setError("Please input your user name");
            return;
        }
        if(TextUtils.isEmpty(Spw)) {
            pw.setError("Please input your password");
            return;
        }
        if(!Spw.equals(Spw2))  {
            pw2.setError("Password does not match.");
            return;
        }

        //Send the request by using volley
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String success = jsonObj.getString("success");
                    if (success.equals("user")) {
                        Toast.makeText(register.this,"Please use another userId, this userid has been registered",Toast.LENGTH_LONG).show();
                    } else if (success.equals("fail")) {
                        Toast.makeText(register.this,"Try another userid to register",Toast.LENGTH_LONG).show();
                    } else if (success.equals("ok")){
                        Toast.makeText(register.this,"Register success! Please login with your account",Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(register.this,success,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(register.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };

        RegReq Reg = new RegReq(Sid,Spw, responseListener);
        RequestQueue queue = Volley.newRequestQueue(register.this);
        queue.add(Reg);


    }
}
