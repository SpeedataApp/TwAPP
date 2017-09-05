package com.example.twapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.twapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo-pc on 2017/5/8.
 */

public class MyAdapter extends BaseAdapter {

    List<twClass> adapterDatas = new ArrayList<twClass>();

    protected LayoutInflater layoutInflater = null;

    public MyAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void addDatas(twClass t) {
        if (t != null) {
//            Collections.reverse(adapterDatas);
            adapterDatas.add(t);
        }
    }

    public void addDatas(List<twClass> lists) {
        if (lists != null) {
            Collections.reverse(adapterDatas);
            adapterDatas.addAll(lists);
        }
    }

    public void addDatas(List<twClass> lists, boolean isClear) {
        if (isClear) {
            adapterDatas.clear();
        }
        adapterDatas.addAll(lists);
    }

    public void removeDatas(int index) {
        if (index >= 0) {
            if (adapterDatas.size() > 0) {
                adapterDatas.remove(index);
            }
        }
    }

    public void removeDatas() {
        if (adapterDatas != null && adapterDatas.size() > 0) {
            adapterDatas.clear();
        }
    }

    public List<twClass> getAllDatasFromAdapter() {
        return adapterDatas;
    }

    @Override
    public int getCount() {
        if (adapterDatas != null) {
            return adapterDatas.size();
        }
        return 0;
    }


    @Override
    public twClass getItem(int position) {
        return adapterDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View views, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(
                R.layout.list_item_layout, null);
        TextView tvModel = view.findViewById(R.id.tv_model);
        TextView tvDate = view.findViewById(R.id.tv_date);
        TextView tvRunningNumber = view.findViewById(R.id.tv_runningnumber);
        TextView tvLowPower = view.findViewById(R.id.tv_low_power);

        twClass twClass = getItem(i);
        //设置数据倒叙
        tvModel.setText(twClass.getModle() + "");
        tvDate.setText(twClass.getDate());
        tvRunningNumber.setText(twClass.getRuningNumber());
        tvLowPower.setText(twClass.isLowPower() + "");
        // 获取要适配的当前项
        return view;

    }

}
