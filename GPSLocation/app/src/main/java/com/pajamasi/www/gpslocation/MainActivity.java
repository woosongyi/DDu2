package com.pajamasi.www.gpslocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView stateTv;
    TextView gpsTv;
    TextView networkTv;
    TextView resultTv;

    /** 여보 집에 가고 싶다며*/
    LocationManager manager;

    /** 여보 집에 가고 싶다며*/
    Geocoder coder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stateTv = (TextView) findViewById(R.id.stateView);
        gpsTv = (TextView) findViewById(R.id.gpsView);
        networkTv = (TextView) findViewById(R.id.networkView);
        resultTv = (TextView)findViewById(R.id.resultView);


        /** 여보 집에 가고 싶다며*/
        coder = new Geocoder(this);


        // 2. 위치 관리자 얻어오기
        /** 여보 집에 가고 싶다며*/
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // 3. 제공가능한 서비스 불러오기

        List<String> providers = manager.getAllProviders();

        String str = "";

        for (int i = 0; i < providers.size(); i++) {

            str += "위치 제공자 : " + providers.get(i) + " 의 상태 : " +
                    manager.isProviderEnabled(providers.get(i)) + "\n";
        }

        stateTv.setText(str);



        //4. 리스너 등록
        /** 여보 집에 가고 싶다며*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GPS_Listener()); // GPS 정보로 가져오기
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new NETWORK_Listener()); // 네트워크 정보로 가져오기
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
            double lat  = location.getLatitude();
            double lon =  location.getLongitude();

            // 위경도 출력
            try {
                list = coder.getFromLocation(lat,lon, 1); // 10은 결과 갯수 -> 1
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {

                resultTv.setText("입출력 오류: "+ e.getMessage());
                e.printStackTrace();
            }


            if (list != null)
                resultTv.setText(list.get(0).toString());

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





    public class GPS_Listener implements LocationListener // gps listener
    {
        List<Address> list = null;
        @Override
        public void onLocationChanged(Location location) {
            gpsTv.setText("GPS 위도; " + location.getLatitude() + "\n경도:"
                    + location.getLongitude() + "\n고도:"
                    + location.getAltitude());

            // 위도 경도 가져오기
            double lat  = location.getLatitude();
            double lon =  location.getLongitude();

            // 위경도 출력
            try {
                list = coder.getFromLocation(lat,lon, 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {

                resultTv.setText("입출력 오류: "+ e.getMessage());
                e.printStackTrace();
            }
            if (list != null)
                resultTv.setText(list.get(0).toString());
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
