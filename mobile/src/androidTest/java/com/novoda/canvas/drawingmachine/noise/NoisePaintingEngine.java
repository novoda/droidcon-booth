package com.novoda.canvas.drawingmachine.noise;

import android.graphics.Canvas;

import com.novoda.canvas.drawingmachine.BaseEngine;
import com.novoda.canvas.drawingmachine.DrawingMachineView;

class NoisePaintingEngine extends BaseEngine {

    private static final long PERIOD_PAINT_MILLIS = 17;

    private DrawingMachineView drawingMachineView;
    private final NoisePainter painter;

    public static NoisePaintingEngine newInstance() {
        return new NoisePaintingEngine(NoisePainter.newInstance());
    }

    private NoisePaintingEngine(NoisePainter painter) {
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
            painter.paintNoiseOn(canvas);
        }
    };
}
