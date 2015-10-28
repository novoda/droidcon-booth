package com.novoda.canvas;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.HORIZONTAL;

public class FiftyShadesOfGrayTest extends NovodaActivityTest {

    private static final int MAX_GRAY = 190;
    private static final int MIN_GRAY = 70;
    private static final int SEGMENTS_IN_LONG_SIDE = 10;
    private static final int SEGMENTS_IN_SHORT_SIDE = 5;
    private static final int NUM_VIEWS = SEGMENTS_IN_LONG_SIDE * SEGMENTS_IN_SHORT_SIDE;
    private static final float GRAY_INCREMENT = MAX_GRAY - MIN_GRAY / NUM_VIEWS;
    private static final float HALF_PIXEL = 0.5f;

    private final List<View> views = new ArrayList<>(NUM_VIEWS);

    private int itemViewWidth;
    private int itemViewHeight;

    @Override
    public void startTestFor(Activity activity) {
        determineItemViewDimensions();
        createAndAddItemViews();
        setInitialBackgroundColors();

        int numberOfChanges = NovodaActivityTest.TIME_LIMIT_FOR_TEST_IN_SECONDS * 1000 / 15;
        for (int i = 0; i < numberOfChanges; i++) {
            final int finalI = i;
            getParent().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setBackgroundColors(finalI);
                }
            }, i * 15);
        }
    }

    private void determineItemViewDimensions() {
        ViewGroup parent = getParent();
        int totalWidth = parent.getMeasuredWidth();
        int totalHeight = parent.getMeasuredHeight();

        if (deviceIsInLandscape()) {
            itemViewWidth = (int) (totalWidth / SEGMENTS_IN_LONG_SIDE + HALF_PIXEL);
            itemViewHeight = (int) (totalHeight / SEGMENTS_IN_SHORT_SIDE + HALF_PIXEL);
        } else {
            itemViewWidth = (int) (totalWidth / SEGMENTS_IN_SHORT_SIDE + HALF_PIXEL);
            itemViewHeight = (int) (totalHeight / SEGMENTS_IN_LONG_SIDE + HALF_PIXEL);
        }
    }

    private boolean deviceIsInLandscape() {
        int orientation = getParent().getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void createAndAddItemViews() {
        LinearLayout linearLayout = new LinearLayout(getParent().getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

        for (int i = 0; i < numberOfRows(); i++) {
            LinearLayout rowOfSquares = getRowOfSquares();
            linearLayout.addView(rowOfSquares);
        }

        getParent().addView(linearLayout);

    }

    private void setInitialBackgroundColors() {
        getParent().setBackgroundColor(Color.BLACK);
        setBackgroundColors(0);
    }

    private void setBackgroundColors(int step) {
        for (int i = 0; i < views.size(); i++) {
            views.get(i).setBackgroundColor(grayAtStep(i, step));
        }
    }

    private int numberOfColumns() {
        return deviceIsInLandscape() ? SEGMENTS_IN_LONG_SIDE : SEGMENTS_IN_SHORT_SIDE;
    }

    private int numberOfRows() {
        return deviceIsInLandscape() ? SEGMENTS_IN_SHORT_SIDE : SEGMENTS_IN_LONG_SIDE;
    }

    @ColorInt
    private static int grayAtStep(int itemView, int step) {
        int i = (itemView + step) % NUM_VIEWS;

        float grayStep = i * GRAY_INCREMENT;
        int accumulatedGray = (int) (MIN_GRAY + grayStep);
        return Color.rgb(accumulatedGray, accumulatedGray, accumulatedGray);
    }

    private LinearLayout getRowOfSquares() {
        LinearLayout linearLayout = new LinearLayout(getParent().getContext());
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));

        for (int i = 0; i < numberOfColumns(); i++) {
            View view = new View(getParent().getContext());
            views.add(view);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(itemViewWidth, itemViewHeight);
            linearLayout.addView(view, layoutParams);
        }
        return linearLayout;
    }

}
