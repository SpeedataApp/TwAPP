package com.example.twapp.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.twapp.R;
import com.example.twapp.control.TwApplication;
import com.example.twapp.db.TwBody;
import com.example.twapp.db.TwBodyDao;
import com.example.twapp.utils.ChartView;

import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;

public class HistoFragment extends Fragment implements View.OnClickListener {
    private Button btnHistory;
    private LineChartView lineChartView;
    private ChartView chartView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//其余置为竖屏
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_histo, null);
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        btnHistory = v.findViewById(R.id.btn_history);
        btnHistory.setOnClickListener(this);
        lineChartView = v.findViewById(R.id.history_chartView);
//        lineChartView.setRotation(90);
        chartView = new ChartView(lineChartView, getActivity());
        chartView.initZhexian();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_history:
                TwBodyDao dao = TwApplication.getsInstance().getDaoSession().getTwBodyDao();

                List<TwBody> userList = dao.loadAll();
                if (userList.size() > 0 && userList != null) {
                    TwBody twBody = userList.get(0);

                    chartView.setKLine(twBody.getTemperatures(), twBody.getTwTime());
                    chartView.onClick();
                }
                break;
        }

    }
}
