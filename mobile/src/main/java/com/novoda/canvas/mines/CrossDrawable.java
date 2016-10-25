package com.novoda.canvas.mines;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;

public final class CrossDrawable extends ShapeDrawable {

	public CrossDrawable(int color) {
		super(new PathShape(createPath(), 10, 10));
		getPaint().setStyle(Paint.Style.STROKE);
		getPaint().setColor(color);
		getPaint().setStrokeWidth(1);
	}

	private static Path createPath() {
		Path path = new Path();
		path.lineTo(10, 10);
		path.moveTo(0, 10);
		path.lineTo(10, 0);
		return path;
	}
}
