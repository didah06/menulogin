package com.example.menulogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView username;
    Button btn_logout;
    Button btnmenu1;
    Button btnmenu2;
    String id_guru, user;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id_guru";
    public static final String TAG_USERNAME = "user";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = findViewById(R.id.user);
        btn_logout = findViewById(R.id.btn_logout);
        btnmenu1 = findViewById(R.id.btnabsen);
        btnmenu2 =  findViewById(R.id.btnziyadah);


        sharedpreferences = getSharedPreferences(MainActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id_guru = getIntent().getStringExtra(TAG_ID);
        user = getIntent().getStringExtra(TAG_USERNAME);

        username.setText("ustadzah : " + user);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(MainActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        btnmenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this, daftarkelas.class);
                startActivity(intent1);
            }
        });
        btnmenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HomeActivity.this, daftarnama.class);
                startActivity(intent2);
            }
        });

    }

}
