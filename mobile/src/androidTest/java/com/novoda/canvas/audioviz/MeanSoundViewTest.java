package com.novoda.canvas.audioviz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.novoda.canvas.base.NovodaActivityTest;

import org.junit.After;

public class MeanSoundViewTest extends NovodaActivityTest {

    private SimpleSoundMeter simpleSoundMeter;
    private Ticker ticker;

    @Override
    public void startTestFor(Activity activity) {
        ensureAudioRecordPermission();

        setupDependencies();

        ViewGroup parent = getParent();
        Context context = parent.getContext();

        MeanSoundView meanSoundView = new MeanSoundView(context, simpleSoundMeter);
        parent.addView(meanSoundView);

        ticker.addTickable(simpleSoundMeter);
        ticker.addTickable(meanSoundView);
    }

    private void ensureAudioRecordPermission() {
        ensurePermissions(
                new String[] {Manifest.permission.RECORD_AUDIO},
                "Need audio recording permission to show audio meter"
        );
    }

    public void setupDependencies() {
        simpleSoundMeter = new SimpleSoundMeter();
        ticker = new Ticker.TickerImpl();

        simpleSoundMeter.start();
        ticker.start();
    }

    @After
    public void tearDown() {
        ticker.stop();
        simpleSoundMeter.stop();
    }
}
