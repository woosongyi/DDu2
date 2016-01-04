package com.example.administrator.imagegridview;

import com.example.administrator.cameragps.Picture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2016-01-03.
 */
public class PictureReader {
    private FileReader fis;
    private BufferedReader br;

    String path;

    public PictureReader(String path){
        try {
            fis = new FileReader(path);
            br = new BufferedReader(fis);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Picture> getData(){
        ArrayList<Picture> picturelist = new ArrayList<Picture>();
        try
        {
            while (true){
                String picture_string = null;
                picture_string = br.readLine();
                if(picture_string ==null){
                    break;
                }
                StringTokenizer st = new StringTokenizer(picture_string,"&");
                while(st.hasMoreTokens()){
                    //name+"&"+today+"&"+lat+"&"+lon+"&"+address+"&"+path; //파일이름,날짜,위도,경도,주소,경로
                    String name = st.nextToken();
                    System.out.println("name : " + name);
                    Date today = new Date(st.nextToken());
                    System.out.println("today : " + today);
                    double lat = Double.parseDouble(st.nextToken());
                    System.out.println("lat : " + lat);
                    double lon = Double.parseDouble(st.nextToken());
                    System.out.println("lon : " + lon);
                    String address = st.nextToken();
                    System.out.println("address : " + address);
                    String path = st.nextToken();
                    System.out.println("path : " + path);
                    Picture picture = new Picture(name,today,lat,lon,address,path);
                    picturelist.add(picture);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("my error : "+e.toString());
        }
        System.out.println("-----picurelistsize : " + picturelist.size());
        System.out.println("-----picurelist : "+picturelist);
       return picturelist;
    }

    public void close(){

        try {
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
