package com.example.twapp.uitl;

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
 * @author :EchoXBR in  2017/8/17 下午7:52.
 *         功能描述:SN实体类
 */

public class TemperatureData {

    //SN数据
    private int Model;
    private int Year;
    private int Day;
    private int Month;
    private String RunningNumber;
    //    private byte[] Marker;
//    private byte[] Len;
//    private byte[] Flags;
    //Flags 数据
    private boolean Encrypt = false;//是否加密
    private int Resolution = 0;//体温精度 0=0.1  1=0.01
    private int TimeUnit = 0;//体温数据寿命的时间单位。当该位为0时，时间单位为秒。当该位为1时，时间单位为分钟
    private int Interval;//体温数据时间间隔
    /**
     * 0：Tmeasure为10秒钟，通常用于测试阶段。
     * 1：Tmeasure为1分钟。
     * 3：保留。
     * 2：Tmeasure为15分钟。
     */



    private boolean isLowBattery = false;//是否低电 1低电 0正常

    private List<Double> Temperatures;//温度数据


    public List<Double> getTemperatures() {
        return Temperatures;
    }

    public void setTemperatures(List<Double> temperatures) {
        Temperatures = temperatures;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getModel() {
        return Model;
    }

    public void setModel(int model) {
        Model = model;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
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

    public int getInterval() {
        return Interval;
    }

    public void setInterval(int interval) {
        Interval = interval;
    }

    public boolean isLowBattery() {
        return isLowBattery;
    }

    public void setLowBattery(boolean lowBattery) {
        isLowBattery = lowBattery;
    }

    @Override
    public String toString() {
        return "TemperatureData{" +
                "Model=" + Model +
                ", Year=" + Year +
                ", Day=" + Day +
                ", Month=" + Month +
                ", RunningNumber='" + RunningNumber + '\'' +
                ", Encrypt=" + Encrypt +
                ", Resolution=" + Resolution +
                ", TimeUnit=" + TimeUnit +
                ", Interval=" + Interval +
                ", isLowBattery=" + isLowBattery +
                ", Temperatures=" + Temperatures +
                '}';
    }
}
