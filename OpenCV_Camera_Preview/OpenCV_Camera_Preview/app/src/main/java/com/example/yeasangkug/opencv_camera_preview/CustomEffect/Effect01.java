package com.example.yeasangkug.opencv_camera_preview.CustomEffect;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.ArrayList;

/** jsp mvc 할떄처럼 Filter를 인터페이스 해놓고 상속 받아서 사용*/

public final class Effect01 implements Filter {

    private final ArrayList<Mat> mChannels = new ArrayList<Mat>(4);

    @Override
    public void apply(final Mat src, final Mat dst) {
//        /** Mat 이라는 데이터 객체를 받아서 안의 내용을 변환 시킨다. */
//
//        Core.split(src, mChannels);
//
//        final Mat g = mChannels.get(1);
//        final Mat b = mChannels.get(2);
//
//        // dst.g = 0.5 * src.g + 0.5 * src.b
//        Core.addWeighted(g, 0.5, b,  0.5, 0.0, g);
//
//        // dst.b = dst.g
//        mChannels.set(2, g);
//
//        Core.merge(mChannels, dst);
    }

    public void apply(Mat src, Mat dst, double value1, double value2, double value3) {
        /** Mat 이라는 데이터 객체를 받아서 안의 내용을 변환 시킨다. */

        Core.split(src, mChannels);

        final Mat g = mChannels.get(1);
        final Mat b = mChannels.get(2);

        // dst.g = 0.5 * src.g + 0.5 * src.b
        Core.addWeighted(g, value1, b,  value2, value3, g);

        // dst.b = dst.g
        mChannels.set(2, g);

        Core.merge(mChannels, dst);
    }
}