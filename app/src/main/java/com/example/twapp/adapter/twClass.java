package com.example.twapp.adapter;

/**
 * Created by lenovo-pc on 2017/8/30.
 */

public class twClass {

    public int modle = 0;
    public String date;
    public String runingNumber;
    public boolean isLowPower;

    public twClass(int modle, String date, String runingNumber, boolean isLowPower) {
        this.modle = modle;
        this.date = date;
        this.runingNumber = runingNumber;
        this.isLowPower = isLowPower;
    }

    public int getModle() {
        return modle;
    }

    public void setModle(int modle) {
        this.modle = modle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRuningNumber() {
        return runingNumber;
    }

    public void setRuningNumber(String runingNumber) {
        this.runingNumber = runingNumber;
    }

    public boolean isLowPower() {
        return isLowPower;
    }

    public void setLowPower(boolean lowPower) {
        isLowPower = lowPower;
    }
}
