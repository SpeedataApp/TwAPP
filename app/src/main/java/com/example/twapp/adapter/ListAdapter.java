package com.example.twapp.adapter;

import android.content.Context;

import com.example.twapp.R;

import java.util.List;

import xyz.reginer.baseadapter.BaseAdapterHelper;
import xyz.reginer.baseadapter.CommonRvAdapter;

/**
 * Created by lenovo-pc on 2017/9/15.
 */

public class ListAdapter extends CommonRvAdapter<TWClass> {
    public ListAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public ListAdapter(Context context, int layoutResId, List<TWClass> data) {
        super(context, layoutResId, data);
    }

    @Override
    public void convert(BaseAdapterHelper helper, TWClass item, int position) {
        helper.setText(R.id.tv_tw_date, item.getTwData());
        helper.setText(R.id.tv_tw_time, item.getTwTime());
    }
}
