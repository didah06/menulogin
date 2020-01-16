package com.example.menulogin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InputAbsensi extends AppCompatActivity {
    EditText id_plp, tanggal, sesi, status_kehadiran, ket;
    Button simpan;
    private static String simpan_url= "http://192.168.5.28/menulogin/input_absensi.php";
    final Calendar myCalendar = Calendar.getInstance();
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_absensi);
        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.EMP_ID);
        id_plp = findViewById(R.id.id_plp);
        tanggal = findViewById(R.id.tanggal);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(InputAbsensi.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        sesi = findViewById(R.id.sesi);
        status_kehadiran = findViewById(R.id.statushadir);
        ket = findViewById(R.id.ket);
        simpan = findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_absen();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tanggal.setText(sdf.format(myCalendar.getTime()));
    }
    private void input_absen() {
        simpan.setVisibility(View.GONE);
        final String id_plp = this.id_plp.getText().toString();
        final String tanggal = this.tanggal.getText().toString();
        final String sesi = this.sesi.getText().toString();
        final String status_kehadiran = this.status_kehadiran.getText().toString();
        final String ket = this.ket.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, simpan_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        simpan.setVisibility(View.VISIBLE);
                        Toast.makeText(InputAbsensi.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        simpan.setVisibility(View.VISIBLE);
                        Toast.makeText(InputAbsensi.this, "input absen error! " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })


        {

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id_santri" , id_plp);
                params.put("tanggal", tanggal);
                params.put("sesi", sesi);
                params.put("status_kehadiran", status_kehadiran);
                params.put("ket", ket);
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rQueue = Volley.newRequestQueue(InputAbsensi.this);
        rQueue.add(stringRequest);
    }
}
