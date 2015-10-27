package com.novoda.canvas.drawingmachine.noise;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

class NoisePainter {

    private final SimplexNoise simplexNoise;
    private final Paint eraserPaint, circlePaint;
    private final static int MAX_CIRCLE_RADIUS = 15;
    private final static int MAX_SHAPES = 20;
    private double time = 0;

    public static NoisePainter newInstance() {
        return new NoisePainter();
    }

    private NoisePainter() {
        eraserPaint = new Paint();
        circlePaint = new Paint();

        eraserPaint.setColor(Color.parseColor("#FEFAE0"));
        circlePaint.setColor(Color.parseColor("#0FACE0"));

        this.simplexNoise = new SimplexNoise();
    }

    void paintNoiseOn(@NonNull Canvas canvas) {
        int heightFactorial = canvas.getHeight() / MAX_SHAPES;
        int widthFactorial = canvas.getWidth() / MAX_SHAPES;
        time += 0.1d;

        for (int i = 1; i < MAX_SHAPES; i++) {
            for (int k = 1; k < MAX_SHAPES; k++) {
                double noiseValue = (simplexNoise.eval(i, k, time) + 1) / 2;
                int calculatedRadius = (int) (noiseValue * MAX_CIRCLE_RADIUS);

                canvas.drawCircle(widthFactorial * i, heightFactorial * k, MAX_CIRCLE_RADIUS, eraserPaint);
                canvas.drawCircle(widthFactorial * i, heightFactorial * k, calculatedRadius, circlePaint);
            }
        }
    }
}
