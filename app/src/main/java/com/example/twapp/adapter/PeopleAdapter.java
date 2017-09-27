package com.example.twapp.adapter;

import android.content.Context;

import com.example.twapp.R;
import com.example.twapp.been.PeopleInfor;

import java.util.List;

import xyz.reginer.baseadapter.BaseAdapterHelper;
import xyz.reginer.baseadapter.CommonRvAdapter;

/**
 * Created by lenovo-pc on 2017/9/18.
 */

public class PeopleAdapter extends CommonRvAdapter<PeopleInfor> {

    public PeopleAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public PeopleAdapter(Context context, int layoutResId, List<PeopleInfor> data) {
        super(context, layoutResId, data);
    }

    @Override
    public void convert(BaseAdapterHelper helper, PeopleInfor item, int position) {
        helper.setText(R.id.name,"姓名："+item.getName());
        helper.setText(R.id.age, "年龄："+item.getAge());
        helper.setText(R.id.gender, "性别："+item.getGender());
        helper.setText(R.id.bed_number, item.getBedNumber()+"床");
        helper.setText(R.id.result,"诊断：" + item.getResult());
    }
}
