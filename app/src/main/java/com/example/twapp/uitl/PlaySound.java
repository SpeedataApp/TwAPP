package com.example.twapp.uitl;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.twapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo-pc on 2017/8/10.
 */

public class PlaySound {

    private static Map<Integer, Integer> mapSRC;
    private static SoundPool sp; //声音池
    public static final int HIGHT_SOUND = 1;
    public static int ERROR_WUXIAO = 2;
    public static int SUCCESS = 3;
    public static int REPETITION = 4;
    public static int NO_CYCLE = 0;//不循环


    //初始化声音池
    public static void initSoundPool(Context context) {
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mapSRC = new HashMap<>();
        //体温过高报警
        mapSRC.put(HIGHT_SOUND, sp.load(context, R.raw.hight, 0));
        //无效二维码
        mapSRC.put(ERROR_WUXIAO, sp.load(context, R.raw.error_wuxiao, 0));
        //扣费成功
        mapSRC.put(SUCCESS, sp.load(context, R.raw.success, 0));
        //二维码重复
        mapSRC.put(REPETITION, sp.load(context, R.raw.repetition, 0));
    }


    /**
     * 播放声音池的声音
     */
    public static void play(int sound, int number) {
        sp.play(mapSRC.get(sound),//播放的声音资源
                1.0f,//左声道，范围为0--1.0
                1.0f,//右声道，范围为0--1.0
                0, //优先级，0为最低优先级
                number,//循环次数,0为不循环
                1);//播放速率，1为正常速率
    }

    public static void stop(int sound) {
        sp.stop(sound);
    }

}
