package com.novoda.canvas.audioviz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class AmplitudeView extends View {

    Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    SimpleSoundMeter soundMeter;

    public AmplitudeView(Context context, SimpleSoundMeter soundMeter) {
        super(context);
        bgPaint.setColor(Color.RED);
        fgPaint.setColor(Color.GREEN);
        this.soundMeter = soundMeter;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPaint(bgPaint);

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int amplitude = soundMeter.getAmplitude();

        path.reset();
        path.moveTo(width / 5, soundMeter.getMean() * height / amplitude);
        path.lineTo(width - width / 5, soundMeter.getMean() * height / amplitude);
        path.lineTo(width - width / 5, amplitude);
        path.lineTo(width / 5, amplitude);
        path.close();

        canvas.drawPath(path, fgPaint);

        super.onDraw(canvas);
    }
}
