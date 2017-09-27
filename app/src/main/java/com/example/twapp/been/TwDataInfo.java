package com.example.twapp.been;

/**
 * Created by lenovo-pc on 2017/8/30.
 */
public class TwDataInfo {
    public String twTime;
    public String twData;
    public int num;

    public TwDataInfo(String twData, String twTime, int num) {
        this.twData = twData;
        this.twTime = twTime;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public TwDataInfo() {

    }

    public String getTwData() {
        return twData;
    }

    public void setTwData(String twData) {
        this.twData = twData;
    }

    public String getTwTime() {
        return twTime;
    }

    public void setTwTime(String twTime) {
        this.twTime = twTime;
    }
}
