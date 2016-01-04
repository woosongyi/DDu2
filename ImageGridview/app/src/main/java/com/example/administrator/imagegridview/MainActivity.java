package com.example.administrator.imagegridview;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.administrator.cameragps.Picture;

import java.io.File;
import java.util.ArrayList;

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
        String PicturePath = ExternalStorageDirectoryPath +"/ddu2/result.txt";
        PictureReader pr = new PictureReader(PicturePath);
        ArrayList<Picture> pr_list= pr.getData();

      System.out.println("정렬하기 전 : " + pr_list);

      quickSort s = new quickSort(); //정렬클래스
      s.Qsort(pr_list,0,pr_list.size()-1); //정렬시작

      System.out.println("정렬한 후 : " + pr_list);
      //가장 최근에 찍은 날짜 순서대로(역순) 40초->30초->20초
      for(Picture picture: pr_list){ //정렬후 앨범그리드
        myImageAdapter.add(picture.getPath());
      }
    }
}
