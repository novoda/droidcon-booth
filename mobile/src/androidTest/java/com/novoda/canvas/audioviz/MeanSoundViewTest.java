package com.novoda.canvas.audioviz;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.novoda.canvas.base.NovodaActivityTest;

public class MeanSoundViewTest extends NovodaActivityTest {

    private SimpleSoundMeter simpleSoundMeter;
    private SoundDataRetriever soundDataRetriever;
    private SoundDataProvider soundDataProvider;

    private Ticker ticker;

    @Override
    public void startTestFor(Activity activity) {
        setupDependencies();

        ViewGroup parent = getParent();
        Context context = parent.getContext();

        MeanSoundView meanSoundView = new MeanSoundView(context, soundDataProvider);
        parent.addView(meanSoundView);

        ticker.addTickable(simpleSoundMeter);
        ticker.addTickable(meanSoundView);
    }

    public void setupDependencies() {
        simpleSoundMeter = new SimpleSoundMeter();
        soundDataRetriever = simpleSoundMeter;
        soundDataProvider = simpleSoundMeter;
        ticker = new Ticker.TickerImpl();

        soundDataRetriever.start();
        ticker.start();
    }

}
