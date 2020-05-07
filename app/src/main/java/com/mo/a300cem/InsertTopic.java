package com.mo.a300cem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertTopic extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://192.168.1.226/AddTopic.php";
    private Map<String, String> params;

    public InsertTopic(String user, String date, String title,String Content, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user", user);
        params.put("date", date);
        params.put("title", title);
        params.put("Content", Content);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}