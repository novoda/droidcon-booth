package com.novoda.canvas.mines;

import android.graphics.*;
import android.graphics.Paint.Cap;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public final class BombDrawable extends Drawable {

	private final Paint paint;
	private final Path cross;
	private final Path itsX;

	public BombDrawable(int innerColor, int outerColor) {
		super();
		paint = new Paint();
		paint.setShader(new RadialGradient(60, 60, 40, innerColor, outerColor, TileMode.CLAMP));
		paint.setColor(innerColor);
		paint.setAntiAlias(true);
		paint.setStrokeCap(Cap.ROUND);
		cross = createCross();
		itsX = createX(28);
	}

	private static Path createCross() {
		Path path = new Path();
		path.moveTo(50, 15);
		path.lineTo(50, 85);
		path.moveTo(15, 50);
		path.lineTo(85, 50);
		return path;
	}

	private Path createX(int dist) {
		Path path = new Path();
		path.moveTo(dist, dist);
		path.lineTo(100-dist, 100-dist);
		path.moveTo(dist, 100-dist);
		path.lineTo(100-dist, dist);
		return path;	
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		int width = getBounds().width();
		int height = getBounds().height();
//		int min = Math.min(width, height);
		canvas.scale(width/100f, height/100f);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5f);
		canvas.drawPath(cross, paint);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3.5f);
		canvas.drawPath(itsX, paint);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawOval(new RectF(30,30,70,70), paint);
		canvas.restore();
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
