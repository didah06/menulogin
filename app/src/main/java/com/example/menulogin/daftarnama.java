package com.example.menulogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.menulogin.konfigurasi.TAG_ID;
import static com.example.menulogin.konfigurasi.TAG_NAMA;

public class daftarnama extends AppCompatActivity implements ListView.OnItemClickListener {
    private ListView listView;
    private ListAdapter adapter;
    private SearchView searchView;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarnama);
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
                daftarnama.this, list, R.layout.list_nama,
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
                loading = ProgressDialog.show(daftarnama.this, "Mengambil Data", "Mohon Tunggu...", false, false);
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
        Intent intent = new Intent(daftarnama.this, input_ziyadah.class);
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

