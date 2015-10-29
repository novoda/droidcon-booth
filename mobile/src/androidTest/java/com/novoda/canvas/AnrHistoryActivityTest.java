package com.novoda.canvas;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.Random;

public class AnrHistoryActivityTest extends NovodaActivityTest {

    private static final int ANR_INTERVAL = 800;

    private static int[] ANR = {
            R.drawable.android_anr_1,
            R.drawable.android_anr_2,
            R.drawable.android_anr_3,
            R.drawable.android_anr_4,
            R.drawable.android_anr_5,
            R.drawable.android_anr_6,
            R.drawable.android_anr_7
    };

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent();
        loadANR(activity, root);
    }

    private void loadANR(Activity activity, final ViewGroup root) {

        final ImageView fakeAnr = new ImageView(activity);
        fakeAnr.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fakeAnr.setAdjustViewBounds(true);
        fakeAnr.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        root.addView(fakeAnr);

        for (int i = 0; i < 200; i++) {
            root.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            fakeAnr.setImageResource(makeItBreak());
                        }
                    }, i * ANR_INTERVAL
            );
        }
    }

    private int makeItBreak() {
        return ANR[randomPick()];
    }

    private int randomPick() {
        return new Random().nextInt(ANR.length);
    }

}
