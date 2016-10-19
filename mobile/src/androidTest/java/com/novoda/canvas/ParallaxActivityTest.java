package com.novoda.canvas;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.novoda.canvas.R;
import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;

public class ParallaxActivityTest extends NovodaActivityTest {

    private static final int SQUARES_COUNT = 40;
    private static final int DURATION_MS = 20000;

    @Override
    public void startTestFor(Activity activity) {
        View parent = getParent();

        List<View> smallSquares = createSquares(activity, true, parent);
        List<View> bigSquares = createSquares(activity, false, parent);

        addSquares((ViewGroup) parent, smallSquares);
        addSquares((ViewGroup) parent, bigSquares);

        animateSquares(smallSquares, true, parent);
        animateSquares(bigSquares, false, parent);

    }

    private List<View> createSquares(Context context, boolean small, View parent) {
        List<View> squares = new ArrayList<>(SQUARES_COUNT);

        int color = getColorFor(context, small);
        final int size = getSizeFor(context, small);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);

        for (int i = 0; i < SQUARES_COUNT; i++) {
            View square = new View(context);

            square.setBackground(drawable);
            square.setLayoutParams(new ActionBar.LayoutParams(size, size));

            square.setY(parent.getHeight() / 5 + size);
            square.setX(parent.getWidth() + size * i * 1.5f);
            squares.add(square);
        }

        return squares;
    }

    private int getSizeFor(Context context, boolean small) {
        int dimenId = small ? R.dimen.small_square_size : R.dimen.big_square_size;
        return context.getResources().getDimensionPixelSize(dimenId);
    }

    private int getColorFor(Context context, boolean small) {
        int colorId = small ? R.color.small_square_bg : R.color.big_square_bg;
        return context.getResources().getColor(colorId);
    }

    private void addSquares(ViewGroup parent, List<View> smallSquares) {
        for (View smallSquare : smallSquares) {
            parent.addView(smallSquare);
        }
    }

    private void animateSquares(List<View> squares, boolean small, View parent) {
        for (View square : squares) {
            moveSquare(square, small, parent);
        }
    }

    private void moveSquare(View square, boolean small, View parent) {
        ObjectAnimator movement = ObjectAnimator
                .ofFloat(square, "translationX", square.getX() - parent.getWidth() * getMultiplierFor(small));
        movement.setDuration(DURATION_MS);
        movement.setRepeatCount(ObjectAnimator.INFINITE);
        movement.setRepeatMode(ObjectAnimator.RESTART);
        movement.setInterpolator(new LinearInterpolator());
        movement.start();
    }

    private int getMultiplierFor(boolean small) {
        return small ? 6 : 12;
    }
}
