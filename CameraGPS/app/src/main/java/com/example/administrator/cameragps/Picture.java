package com.example.administrator.cameragps;

/**
 * Created by Administrator on 2015-12-28.
 */
public class Picture {
    String today; //날짜
    double lat; //위도
    double lon; //경도
    String address; //주소

    @Override
    public String toString() {
        String s = today+" "+lat+" "+lon+" "+address;
        return s;
    }

    public Picture(String today, double lat, double lon, String address){
        this.today = today;
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



    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
