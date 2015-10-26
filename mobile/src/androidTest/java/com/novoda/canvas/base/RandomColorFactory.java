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
}
