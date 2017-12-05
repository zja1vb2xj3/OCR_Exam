package com.example.pdg.ocr_exam;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.surfaceView)
    CameraPreview cameraView;

    @BindView(R.id.takePhoto_Button)
    ImageView takePhoto_Button;

    private TessOCR tessOCR;
    final int RequestCameraPermission = 1001;
    private String dataPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        cameraView.setPictureButtonCallBack(MainActivity.this);
        cameraView.setConvertBitmapCallBack(convertBitmapCallBack);
        takePhoto_Button.setOnClickListener(v -> takePhotoButtonClick());
    }

    CameraPreview.ConvertBitmapCallBack convertBitmapCallBack = new CameraPreview.ConvertBitmapCallBack() {
        @Override
        public void onPhotoData(byte[] bytes) {
            ThisApplication thisApplication = (ThisApplication) getApplicationContext();
            thisApplication.setImageByte(bytes);

            Intent intent = new Intent(MainActivity.this, OCRActivity.class);

            startActivity(intent);
        }
    };

    private void takePhotoButtonClick() {
        photoButtonCallback.onClick();
        cameraView.clearFocus();
    }

    interface PhotoButtonCallback{
        void onClick();
    }

    private PhotoButtonCallback photoButtonCallback;

    public void setPhotoButtonCallback(PhotoButtonCallback photoButtonCallback) {
        this.photoButtonCallback = photoButtonCallback;
    }

}
