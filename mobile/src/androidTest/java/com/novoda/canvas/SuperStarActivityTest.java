package com.novoda.canvas;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;

import static com.novoda.canvas.NovodaActivity.RANDOM;

public class SuperStarActivityTest extends NovodaActivityTest {

    private static final int[] STARS_RESOURCE = {android.R.drawable.star_big_on, android.R.drawable.star_on, android.R.drawable.btn_star_big_on};
    private static final int BATCH_SIZE = 20;
    private static final int OFF_SCREEN_OFFSET = 500;

    @Override
    public void startTestFor(final Activity activity) {
        final View parent = getParent();
        parent.setBackgroundColor(activity.getResources().getColor(android.R.color.black));

        for (int batchIndex = 0; batchIndex < 20; batchIndex++) {
            parent.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            final List<ImageView> stars = createStars(BATCH_SIZE, activity, parent);
                            for (int starIndex = 0; starIndex < BATCH_SIZE; starIndex++) {
                                ImageView star = stars.get(starIndex);
                                ((ViewGroup) parent).addView(star);
                                rotateStar(star);
                                bounceStar(star, parent);
                            }
                        }
                    }
                    , batchIndex * 500
            );
        }

    }

    private List<ImageView> createStars(int batchSize, Activity activity, View parent) {
        List<ImageView> stars = new ArrayList<>();
        for (int i = 0; i < batchSize; i++) {
            ImageView star = new ImageView(activity);
            star.setBackgroundResource(STARS_RESOURCE[RANDOM.nextInt(STARS_RESOURCE.length)]);
            int starSize = RANDOM.nextInt(getMaxStarSize(parent));
            star.setLayoutParams(new ActionBar.LayoutParams(starSize, starSize));
            star.setX(RANDOM.nextInt(parent.getWidth()));
            stars.add(star);
        }
        return stars;
    }

    private int getMaxStarSize(View parent) {
        return parent.getWidth() / 10;
    }

    private void rotateStar(ImageView star) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(star, "rotation", 1080);
        rotation.setDuration(getRotationDuration());
        rotation.start();
    }

    private int getRotationDuration() {
        return RANDOM.nextInt(12000) + 6000;
    }

    private void bounceStar(ImageView star, View parent) {
        ObjectAnimator fall = ObjectAnimator.ofFloat(star, "y", -getMaxStarSize(parent), parent.getHeight() + getMaxStarSize(parent));
        fall.setDuration(getFallDuration());
        fall.setInterpolator(new AccelerateInterpolator());
        fall.start();
    }

    private int getFallDuration() {
        return RANDOM.nextInt(6000) + 3000;
    }

}
