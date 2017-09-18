package com.example.twapp.control;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twapp.R;
import com.example.twapp.uitl.SharedPreferencesUitl;

public class UserActivity extends Activity {

    private Button btn_clear;
    private SharedPreferencesUitl sharedPreferencesUitl;
    TextView tvname;
    ImageView imageViewHead;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        // TODO Auto-generated method stub
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
