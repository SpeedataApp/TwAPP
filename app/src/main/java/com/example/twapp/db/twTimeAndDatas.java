package com.example.twapp.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class twTimeAndDatas {

    @Id(autoincrement = true)
    private Long id;
    @Transient
    private String runNum;
    private String Temperatures;//温度数据
    //    @Unique
    private String TwTime;//温度数据时间

    public twTimeAndDatas( String temperatures, String twTime) {

        Temperatures = temperatures;
        TwTime = twTime;
    }

    @Generated(hash = 1515128923)
    public twTimeAndDatas(Long id, String Temperatures, String TwTime) {
        this.id = id;
        this.Temperatures = Temperatures;
        this.TwTime = TwTime;
    }

    @Generated(hash = 636745493)
    public twTimeAndDatas() {
    }

    public String getTemperatures() {
        return Temperatures;
    }

    public void setTemperatures(String temperatures) {
        Temperatures = temperatures;
    }

    public String getTwTime() {
        return TwTime;
    }

    public void setTwTime(String twTime) {
        TwTime = twTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRunNum() {
        return runNum;
    }

    public void setRunNum(String runNum) {
        this.runNum = runNum;
    }
}
