package com.novoda.canvas.audioviz;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SimpleSoundMeter implements SoundDataRetriever, SoundDataProvider, Tickable {

    private static final int SAMPLE_RATE = 44100;
    public static final int CORE_POOL_SIZE = 4;

    private Executor readExecutor;
    private AudioRecord audioRecord = null;
    private final int minBufferSize;
    private int amplitude = 0;
    private int mean = 0;

    public SimpleSoundMeter() {
        this(new ScheduledThreadPoolExecutor(CORE_POOL_SIZE));
    }

    public SimpleSoundMeter(Executor readExecutor) {
        this(
                readExecutor,
                AudioRecord.getMinBufferSize(
                        SAMPLE_RATE,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT
                )
        );
    }

    private SimpleSoundMeter(Executor readExecutor, int minBufferSize) {
        this.minBufferSize = minBufferSize;
        this.readExecutor = readExecutor;
    }

    @Override
    public void start() {
        if (audioRecord == null) {
            init();
        }

        try {
            audioRecord.startRecording();
        } catch (IllegalStateException e) {
            Log.e(SimpleSoundMeter.class.getSimpleName(), "Could not start: " + e.getMessage());
        }
    }

    private void init() {
        try {
            audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    minBufferSize
            );
        } catch (IllegalArgumentException e) {
            Log.e(SimpleSoundMeter.class.getSimpleName(), "Could not start: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRecord.stop();
        }
    }

    @Override
    public void read() {
        readExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        blockingRead();
                    }
                }
        );
    }

    private void blockingRead() {
        int sum = 0;
        short[] buffer = new short[minBufferSize];

        if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRecord.read(buffer, 0, minBufferSize);
        }

        for (short sample : buffer) {
            sum += sample;
            if (Math.abs(sample) > amplitude) {
                amplitude = Math.abs(sample);
            }
        }

        mean = sum / minBufferSize;
    }

    @Override
    public int getAmplitude() {
        return amplitude;
    }

    @Override
    public int getMean() {
        return Math.abs(mean);
    }

    @Override
    public void tick() {
        read();
    }
}
