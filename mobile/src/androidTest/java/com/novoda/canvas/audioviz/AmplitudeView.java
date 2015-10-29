package com.novoda.canvas.audioviz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

class AmplitudeView extends View implements Tickable {

    private Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();

    private SoundDataProvider soundDataProvider;

    public AmplitudeView(Context context, SoundDataProvider soundDataProvider) {
        super(context);
        backgroundPaint.setColor(Color.RED);
        foregroundPaint.setColor(Color.GREEN);
        this.soundDataProvider = soundDataProvider;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPaint(backgroundPaint);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int amplitude = soundDataProvider.getAmplitude();

        if (amplitude != 0) {
            path.reset();
            path.moveTo(width / 5, soundDataProvider.getMean() * height / amplitude);
            path.lineTo(width - width / 5, soundDataProvider.getMean() * height / amplitude);
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
}
