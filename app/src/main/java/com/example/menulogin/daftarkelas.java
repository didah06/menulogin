package com.example.menulogin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import static com.example.menulogin.konfigurasi.TAG_ID;
import static com.example.menulogin.konfigurasi.TAG_IDPLP;
import static com.example.menulogin.konfigurasi.TAG_MESSAGE;
import static com.example.menulogin.konfigurasi.TAG_NAMA;
import static com.example.menulogin.konfigurasi.TAG_RESULTS;
import static com.example.menulogin.konfigurasi.TAG_VALUE;

public class daftarkelas extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;
    private ListAdapter adapter;
    private SearchView searchView;

    private String JSON_STRING;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarkelas);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView = getListView();
        getJSON();
    }

    private void showEmployee() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id_santri = jo.getString(TAG_ID);
                String name = jo.getString(TAG_NAMA);
                String id_plp = jo.getString(konfigurasi.TAG_IDPLP);

                HashMap<String, String> employees = new HashMap<>();
                employees.put(TAG_ID, id_santri);
                employees.put(TAG_NAMA, name);
                employees.put(konfigurasi.TAG_IDPLP, id_plp);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new SimpleAdapter(
                daftarkelas.this, list, R.layout.list_item,
                new String[]{TAG_ID, TAG_NAMA, konfigurasi.TAG_IDPLP},
                new int[]{R.id.id_santri, R.id.name, R.id.id_plp});

        listView.setAdapter(adapter);
        listView = getListView();
        listView.setTextFilterEnabled(true);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(daftarkelas.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(daftarkelas.this, daftarSinglePlp.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String empId = map.get(TAG_ID).toString();
        intent.putExtra(konfigurasi.EMP_ID, empId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Memanggil/Memasang menu item pada toolbar dari layout menu_bar.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String nextText) {
                listView = getListView();
                if (TextUtils.isEmpty(nextText)) {
                    listView.clearTextFilter();
                } else {
                    listView.setFilterText(nextText);
                }
                return false;
            }
        });
        return true;
    }

    private ListView getListView() {
        return listView;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.back) {
            startActivity(new Intent(this, HomeActivity.class));
        } else if (item.getItemId() == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }
    }



    /*
    private void caridata(final String keyword) {
        StringRequest strReq = new StringRequest(Request.Method.POST, konfigurasi.URL_SEARCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());
                ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        list.clear();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            obj.getString(TAG_ID);
                            obj.getString(TAG_NAMA);
                            obj.getString(konfigurasi.TAG_IDPLP);

                            HashMap<String, String> employees = new HashMap<>();
                            employees.put("keyword", keyword);
                            list.add(employees);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                adapter = new SimpleAdapter(
                        daftarkelas.this, list, R.layout.list_item,
                        new String[]{TAG_ID, TAG_NAMA, konfigurasi.TAG_IDPLP},
                        new int[]{R.id.id_santri, R.id.name, R.id.id_plp});

                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(konfigurasi.TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        strReq.setRetryPolicy(new DefaultRetryPolicy( 1000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rQueue = Volley.newRequestQueue(daftarkelas.this);
        rQueue.add(strReq);
    }
}
*/
