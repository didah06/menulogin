package com.example.menulogin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.menulogin.JSONParser.json;

public class daftarSinglePlp extends AppCompatActivity {
    private EditText editTextId, editTextName, editTextIdPlp, editTexttanggal, editTextsesi,
            editTextstatus_kehadiran, editTextket;
    final Calendar myCalendar = Calendar.getInstance();
    private String id;
    private Button btnabsen, btnkembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_single_plp);

        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.EMP_ID);

        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextIdPlp = findViewById(R.id.editTextIdplp);
        editTexttanggal = findViewById(R.id.tanggal);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        editTexttanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(daftarSinglePlp.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editTextsesi = findViewById(R.id.sesi);
        editTextstatus_kehadiran = findViewById(R.id.statushadir);
        editTextket = findViewById(R.id.ket);
        btnabsen = findViewById(R.id.buttonAbsen);
        btnabsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputAbsen();
            }
        });
        btnkembali = findViewById(R.id.buttonKembali);

        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(daftarSinglePlp.this, daftarkelas.class);
                startActivity(intent);
            }
        });
        btnabsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputAbsen();
            }
        });

        editTextId.setText(id);
        getEmployee();


    }

    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(daftarSinglePlp.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
            }


    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(konfigurasi.TAG_NAMA);
            String id_plp = c.getString(konfigurasi.TAG_IDPLP);

            editTextName.setText(name);
            editTextIdPlp.setText(id_plp);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTexttanggal.setText(sdf.format(myCalendar.getTime()));
    }
    private void inputAbsen(){
            final String id_plp = editTextIdPlp.getText().toString().trim();
            final String tanggal = editTexttanggal.getText().toString().trim();
            final String sesi = editTextsesi.getText().toString().trim();
            final String status_kehadiran = editTextstatus_kehadiran.getText().toString().trim();
            final String ket = editTextket.getText().toString().trim();

    class InputAbsen extends AsyncTask<Void,Void,String>{
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(daftarSinglePlp.this,"Updating...","Wait...",false,false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(daftarSinglePlp.this,s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... params) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put(konfigurasi.KEY_EMP_ID,id);
            hashMap.put(konfigurasi.KEY_EMP_IDPLP, id_plp);
            hashMap.put(konfigurasi.KEY_EMP_TANGGAL,tanggal);
            hashMap.put(konfigurasi.KEY_EMP_SESI,sesi);
            hashMap.put(konfigurasi.KEY_EMP_STATUS, status_kehadiran);
            hashMap.put(konfigurasi.KEY_EMP_KET, ket);

            RequestHandler rh = new RequestHandler();

            String s = rh.sendPostRequest(konfigurasi.simpan_url,hashMap);

            return s;
        }
    }

    InputAbsen ue = new InputAbsen();
        ue.execute();
}

}



