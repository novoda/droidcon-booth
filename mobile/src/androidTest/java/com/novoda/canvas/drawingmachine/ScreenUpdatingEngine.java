package com.novoda.canvas.drawingmachine;

import android.graphics.Canvas;

class ScreenUpdatingEngine extends BaseEngine {

    private static final long PERIOD_UPDATE_MILLIS = 17; //60 FPS

    private DrawingMachineView drawingMachineView;

    public ScreenUpdatingEngine() {
        super(PERIOD_UPDATE_MILLIS);
    }

    @Override
    public void attachTo(DrawingMachineView drawingMachineView) {
        this.drawingMachineView = drawingMachineView;
    }

    @Override
    protected Runnable getTaskToPerform() {
        return updateScreenTask;
    }

    private final Runnable updateScreenTask = new Runnable() {
        @Override
        public void run() {
            Canvas canvas = drawingMachineView.lockCanvas(null);
            if (canvas != null) {
                canvas.drawBitmap(drawingMachineView.getBitmap(), 0, 0, null);
            }
            drawingMachineView.unlockCanvasAndPost(canvas);
        }
    };

}
