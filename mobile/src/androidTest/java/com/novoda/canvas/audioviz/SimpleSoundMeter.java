package com.novoda.canvas.audioviz;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class SimpleSoundMeter implements SoundDataRetriever, SoundDataProvider {

    private static final int SAMPLE_RATE = 44100;

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
        } else {
            audioRecord.startRecording();
        }
    }

    @Override
    public void stop() {
        audioRecord.stop();
    }

    @Override
    public void read() {
        int sum = 0;
        short[] buffer = new short[minBufferSize];
        audioRecord.read(buffer, 0, minBufferSize);

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
        return mean;
    }
}
