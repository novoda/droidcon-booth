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
    private static final int NUMBER_OF_REPEATS = 5;
    private static final String TEXT = "セ ソ キ ク ケ コ イ ウ エ オ ジャ な あ た ア カ サ ザ ジ  ズ ゼ ゾ シ ス";
    private static final Random random = new Random();

    private final TextView[] rain = new TextView[NUMBER_OF_REPEATS];

    private ViewGroup parent;

    @Override
    public void startTestFor(Activity activity) {
        parent = getParent(activity);
        parent.setBackgroundColor(getColor(R.color.matrix_background));

        final int[] randomPositionX = new int[NUMBER_OF_REPEATS];
        scroll(randomPositionX);

    }

    private void scroll(int[] randomPositionX) {
        parent.postDelayed(scrollMatrix(randomPositionX), 200);
    }

    private Runnable scrollMatrix(final int[] randomPositionX) {
        return new Runnable() {
            @Override
            public void run() {
                draw(randomPositionX);
                scroll(randomPositionX);
            }
        };
    }

    private void draw(int[] randomPositionX) {
        selectRandomCharacters(randomPositionX);

        for (int j = 0; j < (parent.getHeight() / FONT_SIZE) * 2; j++) {
            char character = getRandomCharacter();

            for (int i = 0; i < NUMBER_OF_REPEATS; i++) {
                TextView charView = rain[i];
                charView.setX(randomPositionX[i] * FONT_SIZE);
                charView.setY(j * (FONT_SIZE / 2));

                charView.setText(String.valueOf(character));
                charView.setTextColor(Color.rgb(0, 255, 0));
                selectRandomCharacters(randomPositionX);
            }
        }
    }

    private char getRandomCharacter() {
        int randomCharPosition = random.nextInt(TEXT.length());
        return TEXT.charAt(randomCharPosition);
    }

    private void selectRandomCharacters(int[] randomPositionX) {
        for (int i = 0; i < NUMBER_OF_REPEATS; i++) {
            char character = getRandomCharacter();
            randomPositionX[i] = random.nextInt(parent.getWidth() / FONT_SIZE) - 1;

            TextView label = createTextView();
            rain[i] = label;
            label.setText(String.valueOf(character));
            int color = Color.rgb(0, 255 - (i * 5), 0);
            label.setTextColor(color);
            parent.addView(label);
            if (Color.green(color) <= 250) {
                parent.removeView(rain[i]);
//                i = (parent.getHeight() / FONT_SIZE) * 2;
            }
        }
    }

    private TextView createTextView() {
        TextView label = new TextView(parent.getContext());
        label.setTypeface(Typeface.MONOSPACE);
        label.setTextSize(FONT_SIZE);
        return label;
    }

}
