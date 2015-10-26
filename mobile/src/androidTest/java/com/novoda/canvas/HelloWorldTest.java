package com.novoda.canvas;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.canvas.base.NovodaActivityTest;

import static com.novoda.canvas.NovodaActivity.RANDOM;

public class HelloWorldTest extends NovodaActivityTest {

    private static final int COLOUR_CHANGE_INTERVAL = 200;
    private static final int BYTE_AS_INT_RANGE = 255;

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent(activity);
        final TextView text = new TextView(activity);
        text.setText("HELLO WORLD");
        text.setTextSize(150);
        root.addView(text);

        int numberOfChanges = NovodaActivityTest.TIME_LIMIT_FOR_TEST_IN_SECONDS * 1000 / COLOUR_CHANGE_INTERVAL;
        for (int i = 0; i < numberOfChanges; i++) {
            root.postDelayed(new Runnable() {
                @Override
                public void run() {
                    root.setBackgroundColor(createRandomColour());
                    text.setTextColor(createRandomColour());
                }
            }, i * COLOUR_CHANGE_INTERVAL);
        }
    }

    private int createRandomColour() {
        return Color.argb(randomByte(), randomByte(), randomByte(), randomByte());
    }

    private int randomByte() {
        return RANDOM.nextInt(BYTE_AS_INT_RANGE);
    }

}
