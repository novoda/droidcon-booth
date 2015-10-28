package com.novoda.canvas.drawingmachine.spiral;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class SpiralPainter {

    private static final double RADIUS_INCREMENT = 0.225;
    private static final double ANGLE_INCREMENT = 0.005;
    private static final int LINE_ALPHA = 30;

    private final Paint linePaint;
    private final List<Centre> centres;

    private float angle;
    private float radius;

    public static SpiralPainter newInstance(@ColorInt int colour) {
        return new SpiralPainter(createCentres(), createLinePaint(colour));
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
    private static Paint createLinePaint(@ColorInt int colour) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colour);
        paint.setAlpha(LINE_ALPHA);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    private SpiralPainter(List<Centre> centres, Paint linePaint) {
        this.centres = centres;
        this.linePaint = linePaint;
    }

    void paintLinesOn(@NonNull Canvas canvas) {
        for (int i = 0; i < centres.size(); i++) {
            Centre start = centres.get(i);
            Centre end = centres.get((i + 1) % centres.size());
            float startX = (float) (start.getXFor(canvas) + radius * Math.cos(angle));
            float startY = (float) (start.getYFor(canvas) + radius * Math.sin(angle));
            float endX = (float) (end.getXFor(canvas) + radius * Math.sin(-angle));
            float endY = (float) (end.getYFor(canvas) + radius * Math.cos(-angle));

            canvas.drawLine(startX, startY, endX, endY, linePaint);
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

        private float getXFor(@NonNull Canvas canvas) {
            return canvas.getWidth() * relativeX;
        }

        private float getYFor(@NonNull Canvas canvas) {
            return canvas.getHeight() * relativeY;
        }

    }

}
