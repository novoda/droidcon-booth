package com.novoda.canvas;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.Random;

public class FooWorldTest extends NovodaActivityTest {

    private static final int AMPLITUDE = 127;
    private static final int CENTER_FREQUENCY = 128;

    private static final float PHASE_RED = 0f;
    private static final float PHASE_GREEN = 2f;
    private static final float PHASE_BLUE = 4f;

    private static final Random RANDOM = new Random();

    @Override
    public void startTestFor(final Activity activity) {
        final ViewGroup root = (ViewGroup) activity.findViewById(android.R.id.content);

        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );

        final int waveLength = RANDOM.nextInt(1000);

        View child = new View(activity);
        child.setBackgroundResource(R.drawable.android_logo);

        child.setLayoutParams(new ViewGroup.LayoutParams(1000, 1000));

        child.setY(activity.getWindow().getDecorView().getHeight());
        child.setX(root.getWidth());
        root.addView(child);

        for (int i = 0; i < 10000; i++) {
            final int finalI = i;
            root.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            int colour = createSweepColourFor(finalI, waveLength + 500);
                            root.setBackgroundColor(colour);
                        }
                    }, i * 5
            );
        }

    }

    protected int createSweepColourFor(int position, int waveLength) {
        float frequency = getOneCycle(waveLength);

        String r = toHex(calculateColour(position, frequency, PHASE_RED, AMPLITUDE, CENTER_FREQUENCY));
        String g = toHex(calculateColour(position, frequency, PHASE_GREEN, AMPLITUDE, CENTER_FREQUENCY));
        String b = toHex(calculateColour(position, frequency, PHASE_BLUE, AMPLITUDE, CENTER_FREQUENCY));

        Log.e("!!!", "" + "#" + r + g + b);
        return Color.parseColor("#" + r + g + b);
    }

    private float getOneCycle(int waveLength) {
        return (float) (2 * Math.PI / waveLength);
    }

    private int calculateColour(float position, float frequency, float phase, int amplitude, int centerFreq) {
        return (int) (Math.sin((position * frequency) + phase) * amplitude + centerFreq);
    }

    private String toHex(int value) {
        return String.format("%02x", Math.min(value, 255));
    }

}
