package com.novoda.canvas.drawingmachine.spiral;

import android.graphics.Canvas;

import com.novoda.canvas.drawingmachine.BaseEngine;
import com.novoda.canvas.drawingmachine.DrawingMachineView;

class SpiralPaintingEngine extends BaseEngine {

    private static final long PERIOD_PAINT_MILLIS = 1;

    private DrawingMachineView drawingMachineView;
    private final SpiralPainter painter;

    public static SpiralPaintingEngine newInstance() {
        return new SpiralPaintingEngine(SpiralPainter.newInstance());
    }

    private SpiralPaintingEngine(SpiralPainter painter) {
        super(PERIOD_PAINT_MILLIS);
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
}
