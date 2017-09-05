package com.example.twapp.control;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.twapp.R;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageHome;
    private Button btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history_land);
        imageHome = findViewById(R.id.image_home);
        btnHistory=findViewById(R.id.btn_history);
        btnHistory.setOnClickListener(this);
        imageHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_home:
                System.exit(0);
                break;
            case R.id.btn_history:
//                System.exit(0);
                break;
        }

    }
}
