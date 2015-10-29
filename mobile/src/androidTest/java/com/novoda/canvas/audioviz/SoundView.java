package com.novoda.canvas.audioviz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

class SoundView extends View implements Tickable {

    private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();

    private SoundDataProvider soundDataProvider;
    private Normaliser normaliser = new Normaliser();

    private int amplitude;

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
            normaliser.height = bottom;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPaint(backgroundPaint);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int newAmplitude = soundDataProvider.getAmplitude();

        normaliser.setMaxAmplitude(newAmplitude);
        amplitude = normaliser.normalise(newAmplitude);

        L.ui(this, newAmplitude);
        L.ui(this, amplitude);

        if (amplitude != 0) {

            path.reset();
            path.moveTo(width / 5, 0);
            path.lineTo(width - width / 5, 0);
            path.lineTo(width - width / 5, amplitude);
            path.lineTo(width / 5, amplitude);
            path.close();

            canvas.drawPath(path, foregroundPaint);
        }

        super.onDraw(canvas);
    }

    @Override
    public void tick() {
        invalidate();
    }

    private static class Normaliser {

        int maxAmplitude;
        int height;

        public void setMaxAmplitude(int value) {
            if (value > maxAmplitude) {
                maxAmplitude = value;
            }
        }

        public int normalise(int value) {
            return maxAmplitude != 0 ? value * height * (1 / maxAmplitude) : 0;
        }
    }
}
