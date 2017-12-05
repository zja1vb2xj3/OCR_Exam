package com.example.pdg.ocr_exam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OCRActivity extends Activity {

    private TessOCR tessOCR;

    private final String korLang = "kor";
    private final String engLang = "eng";

    @BindView(R.id.resultText)
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        ButterKnife.bind(this);

        ThisApplication thisApplication = (ThisApplication) getApplicationContext();

        byte[] imageByte = thisApplication.getImageByte();

        final Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aad);

        tessOCR = new TessOCR(OCRActivity.this, korLang);

        String strBuffer = "";
        String ocrData = tessOCR.getOCR_Data(rotateBitmap);
        System.out.println(ocrData);

        String result = strBuffer.concat(ocrData);

        resultText.setText(result);
    }
}
