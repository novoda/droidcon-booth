package com.novoda.canvas;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.canvas.base.RandomColorFactory;

import static com.novoda.canvas.NovodaActivity.RANDOM;

public class TilesSpiralActivityTest extends NovodaActivityTest {

    private static final int TILES_COUNT = 10;
    private static final int MIN_SIZE = 100;
    private static final int FADE_DURATION = 1000;

    private final RandomColorFactory randomColorFactory = new RandomColorFactory(RANDOM);
    private Rect availableRect;

    @Override
    public void startTestFor(Activity activity) {
        ViewGroup parent = getParent(activity);
        availableRect = new Rect(parent.getLeft(), parent.getTop(), parent.getRight(), parent.getBottom());

        for (int i = 0; i < TILES_COUNT && screenNotFullAlready(); i++) {
            View tile = createTile(activity);
            parent.addView(tile);
            revealTile(tile);
        }

    }

    private boolean screenNotFullAlready() {
        return availableRect.width() > 0 && availableRect.height() > 0;
    }

    private View createTile(Context context) {
        int color = randomColorFactory.getColor();

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);

        int tileTop = availableRect.top;
        int tileLeft = availableRect.left;
        int tileWidth = getTileWidth();
        int tileHeight = availableRect.height();

        View square = new View(context);
        square.setBackground(drawable);
        square.setLayoutParams(new LinearLayout.LayoutParams(tileWidth, tileHeight));

        square.setY(tileTop);
        square.setX(tileLeft);

        availableRect.set(tileLeft + tileWidth, availableRect.top, availableRect.right, availableRect.bottom);

        return square;
    }

    private int getTileWidth() {
        return Math.min(
                availableRect.width(),
                Math.max(
                        MIN_SIZE,
                        RANDOM.nextInt(availableRect.width() / 2)
                )
        );
    }

    private int getTileHeight() {
        return Math.min(
                availableRect.height(),
                Math.max(
                        MIN_SIZE,
                        RANDOM.nextInt(availableRect.height() / 2)
                )
        );
    }

    private void revealTile(View square) {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(square, "alpha", 0f, 1f);
        alphaAnimator.setDuration(FADE_DURATION);
        alphaAnimator.start();
    }
}
