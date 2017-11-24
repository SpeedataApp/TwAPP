package com.example.twapp.swipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twapp.R;
import com.example.twapp.been.PeopleInfor;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/3/17.
 */

public class SwipeAdapter extends BaseAdapter implements SwipeLayout.OnSwipeStateChangeListener {
    private Context mContext;
    private List<PeopleInfor> list;
    private MyClickListener myClickListener;
    private SwipeLayoutManager swipeLayoutManager;

    public SwipeAdapter(Context mContext, List<PeopleInfor> list) {
        super();
        this.mContext = mContext;
        myClickListener = new MyClickListener();
        this.list = list;
        swipeLayoutManager = SwipeLayoutManager.getInstance();
    }

    public void setList(List<PeopleInfor> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_layout_people, null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);
        holder.name.setText("姓名：" + list.get(position).getName());
        holder.age.setText("年龄：" + list.get(position).getAge());
        holder.gender.setText("性别：" + list.get(position).getGender());
        holder.bednum.setText(list.get(position).getBedNumber() + "床");
        holder.pNnm.setText("住院号："+list.get(position).getPeopleNum());
        holder.runNum.setText("设备号："+list.get(position).getRunNum());
        holder.dianliang.setText(list.get(position).getDianLiang());

        holder.imageView.setImageResource(list.get(position).getId());

        holder.tv_Chanage.setOnClickListener(myClickListener);
        holder.tv_Chanage.setTag(position);
        holder.tv_delete.setOnClickListener(myClickListener);
        holder.tv_delete.setTag(position);
        holder.sv_layout.setOnSwipeStateChangeListener(this);
        holder.sv_layout.setTag(position);

        holder.sv_layout.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayoutManager.closeUnCloseSwipeLayout();
                swipeLayoutManager.closeUnCloseSwipeLayout(false);
                if (onSwipeControlListener != null) {
                    onSwipeControlListener.onItemClick(position);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tv_Chanage, tv_delete;
        TextView pNnm, runNum, name, age, gender, bednum, dianliang;
        ImageView imageView;
        SwipeLayout sv_layout;

        public ViewHolder(View convertView) {
            pNnm = convertView.findViewById(R.id.p_num);
            runNum = convertView.findViewById(R.id.r_num);
            name = convertView.findViewById(R.id.name);
            age = convertView.findViewById(R.id.age);
            gender = convertView.findViewById(R.id.gender);
            bednum = convertView.findViewById(R.id.bed_number);
            dianliang = convertView.findViewById(R.id.dianliang);
            imageView = convertView.findViewById(R.id.image_pass);

            tv_Chanage = convertView.findViewById(R.id.tv_top);
            tv_delete = convertView.findViewById(R.id.tv_delete);
            sv_layout = convertView.findViewById(R.id.sv_layout);
        }

        public static ViewHolder getHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Integer position = (Integer) v.getTag();
            switch (v.getId()) {
                case R.id.tv_top:
                    //ToastUtils.showToast("position : " + position + " overhead is clicked.");
                    swipeLayoutManager.closeUnCloseSwipeLayout(false);
                    if (onSwipeControlListener != null) {
                        onSwipeControlListener.onChangen(position);
                    }
                    break;
                case R.id.tv_delete:
                    //ToastUtils.showToast("position : " + position + " delete is clicked.");
                    swipeLayoutManager.closeUnCloseSwipeLayout(false);
                    if (onSwipeControlListener != null) {
                        onSwipeControlListener.onDelete(position);
                    }
                    break;
                case R.id.sv_layout:


                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onOpen(SwipeLayout swipeLayout) {
        //ToastUtils.showToast(swipeLayout.getTag() + "onOpen.");
    }

    @Override
    public void onClose(SwipeLayout swipeLayout) {
        //ToastUtils.showToast(swipeLayout.getTag() + "onClose.");
    }

    @Override
    public void onStartOpen(SwipeLayout swipeLayout) {
        //            ToastUtils.showToast("onStartOpen.");
    }

    @Override
    public void onStartClose(SwipeLayout swipeLayout) {
        //            ToastUtils.showToast("onStartClose.");
    }

    private OnSwipeControlListener onSwipeControlListener;

    public void setOnSwipeControlListener(OnSwipeControlListener onSwipeControlListener) {
        this.onSwipeControlListener = onSwipeControlListener;
    }

    /**
     * overhead 和 delete点击事件接口
     */
    public interface OnSwipeControlListener {
        void onChangen(int position);

        void onDelete(int position);

        void onItemClick(int position);
    }
}
