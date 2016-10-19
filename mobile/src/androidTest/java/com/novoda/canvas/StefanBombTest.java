package com.novoda.canvas;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.canvas.R;

import java.util.Random;

public class StefanBombTest extends NovodaActivityTest {

    private static final int SELFIE_CHANGE_INTERVAL = 400;

    private static int[] SELFIES = {
            R.drawable.stefan_selfie_01,
            R.drawable.stefan_selfie_02,
            R.drawable.stefan_selfie_03,
            R.drawable.stefan_selfie_04,
            R.drawable.stefan_selfie_05,
            R.drawable.stefan_selfie_06,
            R.drawable.stefan_selfie_07,
            R.drawable.stefan_selfie_08,
            R.drawable.stefan_selfie_09,
            R.drawable.stefan_selfie_10,
            R.drawable.stefan_selfie_11,
            R.drawable.stefan_selfie_12,
            R.drawable.stefan_selfie_13,
            R.drawable.stefan_selfie_14,
            R.drawable.stefan_selfie_15,
            R.drawable.stefan_selfie_16,
            R.drawable.stefan_selfie_17,
            R.drawable.stefan_selfie_18,
            R.drawable.stefan_selfie_19,
            R.drawable.stefan_selfie_20,
            R.drawable.stefan_selfie_21,
            R.drawable.stefan_selfie_22,
            R.drawable.stefan_selfie_23,
            R.drawable.stefan_selfie_24,
            R.drawable.stefan_selfie_25,
            R.drawable.stefan_selfie_26,
            R.drawable.stefan_selfie_27,
            R.drawable.stefan_selfie_28,
            R.drawable.stefan_selfie_29,
            R.drawable.stefan_selfie_30,
            R.drawable.stefan_selfie_31,
            R.drawable.stefan_selfie_32,
            R.drawable.stefan_selfie_33,
            R.drawable.stefan_selfie_34,
            R.drawable.stefan_selfie_35,
            R.drawable.stefan_selfie_36,
            R.drawable.stefan_selfie_37,
            R.drawable.stefan_selfie_38,
            R.drawable.stefan_selfie_39,
            R.drawable.stefan_selfie_40
    };

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent();

        loadSelfies(activity, root);
    }

    private void loadSelfies(Activity activity, final ViewGroup root) {

        final ImageView selfie = new ImageView(activity);
        selfie.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        selfie.setAdjustViewBounds(true);
        selfie.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        root.addView(selfie);

        for (int i = 0; i < 200; i++) {
            root.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selfie.setImageResource(getRandomSelfie());
                }
            }, i * SELFIE_CHANGE_INTERVAL);
        }
    }

    private int getRandomSelfie() {
        return SELFIES[randomPick()];
    }

    private int randomPick() {
        return new Random().nextInt(SELFIES.length);
    }

}
