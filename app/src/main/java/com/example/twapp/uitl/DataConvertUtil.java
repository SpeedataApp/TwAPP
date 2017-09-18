package com.example.twapp.uitl;

/**
 * Created by lenovo-pc on 2017/9/6.
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
}

