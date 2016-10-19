package com.novoda.canvas.mines;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import de.devisnik.mine.Point;

public abstract class CachingDrawer {

	private final SparseArray<Drawable> drawables = new SparseArray<Drawable>();
	private final DrawableStore store = new DrawableStore(new ToBitmapConverter());
	private Point size;

	public CachingDrawer(final int width, final int height) {
		size = new Point(width, height);
	}

	public Point getSize() {
		return size;
	}

	private Drawable get(final int id) {
		Drawable stored = store.get(id);
		if (stored != null)
			return stored;
		stored = store.put(id, prepareDrawable(id));
		if (stored == null)
			return drawables.get(id);
		return stored;
	}

	private Drawable prepareDrawable(final int id) {
		Drawable drawable = drawables.get(id);
		drawable.setBounds(0, 0, size.x, size.y);
		return drawable;
	}

	public void draw(final int id, final Canvas canvas) {
		get(id).draw(canvas);
	}

	public abstract void drawFocus(final Canvas canvas);

	public abstract void drawTouched(final Canvas canvas);

	protected void register(final int id, final Drawable drawable) {
		drawables.put(id, drawable);
	}

	public void setSize(final int width, final int height) {
		if (size.x == width && size.y == height)
			return;
		size = new Point(width, height);
		store.clear();
	}
}
