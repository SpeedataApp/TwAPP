package com.example.twapp.db;
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
 * @author :孙天伟 in  2017/9/27   10:29.
 * 联系方式:QQ:420401567
 * 功能描述:  数据库实体类
 */

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

@Entity
public class TwBody {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String peopleNun;
//    @Unique
    private String pName;
//    @Unique
    private String paAge;
//    @Unique
    private String pGender;
//    @Unique
    private String pBedNumber;
    @Unique
    private String RunningNumber; //设备流水号 15位
//    @Unique
    private long firstTime;
//    @Unique
    private int passId;
//    @Unique
    private String isLowBattery = "";//是否低电 1低电 0正常
//    @Unique
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> Temperatures;//温度数据
//    @Unique
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> TwTime;//温度数据时间
    //SN数据
    @Transient
    private int Model;  //产品型号
    @Transient
    private String Date; //设备生产日期
    //Flags 数据
    @Transient
    private boolean Encrypt = false;//是否加密
    @Transient
    private int Resolution = 0;//体温精度 0=0.1  1=0.01
    @Transient
    private int TimeUnit = 0;//体温数据寿命的时间单位。当该位为0时，

    @Transient
    private int Interval;//体温数据时间间隔  0：Tmeasure为10秒钟，通常用于测试阶段。 1：Tmeasure为1分钟。2：Tmeasure为15分钟。3：保留。

    @Generated(hash = 846555680)
    public TwBody(Long id, String peopleNun, String pName, String paAge, String pGender,
                  String pBedNumber, String RunningNumber, long firstTime, int passId, String isLowBattery,
                  List<String> Temperatures, List<String> TwTime) {
        this.id = id;
        this.peopleNun = peopleNun;
        this.pName = pName;
        this.paAge = paAge;
        this.pGender = pGender;
        this.pBedNumber = pBedNumber;
        this.RunningNumber = RunningNumber;
        this.firstTime = firstTime;
        this.passId = passId;
        this.isLowBattery = isLowBattery;
        this.Temperatures = Temperatures;
        this.TwTime = TwTime;
    }

    public TwBody(String peopleNun, String pName, String paAge, String pGender,
                  String pBedNumber, String RunningNumber, long firstTime, int passId, String isLowBattery,
                  List<String> Temperatures, List<String> TwTime) {
        this.peopleNun = peopleNun;
        this.pName = pName;
        this.paAge = paAge;
        this.pGender = pGender;
        this.pBedNumber = pBedNumber;
        this.RunningNumber = RunningNumber;
        this.firstTime = firstTime;
        this.passId = passId;
        this.isLowBattery = isLowBattery;
        this.Temperatures = Temperatures;
        this.TwTime = TwTime;
    }

    @Generated(hash = 1671162065)
    public TwBody() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeopleNun() {
        return this.peopleNun;
    }

    public void setPeopleNun(String peopleNun) {
        this.peopleNun = peopleNun;
    }

    public String getPName() {
        return this.pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPaAge() {
        return this.paAge;
    }

    public void setPaAge(String paAge) {
        this.paAge = paAge;
    }

    public String getPGender() {
        return this.pGender;
    }

    public void setPGender(String pGender) {
        this.pGender = pGender;
    }

    public String getPBedNumber() {
        return this.pBedNumber;
    }

    public void setPBedNumber(String pBedNumber) {
        this.pBedNumber = pBedNumber;
    }

    public String getRunningNumber() {
        return this.RunningNumber;
    }

    public void setRunningNumber(String RunningNumber) {
        this.RunningNumber = RunningNumber;
    }

    public long getFirstTime() {
        return this.firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }

    public int getPassId() {
        return this.passId;
    }

    public void setPassId(int passId) {
        this.passId = passId;
    }

    public String getIsLowBattery() {
        return this.isLowBattery;
    }

    public void setIsLowBattery(String isLowBattery) {
        this.isLowBattery = isLowBattery;
    }

    public List<String> getTemperatures() {
        return this.Temperatures;
    }

    public void setTemperatures(List<String> Temperatures) {
        this.Temperatures = Temperatures;
    }

    public List<String> getTwTime() {
        return this.TwTime;
    }

    public void setTwTime(List<String> TwTime) {
        this.TwTime = TwTime;
    }


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
}
