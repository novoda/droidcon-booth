package com.novoda.canvas.drawingmachine.spiral;

import android.app.Activity;
import android.support.annotation.ColorInt;

import com.novoda.canvas.R;
import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.canvas.drawingmachine.DrawingMachine;
import com.novoda.canvas.drawingmachine.Engine;
import com.novoda.canvas.drawingmachine.ScreenUpdatingEngine;

import org.junit.After;
import org.junit.Before;

public class SpiralActivityTest extends NovodaActivityTest {

    private DrawingMachine drawingMachine;

    @Before
    public void welcomeToTheDrawingMachine() {
        drawingMachine = new DrawingMachine();

        drawingMachine.registerEngine(new ScreenUpdatingEngine());
        drawingMachine.registerEngine(
                createSpiralEngine(getColor(R.color.dark_gray), 2)
        );
        drawingMachine.registerEngine(
                createSpiralEngine(getColor(R.color.vibrant_red), 4)
        );
    }

    private Engine createSpiralEngine(@ColorInt int colour, long periodInMillis) {
        return new SpiralPaintingEngine.Builder()
                .withColour(colour)
                .withPeriod(periodInMillis)
                .build();
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
