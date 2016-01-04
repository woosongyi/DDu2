package com.example.administrator.cameragps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
//카메라+GPS
public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_CAPTURE = 0;
    File path;
    Calendar calendar ;
    String today;
    TextView stateTv,gpsTv,networkTv,resultTv;
    LocationManager manager;
    Geocoder coder;
    double lat;//위도
    double lon;//경도
    String address;// 주소
    Context Activitycontext;

    FileWriter fout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.Activitycontext=this.getApplication().getApplicationContext();
        setContentView(R.layout.activity_main);
        Button captureButton = (Button) findViewById(R.id.capture);
        stateTv = (TextView) findViewById(R.id.stateView);
        gpsTv = (TextView) findViewById(R.id.gpsView);
        networkTv = (TextView) findViewById(R.id.networkView);
        resultTv = (TextView)findViewById(R.id.resultView);

        coder= new Geocoder(this);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 3. 제공가능한 서비스 불러오기

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GPS_Listener()); // GPS 정보로 가져오기
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new NETWORK_Listener()); // 네트워크 정보로 가져오기

        //****사진저장****
        path =new File( Environment.getExternalStorageDirectory(),"ddu2"); //사진경로

        if(path.exists() ==false){
            path.mkdir();
        }
        System.out.println(path.getPath());

        //사진캡쳐버튼 클릭 리스너
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 이미지 캡쳐 인텐트 생성
                //인텐트의 엑스트라 데이터로 이미지가 저장되는 위치를 Uri로 지정한다.
                calendar = Calendar.getInstance();
                java.util.Date date = calendar.getTime();
                today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path.getPath() + "/" + today + ".jpg")));
                startActivityForResult(i, CAMERA_CAPTURE); //이미지 캡쳐 엑티비티를 실행한다.

                Picture picture = new Picture(lat,lon,address); //사진객체만들기
                PictureWriter pw = new PictureWriter(path.getPath() + "/" +"result.txt");
                pw.addData(picture);
                pw.close();

            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        Bitmap captureBmp = null;

        System.out.println("requestCode : "+requestCode);
        System.out.println("resultCode : " + resultCode);
        if(resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE){
          //  System.out.println("사진이 찍혔어요");
            File file = new File(path.getPath()+"/"+today+".jpg");
            try {
                captureBmp = MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.fromFile(file));

             //   System.out.println("사진이 저장됬어요");
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
        imageView.setRotation(90);
    }

    public class GPS_Listener implements LocationListener // gps listener
    {
        List<Address> list = null;
        @Override
        public void onLocationChanged(Location location) {
            gpsTv.setText("GPS 위도; " + location.getLatitude() + "\n경도:"
                    + location.getLongitude() + "\n고도:"
                    + location.getAltitude());

            // 위도 경도 가져오기
            lat  = location.getLatitude();
            lon =  location.getLongitude();

            // 위경도 출력
            try {
                list = coder.getFromLocation(lat,lon, 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {

                resultTv.setText("입출력 오류: "+ e.getMessage());
                e.printStackTrace();
            }
            if (list != null) {
                String allresult = list.get(0).toString();
                int front, back;
                front = allresult.indexOf("[0:")+4;
                back = allresult.indexOf("],")-1;
                address = allresult.substring(front,back);
                resultTv.setText(address);
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    // 네트워크 위치 리스너
    public class NETWORK_Listener implements LocationListener
    {
        List<Address> list = null;
        @Override
        public void onLocationChanged(Location location) {
            networkTv.setText("네트워크 위도; " + location.getLatitude() + "\n경도:"
                    + location.getLongitude() + "\n고도:"
                    + location.getAltitude());

            // 위도 경도 가져오기
            lat  = location.getLatitude();
            lon =  location.getLongitude();

            // 위경도 출력
            try {
                list = coder.getFromLocation(lat,lon, 1); // 10은 결과 갯수 -> 1
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {

                resultTv.setText("입출력 오류: "+ e.getMessage());
                e.printStackTrace();
            }


            if (list != null) {
                String allresult = list.get(0).toString();
                int front, back;
                front = allresult.indexOf("[0:")+4;
                back = allresult.indexOf("],")-1;
                address = allresult.substring(front,back);
                resultTv.setText(address);
            }


        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
