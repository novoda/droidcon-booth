package com.novoda.canvas.mines;

import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public final class FlagDrawable extends Drawable {

	private final Paint paint;
	private final Path flag;
	private final Path post;
	private final int flagColor;
	private final int poleColor;
	private final int flagColorDark;

	public FlagDrawable(int flagColorLight, int flagColorDark, int poleColor) {
		this.flagColor = flagColorLight;
		this.flagColorDark = flagColorDark;
		this.poleColor = poleColor;
		paint = new Paint();
		paint.setAntiAlias(true);
		flag = createFlagPath();
		post = createPath();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		canvas.scale(getBounds().width() / 100f, getBounds().height() / 100f);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(flagColor);
		paint.setShader(new LinearGradient(57, 45, 25, 45, flagColorDark, flagColor, TileMode.CLAMP));
		canvas.drawPath(flag, paint);
		paint.setStyle(Paint.Style.STROKE);
		paint.setShader(null);
		paint.setColor(poleColor);
		paint.setStrokeWidth(5);
		canvas.drawPath(post, paint);
		canvas.restore();
	}

	private static Path createFlagPath() {
		Path path = new Path();
		path.moveTo(57, 45);
		path.lineTo(57, 20);
		path.lineTo(25, 20);
		path.lineTo(25, 45);
		path.lineTo(55, 45);
		return path;
	}

	private static Path createPath() {
		Path path = new Path();
		path.moveTo(40, 82);
		path.quadTo(55, 70, 70, 82);
		path.close();
		path.moveTo(55, 75);
		path.lineTo(55, 45);
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
