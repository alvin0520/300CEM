package com.mo.a300cem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getTopic extends StringRequest {

    private static final String Topic_URL = "http://192.168.1.226/getTopic.php";
    private Map<String, String> params;

    public getTopic(Response.Listener<String> listener) {
        super(Method.GET, Topic_URL, listener, null);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}