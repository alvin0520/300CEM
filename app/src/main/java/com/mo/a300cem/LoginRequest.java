package com.mo.a300cem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String URL = "http://192.168.1.226/login.php";
    private Map<String, String> params;

    public LoginRequest(String user,String password, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("user", user);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}