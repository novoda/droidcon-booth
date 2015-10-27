package com.novoda.canvas.base;

import android.graphics.Color;
import android.support.annotation.ColorInt;

public class ColorEditor {

    @ColorInt
    public int lighten(@ColorInt int color, int delta) {
        return updateBrightness(color, delta);
    }

    @ColorInt
    public int darken(@ColorInt int color, int delta) {
        return updateBrightness(color, -delta);
    }

    @ColorInt
    private int updateBrightness(@ColorInt int color, int delta) {
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
