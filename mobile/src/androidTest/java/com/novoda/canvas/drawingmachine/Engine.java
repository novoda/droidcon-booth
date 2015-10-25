package com.novoda.canvas.drawingmachine;

public interface Engine {

    void start();
    void stop();
    void attachTo(DrawingMachineView drawingMachineView);
}
