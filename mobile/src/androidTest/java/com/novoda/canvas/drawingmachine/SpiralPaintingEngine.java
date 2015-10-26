package com.novoda.canvas.drawingmachine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.ColorInt;

class SpiralPaintingEngine extends BaseEngine {

    private DrawingMachineView drawingMachineView;
    private final SpiralPainter painter;

    private SpiralPaintingEngine(long periodInMillis, SpiralPainter painter) {
        super(periodInMillis);
        this.painter = painter;
    }

    @Override
    public void attachTo(DrawingMachineView drawingMachineView) {
        this.drawingMachineView = drawingMachineView;
    }

    @Override
    protected Runnable getTaskToPerform() {
        return paintBitmapTask;
    }

    private final Runnable paintBitmapTask = new Runnable() {
        @Override
        public void run() {
            Canvas canvas = drawingMachineView.getCanvas();
            painter.paintLinesOn(canvas);
        }
    };

    static class Builder {
        private static final long DEFAULT_PERIOD_IN_MILLIS = 1;
        private static final int DEFAULT_COLOUR = Color.BLACK;

        private long periodInMillis = DEFAULT_PERIOD_IN_MILLIS;
        private @ColorInt int colour = DEFAULT_COLOUR;

        public SpiralPaintingEngine build() {
            return new SpiralPaintingEngine(periodInMillis, SpiralPainter.newInstance(colour));
        }

        public Builder withPeriod(long periodInMillis) {
            this.periodInMillis = periodInMillis;
            return this;
        }

        public Builder withColour(@ColorInt int colour) {
            this.colour = colour;
            return this;
        }
    }
}
