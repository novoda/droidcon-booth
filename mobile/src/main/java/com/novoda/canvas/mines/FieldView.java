package com.novoda.canvas.mines;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

final class FieldView extends View {

	private final CachingDrawer fieldDrawer;
	private int imageId;
	private boolean isTouched;

	public FieldView(final Context context) {
		this(context, null);
	}

	FieldView(final Context context, final CachingDrawer fieldDrawer) {
		super(context);
		this.fieldDrawer = fieldDrawer;
		setHapticFeedbackEnabled(false);
		setLongClickable(true);
		if (isInEditMode())
			imageId = 10;
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);
		if (fieldDrawer == null)
			return;
		fieldDrawer.draw(imageId, canvas);
		if (isFocused())
			fieldDrawer.drawFocus(canvas);
		if (isTouched)
			fieldDrawer.drawTouched(canvas);
	}

	public void setImageId(final int id) {
		imageId = id;
		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		fieldDrawer.setSize(w, h);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(200, 200);
	}

	public void setTouched(final boolean touched) {
		isTouched = touched;
	}
}
