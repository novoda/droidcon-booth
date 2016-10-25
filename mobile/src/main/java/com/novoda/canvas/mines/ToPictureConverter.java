package com.novoda.canvas.mines;

import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;

public final class ToPictureConverter implements DrawableConverter {

	@Override
	public Drawable convert(Drawable original) {
		Rect bounds = original.getBounds();
		Picture picture = new Picture();
		Canvas canvas = picture.beginRecording(bounds.width(), bounds.height());
		original.draw(canvas);
		picture.endRecording();
		PictureDrawable photo = new PictureDrawable(picture);
		photo.setBounds(0, 0, bounds.width(), bounds.height());
		return photo;
	}

}
