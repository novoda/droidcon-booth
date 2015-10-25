package com.novoda.canvas.base;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Random;

public class RandomAnimationFactory {

    private final Context context;
    private final Random random;


    public RandomAnimationFactory(Context context, Random random) {
        this.context = context;
        this.random = random;
    }

    public Animation getAnimation() {
        return AnimationUtils.loadAnimation(context, getRandomAnimation());
    }

    @AnimRes
    private int getRandomAnimation() {
        switch (random.nextInt(8)) {
            case 0:
                return android.support.design.R.anim.abc_grow_fade_in_from_bottom;
            case 1:
                return android.support.design.R.anim.abc_popup_enter;
            case 2:
                return android.support.design.R.anim.abc_slide_in_bottom;
            case 3:
                return android.support.design.R.anim.abc_slide_in_top;
            case 4:
                return android.support.design.R.anim.design_fab_in;
            case 5:
                return android.support.design.R.anim.design_snackbar_in;
            case 6:
                return android.R.anim.fade_in;
            case 7:
                return android.R.anim.slide_in_left;
            default:
                return android.R.anim.slide_in_left;
        }
    }
}
