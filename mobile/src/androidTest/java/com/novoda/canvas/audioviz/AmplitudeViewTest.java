package com.novoda.canvas.audioviz;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.novoda.canvas.base.NovodaActivityTest;

public class AmplitudeViewTest extends NovodaActivityTest {

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

        soundDataRetriever.start();

        AmplitudeView amplitudeView = new AmplitudeView(context, soundDataProvider);
        parent.addView(amplitudeView);

        ticker.addTickable(simpleSoundMeter);
        ticker.addTickable(amplitudeView);

        L.ui(this, "finished");
    }

//    @After
//    public void tearDown() throws Exception {
//        ticker.stop();
//        soundDataRetriever.stop();
//    }

}
