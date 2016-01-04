package com.example.administrator.cameragps;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2015-12-28.
 */
public class Picture {



    String name; //날짜
    Date today;
    double lat; //위도
    double lon; //경도
    String address; //주소
    String path; //경로

    public Picture(double lat, double lon, String address){
        create_date();

        this.address = address;
        this.lat = lat;
        this.lon = lon;

    }


    private void create_date() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
        this.today = new Date();
        this.name=formatter.format(this.today);
    }

    @Override
    public String toString() {
        String s = name+" "+lat+" "+lon+" "+address;
        return s;
    }


    public Picture(String today, double lat, double lon, String address){
        this.name = today;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public String getToday() {
        return name;
    }

    public void setToday(String today) {
        this.name = today;
    }

    public String getName(){return this.name;}
}
