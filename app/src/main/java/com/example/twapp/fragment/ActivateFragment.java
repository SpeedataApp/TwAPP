package com.example.twapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twapp.R;
import com.example.twapp.db.TwBody;
import com.example.twapp.utils.DBUitl;

import java.util.ArrayList;
import java.util.List;


public class ActivateFragment extends Fragment implements View.OnClickListener {
    private Button btnActivate;
    private EditText pNnm, runNum, name, age, gender, bednum, result;
    DBUitl dBtable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dBtable = new DBUitl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activtate, null);
        btnActivate = v.findViewById(R.id.btn_activate);
        pNnm = v.findViewById(R.id.people_num);
        runNum = v.findViewById(R.id.run_num);
        name = v.findViewById(R.id.edittext_name);
        age = v.findViewById(R.id.edittext_age);
        gender = v.findViewById(R.id.edittext_gender);
        bednum = v.findViewById(R.id.edittext_bed_num);
        result = v.findViewById(R.id.edittext_result);
        btnActivate.setOnClickListener(this);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    List<String> list = new ArrayList<>();

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
                String results = String.valueOf(result.getText());
                if (!pnum.equals("") && !runnum.equals("") && !names.equals("") && !ages.equals("")
                        && !genders.equals("") && !bednums.equals("") && !results.equals("")) {
                    TwBody twBody = new TwBody(pnum, names, ages,
                            genders, bednums, results, runnum, 0, list, list);
                    dBtable.insertDtata(twBody);
                    Toast.makeText(getActivity(), "绑定成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "信息不能为空！", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }
}
