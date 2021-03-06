package com.example.pdg.ocr_exam;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by pdg on 2017-12-01.
 */

public class DisplayManager extends DisplayMetrics {
    private Activity activity;

    public DisplayManager(Activity activity) {
        this.activity = activity;
    }

    public DisplayMetrics getDiplayMetrics(){
        activity.getWindowManager().getDefaultDisplay().getMetrics(this);

        return this;
    }
}
