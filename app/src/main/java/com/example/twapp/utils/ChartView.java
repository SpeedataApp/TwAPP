package com.example.twapp.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * ----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 *
 * @author :孙天伟 in  2017/9/27   13:30.
 *         联系方式:QQ:420401567
 *         功能描述:  画折线
 */
public class ChartView {
    private LineChartView chartView;
    private SharedPreferencesUitl preferencesUitl;
    private Vibrator vibrator;
    private Context context;

    public ChartView(LineChartView chartView, Context context) {
        this.chartView = chartView;
        this.context = context;
        preferencesUitl = SharedPreferencesUitl.getInstance(context, "tw");
        PlaySound.initSoundPool(context);
        vibrator = new Vibrator(context);
    }

    //    折线集合（add多个line就会显示对条线）
    List<Line> chartlines = new ArrayList<>();
    //    横坐标集合，可以设置标注名称，就是x轴的值集合，可以是0-100，也可以是10000-20000
    List<AxisValue> mAxisXValues = new ArrayList<>();
    //    点的集合，顾名思义一个point，就有他所对应的x y值，假如有个点的值是（5,100）x=5 y=100
    List<PointValue> pointValues = new ArrayList<>();
    //    折线，多条折线可以new多个线，要显示谁，就在line集合里add谁
    Line chartline = new Line();
    //    线上的数据，鸡肋但必须得用
    LineChartData lineChartData = new LineChartData();
    //    定义格式，小数点等等信息
    LineChartValueFormatter chartValueFormatter = new SimpleLineChartValueFormatter(2);
    //    X轴、Y轴
    Axis axisX = new Axis();
    Axis axisY = new Axis();


    public void setKLine(List<String> twDate, List<String> twTime) {
//        清空数据，将不影响下一次点击按钮 传过来一个新的list的显示
        chartlines.clear();
        mAxisXValues.clear();
        pointValues.clear();
        /*
        * 这个循环是循环为x轴（x底端轴线）增加值应该只能是float类型的，当然int可以
        */
//        List<String> twTime = TWManager.getTemperatureData().getTwTime();
        for (int i = 0; i < twTime.size(); i++) {
            String time = twTime.get(i).toString();
            AxisValue axisValue = new AxisValue(i);
            axisValue.setLabel(time);
            mAxisXValues.add(axisValue);
        }
//        x轴完事了，该布点了
        for (int i = 0; i < twDate.size(); i++) {
            PointValue pointValue = new PointValue(i, Float.parseFloat(twDate.get(i).toString()));
//            这个点的标注信息，String的随便来
            pointValue.setLabel(twDate.get(i).toString() + "℃");
//                温度显示小数
            chartline.setFormatter(chartValueFormatter);
//            把这个点添加到集合里去,等会显示用
            pointValues.add(pointValue);
        }
//        把点的集合放在线上，显示多条线 就用不同的line分别添加不同的values
        chartline.setValues(pointValues);
//        线的颜色
        chartline.setColor(Color.BLUE);
////        点的颜色
        chartline.setPointColor(Color.RED);
//        形状，ValueShape提供了几个
        chartline.setShape(ValueShape.CIRCLE);
//        线的透明度
//        chartline.setAreaTransparency(0);
//        点的大小
        chartline.setPointRadius(4);
//        点上的标注信息，刚才 pointValues里面每个点的标注
        chartline.setCubic(true);
//        阴影面积
        chartline.setFilled(true);
//        是否用线显示。如果为false 则没有曲线只有点显示
        chartline.setHasLines(true);
//        是否用点显示。如果为false 则没有点
        chartline.setHasPoints(true);
//        点的标注是否显示
        chartline.setHasLabels(true);
//        线的粗细
        chartline.setStrokeWidth(2);
//        显示几条线，就可以分别设置每一条线，然后add进来
        chartlines.add(chartline);

//        线的集合放在chart数据中，
        lineChartData.setLines(chartlines);

/*        x轴上面的值，就是刚才mAxisXValues的值
          5个参数分别是：
          1-例如4*24+12 显示为4日12时
          2-是否有x轴网格线
          3-x轴信息标注颜色是黑色
          4-x轴网格线白色
          5-x轴信息标注字体12
          6-x轴的大名
          Y轴也是这么设置，当你想点击不同的按钮，显示不同的信息时可用type进行处理
*/
        axisX.setHasTiltedLabels(true);// 设置X轴文字向左旋转45度
        axisX.setValues(mAxisXValues).setHasLines(true).setTextColor(Color.BLACK).setLineColor
                (Color.BLACK).setTextSize(12).setName("时间");
        axisY.setHasLines(true).setTextColor(Color.BLACK).setLineColor(Color.BLACK).setTextSize(12).setName("温度(℃)");
        axisX.setHasSeparationLine(true);
//        x 轴在底部
        lineChartData.setAxisXBottom(axisX);
//        y 轴在左，也可以右边
        lineChartData.setAxisYLeft(axisY);
        lineChartData.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
//        这两句话设置折线上点的背景颜色，默认是有一个小方块，而且背景色和点的颜色一样
//        如果想要原来的效果可以不用这两句话，我的显示的是透明的
        lineChartData.setValueLabelBackgroundColor(Color.TRANSPARENT);
        lineChartData.setValueLabelBackgroundEnabled(false);

//        把数据放在chart里，设置完这句话其实就可以显示了
        chartView.setLineChartData(lineChartData);

//        设置行为属性，支持缩放、滑动以及平移，设置他就可以自己设置动作了
        chartView.setInteractive(true);
//        可放大
        chartView.setZoomEnabled(true);
//        我这边设置横向滚动
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
//        设置可视化视图样式，这里能做的东西非常多，
        final Viewport v = new Viewport(chartView.getMaximumViewport());
//        我设置两种。点击不同按钮时，y轴固定最大值最小值不一样
//        这里可以固定x轴，让y轴变化，也可以x轴y轴都固定，也就是固定显示在你设定的区间里的点point（x，y）
        v.top = 45;
        v.bottom = 20;
//        这句话非常关键，上面两种设置，来确定最大可视化样式
//        我们可以理解为，所有点放在linechart时，整个视图全看到时候的样子，也就是点很多很多，距离很紧密
        chartView.setMaximumViewport(v);
//        接着我们要设置，我们打开这个页面时显示的样子
//        如果你想所有，这两句话就不用了
//        当然这个非常灵活，也可以固定显示y轴 最小多少，最大多少
        v.left = -2;
        v.right = 4;
//        确定上两句话的设置
        chartView.setCurrentViewport(v);
    }

    public void initZhexian() {
        axisX.setValues(mAxisXValues).setHasLines(true).setTextColor(Color.BLACK).setLineColor(Color.BLACK).setTextSize(12).setName("时间");
        axisY.setHasLines(true).setTextColor(Color.BLACK).setLineColor(Color.BLACK).setTextSize(12).setName("温度(℃)");
        //        x 轴在底部
        lineChartData.setAxisXBottom(axisX);
        //        y 轴在左，也可以右边
        lineChartData.setAxisYLeft(axisY);
        //        把数据放在chart里，设置完这句话其实就可以显示了
        chartView.setLineChartData(lineChartData);
        //        设置行为属性，支持缩放、滑动以及平移，设置他就可以自己设置动作了
        chartView.setInteractive(true);
        //        可放大
        chartView.setZoomEnabled(true);
        //        我这边设置横向滚动
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        //        设置可视化视图样式，这里能做的东西非常多，
        final Viewport v = new Viewport(chartView.getMaximumViewport());
        //        我设置两种。点击不同按钮时，y轴固定最大值最小值不一样
        //        这里可以固定x轴，让y轴变化，也可以x轴y轴都固定，也就是固定显示在你设定的区间里的点point（x，y）
        v.top = 45;
        v.bottom = 20;
        //        这句话非常关键，上面两种设置，来确定最大可视化样式
        //        我们可以理解为，所有点放在linechart时，整个视图全看到时候的样子，也就是点很多很多，距离很紧密
        chartView.setMaximumViewport(v);
        //        确定上两句话的设置
        chartView.setCurrentViewport(v);


    }

    public void onClick() {
        chartView.setOnValueTouchListener(

                new LineChartOnValueSelectListener() {
                    @Override
                    public void onValueSelected(int i, int i1, PointValue pointValue) {
                        Toast.makeText(context, "温度" + pointValue.getY() + "℃", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onValueDeselected() {
                        Toast.makeText(context.getApplicationContext(), "hhhh", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
