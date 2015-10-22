package com.novoda.canvas;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;

public class ParallaxActivityTest extends NovodaActivityTest {

    private static final int SQUARES_COUNT = 10;
    public static final int DURATION = 20000;

    @Override
    public void startTestFor(Activity activity) {
        View parent = activity.findViewById(android.R.id.content);

        List<ImageView> smallSquares = createSquares(activity, true, parent);
        List<ImageView> bigSquares = createSquares(activity, false, parent);

        addSquares((ViewGroup) parent, smallSquares);
        addSquares((ViewGroup) parent, bigSquares);

        animateSquares(smallSquares, true, parent);
        animateSquares(bigSquares, false, parent);

    }

    private List<ImageView> createSquares(Activity activity, boolean small, View parent) {
        List<ImageView> squares = new ArrayList<>(SQUARES_COUNT);

        int color = getColorFor(small);
        final int size = getSizeFor(activity, small);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);

        for (int i = 0; i < SQUARES_COUNT; i++) {
            ImageView square = new ImageView(activity);

            square.setImageDrawable(drawable);
            square.setLayoutParams(new ActionBar.LayoutParams(size, size));

            square.setY(parent.getHeight() / 5 + size);
            square.setX(parent.getWidth() + size * i * 1.5f);
            squares.add(square);
        }

        return squares;
    }

    private int getSizeFor(Activity activity, boolean small) {
        final int base = small ? 100 : 150;
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                base,
                activity.getResources().getDisplayMetrics()
        );
    }

    private int getColorFor(boolean small) {
        return small ? Color.parseColor("#6633aa") : Color.parseColor("#550099");
    }

    private void addSquares(ViewGroup parent, List<ImageView> smallSquares) {
        for (ImageView smallSquare : smallSquares) {
            parent.addView(smallSquare);
        }
    }

    private void animateSquares(List<ImageView> squares, boolean small, View parent) {
        for (ImageView square : squares) {
            moveSquare(square, small, parent);
        }
    }

    private void moveSquare(ImageView square, boolean small, View parent) {
        ObjectAnimator movement = ObjectAnimator
                .ofFloat(square, "x", square.getX() - parent.getWidth() * getMultiplierFor(small));
        movement.setDuration(DURATION);
        movement.setRepeatCount(ObjectAnimator.INFINITE);
        movement.setRepeatMode(ObjectAnimator.RESTART);
        movement.setInterpolator(new LinearInterpolator());
        movement.start();
    }

    private int getMultiplierFor(boolean small) {
        return small ? 6 : 12;
    }
}
