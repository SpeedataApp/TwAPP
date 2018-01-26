package com.example.twapp.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twapp.R;
import com.example.twapp.adapter.TestData;
import com.example.twapp.base.BaseActivity;
import com.example.twapp.utils.MyEventBus;
import com.example.twapp.utils.ReadSerialPort;
import com.example.twapp.utils.TWManager;
import com.speedata.ui.adapter.CommonAdapter;
import com.speedata.ui.adapter.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActivity {
    //    TextView tvTrue, tvFalse, numtrue, numfasle, numtotal, tv;
    Button btn, btnInstall, btnClear;
    ListView listView;
    boolean isFlag = true;
    private long start;
    private String num;
    private CommonAdapter<TestData> adapter;
    private TestData datas;
    private TextView tw_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        tvFalse = findViewById(R.id.tv_false);
//        tvTrue = findViewById(R.id.tv_true);
//        numtrue = findViewById(R.id.num_true);
//        numfasle = findViewById(R.id.num_false);
//        numtotal = findViewById(R.id.num_total);
        listView = findViewById(R.id.listview);
        btnInstall = findViewById(R.id.install);
        tw_flag = findViewById(R.id.tw_flag);
//        tv = findViewById(R.id.num_false2);

        btn = findViewById(R.id.start);
        btnClear = findViewById(R.id.clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testData.clear();
                initItem();
            }
        });
        EventBus.getDefault().register(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFlag) {
//                    total = 0;
//                    trues = 0;
//                    falses = 0;
//                    falses2 = 0;
//                    numfasle.setText("失败：");
//                    numtrue.setText("成功：");
//                    numtotal.setText("总接收：");
//                    tv.setText("crc错误：");
//
//                    tvFalse.setText("");
//                    tvTrue.setText("");
                    isFlag = false;
                    ReadSerialPort.startReader();
                    btn.setText("关闭接收数据");
                } else {
                    isFlag = true;
                    ReadSerialPort.onDestroy();
                    btn.setText("开始接收数据");
                }
            }
        });
        btnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    public void initItem() {
        adapter = new CommonAdapter<TestData>(this, testData, R.layout.item_test_layout) {
            @Override
            public void convert(ViewHolder helper, TestData item) {
                helper.setText(R.id.num_sn, item.getTwNum());
                helper.setText(R.id.id_true, item.getTrues() + "");
                helper.setText(R.id.id_false, item.getFalses() + "");
                helper.setText(R.id.xiao_shi, item.getFlag9() + "");
                helper.setText(R.id.da_jian, item.getFlag11() + "");
                helper.setText(R.id.da_fen, item.getFlag61() + "");
            }
        };
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    int total = 0;
    int trues = 0;
    int falses = 0;
    int falses2 = 0;

    /**
     * 接收串口数据
     *
     * @param myEventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSerilportData(MyEventBus myEventBus) {
        byte[] originalData = myEventBus.getTemperature();
//        numtotal.setText("总接收：" + (total += 1));
//        if (originalData == null || originalData.length <= 2) {
//            System.out.println("invalid data for len");
//            numfasle.setText("失败：" + (falses += 1));
//            tvFalse.setText(DataConversionUtils.byteArrayToString(originalData));
//        } else if (originalData[1] + 1 != originalData.length) {
//            numfasle.setText("失败：" + (falses += 1));
//            tvFalse.setText(DataConversionUtils.byteArrayToString(originalData));
//            System.out.println("invalid data for len" + (originalData[1] + 1) + " !=" + originalData.length);
//        } else if (originalData[0] != 0x5A) {
//            System.out.println("invalid header");
//            numfasle.setText("失败：" + (falses += 1));
//            tvFalse.setText(DataConversionUtils.byteArrayToString(originalData));
//        } else if (DataConversionUtils.byteArrayToInt(CRCUtil.GetCRC(0xffff, cutBytes(originalData, 1, originalData.length - 1))) != 0) {
//            //TODO 判断 CRC
//            Log.e("log", "校验失败 数据" + DataConversionUtils.byteArrayToString(originalData));
//            tv.setText("crc失败：" + (falses2 += 1));
//            tvFalse.setText(DataConversionUtils.byteArrayToString(originalData));
//            byte[] ff = cutBytes(originalData, 1, originalData.length - 1);
//            byte[] gg = CRCUtil.GetCRC(0xffff, ff);
//            int ss = DataConversionUtils.byteArrayToInt(gg);
//        } else {
//            Log.i("log", "校验通过 数据" + DataConversionUtils.byteArrayToString(originalData));
//            System.out.println("valid ok");
//            numtrue.setText("成功：" + (trues += 1));
//            tvTrue.setText(DataConversionUtils.byteArrayToString(originalData));
//        }
        parseDatas(originalData);
    }

    List<TestData> testData = new ArrayList<>();

    private void test(String num) {
        for (int i = 0; i < testData.size(); i++) {
            if (num.equals(testData.get(i).getTwNum())) {
                testData.get(i).setTrues(testData.get(i).getTrues() + 1);
                if ((System.currentTimeMillis() - testData.get(i).getTimes()) > 10000 && (System.currentTimeMillis() - testData.get(i).getTimes()) < 60000) {
                    testData.get(i).setFlag11(testData.get(i).getFlag11() + 1);
                } else if ((System.currentTimeMillis() - testData.get(i).getTimes()) < 10000 && (System.currentTimeMillis() - testData.get(i).getTimes()) > 0) {
                    testData.get(i).setFlag9(testData.get(i).getFlag9() + 1);
                } else {
                    testData.get(i).setFlag61(testData.get(i).getFlag61() + 1);
                }
                testData.get(i).setTimes(System.currentTimeMillis());
            }

        }
    }

    public void parseDatas(byte[] b) {
        if (TWManager.isValid(b)) {
            TWManager.assembleData().parseFlag().decodeSNandpayload().parseSN();
            tw_flag.setText("FLAG" + "-" + TWManager.getBody().getRunningNumber());
            num = TWManager.getBody().getRunningNumber();
            test(num);

        } else {
            for (int i = 0; i < testData.size(); i++) {
                if (testData.get(i).getTwNum().equals(num)) {
                    testData.get(i).setFalses(testData.get(i).getFalses() + 1);
                }
            }
        }

//        for (int i = 0; i < testData.size(); i++) {
//            if (testData.get(i).getTwNum().equals("132")) {
//                tvTrue.setText(testData.get(i).getTwNum() + "正确:" + testData.get(i).getTrues() + "错误:" + testData.get(i).getFalses() + "小于10秒:" + testData.get(i).getFlag9() + "  大于10小于1分:" + testData.get(i).getFlag11() + "  大于1分:" + testData.get(i).getFlag61() + "\n");
//            } else {
//                tvFalse.setText(testData.get(i).getTwNum() + "正确:" + testData.get(i).getTrues() + "错误:" + testData.get(i).getFalses() + "小于10秒:" + testData.get(i).getFlag9() + "  大于10小于1分:" + testData.get(i).getFlag11() + "  大于1分:" + testData.get(i).getFlag61() + "\n");
//            }
//        }
        initItem();
    }

    private void showInputDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(TestActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(TestActivity.this);
        inputDialog.setTitle("根据flag提示输入sn号").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText.getText().toString().isEmpty()) {
                            datas = new TestData(editText.getText().toString(), 0, 0, 0, 0, 0, 0, 0);
                            testData.add(datas);
                            initItem();
                        }else {
                            Toast.makeText(TestActivity.this, "请输入sn号", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
    }

    /**
     * 截取数组
     *
     * @param bytes  被截取数组
     * @param start  被截取数组开始截取位置
     * @param length 新数组的长度
     * @return 新数组
     */
    public static byte[] cutBytes(byte[] bytes, int start, int length) {
        byte[] res = new byte[length];
        System.arraycopy(bytes, start, res, 0, length);
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReadSerialPort.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ReadSerialPort.onDestroy();
    }
}
