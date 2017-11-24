package com.example.twapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twapp.R;
import com.example.twapp.been.PeopleInfor;
import com.example.twapp.utils.DBUitl;
import com.example.twapp.utils.DialogChange;
import com.example.twapp.view.SwipeListLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import xyz.reginer.baseadapter.BaseAdapterHelper;
import xyz.reginer.baseadapter.CommonRvAdapter;

/**
 * Created by lenovo-pc on 2017/9/18.
 */

public class PeopleAdapter extends CommonRvAdapter<PeopleInfor> {
    List<PeopleInfor> data;
    int layoutID;
    Context context;
    DBUitl dBtable = new DBUitl();

    public PeopleAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public PeopleAdapter(Context context, int layoutResId, List<PeopleInfor> data) {
        super(context, layoutResId, data);
        this.data = data;
        this.layoutID = layoutResId;
        this.context = context;
    }


    @Override
    public void convert(final BaseAdapterHelper helper, PeopleInfor item, final int position) {
        helper.setText(R.id.name, "姓名：" + item.getName());
        helper.setText(R.id.age, "年龄：" + item.getAge());
        helper.setText(R.id.gender, "性别：" + item.getGender());
        helper.setText(R.id.bed_number, item.getBedNumber() + "床");
        helper.setText(R.id.result, "诊断：" + item.getResult());
        helper.setText(R.id.dianliang, item.getDianLiang());
        helper.setBackgroundRes(R.id.image_pass, item.getId());

        final SwipeListLayout sll_main = (SwipeListLayout) helper.getView();
        TextView tv_top = helper.getView().findViewById(R.id.tv_top);
        TextView tv_delete = helper.getView().findViewById(R.id.tv_delete);
        sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(
                sll_main));
        tv_top.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogChange.showCustomizeDialog(context, data.get(position).getRunNum());
                notifyDataSetChanged();
            }
        });
        tv_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sll_main.setStatus(SwipeListLayout.Status.Close, true);
                dBtable .delete(data.get(position).getRunNum());
                data.remove(position);

                notifyDataSetChanged();
            }
        });
        sll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "item onclic" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Set<SwipeListLayout> sets = new HashSet();

    class MyOnSlipStatusListener implements SwipeListLayout.OnSwipeStatusListener {

        private SwipeListLayout slipListLayout;

        public MyOnSlipStatusListener(SwipeListLayout slipListLayout) {
            this.slipListLayout = slipListLayout;
        }

        @Override
        public void onStatusChanged(SwipeListLayout.Status status) {
            if (status == SwipeListLayout.Status.Open) {
                //若有其他的item的状态为Open，则Close，然后移除
                if (sets.size() > 0) {
                    for (SwipeListLayout s : sets) {
                        s.setStatus(SwipeListLayout.Status.Close, true);
                        sets.remove(s);
                    }
                }
                sets.add(slipListLayout);

            } else {
                if (sets.contains(slipListLayout))
                    sets.remove(slipListLayout);
            }
        }

        @Override
        public void onStartCloseAnimation() {

        }

        @Override
        public void onStartOpenAnimation() {

        }

    }
}
