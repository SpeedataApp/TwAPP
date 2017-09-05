package com.example.twapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.twapp.R;
import com.example.twapp.adapter.MyAdapter;
import com.example.twapp.adapter.twClass;
import com.example.twapp.db.TwBody;
import com.example.twapp.uitl.ChartView;
import com.example.twapp.uitl.MyEventBus;
import com.example.twapp.uitl.ReadSerialPort;
import com.example.twapp.uitl.TWManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import lecho.lib.hellocharts.view.LineChartView;


/**
 * Created by lenovo-pc on 2017/7/18.
 */

public class HomeFragment extends android.support.v4.app.Fragment {
    LineChartView chartView;
    private MyAdapter myAdapter;
    private ListView twList;
    private ChartView chartViews;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ReadSerialPort.startReader();
        myAdapter = new MyAdapter(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        chartView = v.findViewById(R.id.chartView);
        chartViews = new ChartView(chartView, getActivity());
        twList = v.findViewById(R.id.homefrg_listview);
        twList.setAdapter(myAdapter);

//        initZhexian();
        return v;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSerilportData(MyEventBus myEventBus) {
        byte[] SerilportData = myEventBus.getTemperature();
        if (TWManager.getInstance().isValid(SerilportData)) {
            TWManager.getInstance().assembleData().parseFlag().decodeSNandpayload().parseSN().parsePayload().saveDb();

            TwBody twBody = TWManager.getTemperatureData();
            twClass twClass = new twClass(twBody.getModel(), twBody.getDate(), twBody.getRunningNumber(), twBody.getIsLowBattery());
            myAdapter.addDatas(twClass);
            myAdapter.notifyDataSetChanged();
//            tdata = twBody.getTemperatures();
            chartViews.setKLine(twBody.getTemperatures(),twBody.getTwTime());
        } else {
            Toast.makeText(getActivity(), "无效数据", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


//    private void initZhexian() {
//        axisX.setValues(mAxisXValues).setHasLines(true).setTextColor(Color.BLACK).setLineColor(Color.WHITE).setTextSize(12).setName("时间");
//        axisY.setHasLines(true).setTextColor(Color.BLACK).setLineColor(Color.WHITE);
//        axisY.setName("温度(℃)");
//        //        X轴上的标注数量,点少的时候可以这么用，点多的时候，就不建议这么用了
//        //        axisX.setMaxLabelChars(8);
//        //        x 轴在底部
//        lineChartData.setAxisXBottom(axisX);
//        //        x 轴在顶部
//        //        lineChartData.setAxisXTop(axisX);
//        //        y 轴在左，也可以右边
//        lineChartData.setAxisYLeft(axisY);
//        //        这两句话设置折线上点的背景颜色，默认是有一个小方块，而且背景色和点的颜色一样
//        //        如果想要原来的效果可以不用这两句话，我的显示的是透明的
//        lineChartData.setValueLabelBackgroundColor(Color.TRANSPARENT);
//        lineChartData.setValueLabelBackgroundEnabled(false);
//        //        把数据放在chart里，设置完这句话其实就可以显示了
//        chartView.setLineChartData(lineChartData);
//        //        设置行为属性，支持缩放、滑动以及平移，设置他就可以自己设置动作了
//        chartView.setInteractive(true);
//        //        可放大
//        chartView.setZoomEnabled(true);
//        //        我这边设置横向滚动
//        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
//        lineChartData.setValueLabelBackgroundEnabled(false);
//        //        把数据放在chart里，设置完这句话其实就可以显示了
//        chartView.setLineChartData(lineChartData);
//        //        设置行为属性，支持缩放、滑动以及平移，设置他就可以自己设置动作了
//        chartView.setInteractive(true);
//        //        可放大
//        chartView.setZoomEnabled(true);
//        //        我这边设置横向滚动
//        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
//        //        设置可视化视图样式，这里能做的东西非常多，
//        final Viewport v = new Viewport(chartView.getMaximumViewport());
//        //        我设置两种。点击不同按钮时，y轴固定最大值最小值不一样
//        //        这里可以固定x轴，让y轴变化，也可以x轴y轴都固定，也就是固定显示在你设定的区间里的点point（x，y）
//        v.top = 45;
//        v.bottom = 25;
//        //        这句话非常关键，上面两种设置，来确定最大可视化样式
//        //        我们可以理解为，所有点放在linechart时，整个视图全看到时候的样子，也就是点很多很多，距离很紧密
//        chartView.setMaximumViewport(v);
//        //        接着我们要设置，我们打开这个页面时显示的样子
//        //        如果你想所有，这两句话就不用了
//        //        当然这个非常灵活，也可以固定显示y轴 最小多少，最大多少
//        v.left = 0;
//        v.right = 3;
//        //        确定上两句话的设置
//        chartView.setCurrentViewport(v);
//
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ReadSerialPort.onDestroy();
    }
}
