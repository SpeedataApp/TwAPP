package com.example.twapp.been;

import com.example.twapp.db.StringConverter;

import org.greenrobot.greendao.annotation.Convert;

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
 * @author :孙天伟 in  2017/9/27   14:01.
 *         联系方式:QQ:420401567
 *         功能描述: 体温标签数据 所有字段
 */

public class TwBeen {
    //SN数据
    private int Model;  //产品型号
    private String Date; //设备生产日期
    private String RunningNumber; //设备流水号 15位
    //Flags 数据
    private boolean Encrypt = false;//是否加密
    private int Resolution = 0;//体温精度 0=0.1  1=0.01
    private int TimeUnit = 0;//体温数据寿命的时间单位。当该位为0时，
    // 时间单位为秒。当该位为1时，时间单位为分钟
    private boolean isLowBattery = false;//是否低电 1低电 0正常
    private long firstTime;
    private int Interval;//体温数据时间间隔  0：Tmeasure为10秒钟，通常用于测试阶段。 1：Tmeasure为1分钟。2：Tmeasure为15分钟。3：保留。
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> Temperatures;//温度数据
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> TwTime;//温度数据时间

    public int getModel() {
        return Model;
    }

    public void setModel(int model) {
        Model = model;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRunningNumber() {
        return RunningNumber;
    }

    public void setRunningNumber(String runningNumber) {
        RunningNumber = runningNumber;
    }

    public boolean isEncrypt() {
        return Encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        Encrypt = encrypt;
    }

    public int getResolution() {
        return Resolution;
    }

    public void setResolution(int resolution) {
        Resolution = resolution;
    }

    public int getTimeUnit() {
        return TimeUnit;
    }

    public void setTimeUnit(int timeUnit) {
        TimeUnit = timeUnit;
    }

    public boolean isLowBattery() {
        return isLowBattery;
    }

    public void setLowBattery(boolean lowBattery) {
        isLowBattery = lowBattery;
    }

    public long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }

    public int getInterval() {
        return Interval;
    }

    public void setInterval(int interval) {
        Interval = interval;
    }

    public List<String> getTemperatures() {
        return Temperatures;
    }

    public void setTemperatures(List<String> temperatures) {
        Temperatures = temperatures;
    }

    public List<String> getTwTime() {
        return TwTime;
    }

    public void setTwTime(List<String> twTime) {
        TwTime = twTime;
    }
}
