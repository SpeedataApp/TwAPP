package com.example.twapp.db;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

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
 * @author :Reginer in  2017/8/7 6:48.
 *         联系方式:QQ:282921012
 *         功能描述:
 */
@Entity
public class TwBody {
    @Id(autoincrement = true)
    private Long id;
    @Unique
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


    @Generated(hash = 1977483397)
    public TwBody(Long id, int Model, String Date, String RunningNumber, boolean Encrypt,
            int Resolution, int TimeUnit, boolean isLowBattery, long firstTime, int Interval,
            List<String> Temperatures, List<String> TwTime) {
        this.id = id;
        this.Model = Model;
        this.Date = Date;
        this.RunningNumber = RunningNumber;
        this.Encrypt = Encrypt;
        this.Resolution = Resolution;
        this.TimeUnit = TimeUnit;
        this.isLowBattery = isLowBattery;
        this.firstTime = firstTime;
        this.Interval = Interval;
        this.Temperatures = Temperatures;
        this.TwTime = TwTime;
    }

    @Generated(hash = 1671162065)
    public TwBody() {
    }


    public long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public List<String> getTwTime() {
        return TwTime;
    }

    public void setTwTime(List<String> twTime) {
        TwTime = twTime;
    }

    public List<String> getTemperatures() {
        return Temperatures;
    }

    public void setTemperatures(List<String> temperatures) {
        Temperatures = temperatures;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getModel() {
        return Model;
    }

    public void setModel(int model) {
        Model = model;
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

    public boolean getEncrypt() {
        return this.Encrypt;
    }

    public boolean getIsLowBattery() {
        return this.isLowBattery;
    }

    public void setIsLowBattery(boolean isLowBattery) {
        this.isLowBattery = isLowBattery;
    }

}
