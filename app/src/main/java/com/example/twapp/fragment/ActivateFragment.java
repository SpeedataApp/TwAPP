package com.example.twapp.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twapp.R;
import com.example.twapp.base.BaseFragment;
import com.example.twapp.db.TwBody;
import com.example.twapp.utils.DBUitl;
import com.example.twapp.utils.DataConvertUtil;

import java.util.ArrayList;
import java.util.List;


public class ActivateFragment extends BaseFragment implements View.OnClickListener {
    private Button btnActivate;
    private EditText pNnm, runNum, name, age, gender, bednum;
    private DBUitl dBtable;
    private List<String> list = new ArrayList<>();
    private List<Long> listLong = new ArrayList<>();

    @Override
    protected void create() {
        dBtable = new DBUitl();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_activtate;
    }

    @Override
    protected void initView(View conteView) {
        btnActivate = conteView.findViewById(R.id.btn_activate);
        pNnm = conteView.findViewById(R.id.people_num);
        runNum = conteView.findViewById(R.id.run_num);
        name = conteView.findViewById(R.id.edittext_name);
        age = conteView.findViewById(R.id.edittext_age);
        gender = conteView.findViewById(R.id.edittext_gender);
        bednum = conteView.findViewById(R.id.edittext_bed_num);
        btnActivate.setOnClickListener(this);
    }

    @Override
    protected void setListener() {

    }


    @Override
    protected void resuem() {
        pNnm.setText("");
        runNum.setText("");
        name.setText("");
        age.setText("");
        gender.setText("");
        bednum.setText("");
    }

    @Override
    protected void psuse() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activate:
                String pnum = String.valueOf(pNnm.getText());
                String runnum = String.valueOf(runNum.getText());
                String names = String.valueOf(name.getText());
                String ages = String.valueOf(age.getText());
                String genders = String.valueOf(gender.getText());
                String bednums = String.valueOf(bednum.getText());
                if (!pnum.equals("") && !runnum.equals("") && !names.equals("") && !ages.equals("")
                        && !genders.equals("") && !bednums.equals("")) {
                    TwBody twBody = new TwBody(pnum, names, ages, genders, bednums, runnum, "1970-1-1 00:00:00", R.drawable.pass_false, "", null, null, null);
                    dBtable.insertDtata(twBody);
                    Toast.makeText(getActivity(), "绑定成功！", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().
                            replace(R.id.main_layout_viewgroup, new HomeFragment()).commit();
                    DataConvertUtil.closeKeybord(pNnm, getActivity());
                } else {
                    Toast.makeText(getActivity(), "信息不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
