package com.example.twapp.control;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twapp.R;
import com.example.twapp.base.BaseActivity;
import com.example.twapp.utils.SharedPreferencesUitl;

public class UserActivity extends BaseActivity {

    private Button btn_clear;
    private SharedPreferencesUitl sharedPreferencesUitl;
    TextView tvname;
    ImageView imageViewHead;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uesr);
        sharedPreferencesUitl = SharedPreferencesUitl.getInstance(this, "tw");
        btn_clear = (Button) findViewById(R.id.user_bt_clearuser);
        tvname = findViewById(R.id.tv_user_name);
        imageViewHead = findViewById(R.id.user_head);
        tvname.setText(sharedPreferencesUitl.read("name", ""));

        btn_clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sharedPreferencesUitl.write("name", "");
                sharedPreferencesUitl.write("upwd", "");
                setResult(250);
                finish();
            }
        });

    }
}
