package com.novoda.canvas.audioviz;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class L {

    public static void d(String t, String m) {
        Log.d(t, m);
    }

    public static void d(Object o, Object m) {
        d(o.getClass().getSimpleName(), "(" + m.getClass().getSimpleName() + ") " + m.toString());
    }

    public static void ui(final Object o, final Object m) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        d(o, m);
                    }
                }
        );
    }
}
