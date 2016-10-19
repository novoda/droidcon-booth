package com.novoda.canvas.audioviz;

public interface SoundDataRetriever {

    void start();

    void stop();

    void read(); // poll?
}
