package com.novoda.canvas;

import android.app.Activity;

import com.novoda.canvas.base.NovodaActivityTest;

public class PartyParrotTest extends NovodaActivityTest {
    @Override
    public void startTestFor(Activity activity) {
        activity.setContentView(R.layout.parrot);
    }

}
