package com.novoda.canvas.drawingmachine;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.view.TextureView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

class DrawingMachine {

    private final List<Engine> engines = new ArrayList<>();

    public void registerEngine(Engine engine) {
        engines.add(engine);
    }

    public void start(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        DrawingMachineView drawingMachineView = DrawingMachineView.createForDrawingOn(parent);
        drawingMachineView.setSurfaceTextureListener(surfaceTextureListener);

        for (Engine engine : engines) {
            engine.attachTo(drawingMachineView);
        }
    }

    private final TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            start();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            //no-op
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            stop();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            //no-op
        }
    };

    private void start() {
        for (Engine engine : engines) {
            engine.start();
        }
    }

    void stop() {
        for (Engine engine : engines) {
            engine.stop();
        }
    }

}
