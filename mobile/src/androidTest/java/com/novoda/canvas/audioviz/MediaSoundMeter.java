package com.novoda.canvas.audioviz;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class MediaSoundMeter implements SoundDataRetriever, SoundDataProvider, Tickable {

    public static final String DEV_NULL = "/dev/null";
    private MediaRecorder mediaRecorder = null;
    private int lastSampledAmplitude;

    public MediaSoundMeter() {
        mediaRecorder = new MediaRecorder();
    }

    @Override
    public int getAmplitude() {
        return lastSampledAmplitude;
    }

    @Override
    public int getMean() {
        // not implemented
        return 0;
    }

    @Override
    public void start() {

        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mediaRecorder.setOutputFile(DEV_NULL);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IllegalStateException | IOException e) {
            Log.e(MediaSoundMeter.class.getSimpleName(), "start() failed: " + e.getMessage());
        }

    }

    @Override
    public void stop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
        }
    }

    @Override
    public void read() {
        lastSampledAmplitude = (int) (mediaRecorder.getMaxAmplitude() / 2700.0);
    }

    @Override
    public void tick() {
        read();
    }
}
