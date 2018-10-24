package com.example.twapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * @author :孙天伟 in  2017/9/27   13:25.
 *         联系方式:QQ:420401567
 *         功能描述:  byte 左右移位操作
 */

public class DataConvertUtil {
    /**
     * 循环左移
     *
     * @param sourceByte 待左移动的值
     * @param n          左移动的为数
     * @return
     */
    public static byte rotateLeft(byte sourceByte, int n) {
        // 去除高位的1
        int temp = sourceByte & 0xFF;
        return (byte) ((temp << n) | (temp >>> (8 - n)));
    }

    /**
     * 循环右移
     *
     * @param sourceByte
     * @param n
     * @return
     */
    public static byte rotateRight(byte sourceByte, int n) {
        // 去除高位的1
        int temp = sourceByte & 0xFF;
        return (byte) ((temp >>> n) | (temp << (8 - n)));
    }

    /**
     * 循环左移
     *
     * @param sourceBytes
     * @param n
     * @return
     */
    public static byte[] rotateLeft(byte[] sourceBytes, int n) {
        byte[] out = new byte[sourceBytes.length];
        for (int i = 0; i < sourceBytes.length; i++) {
            out[i] = rotateLeft(sourceBytes[i], n);
        }
        return out;
    }

    public static byte[] rotateRight(byte[] sourceBytes, int n) {
        byte[] out = new byte[sourceBytes.length];
        for (int i = 0; i < sourceBytes.length; i++) {
            out[i] = rotateRight(sourceBytes[i], n);
        }
        return out;
    }

    /**
     * 取最大值
     *
     * @param arr
     * @return
     */
    public static double getMax(List<Double> arr) {
        double max = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) > max) {
                max = arr.get(i);
            }
        }
        return max;
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

    /**
     * 计算体温时间
     *
     * @param l
     * @return
     */

    public static String testTime(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(l);//获取当前时间
        String Times = formatter.format(curDate);
        return Times;
    }

    public static String testTime_mm(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm");
        Date curDate = new Date(l);//获取当前时间
        String Times = formatter.format(curDate);
        return Times;
    }

    public static String testTime_hh(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:");
        Date curDate = new Date(l);//获取当前时间
        String Times = formatter.format(curDate);
        return Times;
    }

    public static String testTime_ss(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("ss");
        Date curDate = new Date(l);//获取当前时间
        String Times = formatter.format(curDate);
        return Times;
    }

    public static long timesLong(String t) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = dateFormat.parse(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse.getTime();
    }

    /**
     * 获取两个时间的差  几分钟
     *
     * @param str1
     * @param str2
     * @return
     */
    public static long getDistanceMin(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date one;
        Date two;
        long min = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            diff = time1 - time2;
            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)));
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return min;
    }

    /**
     * 转换时间日期格式字串为long型
     *
     * @param time 格式为：yyyy-MM-dd HH:mm的时间日期类型
     */
    public static Long convertTimeToLong(String time) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
//       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);

            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }

    /**
     * 体温贴标签sn转换方法
     *
     * @param SN
     */
    public static void SNToDisplayFormat(int SN) {
        long Model, Year, Month, Day, DayOfYear, RunningNumber;
        long LSN = 0;

        //SN=0xFFFFFFFF;
//        System.out.println(String.format("%d", SN));
        if (SN < 0) {
            LSN = SN + 0x100000000L;
        } else {
            LSN = SN;
        }
//        System.out.println(String.format("%d", LSN));

        DayOfYear = (LSN & 0x0FF8000L) >>> 15;
        Model = (LSN & 0xF0000000L) >>> 28;
        Year = ((LSN & 0x0F000000L) >>> 24) + 17;
        Month = DayOfYear / 31 + 1;
        Day = DayOfYear - (DayOfYear / 31) * 31;
        RunningNumber = (LSN & 0x00007FFFL);

        System.out.println(String.format("Serial Number: FLG%02d-%02d%02d%02d%05d\r\n", Model, Year, Month, Day, RunningNumber));
    }

    /**
     * 体温贴标签sn转换方法
     *
     * @param Display
     */
    public static void DisplayFormatToSn(String Display) {
        long Model, Year, Month, Day, RunningNumber, DayOfYear, SN;

        Model = Long.parseLong(Display.substring(3, 5));
        Year = Long.parseLong(Display.substring(6, 8));
        Month = Long.parseLong(Display.substring(8, 10));
        Day = Long.parseLong(Display.substring(10, 12));
        RunningNumber = Long.parseLong(Display.substring(12, 17));

        //System.out.println(" " + Model + " " + Year + " " + Month + " " + Day + " " + RunningNumber);

        DayOfYear = (Month - 1) * 31 + Day;
        SN = (Model << 28) | ((Year - 17) << 24) | (DayOfYear << 15) | RunningNumber;

        System.out.println(String.format("SN: 0x%08X\r\n", SN));
    }
}

