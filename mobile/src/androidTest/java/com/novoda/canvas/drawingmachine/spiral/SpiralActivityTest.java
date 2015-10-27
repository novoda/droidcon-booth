package com.novoda.canvas.drawingmachine.spiral;

import android.app.Activity;

import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.canvas.drawingmachine.DrawingMachine;
import com.novoda.canvas.drawingmachine.ScreenUpdatingEngine;

import org.junit.After;
import org.junit.Before;

public class SpiralActivityTest extends NovodaActivityTest {

    private DrawingMachine drawingMachine;

    @Before
    public void welcomeToTheDrawingMachine() {
        drawingMachine = new DrawingMachine();
        drawingMachine.registerEngine(SpiralPaintingEngine.newInstance());
        drawingMachine.registerEngine(new ScreenUpdatingEngine());
    }

    @Override
    public void startTestFor(Activity activity) {
        drawingMachine.start(activity);
    }

    @After
    public void thankYouForWatching() {
        drawingMachine.stop();
    }
}
