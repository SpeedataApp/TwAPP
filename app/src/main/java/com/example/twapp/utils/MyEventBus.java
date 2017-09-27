package com.example.twapp.utils;

/**
 * Created by lenovo-pc on 2017/8/10.
 */

public class MyEventBus {
    private byte temperature[];

    public MyEventBus(byte[] temperature) {
        this.temperature = temperature;
    }

    public byte[] getTemperature() {
        return temperature;
    }

    public void setTemperature(byte[] temperature) {
        this.temperature = temperature;
    }
}
