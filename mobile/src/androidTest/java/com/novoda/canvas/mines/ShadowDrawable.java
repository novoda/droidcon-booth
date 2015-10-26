package com.novoda.canvas.mines;

import android.graphics.*;
import android.graphics.drawable.Drawable;

public final class ShadowDrawable extends Drawable {

	private static final int COLOR_DARK = 0x60000000;
	private static final int COLOR_LIGHT = 0x60ffffff;
	private static final int STROKE_WIDTH = 1;
	private final Paint paint;

	private static Path createDarkPath(int width, int height) {
		final Path path = new Path();
		path.moveTo(0, height);
		path.lineTo(width, height);
		path.lineTo(width, 0);
		return path;
	}

	private static Path createLightPath(int width, int height) {
		final Path path = new Path();
		path.moveTo(0, height);
		path.lineTo(0, 0);
		path.lineTo(width, 0);
		return path;
	}

	public ShadowDrawable() {
		paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(STROKE_WIDTH);
	}

	@Override
	public void draw(Canvas canvas) {
		Rect bounds = getBounds();
		paint.setColor(COLOR_LIGHT);
		int width = bounds.width() - STROKE_WIDTH;
		int height = bounds.height() - STROKE_WIDTH;
		canvas.drawPath(createLightPath(width, height), paint);
		paint.setColor(COLOR_DARK);
		canvas.drawPath(createDarkPath(width, height), paint);
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
