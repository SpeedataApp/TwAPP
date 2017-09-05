package com.example.twapp.uitl;

import android.util.Log;

import com.example.twapp.control.TwApplication;
import com.example.twapp.db.TwBody;
import com.example.twapp.db.TwBodyDao;
import com.speedata.libutils.DataConversionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //
 * //         .............................................
 * //                  佛祖镇楼                  BUG辟易
 *
 * @author :EchoXBR in  2017/8/17 下午7:55.
 *         功能描述:体温贴数据组装
 */

public class TWManager {

    public static TWManager mInstance;

    /**
     * 单实例
     *
     * @return ExcelUtils
     */
    public static TWManager getInstance() {
        if (mInstance == null) {
            synchronized (TWManager.class) {
                if (mInstance == null) {
                    mInstance = new TWManager();
                }
            }
        }
        return mInstance;
    }


    static byte Marker = 0x5A;
    static byte Len;
    static byte Flags;
    static byte[] SNs;
    static byte[] Payload;


    static byte[] byteData;

    /**
     * 判断原始数据是否有效
     *
     * @param originalData
     * @return
     */
    public static boolean isValid(byte[] originalData) {
        byteData = originalData;

        if (originalData == null || originalData.length <= 2) {
            System.out.println("invalid data for len");
            return false;
        } else if (originalData[1] + 1 != originalData.length) {
            System.out.println("invalid data for len" + (originalData[1] + 1) + " !=" + originalData.length);
            return false;
        } else if (originalData[0] != Marker) {
            System.out.println("invalid header");
            return false;
        } else if (DataConversionUtils.byteArrayToInt(CRCUtil.GetCRC(0xffff, cutBytes(originalData, 1, originalData.length - 1))) != 0) {
            //TODO 判断 CRC
            byte[] ff = cutBytes(originalData, 1, originalData.length - 1);
            byte[] gg = CRCUtil.GetCRC(0xffff, ff);
            int ss = DataConversionUtils.byteArrayToInt(gg);
            return false;
        } else {
            System.out.println("valid ok");
            return true;

        }
    }

    /**
     * 装配数据
     *
     * @return
     */
    public static TWManager assembleData() {
        Len = byteData[1]; //长度 1字节
        Flags = byteData[2];////标识  1字节
        SNs = cutBytes(byteData, 3, 4);//设备id 4字节
        Payload = cutBytes(byteData, 7, Len - 8);
        Bpayload = new byte[Len - 8];
        return mInstance;
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

    private static TwBody temperatureData = new TwBody();

    public static TWManager parseFlag() {
        StringBuffer SNString = new StringBuffer();
        SNString.append(getBit(Flags));
        String flagResult = SNString.toString();
        System.out.println("=to bit result=" + flagResult);
        int Encrypt = Integer.parseInt(flagResult.substring(0, 1), 2);//是否加密
        int Resolution = Integer.parseInt(flagResult.substring(1, 2), 2);//体温数据精度
        int TimeUnit = Integer.parseInt(flagResult.substring(2, 3), 2);//时间单位0时，时间单位为秒。当该位为1时，时间单位为分钟
        int Interval = Integer.parseInt(flagResult.substring(3, 5), 2);//发送数据的时间间隔
        int LowBattery = Integer.parseInt(flagResult.substring(5, 6), 2);//1，代表体温探头的电池电量低，应该尽快更换。如果电池电量正常，则为0。
        int Reserved = Integer.parseInt(flagResult.substring(6, 8), 2);//预留
        System.out.print("flag" + "Encrypt" + Encrypt + "Resolution" + Resolution + "TimeUnit" + TimeUnit + "Interval" + Interval
                + "LowBattery" + LowBattery);
        if (Encrypt == 0) {//无加密
            temperatureData.setEncrypt(false);
        } else {//加密（包含sn+payload）
            temperatureData.setEncrypt(true);
        }
        if (Resolution == 0) {//体温数据的精度为0.1摄氏度，每个体温数据需要一个字节
            temperatureData.setResolution(0);
        } else {//体温数据的精度为0.01摄氏度，每个体温数据需要两个字节表示，这通常是在调试阶段使用
            temperatureData.setResolution(1);
        }
        if (TimeUnit == 0) {//时间单位为秒
            temperatureData.setTimeUnit(0);
        } else {//时间单位为分钟
            temperatureData.setTimeUnit(1);
        }
        if (Interval == 0) {//Tmeasure为10秒钟，通常用于测试阶段。
            temperatureData.setInterval(10 * 1000);
        } else if (Interval == 1) {//Tmeasure为1分钟。 60秒
            temperatureData.setInterval(60 * 1000);
        } else if (Interval == 2) {//Tmeasure为15分钟。 900秒
            temperatureData.setInterval(900 * 1000);
        } else if (Interval == 3) {//保留。保留
//            temperatureData.setInterval(3);
        }

        if (LowBattery == 0) {//代表电池电量正常
            temperatureData.setLowBattery(true);
        } else {//代表体温探头的电池电量低
            temperatureData.setLowBattery(false);
        }
        return mInstance;
    }

    static byte[] Bsns = new byte[4];
    static byte[] Bpayload;

    public static TWManager decodeSNandpayload() {
        if (temperatureData.isEncrypt()) {
            for (int i = 0; i < SNs.length - 1; i++) {
                Bsns[i] = rotateLeft(SNs[i], 2);
            }
            System.out.print("tw" + DataConversionUtils.byteArrayToStringLog(Bsns, Bsns.length));

            for (int i = 0; i < Payload.length - 1; i++) {
                Bpayload[i] = rotateLeft(Payload[i], 2);
            }
        } else {
            Bsns = SNs;
            Bpayload = Payload;
        }


        return mInstance;
    }

    /**
     * 循环左移
     *
     * @param sourceByte 待左移动的值
     * @param n          左移动的为数
     * @return
     */
    public static byte rotateLeft(byte sourceByte, int n) {
        int temp = sourceByte;
        return (byte) ((temp << n));
    }


    /**
     * 组装SN数据
     *
     * @param
     * @return
     */
    public static TWManager parseSN() {
//        TemperatureData temperatureData = new TemperatureData();
        StringBuffer SNString = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            SNString.append(getBit(Bsns[i]));
        }
        String result = SNString.toString();
        System.out.println("=to bit result=" + result);
        String runNum = result.substring(17, 32);
        String day = result.substring(8, 17);
        String year = result.substring(4, 8);
        String model = result.substring(0, 4);

        temperatureData.setRunningNumber(Integer.parseInt(runNum, 2) + "");//流水号
        int i_day = Integer.parseInt(day, 2);
        int i_year = Integer.parseInt(year, 2);
        temperatureData.setDate(getYear(i_year) + "." + getMonth(i_day) + "." + getDay(i_day));
        Log.i("tw", "date: " + getYear(i_year) + "." + getMonth(i_day) + "." + getDay(i_day));
        int i_model = Integer.parseInt(model, 2);
        System.out.println("model:" + i_model);
        temperatureData.setModel(i_model);
        System.out.println(temperatureData);
        return mInstance;
    }

    private static int Timefirst = 0;
    private static List<String> Temperatures = new ArrayList();
    private static List<String> twTime = new ArrayList<>();

    /**
     * 组装温度数据
     *
     * @param
     * @return
     */
    public static TWManager parsePayload() {
        if (Bpayload.length >= 2) {
            int firstTime = Integer.parseInt(getBit(Bpayload[0]), 2);//第一个体温数据（Data1）的生存时间
            int interval = temperatureData.getInterval();
            if (temperatureData.getTimeUnit() == 0) {//单位 秒
                if (firstTime * 1000 <= interval) {
                    Timefirst = firstTime * 1000;
                } else {
                    return mInstance;
                }
            } else {//单位 分钟
                if (firstTime * 60 * 1000 <= interval) {
                    Timefirst = firstTime * 60 * 1000;
                } else {
                    return mInstance;
                }
            }
        }
        long nowResultTime = System.currentTimeMillis() - Timefirst;
        String t = testTime(nowResultTime);
        //判断精度
        if (temperatureData.getResolution() == 0) {
            for (int i = 1; i < Bpayload.length - 1; i++) {
                int tt = Integer.parseInt(getBit(Bpayload[i]), 2);
                //Temperature = 25 + DataN / 10      (当Resolution为0时)
                double temp = 25 + tt / 10;
                long ss = temperatureData.getFirstTime();
                String dd=testTime(ss);
                if (nowResultTime > ss) {
                    Temperatures.add(String.valueOf(temp));
                    twTime.add(time(i));
                }
            }
        } else if (temperatureData.getResolution() == 1) {
//            Temperature = 25 + DataN/ 100(当Resolution为1时)
            for (int i = 1; i < Bpayload.length; i += 2) {
                String bit = getBit(Bpayload[i]);
                String bit1 = getBit(Bpayload[i + 1]);
                System.out.println("bit=" + bit + " bit1=" + bit1);
                int tt = Integer.parseInt(bit + bit1, 2);
                System.out.println("temp=" + tt);
                double temp = 25 + tt / 100;
                System.out.println("temp=" + temp);
                if (nowResultTime > temperatureData.getFirstTime()) {
                    Temperatures.add(String.valueOf(temp));
                    twTime.add(time(i));
                }
            }
        }
        temperatureData.setFirstTime(nowResultTime);
        temperatureData.setTemperatures(Temperatures);
        temperatureData.setTwTime(twTime);
        return mInstance;
    }

    public static TwBody getTemperatureData() {
        return temperatureData;
    }

    /**
     * 计算每个数据的时间间隔
     *
     * @param i
     * @return
     */
    public static String time(int i) {
        long nowTime = System.currentTimeMillis();
        long timeN = nowTime - Timefirst - (i - 1) * temperatureData.getInterval();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/HH/mm");
        Date curDate = new Date(timeN);//获取当前时间
        String Time = formatter.format(curDate);
        Log.i("tw", "parsePayload: time " + Time);
        return Time;
    }

    public static String testTime(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(l);//获取当前时间
        String Times = formatter.format(curDate);
        return Times;
    }

    /**
     * 保存数据库
     */
    public static void saveDb() {
        TwBodyDao mDao = TwApplication.getsInstance().getDaoSession().getTwBodyDao();
        mDao.insertOrReplace(temperatureData);
        List<TwBody> twBodyList = mDao.loadAll();
        Log.i("tw", "saveDb: " + twBodyList.size());
    }

    /**
     * byte 转 bit
     *
     * @param by
     * @return String
     */
    public static String getBit(byte by) {
        StringBuffer sb = new StringBuffer();
        sb.append((by >> 7) & 0x1)
                .append((by >> 6) & 0x1)
                .append((by >> 5) & 0x1)
                .append((by >> 4) & 0x1)
                .append((by >> 3) & 0x1)
                .append((by >> 2) & 0x1)
                .append((by >> 1) & 0x1)
                .append((by >> 0) & 0x1);
        return sb.toString();
    }

    private static int getMonth(int data) {
        return (data / 31) + 1;
    }

    private static int getDay(int data) {
        return data % 31;
    }

    /**
     * 设备生产的年份，4位，取值从0到15。从2017年开始，2017年为0，2018年为1，依次类推。
     *
     * @param year
     * @return
     */
    private static int getYear(int year) {
        return year + 2017;
    }


}
