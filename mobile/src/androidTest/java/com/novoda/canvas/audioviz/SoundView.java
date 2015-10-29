package com.novoda.canvas.audioviz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

class SoundView extends View implements Tickable {

    private static final int WIDTH_DIVIDER = 5;

    private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();

    private SoundDataProvider soundDataProvider;
    private Normalizer normalizer = new Normalizer();

    public SoundView(Context context, SoundDataProvider soundDataProvider) {
        super(context);
        this.soundDataProvider = soundDataProvider;

        backgroundPaint.setColor(Color.RED);
        foregroundPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            normalizer.height = bottom;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPaint(backgroundPaint);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int dividedWidth = width / WIDTH_DIVIDER;
        int value = soundDataProvider.getMean();

        int normalizedValue = normalizer.normalise(value);

        path.reset();
        path.moveTo(dividedWidth, normalizedValue);
        path.lineTo(width - dividedWidth, normalizedValue);
        path.lineTo(width - dividedWidth, height);
        path.lineTo(dividedWidth, height);
        path.close();

        canvas.drawPath(path, foregroundPaint);

        super.onDraw(canvas);
    }

    @Override
    public void tick() {
        invalidate();
    }

    private static class Normalizer {

        int height;
        private int maxValue;

        private void setMaxValue(int value) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        public int normalise(int value) {
            setMaxValue(value);
            return maxValue != 0 ? (int) (value * height * (1 / (float) maxValue)) : 0;
        }
    }
}
