package com.example.twapp.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.twapp.been.TwBeen;
import com.example.twapp.db.TwBody;
import com.speedata.libutils.DataConversionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * @author :孙天伟 in  2017/9/27   13:29.
 *         联系方式:QQ:420401567
 *         功能描述:  体温贴数据组装
 */

public class TWManager {
    public static TWManager mInstance;
    private static byte Marker = 0x5A;
    private static byte Len;
    private static byte Flags;
    private static byte[] SNs;
    private static byte[] Payload;
    private static byte[] byteData;
    private static byte[] Bsns = new byte[4];
    private static byte[] Bpayload;
    private static int Timefirst = 0;
    private static Context mContext;
    private static SharedPreferencesUitl preferencesUitl;
    private static Vibrator vibrator;

    /**
     * 单实例
     *
     * @return ExcelUtils
     */
    public static TWManager getInstance(Context context) {
        mContext = context;
        preferencesUitl = SharedPreferencesUitl.getInstance(context, "tw");
        vibrator = new Vibrator(context);
        if (mInstance == null) {
            synchronized (TWManager.class) {
                if (mInstance == null) {
                    Log.i("tws", "重构 体温manage ");
                    mInstance = new TWManager();
                }
            }
        }
        return mInstance;
    }

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

    //    private static TwBody twBeen = new TwBody();/
    private static TwBeen twBeen = new TwBeen();

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
        Log.i("tws", "flag" + "Encrypt" + Encrypt + "Resolution" + Resolution + "TimeUnit" + TimeUnit + "Interval" + Interval
                + "LowBattery" + LowBattery);
        if (Encrypt == 0) {//无加密
            twBeen.setEncrypt(false);
        } else {//加密（包含sn+payload）
            twBeen.setEncrypt(true);
        }
        if (Resolution == 0) {//体温数据的精度为0.1摄氏度，每个体温数据需要一个字节
            twBeen.setResolution(0);
        } else {//体温数据的精度为0.01摄氏度，每个体温数据需要两个字节表示，这通常是在调试阶段使用
            twBeen.setResolution(1);
        }
        if (TimeUnit == 0) {//时间单位为秒
            twBeen.setTimeUnit(0);
        } else {//时间单位为分钟
            twBeen.setTimeUnit(1);
        }
        if (Interval == 0) {//Tmeasure为10秒钟，通常用于测试阶段。
            twBeen.setInterval(10 * 1000);
        } else if (Interval == 1) {//Tmeasure为1分钟。 60秒
            twBeen.setInterval(60 * 1000);
        } else if (Interval == 2) {//Tmeasure为15分钟。 900秒
            twBeen.setInterval(900 * 1000);
        } else if (Interval == 3) {//保留。保留
//            twBeen.setInterval(3);
        }

        if (LowBattery == 0) {//代表电池电量正常
            twBeen.setLowBattery(true);
            sendBroadCast(true);
        } else {//代表体温探头的电池电量低
            twBeen.setLowBattery(false);
            sendBroadCast(true);
        }
        return mInstance;
    }

    private static void sendBroadCast(boolean b) {
        Intent intent = new Intent();
        intent.setAction("com.tw.LowBattery");
        intent.putExtra("lowBattery", b);
        mContext.sendBroadcast(intent);
    }

    public static TWManager decodeSNandpayload() {
        if (twBeen.isEncrypt()) {
            Bsns = DataConvertUtil.rotateLeft(SNs, 2);
            Bpayload = DataConvertUtil.rotateLeft(Payload, 2);
        } else {
            Bsns = SNs;
            Bpayload = Payload;
        }
        return mInstance;
    }

    static DBUitl dBtable = new DBUitl();

    /**
     * 组装SN数据
     *
     * @param
     * @return
     */
    public static TWManager parseSN() {
//        twBeen twBeen = new twBeen();
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
        Log.i("tws", "parseSN: " + "runnum:" + Integer.parseInt(runNum, 2));
        twBeen.setRunningNumber(Integer.parseInt(runNum, 2) + "");//流水号
        int i_day = Integer.parseInt(day, 2);
        int i_year = Integer.parseInt(year, 2);
        twBeen.setDate(getYear(i_year) + "." + getMonth(i_day) + "." + getDay(i_day));
        Log.i("tw", "date: " + getYear(i_year) + "." + getMonth(i_day) + "." + getDay(i_day));
        int i_model = Integer.parseInt(model, 2);
        System.out.println("model:" + i_model);
        twBeen.setModel(i_model);
        System.out.println(twBeen);
        return mInstance;
    }

    /**
     * 组装温度数据
     *
     * @param
     * @return
     */
    public static TWManager parsePayload() {
        Log.i("tws", "parsePayload: 数据  ： " + DataConversionUtils.byteArrayToString(Bpayload));
        if (Bpayload.length >= 2 && dBtable.whereRunNum(twBeen.getRunningNumber())) {
            int firstTime = Integer.parseInt(getBit(Bpayload[0]), 2);//第一个体温数据（Data1）的生存时间
            int interval = twBeen.getInterval();//间隔时间
            if (twBeen.getTimeUnit() == 0) {//单位 秒
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
            Log.i("tws", "parsePayload: " + "生存时间" + Timefirst + "    间隔：" + interval);
            TwBody body = dBtable.queryTwBody(twBeen.getRunningNumber());
            long ResultfirstTime = body.getFirstTime();//c查询最新的数据 第一个体温数据的生存时间
            String dd = testTime(ResultfirstTime);//数据库时间
            String cc = testTime(System.currentTimeMillis() - Timefirst);//真实第一时间
            int twLengths = (body.getTemperatures().size());//上一次保存接受体温标签接收过多少个数据
            Log.i("tws", "parsePayload: " + "数据库时间：" + dd + "真实时间：" + cc + "数据库长度：" + twLengths + "现实数据长度：" + Bpayload.length);
            if ((System.currentTimeMillis() - Timefirst) > ResultfirstTime) {
                List<String> Temperatures = new ArrayList();
                List<String> twTime = new ArrayList<>();

                dBtable.cahageData(twBeen.getRunningNumber(), System.currentTimeMillis() - Timefirst);//保存第一个体温数据的生存时间
                //判断精度
                if (twBeen.getResolution() == 0) {
                    for (int i = 1; i < Bpayload.length; i++) {
                        if (Bpayload[i] == 0xFF || Bpayload[i] == -2 || Bpayload[i] == 0xFD) {//温度过高  温度错误  温度过低
                            continue;
                        }
                        int tt = Integer.parseInt(getBit(Bpayload[i]), 2);
                        //Temperature = 25 + DataN / 10      (当Resolution为0时)
                        double temp = 25 + tt / 10;
                        isHights(temp);
                        Temperatures.add(String.valueOf(temp));
                        twTime.add(time(i));

                    }
                } else if (twBeen.getResolution() == 1) {
                    int j = 1;
                    // Temperature = 25 + DataN/ 100(当Resolution为1时)
                    for (int i = 1; i < Bpayload.length; i += 2) {
                        j += 1;
                        int data = DataConversionUtils.byteArrayToInt(cutBytes(Bpayload, i, 2), false);
                        if (data == 0xFFFF || data == 0xFFFE || data == 0xFFFD) {//温度过高  温度错误  温度过低
                            continue;
                        }
                        String bit = getBit(Bpayload[i]);
                        String bit1 = getBit(Bpayload[i + 1]);
                        System.out.println("bit=" + bit + " bit1=" + bit1);
                        int tt = Integer.parseInt(bit + bit1, 2);
                        System.out.println("temp=" + tt);
                        double temp = 25 + tt / 100;
                        isHights(temp);
                        System.out.println("temp=" + temp);
                        Temperatures.add(String.valueOf(temp));
                        twTime.add(time(j));
                    }
                }
                //根据体温贴id（流水号）修改数据库对应的体温数据信息
                dBtable.cahageData(twTime, Temperatures, twBeen.getRunningNumber());
            }
        }
        return mInstance;
    }

    public static void isHights(double d) {
        if (d > 37.50) {
            boolean cc = preferencesUitl.read("thightSound", false);
            if (cc) {
                PlaySound.play(PlaySound.HIGHT_SOUND, PlaySound.NO_CYCLE);
            }
            if (preferencesUitl.read("thightShake", false)) {
                vibrator.setVibrator();
            }
        }
    }

    public static TwBeen getTemperatureData() {
        return twBeen;
    }

    static long timeN = 0;

    /**
     * 计算每个数据的时间间隔
     *
     * @param i
     * @return
     */
    public static String time(int i) {

        long nowTime = System.currentTimeMillis();
        if (Bpayload.length > 15) {
            if (Bpayload.length - i <= 15) {
                timeN = timeN - 60000;
            } else {
                timeN = nowTime - Timefirst - (i - 1) * twBeen.getInterval();
            }
        } else {
            timeN = nowTime - Timefirst - (i - 1) * twBeen.getInterval();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(timeN);//获取当前时间
        String Time = formatter.format(curDate);
        Log.i("tw", "parsePayload: time " + Time);
        return Time;
    }

    /**
     * 计算体温时间
     *
     * @param l
     * @return
     */
    public static String testTime(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(l);//获取当前时间
        String Times = formatter.format(curDate);
        return Times;
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
