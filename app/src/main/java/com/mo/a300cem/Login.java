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

public class Login extends AppCompatActivity implements BiometricCallback {
    String StrUser, StrPassword;
    String Fuser;
    private EditText user,password;
    BiometricManager mBiometricManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Fuser = getSharedPreferences("user", MODE_PRIVATE)
                .getString("fuser", "");
        if (!TextUtils.isEmpty(Fuser)){
            mBiometricManager = new BiometricManager.BiometricBuilder(Login.this)
                    .setTitle(getString(R.string.biometric_title))
                    .setSubtitle(getString(R.string.biometric_subtitle))
                    .setDescription(getString(R.string.biometric_description))
                    .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                    .build();
            //start authentication
            mBiometricManager.authenticate(Login.this);
        }
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
        Intent i = new Intent(this, register.class);
        startActivity(i);
    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(Login.this,"Login Fail",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_cancelled),
                Toast.LENGTH_LONG).show();
        mBiometricManager.cancelAuthentication();
    }
    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(Login.this,"Login Success",Toast.LENGTH_LONG).show();
        Intent i = new Intent(Login.this, MainActivity.class);
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        pref.edit()
                .putString("user", Fuser)
                .commit();
        startActivity(i);
        finish();
    }
    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
// Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
// Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }
}
