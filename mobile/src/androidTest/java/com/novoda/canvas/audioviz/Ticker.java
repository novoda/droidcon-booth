package com.novoda.canvas.audioviz;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public interface Ticker {

    void tick(); // TODO FIXME maybe remove this as the ticker should tick on its own

    void start();

    void stop();

    void addTickable(Tickable tickable);

    void removeTickable(Tickable tickable);

    List<Tickable> getTickables();

    class TickerImpl implements Ticker {

        public static final int DEFAULT_INTERVAL = 50; // ms

        int interval;
        Handler handler;
        Runnable runnable; // TODO maybe implement StoppableRunnable?
        List<Tickable> tickables;

        public TickerImpl() {
            this(DEFAULT_INTERVAL, new Handler(), new ArrayList<Tickable>());
        }

        public TickerImpl(Handler handler, int interval) {
            this(interval, handler, new ArrayList<Tickable>());
        }

        private TickerImpl(final int interval, final Handler handler, List<Tickable> tickables) {
            this.interval = interval;
            this.handler = handler;
            this.tickables = tickables;
            this.runnable = new Runnable() {
                @Override
                public void run() {
                    tick();
                    handler.postDelayed(this, interval);
                }
            };
        }

        @Override
        public void tick() {
            for (Tickable tickable : tickables) {
                tickable.tick();
            }
        }

        @Override
        public void start() {
            handler.post(runnable);
        }

        @Override
        public void stop() {
            handler.removeCallbacks(runnable);
        }

        @Override
        public void addTickable(Tickable tickable) {
            tickables.add(tickable);
        }

        @Override
        public void removeTickable(Tickable tickable) {
            tickables.remove(tickable);
        }

        @Override
        public List<Tickable> getTickables() {
            return tickables;
        }
    }
}
