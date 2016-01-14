package com.example.yeasangkug.opencv_camera_preview;

import java.io.FileOutputStream;
import java.util.List;

import org.opencv.android.JavaCameraView;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;

/** 사진 기능과 저장 기능을 위해서 커스텀 뷰를 사용한다. opencv에서 제공한 java cameraView 사용 */

public class DDu2CameraView extends JavaCameraView implements PictureCallback {

    // 저장할 파일의 위치
    private String mPictureFileName;

    public DDu2CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public List<String> getEffectList() {
        return mCamera.getParameters().getSupportedColorEffects();
    }

    public boolean isEffectSupported() {
        return (mCamera.getParameters().getColorEffect() != null);
    }

    public String getEffect() {
        return mCamera.getParameters().getColorEffect();
    }

    public void setEffect(String effect) {
        Camera.Parameters params = mCamera.getParameters();
        params.setColorEffect(effect);
        mCamera.setParameters(params);
    }

    public List<Size> getResolutionList() {
        return mCamera.getParameters().getSupportedPreviewSizes();
    }

    public void setResolution(Size resolution) {
        disconnectCamera();
        mMaxHeight = resolution.height;
        mMaxWidth = resolution.width;
        connectCamera(getWidth(), getHeight());
    }

    public Size getResolution() {
        return mCamera.getParameters().getPreviewSize();
    }


    // 외부에서 저장할 파일 이름을 받아서 해당 위치에 저장 한다.
    public void takePicture(final String fileName) {
        System.out.println("사진 저장하기 위해서 파일 이름 받아옴 : "+fileName);
        this.mPictureFileName = fileName;

        // Postview and jpeg are sent in the same buffers if the queue is not empty when performing a capture.
        // Clear up buffers to avoid mCamera.takePicture to be stuck because of a memory issue

        mCamera.setPreviewCallback(null);

        // PictureCallback is implemented by the current class
        mCamera.takePicture(null, null, this);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        // The camera preview was automatically stopped. Start it again.
        System.out.println("파일 저장 실행");
        mCamera.startPreview();
        mCamera.setPreviewCallback(this);

        // jpg 파일로 실제로 파일을 저장하는 부분
        try {
            FileOutputStream fos = new FileOutputStream(mPictureFileName);

            fos.write(data);
            fos.close();
            System.out.println("파일 저장 완료");

        } catch (java.io.IOException e) {
            System.out.println("파일 저장 실패 : "+e.toString());
        }

    }
}
