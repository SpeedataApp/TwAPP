package com.example.twapp.utils;

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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(l);//获取当前时间
        String Times = formatter.format(curDate);
        return Times;
    }
}

