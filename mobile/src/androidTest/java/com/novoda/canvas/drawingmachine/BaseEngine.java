package com.novoda.canvas.drawingmachine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class BaseEngine implements Engine {

    private final long periodInMillis;

    private ScheduledExecutorService scheduledExecutorService;

    public BaseEngine(long periodInMillis) {
        this.periodInMillis = periodInMillis;
    }

    @Override
    public void start() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(getTaskToPerform(), 0, periodInMillis, TimeUnit.MILLISECONDS);
    }

    protected abstract Runnable getTaskToPerform();

    @Override
    public void stop() {
        if (scheduledExecutorService == null) {
            throw new IllegalStateException("Cannot stop a non started engine");
        }

        scheduledExecutorService.shutdown();
    }
}
