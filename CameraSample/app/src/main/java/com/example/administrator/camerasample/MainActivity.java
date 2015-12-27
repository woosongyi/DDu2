package com.example.administrator.camerasample;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.graphics.Bitmap;
import android.net.Uri;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_CAPTURE = 0;
    File path;
    Calendar calendar ;
    String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Button captureButton = (Button) findViewById(R.id.capture);

        path = Environment.getExternalStorageDirectory();
        System.out.println(path.getPath());

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 이미지 캡쳐 인텐트 생성
                //인텐트의 엑스트라 데이터로 이미지가 저장되는 위치를 Uri로 지정한다.
                calendar = Calendar.getInstance();
                java.util.Date date = calendar.getTime();
                today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path.getPath()+"/"+today)));
                startActivityForResult(i, CAMERA_CAPTURE); //이미지 캡쳐 엑티비티를 실행한다.
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        Bitmap captureBmp = null;
        System.out.println("requestCode : "+requestCode);
        System.out.println("resultCode : "+resultCode);
        if(resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE){
            System.out.println("사진이 찍혔어요");
            File file = new File(path.getPath()+"/"+today);
            try {
                captureBmp = MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.fromFile(file));
            }catch (FileNotFoundException e){
                System.out.println("사진 저장 에러1 : "+e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("사진 저장 에러2 : "+e.toString());
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("사진이 안찍혔어요!");
        }
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(captureBmp);
    }

}
