package com.novoda.canvas.audioviz;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.novoda.canvas.base.NovodaActivityTest;

public class SoundViewTest extends NovodaActivityTest {

    private SimpleSoundMeter simpleSoundMeter;
    private SoundDataRetriever soundDataRetriever;
    private SoundDataProvider soundDataProvider;

    private Ticker ticker;

    public void setupDependencies() {
        simpleSoundMeter = new SimpleSoundMeter();
        soundDataRetriever = simpleSoundMeter;
        soundDataProvider = simpleSoundMeter;
        ticker = new Ticker.TickerImpl();

        soundDataRetriever.start();
        ticker.start();
    }

    @Override
    public void startTestFor(Activity activity) {

        setupDependencies();

        final ViewGroup parent = getParent();
        final Context context = parent.getContext();

        SoundView soundView = new SoundView(context, soundDataProvider);
        parent.addView(soundView);

        ticker.addTickable(simpleSoundMeter);
        ticker.addTickable(soundView);
    }

}
