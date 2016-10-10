package com.novoda.canvas;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Random;

public class NovodaActivity extends Activity {

    public static final Random RANDOM = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novoda);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
