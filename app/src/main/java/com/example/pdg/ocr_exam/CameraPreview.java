package com.example.pdg.ocr_exam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by pdg on 2017-12-01.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Camera camera;

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        this.setOnClickListener(onClickListener);

    }

    public void setPictureButtonCallBack(Activity activity) {
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.setPhotoButtonCallback(photoButtonCallback);
    }

    MainActivity.PhotoButtonCallback photoButtonCallback = new MainActivity.PhotoButtonCallback() {
        @Override
        public void onClick() {
            System.out.println("버튼클릭");

            camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    Camera.Parameters parameters = camera.getParameters();
                    int width = parameters.getPreviewSize().width;
                    int height = parameters.getPreviewSize().height;

                    YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    yuvImage.compressToJpeg(new Rect(0, 0, width, height), 100, outputStream);

                    byte[] bytes = outputStream.toByteArray();

                    convertBitmapCallBack.onPhotoData(bytes);
                }
            });
        }
    };
    private ConvertBitmapCallBack convertBitmapCallBack;

    public void setConvertBitmapCallBack(ConvertBitmapCallBack convertBitmapCallBack) {
        this.convertBitmapCallBack = convertBitmapCallBack;
    }

    interface ConvertBitmapCallBack{
        void onPhotoData(byte[] bytes);
    }

    SurfaceView.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                }
            });
        }
    };


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        System.out.println("created");

        startCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w, int h) {
        System.out.println("changed");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        System.out.println("destroyed");
//        camera.stopPreview();
//        camera = null;
    }

    private void startCamera(){
        try {
            camera = Camera.open();

            camera.setPreviewDisplay(holder);

            camera.setDisplayOrientation(90);

            camera.startPreview();

        } catch (IOException e) {
            camera.release();
            camera = null;
        }
    }


}
