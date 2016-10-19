package com.novoda.canvas.mines;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;

public final class DrawableStore {

	private final SparseArray<Drawable> cache = new SparseArray<Drawable>();
	private final DrawableConverter converter;

	public DrawableStore(final DrawableConverter converter) {
		this.converter = converter;
	}

	public Drawable get(final int id) {
		return cache.get(id);
	}

	public Drawable put(final int id, final Drawable imageToCache) {
		Drawable converted = converter.convert(imageToCache);
		cache.put(id, converted);
		return converted;
	}

	public boolean hasDrawableForId(final int id) {
		return cache.get(id) != null;
	}

	public void clear() {
		cache.clear();
	}

}
