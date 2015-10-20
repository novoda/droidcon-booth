package com.novoda.canvas;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.Random;

public class HelloWorldTest extends NovodaActivityTest {

    private static final int COLOUR_CHANGE_INTERVAL = 200;
    private static final int BYTE_AS_INT_RANGE = 255;

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = (ViewGroup) activity.findViewById(android.R.id.content);
        final TextView text = new TextView(activity);
        text.setText("HELLO WORLD");
        text.setTextSize(250);
        root.addView(text);

        for (int i = 0; i < 200; i++) {
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
        return new Random().nextInt(BYTE_AS_INT_RANGE);
    }

}
