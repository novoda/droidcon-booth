package com.novoda.canvas.mines;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;

public final class TouchDrawable extends Drawable {

	private final Paint paint;
	private final Path circle;
	private final int color;

	public TouchDrawable(int color) {
		this.color = color;
		paint = new Paint();
		paint.setAntiAlias(true);
		circle = createCircle();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		canvas.scale(getBounds().width() / 100f, getBounds().height() / 100f);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
		canvas.drawPath(circle, paint);
		canvas.restore();
	}

	private static Path createCircle() {
		Path path = new Path();
		path.addCircle(50f, 50f, 40, Direction.CW);
		return path;
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
