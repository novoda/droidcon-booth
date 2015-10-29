package com.novoda.canvas;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.novoda.canvas.base.NovodaActivityTest;

/**
 * @author helen.mead@ocado.com
 */
public class HelenMeadTest extends NovodaActivityTest {

    @Override
    public void startTestFor(Activity activity) {
        final ImageView imageView = new ImageView(activity);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.drawable.helenmeadghost);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(activity, R.anim.shake);
        imageView.startAnimation(fadeInAnimation);
        getParent().addView(imageView);
    }
}