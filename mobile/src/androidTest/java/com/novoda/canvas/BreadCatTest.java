package com.novoda.canvas;

import com.novoda.canvas.base.NovodaActivityTest;

import android.animation.Animator;
import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BreadCatTest extends NovodaActivityTest {

    // Data
    int[] cats = new int[]{
        R.drawable.cat1,
        R.drawable.cat2,
        R.drawable.cat3,
        R.drawable.cat4,
        R.drawable.cat5,
        R.drawable.cat6
    };

    // Config
    final static int ANIMATION_DURATION = 100;
    final static int STILL_DURATION = 500;

    // Objects
    ImageView imageBottom, imageTop;
    int currentCat;

    @Override
    public void startTestFor(final Activity activity) {
        currentCat = 0;

        imageBottom = new ImageView(activity);
        imageTop = new ImageView(activity);

        imageBottom
            .setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageTop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));

        imageBottom.setAdjustViewBounds(true);
        imageBottom.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageTop.setAdjustViewBounds(true);
        imageTop.setScaleType(ImageView.ScaleType.CENTER_CROP);

        getParent().addView(imageBottom);
        getParent().addView(imageTop);

        lockAndLoad();
    }

    private void setNewCat() {
        for(int i = 0; i < 10; i ++) {
            int randomCat = getRandomCat();
            if(randomCat != currentCat) {
                currentCat = randomCat;
                return;
            }
        }

        currentCat = getNextCat();
    }

    private int getRandomCat() {
        return (int) Math.floor(Math.random() * cats.length);
    }

    private int getNextCat() {
        int cat = currentCat + 1;
        if(cat >= cats.length) {
            return 0;
        } else {
            return cat;
        }
    }

    private void lockAndLoad() {
        imageBottom.setImageResource(cats[currentCat]);
        setNewCat();
        imageTop.setImageResource(cats[currentCat]);

        imageTop.setAlpha(0f);
        imageTop.animate().setStartDelay(STILL_DURATION).setDuration(ANIMATION_DURATION)
            .setInterpolator(new DecelerateInterpolator()).alpha(1f).setListener(
            new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }
                @Override
                public void onAnimationEnd(Animator animator) {
                    lockAndLoad();
                }
                @Override
                public void onAnimationCancel(Animator animator) {

                }
                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
    }

}
