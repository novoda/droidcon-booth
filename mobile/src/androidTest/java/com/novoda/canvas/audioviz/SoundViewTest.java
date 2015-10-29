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

    //@Before
    public void mySetUp() {
        simpleSoundMeter = new SimpleSoundMeter();
        soundDataRetriever = simpleSoundMeter;
        soundDataProvider = simpleSoundMeter;
        ticker = new Ticker.TickerImpl();

        soundDataRetriever.start();
        ticker.start();
    }

    /*@Test
    public void theTest() {
        this.startTestFor(activity);
    }*/

    @Override
    public void startTestFor(Activity activity) {

        mySetUp();

        final ViewGroup parent = getParent();
        final Context context = parent.getContext();

        SoundView soundView = new SoundView(context, soundDataProvider);
        parent.addView(soundView);

        ticker.addTickable(simpleSoundMeter);
        ticker.addTickable(soundView);
    }

//    @After
//    public void tearDown() throws Exception {
//        ticker.stop();
//        soundDataRetriever.stop();
//    }

}
