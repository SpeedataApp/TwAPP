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

    List<TWClass> adapterDatas = new ArrayList<TWClass>();

    protected LayoutInflater layoutInflater = null;

    public MyAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void addDatas(TWClass t) {
        if (t != null) {
//            Collections.reverse(adapterD  atas);
            adapterDatas.add(t);
        }
    }

    public void addDatas(List<TWClass> lists) {
        if (lists != null) {
            Collections.reverse(adapterDatas);
            adapterDatas.addAll(lists);
        }
    }

    public void addDatas(List<TWClass> lists, boolean isClear) {
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

    public List<TWClass> getAllDatasFromAdapter() {
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
    public TWClass getItem(int position) {
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
        TextView tvTime = view.findViewById(R.id.tv_tw_time);
        TextView tvDate = view.findViewById(R.id.tv_tw_date);

        TWClass twClass = getItem(i);
//        for (int j = 0; j < twClass.getTwData().size(); j++) {
            tvTime.setText(twClass.getTwTime());
            tvDate.setText(twClass.getTwData());
//        }
        // 获取要适配的当前项
        return view;
    }
}
