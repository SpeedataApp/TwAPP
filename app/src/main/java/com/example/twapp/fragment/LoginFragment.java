package com.example.twapp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twapp.R;
import com.example.twapp.control.MainActivity;
import com.example.twapp.utils.SharedPreferencesUitl;

public class LoginFragment extends Fragment {

    private Button btn_log, btn_reg;
    private EditText etemail, etpwd;
    SharedPreferencesUitl sharedPreferencesUitl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        btn_log = v.findViewById(R.id.fralogin_btn_login);
        btn_reg = v.findViewById(R.id.fralogin_btn_register);
        etemail = v.findViewById(R.id.fralogin_tv_email);
        etpwd = v.findViewById(R.id.fralogin_tv_pwd);
        btn_log.setOnClickListener(linClickListener);
        btn_reg.setOnClickListener(linClickListener);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesUitl = SharedPreferencesUitl.getInstance(getActivity(), "tw");
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        etpwd.setText("");
        etpwd.setHint("请输入密码");
    }

    private MainActivity mainActivity;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    private View.OnClickListener linClickListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.fralogin_btn_login:
                    name = etemail.getText().toString().trim();
                    upwd = etpwd.getText().toString().trim();
                    if (name.equals("jack") && upwd.equals("111111")) {
                        Toast.makeText(mainActivity, "登录成功", 0).show();
                        sharedPreferencesUitl.write("name", name);
                        sharedPreferencesUitl.write("upwd", upwd);
                        mainActivity.changeFragment(mainActivity.homeFragment);
                    }else {
                        Toast.makeText(getActivity(),"密码错误",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.fralogin_btn_register:
                    mainActivity.changeFragment(mainActivity.homeFragment);
                    break;

            }
        }
    };

    private String name = null;
    private String upwd = null;

}
