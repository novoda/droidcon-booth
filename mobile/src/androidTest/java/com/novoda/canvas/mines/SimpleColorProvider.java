package com.novoda.canvas.mines;

import com.novoda.canvas.R;;

import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

public final class SimpleColorProvider {

    public enum ColorId {
        NO_1(R.color.NO_1_COLOR),
        NO_2(R.color.NO_2_COLOR),
        NO_3(R.color.NO_3_COLOR),
        NO_4(R.color.NO_4_COLOR),
        NO_5(R.color.NO_5_COLOR),
        NO_6(R.color.NO_6_COLOR),
        NO_7(R.color.NO_7_COLOR),
        NO_8(R.color.NO_8_COLOR),
        ERROR(R.color.ERROR_COLOR),
        FLAG_LIGHT(R.color.FLAG_COLOR),
        FLAG_DARK(R.color.FLAG_COLOR),
        FLAGPOLE(R.color.FLAGPOLE_COLOR),
        BOMB_BODY(R.color.BOMB_COLOR_BODY),
        BOMB_SPIKES(R.color.BOMB_COLOR_SPIKES),
        BUTTON_UP_LEFT(R.color.BUTTON_COLOR_UP_LEFT),
        BUTTON_LOW_RIGHT(
                R.color.BUTTON_COLOR_LOW_RIGHT),
        FOCUS(R.color.FOCUS_COLOR),
        TOUCH(R.color.TOUCH_COLOR);

        private final int defaultColor;

        ColorId(int defaultColor) {
            this.defaultColor = defaultColor;
        }
    }

    private final Map<ColorId, Integer> colorMap = new HashMap<>();

    public SimpleColorProvider(Resources resources) {
        for (ColorId colorId : ColorId.values()) {
            map(colorId, resources, colorMap);
        }
    }

    @SuppressWarnings("boxing")
    public int getValue(ColorId id) {
        return colorMap.get(id);
    }

    @SuppressWarnings("boxing")
    private static void map(ColorId id, Resources resources, Map<ColorId, Integer> colorMap) {
        colorMap.put(id, resources.getColor(id.defaultColor));
    }
}
