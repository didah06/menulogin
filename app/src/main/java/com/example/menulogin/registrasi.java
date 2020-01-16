package com.example.menulogin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registrasi extends AppCompatActivity {

    private EditText nm_guru, user, passwd, id_plp, c_passwd;
    Button btnLogin, btnRegister;
    private static String register_url= "http://192.168.3.67/menulogin/register.php";
    private static final String TAG_SUCCESS = "success";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        nm_guru = findViewById(R.id.nm_guru);
        id_plp = findViewById(R.id.id_plp);
        user = findViewById(R.id.user);
        passwd = findViewById(R.id.passwd);
        c_passwd = findViewById(R.id.c_passwd);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        //Launch Login screen when Login Button is clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registrasi.this, MainActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }

    private void Register(){
        btnRegister.setVisibility(View.GONE);

        final String nm_guru = this.nm_guru.getText().toString();
        final String id_plp = this.id_plp.getText().toString();
        final String user = this.user.getText().toString();
        final String passwd = this.passwd.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        btnLogin.setVisibility(View.VISIBLE);
                        Toast.makeText(registrasi.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registrasi.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        btnRegister.setVisibility(View.VISIBLE);
                    }
                })
        {

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("nm_guru", nm_guru);
                params.put("id_plp" , id_plp);
                params.put("user", user);
                params.put("passwd", passwd);
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rQueue = Volley.newRequestQueue(registrasi.this);
        rQueue.add(stringRequest);

    }

}