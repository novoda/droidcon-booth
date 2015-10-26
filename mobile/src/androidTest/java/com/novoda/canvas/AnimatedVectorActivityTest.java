package com.novoda.canvas;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.widget.ImageView;

import com.novoda.canvas.base.NovodaActivityTest;

import org.junit.After;

import tyrantgit.explosionfield.ExplosionField;

import static android.widget.ImageView.ScaleType.CENTER_INSIDE;

public class AnimatedVectorActivityTest extends NovodaActivityTest {

    public static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());
    public static final int INITIAL_DELAY_MILLIS = 2000;
    public static final int EXPLOSION_DELAY_MILLIS = 8500;

    private ExplosionField explosionField;
    private ImageView imageView;

    @Override
    public void startTestFor(Activity activity) {
        imageView = createImageView(activity);
        getParent(activity).addView(imageView);
        explosionField = ExplosionField.attach2Window(activity);

        delayInitialAnimation();
        delayExplosion();
    }

    private ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.image_padding);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(CENTER_INSIDE);

        AnimatedVectorDrawableCompat vectorDrawable = AnimatedVectorDrawableCompat.create(context, R.drawable.vector_animated_novoda_blue_logo);
        imageView.setImageDrawable(vectorDrawable);
        return imageView;
    }

    private void delayInitialAnimation() {
        MAIN_THREAD.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Drawable drawable = imageView.getDrawable();
                        ((Animatable) drawable).start();
                    }
                }, INITIAL_DELAY_MILLIS
        );
    }

    private void delayExplosion() {
        MAIN_THREAD.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        explosionField.explode(imageView);
                    }
                }, EXPLOSION_DELAY_MILLIS
        );
    }

    @After
    public void tearDown() {
        explosionField.clear();
    }
}
