package com.example.twapp.uitl;

import android.content.Context;

/**
 * Created by lenovo-pc on 2017/8/31.
 */

public class Vibrator {//震动器
    private android.os.Vibrator vibrator;

    public Vibrator(Context context) {
        vibrator = (android.os.Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void setVibrator() {
        long[] pattern = {100, 400, 100, 400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1);           //重复两次上面的pattern 如果只想震动一次，index设为-1

    }

    public void cancel() {
        vibrator.cancel();
    }
}
