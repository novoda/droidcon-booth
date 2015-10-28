package com.novoda.canvas.audioviz;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.novoda.canvas.base.NovodaActivityTest;

import org.junit.After;
import org.junit.Before;

public class AmplitudeViewTest extends NovodaActivityTest {

    private SimpleSoundMeter simpleSoundMeter;
    private SoundDataRetriever soundDataRetriever = simpleSoundMeter;
    private SoundDataProvider soundDataProvider = simpleSoundMeter;

    @Before
    public void setUp() {
        simpleSoundMeter = new SimpleSoundMeter();
    }

    @Override
    public void startTestFor(Activity activity) {

        final ViewGroup parent = getParent();
        final Context context = parent.getContext();

        soundDataRetriever.start();

        AmplitudeView amplitudeView = new AmplitudeView(context, soundDataProvider);
        parent.addView(amplitudeView);
    }

    @After
    public void tearDown() throws Exception {
        soundDataRetriever.stop();
    }

}
