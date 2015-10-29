package com.novoda.canvas.audioviz;

import android.media.MediaRecorder;

import java.io.IOException;

public class MediaSoundMeter implements SoundDataRetriever, SoundDataProvider, Tickable {

    private MediaRecorder mediaRecorder = null;
    private int lastSampledAmplitude;

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
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
            try {
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                mediaRecorder.setOutputFile("/dev/null");
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException | IOException e) {
                L.d(this, "start() failed: " + e.getMessage());
            }
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
