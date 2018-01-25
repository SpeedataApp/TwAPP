package com.example.twapp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twapp.R;
import com.example.twapp.base.BaseFragment;
import com.example.twapp.control.MainActivity;
import com.example.twapp.utils.DataConvertUtil;
import com.example.twapp.utils.SharedPreferencesUitl;

public class LoginFragment extends BaseFragment {

    private Button btn_log, btn_reg;
    private EditText etemail, etpwd;
    private SharedPreferencesUitl sharedPreferencesUitl;


    @Override
    protected int getViewID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View conteView) {
        btn_log = conteView.findViewById(R.id.fralogin_btn_login);
        btn_reg = conteView.findViewById(R.id.fralogin_btn_register);
        etemail = conteView.findViewById(R.id.fralogin_tv_email);
        etpwd = conteView.findViewById(R.id.fralogin_tv_pwd);
        btn_log.setOnClickListener(linClickListener);
        btn_reg.setOnClickListener(linClickListener);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void create() {
        sharedPreferencesUitl = SharedPreferencesUitl.getInstance(getActivity(), "tw");
    }

    @Override
    protected void resuem() {

    }

    @Override
    protected void psuse() {

    }

    @Override
    public void onStop() {
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
                        DataConvertUtil.closeKeybord(etpwd, getActivity());
                    } else {
                        Toast.makeText(getActivity(), "密码错误", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.fralogin_btn_register:
                    DataConvertUtil.closeKeybord(etpwd, getActivity());
                    mainActivity.changeFragment(mainActivity.homeFragment);
                    break;

            }
        }
    };

    private String name = null;
    private String upwd = null;

}
