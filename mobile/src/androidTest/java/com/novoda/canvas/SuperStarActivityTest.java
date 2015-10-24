package com.novoda.canvas;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuperStarActivityTest extends NovodaActivityTest {

    private static final Random RANDOM = new Random();
    private static final int STAR_COUNT = 10;

    private static final int[] STARS_RESOURCE = {android.R.drawable.star_big_on, android.R.drawable.star_on, android.R.drawable.btn_star_big_on};

    @Override
    public void startTestFor(Activity activity) {
        View parent = getParent(activity);
        List<ImageView> stars = createStars(activity, parent);

        for (ImageView star : stars) {
            ((ViewGroup) parent).addView(star);
            rotateStar(star);
            bounceStar(star, parent);
        }
    }

    private List<ImageView> createStars(Activity activity, View parent) {
        List<ImageView> stars = new ArrayList<>();
        for (int i = 0; i < STAR_COUNT; i++) {
            ImageView star = new ImageView(activity);
            star.setBackgroundResource(STARS_RESOURCE[RANDOM.nextInt(STARS_RESOURCE.length)]);
            int starSize = RANDOM.nextInt(parent.getWidth() / 2);
            star.setLayoutParams(new ActionBar.LayoutParams(starSize, starSize));
            star.setX(RANDOM.nextInt(parent.getWidth() / 2));
            stars.add(star);
        }
        return stars;
    }

    private void rotateStar(ImageView star) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(star, "rotation", 1080);
        rotation.setDuration(RANDOM.nextInt(1500));
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setRepeatMode(ObjectAnimator.REVERSE);
        rotation.start();
    }

    private void bounceStar(ImageView star, View parent) {
        ObjectAnimator rise = ObjectAnimator.ofFloat(star, "y", 0, parent.getHeight() + star.getHeight());
        rise.setDuration(RANDOM.nextInt(2000));
        rise.setRepeatCount(ObjectAnimator.INFINITE);
        rise.setRepeatMode(ObjectAnimator.REVERSE);
        rise.start();
    }

}
