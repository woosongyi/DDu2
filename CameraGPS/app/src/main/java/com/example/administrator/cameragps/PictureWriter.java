package com.example.administrator.cameragps;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2016-01-03.
 */
public class PictureWriter {
    private FileWriter fos;

    String path;

    public PictureWriter(String path){
        try {
            fos = new FileWriter(path,true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addData(Picture picture){
        try {
           fos.write(picture.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){

        try {
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
