package com.novoda.canvas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.novoda.canvas.base.NovodaActivityTest;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class EdenTest extends NovodaActivityTest {

    /*Mike Scamell, mike@edenagency.co.uk*/

    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    ImageView edenLogo;

    @Override
    public void startTestFor(final Activity activity) {
        edenLogo = new ImageView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        edenLogo.setLayoutParams(params);
        edenLogo.setScaleType(ImageView.ScaleType.CENTER);
        edenLogo.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.eden));

        getParent().setBackgroundColor(ContextCompat.getColor(activity, R.color.eden_red));
        getParent().addView(edenLogo);

        scaleUpAnim();
    }

    public void scaleUpAnim() {

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(edenLogo, "scaleX", 0.2f, 1f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(edenLogo, "scaleY", 0.2f, 1f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);

        animatorSet.play(bounceAnimX).with(bounceAnimY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                scaleUpAnim();
            }
        });
        animatorSet.start();
    }
}
