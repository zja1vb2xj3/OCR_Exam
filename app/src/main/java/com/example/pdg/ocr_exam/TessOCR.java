package com.example.pdg.ocr_exam;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Created by pdg on 2017-12-05.
 */

public class TessOCR {
    private TessBaseAPI tessBaseAPI;

    public TessOCR(Context context, String lang) {
        tessBaseAPI = new TessBaseAPI();

        String dataPath = context.getFilesDir() + "/tesseract/";

        tessBaseAPI.init(dataPath, lang);
    }

    public String getOCR_Data(Bitmap bitmap){
        tessBaseAPI.setImage(bitmap);

        return tessBaseAPI.getUTF8Text();
    }

    public void finish(){
        if(tessBaseAPI != null)
            tessBaseAPI.end();
    }
}
