package com.novoda.canvas.base;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import java.util.Random;

public class RandomColorFactory {

    public static final int BASE_LEVEL = 50;
    public static final int TOP_UP_LEVEL = 205;
    private final Random random;

    public RandomColorFactory(Random random) {
        this.random = random;
    }

    @ColorInt
    public int getColor() {
        int red = BASE_LEVEL + random.nextInt(TOP_UP_LEVEL);
        int green = BASE_LEVEL + random.nextInt(TOP_UP_LEVEL);
        int blue = BASE_LEVEL + random.nextInt(TOP_UP_LEVEL);
        return Color.rgb(red, green, blue);
    }

    @ColorInt
    public int updateBrightness(@ColorInt int color, int delta) {
        int red = updateComponentBrightness(Color.red(color), delta);
        int green = updateComponentBrightness(Color.green(color), delta);
        int blue = updateComponentBrightness(Color.blue(color), delta);
        return Color.rgb(red, green, blue);
    }

    private int updateComponentBrightness(int component, int delta) {
        int lightenValue = component + delta;
        if (lightenValue > 255) {
            lightenValue = 255;
        }
        if (lightenValue < 0) {
            lightenValue = 0;
        }
        return lightenValue;
    }
}
