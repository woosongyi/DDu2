package com.example.administrator.imagegridview;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.administrator.cameragps.Picture;

import java.io.File;

public class MainActivity extends AppCompatActivity {
ImageAdapter myImageAdapter;
    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridview = (GridView) findViewById(R.id.gridView);
        myImageAdapter = new ImageAdapter(this);
        gridview.setAdapter(myImageAdapter);

        String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String targetPath = ExternalStorageDirectoryPath +"/ddu2";



        File targetDirectory = new File(targetPath);
        System.out.println("최종 지정된 경로 : "+targetDirectory);
        File[] files = targetDirectory.listFiles();
        System.out.println("폴더인가? : "+targetDirectory.isDirectory());
        try
        {
            String[] list = targetDirectory.list();
            System.out.println("list 의 사이즈 : " +list);
        }catch(Exception e){System.out.println(e.toString());}

        System.out.println(files);
        for(File file: files){

            myImageAdapter.add(file.getAbsolutePath());
        }

    }
}
