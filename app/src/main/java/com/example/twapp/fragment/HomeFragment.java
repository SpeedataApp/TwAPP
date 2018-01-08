package com.example.twapp.fragment;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.twapp.R;
import com.example.twapp.adapter.TwDatasAdapter;
import com.example.twapp.been.PeopleInfor;
import com.example.twapp.been.TwDataInfo;
import com.example.twapp.db.TwBody;
import com.example.twapp.swipe.SwipeAdapter;
import com.example.twapp.swipe.SwipeLayoutManager;
import com.example.twapp.utils.ChartUtils;
import com.example.twapp.utils.ChartView;
import com.example.twapp.utils.DBUitl;
import com.example.twapp.utils.DialogChange;
import com.example.twapp.utils.MyEventBus;
import com.example.twapp.utils.PlaySound;
import com.example.twapp.utils.ReadSerialPort;
import com.example.twapp.utils.SharedPreferencesUitl;
import com.example.twapp.utils.TWManager;
import com.example.twapp.utils.Vibrator;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.speedata.libutils.DataConversionUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;


/**
 * Created by lenovo-pc on 2017/7/18.
 */

public class HomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener, SwipeAdapter.OnSwipeControlListener {
   TextView tvFlag;
  private  LineChartView chartView;
    private ChartView chartViews;
    private ImageView imageStart;
    private Vibrator vibrator;
    private TwDatasAdapter listAdapter;
    private ListView listPeople;
    private SwipeLayoutManager swipeLayoutManager;
    private SwipeAdapter swipeAdapter;


    private RecyclerView RecyclerView;
    private List<TwDataInfo> list = new ArrayList<>();
    private List<PeopleInfor> inforList = new ArrayList<PeopleInfor>();
    private ToggleButton toggleButton;
    private LinearLayout lyTable;
    private DBUitl dBtable;
    private String runingNumber = "";
    private static SharedPreferencesUitl preferencesUitl;
    private PeopleInfor peopleInfor;
    private boolean isFlag = true;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        preferencesUitl = SharedPreferencesUitl.getInstance(getActivity(), "tw");
        vibrator = new Vibrator(getActivity());
        dBtable = new DBUitl();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.update");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.update".equals(intent.getAction())) {
                initInfo();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        initInfo();
    }

    private List<Entry> getData(List<String> templeat) {
        List<Entry> values = new ArrayList<>();
        for (int i = 0; i < templeat.size(); i++) {
            values.add(new Entry(i, Float.parseFloat(templeat.get(i))));
        }
        return values;
    }

    LineChart mLineChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tvFlag = v.findViewById(R.id.tv_flag);
        chartView = v.findViewById(R.id.chartView);

        mLineChart = v.findViewById(R.id.chart);
        ChartUtils.initChart(mLineChart);
        listPeople = v.findViewById(R.id.list_people);
        imageStart = v.findViewById(R.id.image_start);
        imageStart.setOnClickListener(this);
        toggleButton = v.findViewById(R.id.tbtn_change);
        lyTable = v.findViewById(R.id.table);
        chartViews = new ChartView(chartView, getActivity());
        chartViews.onClick();
        chartViews.initZhexian();
        RecyclerView = v.findViewById(R.id.recyclerview);
        listAdapter = new TwDatasAdapter(getActivity(), R.layout.list_item_layout, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        layoutManager.setRecycleChildrenOnDetach(true);
        RecyclerView.setAdapter(listAdapter);
        initSwipView();
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    chartView.setVisibility(View.VISIBLE);
                    mLineChart.setVisibility(View.VISIBLE);
                    lyTable.setVisibility(View.GONE);
                    if (!"".equals(runingNumber)) {
                        TwBody twBody = dBtable.queryTwBody(runingNumber);
                        if (twBody.getTemperatures() != null) {
                            try {
                                ChartUtils.notifyDataSetChanged(mLineChart, twBody.getTwTime(), getData(twBody.getTemperatures()));
//                                chartViews.setKLine(twBody.getTemperatures(), twBody.getTwTime());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
//                    chartView.setVisibility(View.GONE);
                    mLineChart.setVisibility(View.GONE);
                    lyTable.setVisibility(View.VISIBLE);

                }
            }
        });
        return v;
    }

    private void initSwipView() {
        swipeLayoutManager = SwipeLayoutManager.getInstance();
        swipeAdapter = new SwipeAdapter(getActivity(), inforList);

        listPeople.setAdapter(swipeAdapter);
        listPeople.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                swipeLayoutManager.closeUnCloseSwipeLayout();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        swipeAdapter.setOnSwipeControlListener(this);
    }

    private void initInfo() {
        List<TwBody> list = dBtable.queryAll();
        inforList.clear();
        for (int i = 0; i < list.size(); i++) {
            peopleInfor = new PeopleInfor(list.get(i).getRunningNumber(), list.get(i).getPeopleNun(), list.get(i).
                    getPName(), list.get(i).getPaAge(), list.get(i).getPGender(),
                    list.get(i).getPBedNumber(), list.get(i).getIsLowBattery(), list.get(i).getPassId());
            inforList.add(peopleInfor);
            swipeAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 接收串口数据
     *
     * @param myEventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSerilportData(MyEventBus myEventBus) {
        byte[] SerilportDatas = myEventBus.getTemperature();
        parseDatas(SerilportDatas);
    }

    public void parseDatas(byte[] b) {
        if (TWManager.isValid(b)) {
            TWManager.assembleData().parseFlag().decodeSNandpayload().parseSN().parsePayload();
            initInfo();
            tvFlag.setText("FLAG"+TWManager.getBody().getModel()+"-"+TWManager.getBody().getDate()+TWManager.getBody().getRunningNumber());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    /**
     * 高低温报警
     *
     * @param d
     */
    public void isHights(float d) {
        if (d > preferencesUitl.read("hight", 38.0f)) {
            boolean cc = preferencesUitl.read("thightSound", false);
            if (cc) {
                PlaySound.play(PlaySound.HIGHT_SOUND, PlaySound.NO_CYCLE);
            }
            if (preferencesUitl.read("thightShake", false)) {
                vibrator.setVibrator();
            }
        } else if (d < preferencesUitl.read("low", 36.0f)) {
            boolean cc = preferencesUitl.read("thightSound", false);
            if (cc) {
                PlaySound.play(PlaySound.HIGHT_SOUND, PlaySound.NO_CYCLE);
            }
            if (preferencesUitl.read("thightShake", false)) {
                vibrator.setVibrator();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getActivity().unregisterReceiver(broadcastReceiver);
        ReadSerialPort.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        if (isFlag) {
            String s = "5A17C004D9205FC0FFBFFFBFFFBFFFBFFFBFFFBFFFBFE667";
//            byte[] s = {0x5A, 0x1B, (byte) 0xB0, 0x10, 0x67, (byte) 0x80, 0x7B, (byte) 0x80, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF,
//                    (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte)
//                    0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0xBF, (byte) 0x95, 0x44};
            if (TWManager.isValid(DataConversionUtils.HexString2Bytes(s))) {
                TWManager.assembleData().parseFlag().decodeSNandpayload().parseSN().parsePayload();
            }
            isFlag = false;
            dBtable.ChagePassIDs();
            initInfo();
            imageStart.setBackground(getActivity().getDrawable(R.drawable.stop));
            ReadSerialPort.startReader();
        } else {
            isFlag = true;
            imageStart.setBackground(getActivity().getDrawable(R.drawable.start));
            ReadSerialPort.onDestroy();
        }
    }


    @Override
    public void onChangen(int position) {
        DialogChange.showCustomizeDialog(getActivity(), inforList.get(position).getRunNum());
    }

    @Override
    public void onDelete(int position) {
        dBtable.delete(inforList.get(position).getRunNum());
        inforList.remove(position);
        swipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        runingNumber = inforList.get(position).getRunNum();
        TwBody twBody = dBtable.queryTwBody(runingNumber);
        try {
            if (twBody != null) {
                if (twBody.getTemperatures() != null) {
                    list.clear();
                    for (int i = twBody.getTemperatures().size() - 1; i >= 0; i--) {
                        isHights(Float.parseFloat(twBody.getTemperatures().get(i)));
                        TwDataInfo twClass = new TwDataInfo();
                        twClass.setTwData(twBody.getTemperatures().get(i) + "");
                        twClass.setTwTime(twBody.getTwTime().get(i));
                        twClass.setNum(i);
                        list.add(twClass);
                        listAdapter.notifyDataSetChanged();
                        //                    ChartUtils.notifyDataSetChanged(mLineChart, twBody.getTwTime(), getData(twBody.getTemperatures()));
                    }
                } else {
                    Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}