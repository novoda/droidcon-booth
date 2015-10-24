package com.novoda.canvas;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.Random;

public class TheMatrixActivityTest extends NovodaActivityTest {

    private static final int FONT_SIZE = 20;
    private static final String CHARACTERS = "セ ソ キ ク ケ コ イ ウ エ オ ジャ な あ た ア カ サ ザ ジ  ズ ゼ ゾ シ ス";
    private static final Random RANDOM = new Random();

    private ViewGroup parent;
    private TextView view;

    @Override
    public void startTestFor(Activity activity) {
        parent = getParent(activity);
        parent.setBackgroundColor(getColor(R.color.matrix_background));
        createNextRainDropView();
        scroll();
    }

    private void createNextRainDropView() {
        TextView view = createTextView();
        char character = getRandomCharacter();
        view.setText(String.valueOf(character));
        parent.addView(view);
        this.view = view;

    }

    private TextView createTextView() {
        TextView label = new TextView(parent.getContext());
        label.setTypeface(Typeface.MONOSPACE);
        label.setTextSize(FONT_SIZE);
        return label;
    }

    private char getRandomCharacter() {
        int randomPosition = RANDOM.nextInt(CHARACTERS.length());
        return CHARACTERS.charAt(randomPosition);
    }

    private void scroll() {
        parent.postDelayed(scrollMatrix(), 200);
    }

    private Runnable scrollMatrix() {
        return new Runnable() {
            @Override
            public void run() {
                draw();
                scroll();
            }
        };
    }

    private void draw() {
        for (int y = 0; y < (parent.getHeight() / FONT_SIZE) * 2; y++) {
            TextView charView = view;
            charView.setX((RANDOM.nextInt(parent.getWidth() / FONT_SIZE) - 1) * FONT_SIZE);
            charView.setY(y * (FONT_SIZE / 2));
            charView.setTextColor(Color.rgb(0, 255 - (y * 2), 0));
            createNextRainDropView();
        }
    }

}
