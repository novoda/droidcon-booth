package com.novoda.canvas.drawingmachine;

import android.app.Activity;

import com.novoda.canvas.base.NovodaActivityTest;

import org.junit.After;
import org.junit.Before;

public class DrawingMachineActivityTest extends NovodaActivityTest {

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
