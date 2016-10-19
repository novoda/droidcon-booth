package com.novoda.canvas;

import android.app.Activity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.novoda.canvas.base.NovodaActivityTest;
import com.novoda.canvas.R;

public class TumbleAcivityTest extends NovodaActivityTest {

    private ImageView groundImage;
    private ImageView androidImage;

    @Override
    public void startTestFor(final Activity activity) {
        View.inflate(activity.getApplicationContext(), R.layout.tumble_activity, this.getParent());
        groundImage = (ImageView) activity.findViewById(R.id.tumble_activity_ground_image);
        androidImage = (ImageView) activity.findViewById(R.id.tumble_activity_android_image);

        groundImage.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake));
        androidImage.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake));

        getParent().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        androidImage.animate().rotation(435).start();
                        androidImage.setPadding(0, 10, 0, 0);
                        androidImage.setAnimation(null);
                        groundImage.setAnimation(null);
                    }
                }, 4000
        );

        getParent().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        androidImage.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.float_up));
                    }
                }, 4500
        );

    }

}
