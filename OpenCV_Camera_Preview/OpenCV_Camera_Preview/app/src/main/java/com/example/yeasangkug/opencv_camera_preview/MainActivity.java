package com.example.yeasangkug.opencv_camera_preview;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yeasangkug.opencv_camera_preview.CustomEffect.Effect01;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/** OPEN CV 카메라를 사용하기 위해서 CVCameraViewListener2 를 상속 받는다. */
public class MainActivity extends ActionBarActivity implements CvCameraViewListener2{

    private Handler handler;
    private Context context;

    private static DDu2CameraView mOpenCvCameraView; // 카메라 뷰 (직접만든!)
    private Button capture,camerachange;
    private EditText value1_edt,value2_edt,value3_edt;
    public double value1,value2,value3; //카메라 효과값
    private Boolean effect = false;     // 효과 적용 여부
    private Boolean camerafacing = false;
    private Boolean takePhoto = false;  // 커스텀 효과를 위해서 true, false로 구분


    private Mat mBgr;   // 사진 저장할때 쓰는 변수

    /* open cv 카메로 라이브러리 로드 */
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    // 뷰를 활성화 시킨다.
                    mOpenCvCameraView.enableView();

                    mBgr = new Mat();

                }
                break;
                default:
                {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 핸들러 및 컨텍스트 지정
        handler = new Handler();
        context = this.getApplication().getApplicationContext();

        // 레이아웃 설정
        setContentView(R.layout.activity_main);

        // 화면을 항상 켜둔 채로 유지
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 카메라 뷰가 나타낼 opencv 화면 뷰 리소스 설정
        mOpenCvCameraView = (DDu2CameraView) findViewById(R.id.tutorial1_activity_java_surface_view);
        // 화면에 나타내기

        mOpenCvCameraView.setCameraIndex(1);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        // 카메라 콜백 리스너 설정 현재 엑티비티가 상속 받았기 때문에 this
        mOpenCvCameraView.setCvCameraViewListener(this);

        capture = (Button)findViewById(R.id.capture);
        camerachange =(Button)findViewById(R.id.camerachange);
        value1_edt = (EditText)findViewById(R.id.value1);
        value2_edt = (EditText)findViewById(R.id.value2);
        value3_edt = (EditText)findViewById(R.id.value3);

    }

    // 버튼 온클릭 이벤트
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.capture : // 사진 저장 버튼

                takePhoto = true;   // 사진 저장을 활성화

            break;

            case R.id.effect :  // 사진 효과 버튼

                value1 = Double.parseDouble(value1_edt.getText().toString());
                value2 = Double.parseDouble(value2_edt.getText().toString());
                value3 = Double.parseDouble(value3_edt.getText().toString());
                if(effect)
                {
                    effect = false;  // 이팩트 효과 해제
                }
                else
                {
                    effect = true;  // 이팩트 효과 지정
                }

            break;

            case R.id.camerachange : // 카메라 전환 버튼

                break;
        }

    }

    // 사진 저장하는 메소드
    private void savePhoto(Mat rgba)
    {
        // 시간 정보 받아오기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateandTime = sdf.format(new Date());


        // 파일이름
        final String fileName = Environment.getExternalStorageDirectory().getPath() +
                "/sample_picture_" + currentDateandTime + ".jpg";


        // 이미지 컬러 컨버팅 rgba에 들어 있는 값을 3번째 매개변수 값을 통해서  mBgr에 받아온다.
        Imgproc.cvtColor(rgba, mBgr, Imgproc.COLOR_RGBA2BGR, 3);


        // 실제 사진 저장부분 변환된 mBgr 안에 들어 있는 내용을 파일 이름으로 저장 한다.
        if (!Imgcodecs.imwrite(fileName, mBgr)) {
           System.out.println("사진 저장 실패");
        }

        System.out.println("사진 저장 완료 ");

        handler.post(new Runnable() {
            @Override
            public void run() {
                // 외부 스레드에서 호출 하였기 때문에 핸드러를써서 유아이 수정
                Toast.makeText(context, fileName + " saved", Toast.LENGTH_SHORT).show();
            }
        });


        //mOpenCvCameraView.takePicture(fileName);  커스텀 효과 저장에서는 사용하지 않는다.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /** open cv 카메라 리스너 (이벤트가 이쪽으로 들어온다) */
    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override // 현재 카메라 프레임의 rgba 값을 리턴해준다. mat 클래스로 아마도??
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        final Mat rgba = inputFrame.rgba();
        Core.flip(rgba, rgba, 1);
        /** Mat 안에는 기존의 rgba 값이 들어 있다 이것을 효과를 적용 시킨다.
         *  현재 리스너로 해당 화면이 계속 전송 되고 있기 때문에 여기서 사진 저장도 한다.
         * */

        if(effect) // 효과 적용일때만 작동!
        {
            new Effect01().apply(rgba, rgba,value1,value2,value3);
        }



        if(takePhoto) // 사진 저장이 활성화 상태 이면
        {
            // 사진 저장을 막는다
            takePhoto = false;

            // 현재 rgba값을 사진 저장으로 넘긴다
            savePhoto(rgba);

        }



        // 변형된 효과 적용된 rgba를 리턴
        return rgba;
    }



    /** 엑티비티 주요 메소드 오버라이딩 */
    @Override
    protected void onResume() { // 액티비티 재실행시
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            // opencv 버전을 맞춰주어야 한다 반드시!
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

    }

    @Override
    protected void onPause() { // 액시비티 일시 정지
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    protected void onDestroy() { // 액티비티 파괴 (종료)
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();

    }
}
