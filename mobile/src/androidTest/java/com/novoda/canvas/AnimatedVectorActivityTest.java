package com.novoda.canvas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.widget.ImageView;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.Random;

import org.junit.After;

import tyrantgit.explosionfield.ExplosionField;

import static android.widget.ImageView.ScaleType.CENTER_INSIDE;

public class AnimatedVectorActivityTest extends NovodaActivityTest {

    enum Theme {
        BLUE(R.drawable.vector_animated_novoda_blue_logo, Color.parseColor("#FFFFFF")),
        WHITE(R.drawable.vector_animated_novoda_white_logo, Color.parseColor("#26A3DB"));

        private final int vectorDrawableRes;
        private final int backgroundColor;

        Theme(@DrawableRes int vectorDrawableRes, @ColorInt int backgroundColor) {
            this.vectorDrawableRes = vectorDrawableRes;
            this.backgroundColor = backgroundColor;
        }
    }

    public static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());
    public static final int INITIAL_DELAY_MILLIS = 2000;
    public static final int EXPLOSION_DELAY_MILLIS = 8500;

    private ExplosionField explosionField;
    private ImageView imageView;

    @Override
    public void startTestFor(Activity activity) {
        Theme theme = new Random().nextBoolean() ? Theme.BLUE : Theme.WHITE;
        activity.getWindow().setBackgroundDrawable(new ColorDrawable(theme.backgroundColor));
        imageView = createImageView(activity, theme);
        getParent(activity).addView(imageView);
        explosionField = ExplosionField.attach2Window(activity);

        delayInitialAnimation();
        delayExplosion();
    }

    private ImageView createImageView(Context context, Theme theme) {
        ImageView imageView = new ImageView(context);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.image_padding);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(CENTER_INSIDE);

        AnimatedVectorDrawableCompat vectorDrawable = AnimatedVectorDrawableCompat.create(context, theme.vectorDrawableRes);
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
