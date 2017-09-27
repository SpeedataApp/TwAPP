package com.example.twapp.adapter;

import android.content.Context;

import com.example.twapp.R;
import com.example.twapp.been.TwDataInfo;

import java.util.List;

import xyz.reginer.baseadapter.BaseAdapterHelper;
import xyz.reginer.baseadapter.CommonRvAdapter;

/**
 * Created by lenovo-pc on 2017/9/15.
 */

public class TwDatasAdapter extends CommonRvAdapter<TwDataInfo> {
    public TwDatasAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public TwDatasAdapter(Context context, int layoutResId, List<TwDataInfo> data) {
        super(context, layoutResId, data);
    }

    @Override
    public void convert(BaseAdapterHelper helper, TwDataInfo item, int position) {
        helper.setText(R.id.tv_tw_date, item.getTwData());
        helper.setText(R.id.tv_tw_time, item.getTwTime());
        helper.setText(R.id.tv_tw_num, String.valueOf(item.getNum()));
        helper.setText(R.id.tv_tw_num, String.valueOf(item.getNum()));
    }
}
