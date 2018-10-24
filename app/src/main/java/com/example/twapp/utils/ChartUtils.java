package com.example.twapp.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * 图表工具
 * Created by yangle on 2016/11/29.
 */
public class ChartUtils {

    private static XAxis xAxis;

    /**
     * 初始化图表
     *
     * @return 初始化后的图表
     */
    public static LineChart initChart(LineChart mLineChart) {
//        mLineChart.setMaxVisibleValueCount(4);//设置图表绘制可见标签数量最大值. 仅在setDrawValues() 启用时生效
        // 是否在折线图上添加边框
        mLineChart.setDrawBorders(false);
        //描述信息
        Description description = new Description();
        description.setText("体温曲线图");
        //设置描述信息
        mLineChart.setDescription(description);
        mLineChart.setBorderWidth(2f);
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        mLineChart.setNoDataText("暂无数据");

        // 是否绘制背景颜色。
        mLineChart.setDrawGridBackground(false);
        // 触摸
        mLineChart.setTouchEnabled(true);
        // 拖拽
        mLineChart.setDragEnabled(true);
        // 缩放
        mLineChart.setScaleEnabled(true);

        mLineChart.setPinchZoom(false);
        // 隐藏右边 的坐标轴
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.setViewPortOffsets(70, 60, 50, 100);

        // 设置背景
        mLineChart.setBackgroundColor(Color.WHITE);
        mLineChart.setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
        mLineChart.setHighlightPerDragEnabled(true);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        mLineChart.setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        mLineChart.setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。


        xAxis = mLineChart.getXAxis();
        //是否启用X轴
        xAxis.setEnabled(true);
        //是否绘制X轴线
        xAxis.setDrawAxisLine(true);
        //设置X轴上每个竖线是否显示
        xAxis.setDrawGridLines(true);
        //设置是否绘制X轴上的对应值(标签)
        xAxis.setDrawLabels(true);


        //设置X轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置竖线为虚线样式
        // xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setGranularity(1);
        //设置x轴标签数
//        xAxis.setLabelCount(5);
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setLabelRotationAngle(20f);//设置x轴标签的旋转角度

        //图表第一个和最后一个label数据不超出左边和右边的Y轴
        // xAxis.setAvoidFirstLastClipping(true);
        yAxis = mLineChart.getAxisLeft();
        yAxis.setEnabled(true);  // 隐藏右边 的坐标轴
        yAxis.setDrawGridLines(true);// 隐藏左边坐标轴横网格线
        yAxis.setGranularity(1);//设置Y轴最小间隔
//        leftAxis.setLabelCount(5);
        yAxis.setStartAtZero(false);
        yAxis.setAxisMaxValue(45.0f);
        yAxis.setAxisMinValue(35.0f);//设置轴的最小值。这样设置将不会根据提供的数据自动计算。
        // 设置比例图标示，就是那个一组y的value的
        Legend mLegend = mLineChart.getLegend();
        mLegend.setEnabled(false);
//        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        mLegend.setForm(Legend.LegendForm.SQUARE);// 样式
//        mLegend.setFormSize(15.0f);// 字体
//        mLegend.setTextColor(Color.RED);// 颜色
        mLineChart.invalidate();
        return mLineChart;
    }

    private static YAxis yAxis;

    /**
     * 设置图表数据
     *
     * @param chart  图表
     * @param values 数据
     */
    public static void setChartData(LineChart chart, List<Entry> values) {


        LineDataSet lineDataSet;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // y轴数据集
            lineDataSet = new LineDataSet(values, "");
            for (int i = 0; i < values.size(); i++) {
                Entry entry = values.get(i);
                // 低热37.3~38℃（99.1~100.4F）
                // 中等热38.1~39℃（100.6~102.2F）
                // 高热39.1~41℃（102.4~105.8F）
                // 超高热41℃（105.8F）及以上
                if (entry.getY() > 37.2f) {
                    setHIGHTLine("低热", 37.3f, Color.parseColor("#FFC125"));
                    if (entry.getY() > 38.1f) {
                        setHIGHTLine("中等热", 38.1f, Color.parseColor("#FF8C00"));
                        if (entry.getY() > 39.1f) {
                            setHIGHTLine("高热", 39.1f, Color.parseColor("#EE4000"));
                            if (entry.getY() > 40.0f) {
                                setHIGHTLine("超高热", 40.0f, Color.parseColor("#EE0000"));
                            }
                        }
                    }
                }
            }
            // 用y轴的集合来设置参数
            // 线宽
            lineDataSet.setLineWidth(1.5f);
            // 显示的圆形大小
            lineDataSet.setCircleSize(3.0f);
            // 折线的颜色
            lineDataSet.setColor(Color.DKGRAY);
            // 圆球的颜色
            lineDataSet.setCircleColor(Color.BLUE);
            // 设置mLineDataSet.setDrawHighlightIndicators(false)后，
            // Highlight的十字交叉的纵横线将不会显示，
            // 同时，lineDataSet.setHighLightColor(Color.CYAN)失效。
            lineDataSet.setDrawHighlightIndicators(true);
            // 按击后，十字交叉线的颜色
            lineDataSet.setHighLightColor(Color.CYAN);
            // 设置这项上显示的数据点的字体大小。
            lineDataSet.setValueTextSize(10.0f);
            lineDataSet.setDrawCircleHole(true);
            // 默认是直线
            // 曲线的平滑度，值越大越平滑。
            lineDataSet.setCubicIntensity(0.2f);
            // 填充曲线下方的区域，红色，半透明。
            //        lineDataSet.setDrawFilled(true);
            //        lineDataSet.setFillAlpha(128);
            //        lineDataSet.setFillColor(Color.RED);
            // 填充折线上数据点、圆球里面包裹的中心空白处的颜色。
            lineDataSet.setCircleColorHole(Color.YELLOW);
            lineDataSet.setDrawValues(true);
            // 不显示定位线
            // lineDataSet.setHighlightEnabled(false);
            LineData data = new LineData(lineDataSet);
            chart.setData(data);
            chart.setVisibleXRangeMaximum(5.0f);//设置x轴最多显示数据条数，（要在设置数据源后调用，否则是无效的）
            chart.invalidate();
        }
    }

    private List<Entry> getData(List<String> templeat) {
        List<Entry> values = new ArrayList<>();
        for (int i = 0; i < templeat.size(); i++) {
            values.add(new Entry(i, Float.parseFloat(templeat.get(i))));
        }
        return values;
    }

    /**
     * 更新图表
     *
     * @param chart  图表
     * @param values 数据
     */
    public static void notifyDataSetChanged(LineChart chart, final List<String> times, List<Entry> values) {
//        chart.getXAxis().setLabelCount(times.size());
//        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                String[] dayValues = new String[times.size()];
//                for (int i = 0; i < dayValues.length; i++) {
//                    dayValues[i] = value+times.get(i);
////                    return value+times.get(i);
//                }
//                return String.valueOf(dayValues); // e.g. append a dollar-sign
////                return xValuesProcess(times)[(int) value];
//            }
//
//            @Override
//            public int getDecimalDigits() {
//                return 0;
//            }
//        });
        MyXFormatter myXFormatter = new MyXFormatter(times);
        xAxis.setValueFormatter(myXFormatter);
        chart.invalidate();
        setChartData(chart, values);
    }

    public static class MyXFormatter implements IAxisValueFormatter {
        List<String> mValues;

        public MyXFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int i = (int) value % mValues.size();
            return mValues.get(i).substring(10);
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    }

    /**
     * x轴数据处理
     *
     * @return x轴数据
     */
    private static String[] xValuesProcess(List<String> times) {
        String[] dayValues = new String[times.size()];
        for (int i = 0; i < dayValues.length; i++) {
            dayValues[i] = times.get(i);
        }
        return dayValues;
    }

    /**
     * s设置警戒线
     *
     * @param name
     * @param value
     * @param color
     */

    public static void setHIGHTLine(String name, float value, int color) {
        LimitLine ll = new LimitLine(value, name);
        ll.setLineWidth(1f);
        ll.setLineColor(color);
        ll.setTextColor(color);
        ll.setTextSize(12);
        ll.setEnabled(true);
        yAxis.addLimitLine(ll);
    }
}
