package com.wtcl.learn01.entity;

/**
 * @project: LearnAndroid
 * @desc: this is ...
 * @author: WTCL
 * @date: 2021/7/13
 */
public class Channel {
    private int id;
    private String cityName;
    private String temp;
    private int wind;
    private int pm250;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public int getPm250() {
        return pm250;
    }

    public void setPm250(int pm250) {
        this.pm250 = pm250;
    }
}