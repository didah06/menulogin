package com.example.menulogin;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class SpRequest extends StringRequest {
    private static final String URL = "http://192.168.5.31/menulogin/getPLP.php";
    private Map<String, String> params;

    public SpRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
