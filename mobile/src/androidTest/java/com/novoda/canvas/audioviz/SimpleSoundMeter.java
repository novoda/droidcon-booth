package com.novoda.canvas.audioviz;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SimpleSoundMeter implements SoundDataRetriever, SoundDataProvider, Tickable {

    private static final int SAMPLE_RATE = 44100;
    public static final int CORE_POOL_SIZE = 4;

    private Executor readExecutor = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);
    private AudioRecord audioRecord = null;
    private final int minBufferSize;
    private int amplitude = 0;
    private int mean = 0;

    public SimpleSoundMeter() {
        minBufferSize = AudioRecord.getMinBufferSize(
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
        );
    }

    @Override
    public void start() {
        if (audioRecord == null) {
            audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    minBufferSize
            );
        }

        if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRecord.startRecording();
        }
    }

    @Override
    public void stop() {
        audioRecord.stop();
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
