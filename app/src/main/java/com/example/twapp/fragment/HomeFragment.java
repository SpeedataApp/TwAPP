package com.example.twapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.twapp.R;
import com.example.twapp.adapter.PeopleAdapter;
import com.example.twapp.adapter.TwDatasAdapter;
import com.example.twapp.been.PeopleInfor;
import com.example.twapp.been.TwDataInfo;
import com.example.twapp.db.TwBody;
import com.example.twapp.utils.ChartView;
import com.example.twapp.utils.DBUitl;
import com.example.twapp.utils.MyEventBus;
import com.example.twapp.utils.ReadSerialPort;
import com.example.twapp.utils.ScanDecode;
import com.example.twapp.utils.SharedPreferencesUitl;
import com.example.twapp.utils.TWManager;
import com.example.twapp.utils.Vibrator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;
import xyz.reginer.baseadapter.CommonRvAdapter;


/**
 * Created by lenovo-pc on 2017/7/18.
 */

public class HomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener, CommonRvAdapter.OnItemClickListener {
    LineChartView chartView;
    private ChartView chartViews;
    private ImageView imageStart;
    private SharedPreferencesUitl preferencesUitl;
    private Vibrator vibrator;
    private TwDatasAdapter listAdapter;
    private PeopleAdapter infaoAdapter;
    private RecyclerView RecyclerView;
    private RecyclerView peopleList;
    private List<TwDataInfo> list = new ArrayList<>();
    private List<PeopleInfor> inforList = new ArrayList<>();
    private ToggleButton toggleButton;
    private LinearLayout lyTable;
    private ScanDecode scanDecode;
    private DBUitl dBtable;
    private String runingNumber;
    //    private TWManager twManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        preferencesUitl = SharedPreferencesUitl.getInstance(getActivity(), "tw");
        vibrator = new Vibrator(getActivity());
//        scanDecode = new ScanDecode(getActivity());
//        twManager = TWManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.se4500.onDecodeComplete");
        getActivity().registerReceiver(receiver, filter);
        dBtable = new DBUitl();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.se4500.onDecodeComplete".equals(intent.getAction())) {
                String ss = intent.getStringExtra("se4500");
                Toast.makeText(context, ss, Toast.LENGTH_SHORT).show();
            }
        }
    };

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 5000);
            parseDatas(datas2);
        }
    };

    byte[] datas1 = {0x5A, 0x0A, (byte) 0x88, 0x04, (byte) 0xD9, 0x20, (byte) 0xDE,
            (byte) 0xC0, (byte) 0xC7, (byte) 0xF7, 0x0D};
    byte[] datas2 = {0x5A, 0x0b, (byte) 0x88, 0x04, (byte) 0xD9, 0x20, (byte) 0xDE,
            0x40, 0x47, (byte) 0xC7, (byte) 0xF0, (byte) 0xB2};

    @Override
    public void onResume() {
        super.onResume();
        initInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        chartView = v.findViewById(R.id.chartView);
        imageStart = v.findViewById(R.id.image_start);
        toggleButton = v.findViewById(R.id.tbtn_change);
        lyTable = v.findViewById(R.id.table);
        imageStart.setOnClickListener(this);
        chartViews = new ChartView(chartView, getActivity());
        chartViews.onClick();
        chartViews.initZhexian();
        RecyclerView = v.findViewById(R.id.recyclerview);
        peopleList = v.findViewById(R.id.RecyclerView_info);
        infaoAdapter = new PeopleAdapter(getActivity(), R.layout.list_item_layout_2, inforList);
        listAdapter = new TwDatasAdapter(getActivity(), R.layout.list_item_layout, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        peopleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        peopleList.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        layoutManager.setRecycleChildrenOnDetach(true);
        RecyclerView.setAdapter(listAdapter);
        peopleList.setAdapter(infaoAdapter);
        infaoAdapter.setOnItemClickListener(this);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chartView.setVisibility(View.VISIBLE);
                    lyTable.setVisibility(View.GONE);
                    TwBody twBody = dBtable.queryTwBody(runingNumber);
                    if (twBody.getTemperatures() != null || twBody.getTemperatures().size() != 0) {
                        chartViews.setKLine(twBody.getTemperatures(), twBody.getTwTime());
                    }
                } else {
                    chartView.setVisibility(View.GONE);
                    lyTable.setVisibility(View.VISIBLE);

                }
            }
        });
        return v;
    }

    private void initInfo() {
        List<TwBody> list = dBtable.queryAll();
        inforList.clear();
        for (int i = 0; i < list.size(); i++) {
            PeopleInfor inforClass1 = new PeopleInfor(list.get(i).getRunningNumber(), list.get(i).getPeopleNun(), list.get(i).
                    getpName(), list.get(i).getPaAge(), list.get(i).getpGender(),
                    list.get(i).getpBedNumber(), list.get(i).getpResult());
            inforList.add(inforClass1);
            infaoAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSerilportData(MyEventBus myEventBus) {
        byte[] SerilportData = myEventBus.getTemperature();
        parseDatas(SerilportData);
    }

    public void parseDatas(byte[] b) {
        if (TWManager.isValid(b)) {
            TWManager.assembleData().parseFlag().decodeSNandpayload().parseSN().parsePayload();
        } else {
            Toast.makeText(getActivity(), "无效数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    boolean isFlag = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (isFlag) {
            isFlag = false;
            imageStart.setBackground(getActivity().getDrawable(R.drawable.stop));
            ReadSerialPort.startReader();
//            parseDatas(datas1)；
//            handler.postDelayed(runnable, 0);
        } else {
            isFlag = true;
            imageStart.setBackground(getActivity().getDrawable(R.drawable.start));
            ReadSerialPort.onDestroy();
//            handler.removeCallbacks(runnable);
//            parseDatas(datas2);
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
        runingNumber = inforList.get(position).getRunNum();
        TwBody twBody = dBtable.queryTwBody(runingNumber);
        if (twBody.getTemperatures() != null || twBody.getTemperatures().size() > 1) {
            list.clear();
            for (int i = 0; i < twBody.getTemperatures().size(); i++) {
                TwDataInfo twClass = new TwDataInfo();
                twClass.setTwData(twBody.getTemperatures().get(i));
                twClass.setTwTime(twBody.getTwTime().get(i));
                twClass.setNum(i + 1);
                list.add(twClass);
                listAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ReadSerialPort.onDestroy();
        handler.removeCallbacks(runnable);
        getActivity().unregisterReceiver(receiver);
    }

}