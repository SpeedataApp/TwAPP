package com.example.twapp.adapter;

/**
 * Created by lenovo-pc on 2017/9/18.
 */

public class InforClass {

    private String name;
    private String age;
    private String gender;
    private String bedNumber;
    private String result;
    private String keshi;

    public InforClass(String name, String age, String gender, String bedNumber, String result, String keshi) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bedNumber = bedNumber;
        this.result = result;
        this.keshi = keshi;
    }

    public String getKeshi() {
        return keshi;
    }

    public void setKeshi(String keshi) {
        this.keshi = keshi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
