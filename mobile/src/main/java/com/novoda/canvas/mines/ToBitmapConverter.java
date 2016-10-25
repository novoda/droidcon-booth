package com.novoda.canvas.mines;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public final class ToBitmapConverter implements DrawableConverter {

	@Override
	public Drawable convert(Drawable original) {
		Rect bounds = original.getBounds();
		Bitmap bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		original.draw(canvas);
		BitmapDrawable photo = new BitmapDrawable(bitmap);
		photo.setBounds(0, 0, bounds.width(), bounds.height());	
		return photo;
	}

}
