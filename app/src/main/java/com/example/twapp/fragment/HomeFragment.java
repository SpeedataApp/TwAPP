package com.example.twapp.fragment;

import android.content.Context;
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
import com.example.twapp.adapter.InfaoAdapter;
import com.example.twapp.adapter.InforClass;
import com.example.twapp.adapter.ListAdapter;
import com.example.twapp.adapter.TWClass;
import com.example.twapp.db.TwBody;
import com.example.twapp.uitl.ChartView;
import com.example.twapp.uitl.MyEventBus;
import com.example.twapp.uitl.PlaySound;
import com.example.twapp.uitl.ReadSerialPort;
import com.example.twapp.uitl.SharedPreferencesUitl;
import com.example.twapp.uitl.TWManager;
import com.example.twapp.uitl.Vibrator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;


/**
 * Created by lenovo-pc on 2017/7/18.
 */

public class HomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    LineChartView chartView;
    private ChartView chartViews;
    private ImageView imageStart;
    private SharedPreferencesUitl preferencesUitl;
    private Vibrator vibrator;
    private ListAdapter listAdapter;
    private InfaoAdapter infaoAdapter;
    private RecyclerView RecyclerView;
    private RecyclerView recyclerViewInfo;
    private List<TWClass> list = new ArrayList<>();
    private List<InforClass> inforList = new ArrayList<>();
    private ToggleButton toggleButton;
    private LinearLayout lyTable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        preferencesUitl = SharedPreferencesUitl.getInstance(getActivity(), "tw");
        vibrator = new Vibrator(getActivity());
    }


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
        recyclerViewInfo = v.findViewById(R.id.RecyclerView_info);
        infaoAdapter = new InfaoAdapter(getActivity(), R.layout.list_item_layout_2, inforList);
        listAdapter = new ListAdapter(getActivity(), R.layout.list_item_layout, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerViewInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewInfo.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
//        layoutManager.setStackFromEnd(true);
//        layoutManager.setReverseLayout(true);
        layoutManager.setRecycleChildrenOnDetach(true);
        RecyclerView.setAdapter(listAdapter);
        recyclerViewInfo.setAdapter(infaoAdapter);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chartView.setVisibility(View.VISIBLE);
                    lyTable.setVisibility(View.GONE);
                } else {
                    chartView.setVisibility(View.GONE);
                    lyTable.setVisibility(View.VISIBLE);

                }
            }
        });
        //        initZhexian();
        initInfo();
        return v;
    }

    private void initInfo() {
        InforClass inforClass1 = new InforClass("张无忌", "23", "男", "1023", "骨折", "骨科");
        inforList.add(inforClass1);
        InforClass inforClass2 = new InforClass("赵敏", "5", "女", "1124", "发烧", "儿科");
        inforList.add(inforClass2);
        infaoAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSerilportData(MyEventBus myEventBus) {
        byte[] SerilportData = myEventBus.getTemperature();
        parseDatas(SerilportData);
    }

    private boolean isHight;

    public void parseDatas(byte[] b) {
        if (TWManager.getInstance().isValid(b)) {
            TWManager.getInstance().assembleData().parseFlag().decodeSNandpayload().parseSN().parsePayload().saveDb();

            TwBody twBody = TWManager.getTemperatureData();
            if (twBody.getTemperatures() != null || twBody.getTemperatures().size() != 0) {
                for (int i = 0; i < twBody.getTemperatures().size(); i++) {
                    TWClass twClass = new TWClass();
                    twClass.setTwData(twBody.getTemperatures().get(i));
                    twClass.setTwTime(twBody.getTwTime().get(i));
                    list.add(twClass);
                    listAdapter.notifyDataSetChanged();
                }

                List<String> tdata = twBody.getTemperatures();
                for (int i = 0; i < tdata.size(); i++) {
                    if (Double.valueOf(tdata.get(i)) > 27) {
                        isHight = false;
                    }
                }
                if (!isHight) {
                    boolean cc = preferencesUitl.read("thightSound", false);
                    if (cc) {
                        PlaySound.play(PlaySound.HIGHT_SOUND, PlaySound.NO_CYCLE);
                    }
                    if (preferencesUitl.read("thightShake", false)) {
                        vibrator.setVibrator();
                    }
                }
                chartViews.setKLine(twBody.getTemperatures(), twBody.getTwTime());
            }
        } else {
            Toast.makeText(getActivity(), "无效数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ReadSerialPort.onDestroy();
        handler.removeCallbacks(runnable);
    }

    boolean isFlag = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (isFlag) {
            isFlag = false;
            imageStart.setBackground(getActivity().getDrawable(R.drawable.stop));
//            ReadSerialPort.startReader();
//            parseDatas(datas1)；
            handler.postDelayed(runnable, 0);
        } else {
            isFlag = true;
            imageStart.setBackground(getActivity().getDrawable(R.drawable.start));
//            ReadSerialPort.onDestroy();
            handler.removeCallbacks(runnable);
//            parseDatas(datas2);
        }
    }
}
