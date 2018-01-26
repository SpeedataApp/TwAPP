package com.example.twapp.adapter;

public class TestData {
    String twNum = "";
    int flag9 = 0;
    int flag11 = 0;
    int flag61 = 0;
    int total = 0;
    int trues = 0;
    int falses = 0;
    long times = 0;

    public TestData(String twNum, int flag9, int flag11, int flag61, int total, int trues, int falses, long times) {
        this.twNum = twNum;
        this.flag9 = flag9;
        this.flag11 = flag11;
        this.flag61 = flag61;
        this.total = total;
        this.trues = trues;
        this.falses = falses;
        this.times = times;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public String getTwNum() {
        return twNum;
    }

    public void setTwNum(String twNum) {
        this.twNum = twNum;
    }

    public int getFlag9() {
        return flag9;
    }

    public void setFlag9(int flag9) {
        this.flag9 = flag9;
    }

    public int getFlag11() {
        return flag11;
    }

    public void setFlag11(int flag11) {
        this.flag11 = flag11;
    }

    public int getFlag61() {
        return flag61;
    }

    public void setFlag61(int flag61) {
        this.flag61 = flag61;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTrues() {
        return trues;
    }

    public void setTrues(int trues) {
        this.trues = trues;
    }

    public int getFalses() {
        return falses;
    }

    public void setFalses(int falses) {
        this.falses = falses;
    }
}
