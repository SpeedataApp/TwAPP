package com.example.twapp.adapter;

import android.content.Context;

import com.example.twapp.R;

import java.util.List;

import xyz.reginer.baseadapter.BaseAdapterHelper;
import xyz.reginer.baseadapter.CommonRvAdapter;

/**
 * Created by lenovo-pc on 2017/9/18.
 */

public class InfaoAdapter extends CommonRvAdapter<InforClass> {

    public InfaoAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public InfaoAdapter(Context context, int layoutResId, List<InforClass> data) {
        super(context, layoutResId, data);
    }

    @Override
    public void convert(BaseAdapterHelper helper, InforClass item, int position) {
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.age, item.getAge());
        helper.setText(R.id.gender, item.getGender());
        helper.setText(R.id.bed_number, item.getBedNumber()+"床");
        helper.setText(R.id.result, item.getResult());
        helper.setText(R.id.keshi, item.getKeshi());
    }
}
