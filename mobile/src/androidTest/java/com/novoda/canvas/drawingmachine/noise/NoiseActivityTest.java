package com.novoda.canvas.drawingmachine.noise;

import android.app.Activity;

import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.canvas.drawingmachine.DrawingMachine;
import com.novoda.canvas.drawingmachine.ScreenUpdatingEngine;

import org.junit.After;
import org.junit.Before;

public class NoiseActivityTest extends NovodaActivityTest {

    private DrawingMachine drawingMachine;

    @Before
    public void welcomeToTheDrawingMachine() {
        drawingMachine = new DrawingMachine();
        drawingMachine.registerEngine(NoisePaintingEngine.newInstance());
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
