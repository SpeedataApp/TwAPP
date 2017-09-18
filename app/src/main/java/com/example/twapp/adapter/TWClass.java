package com.example.twapp.adapter;

/**
 * Created by lenovo-pc on 2017/8/30.
 */

public class TWClass {

    //    public List<String> twData;
//    public List<String> twTime;
    public String twData;

    public TWClass(String twData, String twTime) {
        this.twData = twData;
        this.twTime = twTime;
    }

    public String twTime;

    public TWClass() {

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
    //    public TWClass(List<String> twData, List<String> twTime) {
//        this.twData = twData;
//        this.twTime = twTime;
//    }
//
//    public List<String> getTwData() {
//        return twData;
//    }
//
//    public void setTwData(List<String> twData) {
//        this.twData = twData;
//    }
//
//    public List<String> getTwTime() {
//        return twTime;
//    }
//
//    public void setTwTime(List<String> twTime) {
//        this.twTime = twTime;
//    }
}
