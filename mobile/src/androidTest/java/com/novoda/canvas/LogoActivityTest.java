package com.novoda.canvas;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.novoda.canvas.base.NovodaActivityTest;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class LogoActivityTest extends NovodaActivityTest {

    private static final int DURATION_MILLIS = 1000;
    private static final float FROM_X = 0.8f;
    private static final float TO_X = 1f;
    private static final float FROM_Y = 0.8f;
    private static final float TO_Y = 1f;
    private static final float PIVOT_X = 0.5f;
    private static final float PIVOT_Y = 0.5f;

    @Override
    public void startTestFor(Activity activity) {
        ViewGroup parent = getParent(activity);
        ImageView logo = createNovodaLogo(parent);

        Animation pulse = createPulseAnimation();
        logo.startAnimation(pulse);
    }

    private ImageView createNovodaLogo(ViewGroup parent) {
        ImageView logo = new ImageView(parent.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        logo.setLayoutParams(params);
        logo.setScaleType(ImageView.ScaleType.CENTER);
        logo.setImageResource(R.drawable.novoda_logo);
        parent.addView(logo);
        return logo;
    }

    private Animation createPulseAnimation() {
        Animation pulse = new ScaleAnimation(
                FROM_X, TO_X,
                FROM_Y, TO_Y,
                Animation.RELATIVE_TO_SELF, PIVOT_X,
                Animation.RELATIVE_TO_SELF, PIVOT_Y
        );
        pulse.setDuration(DURATION_MILLIS);
        pulse.setRepeatCount(ObjectAnimator.INFINITE);
        pulse.setRepeatMode(ObjectAnimator.REVERSE);
        pulse.setInterpolator(new OvershootInterpolator());
        return pulse;
    }
}
