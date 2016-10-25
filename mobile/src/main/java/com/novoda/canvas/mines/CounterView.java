package com.novoda.canvas.mines;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public final class CounterView extends TextView {

    private final int numberOfDigits;
    private final char[] digits;

    public CounterView(Context context, AttributeSet attribs) {
        super(context, attribs);
        numberOfDigits = 3;
        digits = new char[numberOfDigits];
        setValue(0);
    }

    private Typeface createTypeFace(Context context, String font) {
        if (font == null)
            return Typeface.DEFAULT;
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public void setValue(final int value) {
        for (int i = numberOfDigits - 1, remainder = value; i >= 0; i -= 1, remainder /= 10)
            digits[i] = Character.forDigit(remainder % 10, 10);
        setText(new String(digits));
    }
}
