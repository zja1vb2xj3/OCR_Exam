package com.example.pdg.ocr_exam;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by pdg on 2017-12-05.
 */

public class ThisApplication extends Application {
    private byte[] imageByte;

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }
}
