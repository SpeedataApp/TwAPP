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
 *         联系方式:QQ:420401567
 *         功能描述:  数据库实体类
 */
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
@Entity
public class TwBody {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    //SN数据
    private String peopleNun;
    private String pName;
    private String paAge;
    private String pGender;
    private String pBedNumber;
    private String pResult;
    private String RunningNumber; //设备流水号 15位
    private long firstTime;
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> Temperatures;//温度数据
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> TwTime;//温度数据时间

    public TwBody(String peopleNun, String pName, String paAge, String pGender, String pBedNumber, String pResult, String runningNumber, long firstTime, List<String> temperatures, List<String> twTime) {
        this.peopleNun = peopleNun;
        this.pName = pName;
        this.paAge = paAge;
        this.pGender = pGender;
        this.pBedNumber = pBedNumber;
        this.pResult = pResult;
        RunningNumber = runningNumber;
        this.firstTime = firstTime;
        Temperatures = temperatures;
        TwTime = twTime;
    }

    @Generated(hash = 1671162065)
    public TwBody() {
    }

    @Generated(hash = 1298473826)
    public TwBody(Long id, String peopleNun, String pName, String paAge, String pGender, String pBedNumber, String pResult, String RunningNumber, long firstTime, List<String> Temperatures,
                  List<String> TwTime) {
        this.id = id;
        this.peopleNun = peopleNun;
        this.pName = pName;
        this.paAge = paAge;
        this.pGender = pGender;
        this.pBedNumber = pBedNumber;
        this.pResult = pResult;
        this.RunningNumber = RunningNumber;
        this.firstTime = firstTime;
        this.Temperatures = Temperatures;
        this.TwTime = TwTime;
    }

    public String getpGender() {
        return pGender;
    }

    public void setpGender(String pGender) {
        this.pGender = pGender;
    }

    public String getpBedNumber() {
        return pBedNumber;
    }

    public void setpBedNumber(String pBedNumber) {
        this.pBedNumber = pBedNumber;
    }

    public String getPeopleNun() {
        return peopleNun;
    }

    public void setPeopleNun(String peopleNun) {
        this.peopleNun = peopleNun;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPaAge() {
        return paAge;
    }

    public void setPaAge(String paAge) {
        this.paAge = paAge;
    }

    public String getpResult() {
        return pResult;
    }

    public void setpResult(String pResult) {
        this.pResult = pResult;
    }

    public String getRunningNumber() {
        return RunningNumber;
    }

    public void setRunningNumber(String runningNumber) {
        RunningNumber = runningNumber;
    }

    public long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPName() {
        return this.pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }


    public String getPResult() {
        return this.pResult;
    }

    public void setPResult(String pResult) {
        this.pResult = pResult;
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
}
