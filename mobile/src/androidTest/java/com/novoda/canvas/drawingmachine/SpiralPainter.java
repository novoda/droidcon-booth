package com.novoda.canvas.drawingmachine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class SpiralPainter {

    private static final Centre ORIGIN = new Centre(0.5f, 0.5f);
    private static final double RADIUS_INCREMENT = 0.125;
    private static final double ANGLE_INCREMENT = 0.005;
    private static final int LINE_ALPHA = 10;

    private final Paint linePaint;
    private final Paint pointPaint;
    private final List<Centre> centres;

    private float angle;
    private float radius;

    public static SpiralPainter newInstance() {
        return new SpiralPainter(createCentres(), createLinePaint(), createPointPaint());
    }

    @NonNull
    private static List<Centre> createCentres() {
        List<Centre> centres = new ArrayList<>();
        centres.add(new Centre(0.5f, 0.15f));
        centres.add(new Centre(0.15f, 0.85f));
        centres.add(new Centre(0.85f, 0.85f));
        return centres;
    }

    @NonNull
    private static Paint createLinePaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setAlpha(LINE_ALPHA);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    @NonNull
    private static Paint createPointPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    private SpiralPainter(List<Centre> centres, Paint linePaint, Paint pointPaint) {
        this.centres = centres;
        this.linePaint = linePaint;
        this.pointPaint = pointPaint;
    }

    void paintLinesOn(@NonNull Canvas canvas) {
        float startX = ORIGIN.getXFor(canvas);
        float startY = ORIGIN.getYFor(canvas);
        for (Centre otherCentre : centres) {
            float endX = (float) (otherCentre.getXFor(canvas) + radius * Math.sin(angle));
            float endY = (float) (otherCentre.getYFor(canvas) + radius * Math.cos(angle));
            canvas.drawLine(startX, startY, endX, endY, linePaint);
            canvas.drawPoint(endX, endY, pointPaint);
        }
        updateCoordinates();
    }

    private void updateCoordinates() {
        radius += RADIUS_INCREMENT;
        angle += ANGLE_INCREMENT;
    }

    private static class Centre {

        private final float relativeX;
        private final float relativeY;

        private Centre(float relativeX, float relativeY) {
            this.relativeX = relativeX;
            this.relativeY = relativeY;
        }

        private float getXFor(Canvas canvas) {
            return canvas.getWidth() * relativeX;
        }

        private float getYFor(Canvas canvas) {
            return canvas.getHeight() * relativeY;
        }

    }

}
