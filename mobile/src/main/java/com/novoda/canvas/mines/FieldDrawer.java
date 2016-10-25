package com.novoda.canvas.mines;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.LayerDrawable;

public class FieldDrawer extends CachingDrawer {

	private static Drawable createLayer(final Drawable... drawables) {
		return new LayerDrawable(drawables);
	}

	private final SimpleColorProvider colorProvider;

	public FieldDrawer(final SimpleColorProvider colorProvider, final Typeface face) {
		super(10, 10);
		this.colorProvider = colorProvider;
		initDrawables(face);
	}

	private int getColor(final SimpleColorProvider.ColorId id) {
		return colorProvider.getValue(id);
	}

	@Override
	public void drawFocus(final Canvas canvas) {
		draw(14, canvas);
	}

	private void initDrawables(final Typeface face) {
		GradientDrawable button_background = new GradientDrawable(Orientation.TL_BR, new int[] {
				getColor(SimpleColorProvider.ColorId.BUTTON_UP_LEFT), getColor(SimpleColorProvider.ColorId.BUTTON_LOW_RIGHT) });
		BombDrawable bomb = new BombDrawable(getColor(SimpleColorProvider.ColorId.BOMB_BODY), getColor(SimpleColorProvider.ColorId.BOMB_SPIKES));
		Drawable button = createLayer(button_background, new ShadowDrawable());
		FlagDrawable flag = new FlagDrawable(getColor(SimpleColorProvider.ColorId.FLAG_LIGHT), getColor(SimpleColorProvider.ColorId.FLAG_DARK),
				getColor(SimpleColorProvider.ColorId.FLAGPOLE));
		Drawable focus = new ColorDrawable(getColor(SimpleColorProvider.ColorId.FOCUS));
		Drawable touched = new TouchDrawable(getColor(SimpleColorProvider.ColorId.TOUCH));

		register(0, new ColorDrawable(Color.TRANSPARENT));
		register(1, createNumber(1, face, SimpleColorProvider.ColorId.NO_1));
		register(2, createNumber(2, face, SimpleColorProvider.ColorId.NO_2));
		register(3, createNumber(3, face, SimpleColorProvider.ColorId.NO_3));
		register(4, createNumber(4, face, SimpleColorProvider.ColorId.NO_4));
		register(5, createNumber(5, face, SimpleColorProvider.ColorId.NO_5));
		register(6, createNumber(6, face, SimpleColorProvider.ColorId.NO_6));
		register(7, createNumber(7, face, SimpleColorProvider.ColorId.NO_7));
		register(8, createNumber(8, face, SimpleColorProvider.ColorId.NO_8));
		register(9, createLayer(button, bomb));
		register(10, button);
		register(11, createLayer(button, flag));
		register(12, createLayer(button, flag, new CrossDrawable(getColor(SimpleColorProvider.ColorId.ERROR))));
		register(13, createLayer(new ColorDrawable(getColor(SimpleColorProvider.ColorId.ERROR)), bomb));
		register(14, focus);
		register(15, touched);
	}

	private NumberDrawable createNumber(final int number, final Typeface face, final SimpleColorProvider.ColorId colorId) {
		return new NumberDrawable(number, getColor(colorId), face);
	}

	@Override
	public void drawTouched(final Canvas canvas) {
		draw(15, canvas);
	}
}
