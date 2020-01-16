package com.example.menulogin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class input_ziyadah extends AppCompatActivity {
    EditText editTextId, editTextName, editTextIdPlp, editTexttanggal, editTextSesi, editTextJuz,
            editTextIdSurat, editTextAyatawal, editTextAyatakhir, editTextNilai;
    final Calendar myCalendar = Calendar.getInstance();
    private String id;
    private Button btnInput, btnkembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ziyadah);
        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.EMP_ID2);

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
                new DatePickerDialog(input_ziyadah.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editTextSesi = findViewById(R.id.sesi);
        editTextJuz = findViewById(R.id.juz);
        editTextIdSurat = findViewById(R.id.id_surat);
        editTextAyatawal = findViewById(R.id.ayat_awal);
        editTextAyatakhir = findViewById(R.id.ayat_akhir);
        editTextNilai = findViewById(R.id.nilai);
        btnkembali = findViewById(R.id.buttonKembali);
        btnInput = findViewById(R.id.buttonInput);
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(input_ziyadah.this, daftarnama.class);
                startActivity(intent);
            }
        });
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputZiyadah();
            }
        });

        editTextId.setText(id);
        getEmployee();
    }
    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(input_ziyadah.this,"Fetching...","Wait...",false,false);
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
    private void inputZiyadah(){
        final String id_plp = editTextIdPlp.getText().toString().trim();
        final String tanggal = editTexttanggal.getText().toString().trim();
        final String sesi = editTextSesi.getText().toString().trim();
        final String juz = editTextJuz.getText().toString().trim();
        final String id_surat = editTextIdSurat.getText().toString().trim();
        final String ayat_awal = editTextAyatawal.getText().toString().trim();
        final String ayat_akhir = editTextAyatakhir.getText().toString().trim();
        final String nilai = editTextNilai.getText().toString().trim();

        class InputAbsen extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(input_ziyadah.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(input_ziyadah.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_EMP_ID,id);
                hashMap.put(konfigurasi.KEY_EMP_IDPLP, id_plp);
                hashMap.put(konfigurasi.KEY_EMP_TANGGAL,tanggal);
                hashMap.put(konfigurasi.KEY_EMP_SESI,sesi);
                hashMap.put(konfigurasi.KEY_EMP_JUZ, juz);
                hashMap.put(konfigurasi.KEY_EMP_IDSURAT, id_surat);
                hashMap.put(konfigurasi.KEY_EMP_AYATAWAL, ayat_awal);
                hashMap.put(konfigurasi.KEY_EMP_AYATAKHIR, ayat_akhir);
                hashMap.put(konfigurasi.KEY_EMP_NILAI, nilai);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.simpan_url,hashMap);

                return s;
            }
        }

        InputAbsen ue = new InputAbsen();
        ue.execute();
    }

}
