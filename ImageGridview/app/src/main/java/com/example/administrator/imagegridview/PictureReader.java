package com.example.administrator.imagegridview;

import com.example.administrator.cameragps.Picture;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

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
    public ArrayList<String> getData(){

        try
        {
            while (true){
                String picture = null;
                picture = br.readLine();
                if(picture==null){
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("my error : "+e.toString());
        }

        return picture;
    }

    public void close(){

        try {
            fis.close();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
