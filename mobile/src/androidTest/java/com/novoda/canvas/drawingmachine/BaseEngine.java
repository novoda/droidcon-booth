package com.novoda.canvas.drawingmachine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

abstract class BaseEngine implements Engine {

    private static ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    private final long periodInMillis;

    public BaseEngine(long periodInMillis) {
        this.periodInMillis = periodInMillis;
    }

    @Override
    public void start() {
        EXECUTOR.scheduleAtFixedRate(getTaskToPerform(), 0, periodInMillis, TimeUnit.MILLISECONDS);
    }

    protected abstract Runnable getTaskToPerform();

    @Override
    public void stop() {
        EXECUTOR.shutdown();
    }
}
