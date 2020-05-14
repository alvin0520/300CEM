package com.mo.a300cem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class addCommentToServer extends StringRequest {

    private static final String REQUEST_URL = "http://192.168.1.226/addC.php";

    private Map<String, String> params;

    public addCommentToServer(String id,String user,String Comment,String date,String location, Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user", user);
        params.put("Comt", Comment);
        params.put("date", date);
        params.put("id", id);
        params.put("location", location);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}