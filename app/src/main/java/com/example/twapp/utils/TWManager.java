package com.example.twapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.twapp.R;
import com.example.twapp.db.TwBody;
import com.speedata.libutils.DataConversionUtils;

import java.text.ParseException;
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
    //    private static long Timefirst = 0;
    private static SharedPreferencesUitl preferencesUitl;
    private static Context mContext;
    private static DBUitl dBtable;
    private static TwBody body;
    private static List<String> list;
    private static int intFlg = 0;
    private static com.example.twapp.db.twTimeAndDatas twTimeAndDatas;
    private static final String TAG = "tws";

    /**
     * 单实例
     *
     * @return ExcelUtils
     */
    public static TWManager getInstance(Context context) {

        mContext = context;
        preferencesUitl = SharedPreferencesUitl.getInstance(context, "tw");
        if (mInstance == null) {
            synchronized (TWManager.class) {
                if (mInstance == null) {
                    mInstance = new TWManager();
                    dBtable = new DBUitl();
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
        } else if (DataConversionUtils.byteArrayToInt(CRCUtil.GetCRC(0xffff, DataConvertUtil.cutBytes(originalData, 1, originalData.length - 1))) != 0) {
            //TODO 判断 CRC
            Log.i("tws", "isValid:判断错误 CRC ");
//            byte[] ff = cutBytes(originalData, 1, originalData.length - 1);
//            byte[] gg = CRCUtil.GetCRC(0xffff, ff);
//            int ss = DataConversionUtils.byteArrayToInt(gg);
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
        SNs = DataConvertUtil.cutBytes(byteData, 3, 4);//设备id 4字节
        Payload = DataConvertUtil.cutBytes(byteData, 7, Len - 8);
        Bpayload = new byte[Len - 8];
        return mInstance;
    }


    private static TwBody twBeen = new TwBody();
//    private static TwBeen twBeen = new TwBeen();

    public static TWManager parseFlag() {
        StringBuffer SNString = new StringBuffer();
        SNString.append(DataConvertUtil.getBit(Flags));
        String flagResult = SNString.toString();
        System.out.println("=to bit result=" + flagResult);
        int Encrypt = Integer.parseInt(flagResult.substring(0, 1), 2);//是否加密
        int Resolution = Integer.parseInt(flagResult.substring(1, 2), 2);//体温数据精度
        int TimeUnit = Integer.parseInt(flagResult.substring(2, 3), 2);//时间单位0时，时间单位为秒。当该位为1时，时间单位为分钟
        int Interval = Integer.parseInt(flagResult.substring(3, 5), 2);//发送数据的时间间隔
        int LowBattery = Integer.parseInt(flagResult.substring(5, 6), 2);//1，代表体温探头的电池电量低，应该尽快更换。如果电池电量正常，则为0。
        int Reserved = Integer.parseInt(flagResult.substring(6, 8), 2);//预留
        Log.i("tws", "flag" + "Encrypt::" + Encrypt + "Resolution::" + Resolution + "TimeUnit::" + TimeUnit + "Interval:::" + Interval
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
            twBeen.setInterval(10);
        } else if (Interval == 1) {//Tmeasure为1分钟。 60秒
            twBeen.setInterval(60);
        } else if (Interval == 2) {//Tmeasure为15分钟。 900秒
            twBeen.setInterval(900);
        } else if (Interval == 3) {//保留。保留
//            twBeen.setInterval(3);
        }

        if (LowBattery == 0) {//代表电池电量正常
            twBeen.setIsLowBattery(mContext.getString(R.string.islowbattery));
        } else {//代表体温探头的电池电量低
            twBeen.setIsLowBattery(mContext.getString(R.string.lowbattery));
        }
        return mInstance;
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


    /**
     * 组装SN数据
     *
     * @param
     * @return
     */
    public static TWManager parseSN() {
        StringBuffer SNString = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            SNString.append(DataConvertUtil.getBit(Bsns[i]));
        }
        String result = SNString.toString();
        System.out.println("=to bit result=" + result);
        String runNum = result.substring(17, 32);
        String day = result.substring(8, 17);
        String year = result.substring(4, 8);
        String model = result.substring(0, 4);
        Log.i("tws", "parseSN: " + result + "#" + year + "#" + day + "#" + model);
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

    static int length = 0;

    /**
     * 组装温度数据
     *
     * @param
     * @return
     */
    public static TWManager parsePayload() {
        Log.i("tws", "parsePayload:体温数据： " + DataConversionUtils.byteArrayToString(Bpayload));
        if (Bpayload.length >= 2 && dBtable.queryRunNum(twBeen.getRunningNumber())) {
            int first = Integer.parseInt(DataConvertUtil.getBit(Bpayload[0]), 2);//第一个体温数据（Data1）的生存时间
            int interval = twBeen.getInterval();//间隔时间
            long NowTimes = System.currentTimeMillis() / 1000 * 1000;//系统时间精确到分钟
            list = new ArrayList<>();
            intFlg = 0;
            body = dBtable.queryTwBody(twBeen.getRunningNumber());
            String ResultfirstTime = body.getFirstTime();//c查询最新的数据 第一个体温数据的生存时间
            long Timefirst = 0;
//            if (interval == 900) {
//                if (twBeen.getTimeUnit() == 0) {//单位 秒
//                    if (first <= interval) {
//                        Timefirst = 60;
//                        newTime = (NowTimes - Timefirst * 1000); //最新数据的时间
//                        Log.i("firstTime", twBeen.getTimeUnit() + "第一个数据生存时间: " + Timefirst + "分");
//                    } else {
//                        return mInstance;
//                    }
//                } else if (twBeen.getTimeUnit() == 1) {//单位 分钟
//                    if (first * 60 <= interval) {
//                        Timefirst = (first + 1) * 60;
//                        newTime = (NowTimes - Timefirst * 1000); //最新数据的时间
//                        Log.i("firstTime", twBeen.getTimeUnit() + "第一个数据生存时间: " + first + "fen");
//                    } else {
//                        return mInstance;
//                    }
//                }
//            } else {
//                if (twBeen.getTimeUnit() == 0) {//单位 秒
//                    if (first <= interval) {
//                        Timefirst = first;
//                        newTime = (NowTimes - Timefirst * 1000); //最新数据的时间
//                        Log.i("firstTime", twBeen.getTimeUnit() + "第一个数据生存时间: " + first + "秒");
//                    } else {
//                        return mInstance;
//                    }
//                }
//            }
            if (twBeen.getTimeUnit() == 0) {//单位 秒
                if (first < interval) {//生存时间要小于间隔时间
                    Timefirst = first;
                } else {
                    return mInstance;
                }
            } else if (twBeen.getTimeUnit() == 1) {//单位 分钟
                if (first * 60 < interval) {//生存时间要小于间隔时间
                    Timefirst = first * 60;
                } else {
                    return mInstance;
                }
            }
            long newTime = (NowTimes - Timefirst * 1000); //最新数据的时间
            newTime = isTimes(newTime);
            Log.i(TAG, "保存数据时间：" + ResultfirstTime + "本次最新时间：" + DataConvertUtil.testTime(newTime));
            if (compareNowTime(ResultfirstTime, DataConvertUtil.testTime(newTime - Integer.parseInt(DataConvertUtil.testTime_ss(newTime)) * 1000), twBeen.getInterval())) {
                List<String> Temperatures = new ArrayList();
                List<String> twTime = new ArrayList<>();
                List<Long> twTimeLong = new ArrayList<>();
                //判断精度
                if (twBeen.getResolution() == 0) {
                    for (int i = Bpayload.length - 1; i > 0; i--) {
                        double tt = Integer.parseInt(DataConvertUtil.getBit(Bpayload[i]), 2);
                        if (Bpayload[i] == (byte) 0xFF) {//温度过高
                            Temperatures.add("up");
                            twTime.add(tiemss(i, newTime));
                        } else if (Bpayload[i] == (byte) 0xFE) {//温度过低
                            Temperatures.add("low");
                            twTime.add(tiemss(i, newTime));
                        } else if (Bpayload[i] == (byte) 0xFD) {// 温度错误
                            Temperatures.add("erro");
                            twTime.add(tiemss(i, newTime));
                        } else {
                            //Temperature = 25 + DataN / 10      (当Resolution为0时)
                            double temp = (25 + tt / 10);
                            Temperatures.add(String.valueOf(temp));
                            twTime.add(tiemss(i, newTime));
                        }
                    }
                } else if (twBeen.getResolution() == 1) {
                    int j = 1;
                    // Temperature = 25 + DataN/ 100(当Resolution为1时)
                    for (int i = Bpayload.length - 2; i > 0; i -= 2) {
                        String bit = DataConvertUtil.getBit(Bpayload[i]);
                        String bit1 = DataConvertUtil.getBit(Bpayload[i + 1]);
                        System.out.println("bit=" + bit + " bit1=" + bit1);
                        double tt = Integer.parseInt(bit + bit1, 2);
                        if ((byte) Integer.parseInt(bit + bit1, 2) == (byte) 0xFFFF) {//温度过高
                            Temperatures.add("up");
                            twTime.add(tiemss(i, newTime));
                        } else if ((byte) Integer.parseInt(bit + bit1, 2) == (byte) 0xFFFE) {//温度过低
                            Temperatures.add("low");
                            twTime.add(tiemss(i, newTime));
                        } else if ((byte) Integer.parseInt(bit + bit1, 2) == (byte) 0xFFFD) {// 温度错误
                            Temperatures.add("erro");
                            twTime.add(tiemss(i, newTime));
                        } else {
                            System.out.println("temp=" + tt);
                            double temp = 25 + tt / 100;
                            System.out.println("temp=" + temp);
                            Temperatures.add(String.valueOf(temp));
                            twTime.add(tiemss(i, newTime));
                        }
                        j++;
                    }
                }
                List<String> liststiem = new ArrayList<>();
                List<String> liststiem2 = body.getTwTime();
                List<String> list2 = body.getTemperatures();

                if (liststiem2 != null) {
                    for (int i = 0; i < list2.size(); i++) {//添加到新list 保证数据可以自增
                        liststiem.add(liststiem2.get(i));
                        list.add(list2.get(i));
                    }
                    for (int i = 0; i < twTime.size(); i++) {
                        Log.i("qqq", "时间: " + twTime.get(i));
                        if (compareNowTime(ResultfirstTime, twTime.get(i), twBeen.getInterval())) {//新接收数据遍历比较数据库上一个数据时间
                            liststiem.add(twTime.get(i));
                            list.add(Temperatures.get(i));
                        }
                    }
                } else {
                    liststiem = twTime;
                    list = Temperatures;
                }
                //根据体温贴id（流水号）修改数据库对应的体温数据信息
                dBtable.cahageData(twBeen.getRunningNumber(), twBeen.getModel(), twBeen.getDate(), twBeen.isEncrypt(),
                        twBeen.getResolution(), twBeen.getInterval(), twBeen.getTimeUnit(), twBeen.getIsLowBattery(),
                        R.drawable.pass_true, liststiem, list, twTimeLong);
//                for (int i = 0; i < list.size(); i++) {
//                    twTimeAndDatas = new twTimeAndDatas(list.get(i).toString(), liststiem.get(i).toString());
////                    twBeen.setTwTimeAndDatas(twTimeAndDatas);
//                }
//                dBtable.insertDtatas(twTimeAndDatas);
            }
        }
        return mInstance;
    }

    /**
     * 与当前时间比较早晚
     * 需要比较的时间
     *
     * @return
     */

    public static boolean compareNowTime(String shujukutime, String nowTime, int jiange) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = dateFormat.parse(shujukutime);
            Date parse1 = dateFormat.parse(nowTime);
            long diff = parse1.getTime() - parse.getTime();
            Log.i(TAG, "数据库时间：" + parse + "第一个时间" + parse1 + "时间差: " + diff);
            if (jiange == 900) {
                if (diff >= 840 * 1000) {
                    isDayu = true;
                } else {
                    isDayu = false;
                }
            } else {
                if (diff > 50000) {
                    isDayu = true;
                } else {
                    isDayu = false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isDayu;
    }

    /**
     * 计算判断时间重新标定时间>
     * A方案 小于15分取整点，15-30取15分，30-45取30分，45-60取45
     */
    private static long isTimes(long times) {
        if (twBeen.getInterval() == 900) {
            int mm = Integer.parseInt(DataConvertUtil.testTime_mm(times));
            if (mm < 15) {
                times -= Integer.parseInt(DataConvertUtil.testTime_mm(times)) * 60000;
            } else if (mm > 15 && mm < 30) {
                times -= (Integer.parseInt(DataConvertUtil.testTime_mm(times)) - 15) * 60000;
            } else if (mm > 30 && mm < 45) {
                times -= (Integer.parseInt(DataConvertUtil.testTime_mm(times)) - 30) * 60000;
            } else if (mm > 45) {
                int s = Integer.parseInt(DataConvertUtil.testTime_mm(times)) - 45;
                times -= (s) * 60000;
            }
        }
        return times;
    }

    /**
     * 获取时间间隔
     *
     * @return
     */

    public static String tiemss(int i, long newTime) {
        long diff = 0;
        long times = newTime - (i - 1) * twBeen.getInterval() * 1000;
        times -= Integer.parseInt(DataConvertUtil.testTime_ss(times)) * 1000;

        times = isTimes(times);//A方案
//        try {//B方案
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date parse = dateFormat.parse(body.getFirstTime());
////            long s = parse.getTime() - (Long.parseLong(DataConvertUtil.testTime_mm(parse.getTime())) * 1000);
//            diff = times - parse.getTime();
// //           if (twBeen.getInterval() == 900) {
//            if (diff > 900 * 1000 && Math.abs(diff - 900 * 1000) < 1900 * 1000 && diff > 0) {
//                times = times - Math.abs(diff - 900 * 1000);
//                times -= Integer.parseInt(DataConvertUtil.testTime_mm(times)) * 1000;
//                dBtable.cahageData(twBeen.getRunningNumber(), DataConvertUtil.testTime(times));//保存第一个体温数据的生存时间
//                Log.i("tsss", "差：" + diff + "&&&最终时间: " + DataConvertUtil.testTime(times));
//                return DataConvertUtil.testTime(times);
//            } else {
//                times -= Integer.parseInt(DataConvertUtil.testTime_mm(times)) * 1000;
//                dBtable.cahageData(twBeen.getRunningNumber(), DataConvertUtil.testTime(times));//保存第一个体温数据的生存时间
//                Log.i("tsss", "差：" + diff + "&&&最终时间: " + DataConvertUtil.testTime(times));
//                return DataConvertUtil.testTime(times);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Log.i(TAG, "遍历时间：" + DataConvertUtil.testTime(times));
        dBtable.cahageData(twBeen.getRunningNumber(), DataConvertUtil.testTime(times));//保存第一个体温数据的生存时间
        return DataConvertUtil.testTime(times);

    }

//    /**
//     * @param i
//     * @param newTime 最新体温数据时间
//     * @return
//     */
//    public static String time(int i, long newTime) {
//        intFlg++;
//        Log.i("ddddddddd", "dddddddddddd: " + DataConvertUtil.testTime(times));
//        if (length > 17 && Bpayload.length - 1 > 17) {
//            times = newTime - (i - 1) * twBeen.getInterval();
//        } else {
//            if (Bpayload.length - 1 > 16) {
//                if (intFlg > 16) {
//                    times = newTime - (i - 1) * twBeen.getInterval();
//                    Log.i("ddddddddd", "大于十五个 " + i + "kkkk" + DataConvertUtil.testTime(times));
//                } else {
//                    if (intFlg > length) {
//                        times = times + 60000;
//                    }
//                    Log.i("ddddddddd", "前十6个 " + i + "kkkk" + DataConvertUtil.testTime(times));
//                }
//            } else {
//                times = newTime - (i - 1) * twBeen.getInterval();
//            }
//        }
//        return DataConvertUtil.testTime(times);
//    }

    public static TwBody getBody() {
        return twBeen;
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
