package com.example.yeasangkug.opencv_camera_preview.CustomEffect;

import org.opencv.core.Mat;

public interface Filter {
    public abstract void apply(final Mat src, final Mat dst);
}
