package com.novoda.canvas.mines;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public final class NumberDrawable extends Drawable {

	private final String digit;
	private final Paint paint;

	public NumberDrawable(int digit, int color) {
		this(digit, color, null);
	}

	public NumberDrawable(int digit, int color, Typeface face) {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
		paint.setTextAlign(Align.CENTER);
		paint.setTypeface(face);
		this.digit = Integer.toString(digit);
	}

	@Override
	public void draw(Canvas canvas) {
		paint.setTextSize(getBounds().height());
		canvas.drawText(digit, getBounds().exactCenterX(),
                getBounds().bottom
                        - (getBounds().bottom + paint.ascent() + paint
                        .descent()) / 2, paint);
	}

	@Override
	public int getOpacity() {
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}
}
