package com.novoda.canvas.mines;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import de.devisnik.mine.IField;
import de.devisnik.mine.IFieldListener;

public class FieldController {

    private static final float TARGET_ZOOM = 1.1f;
    private static final int ZOOM_DURATION = 150;
    private static final Animation CLICK_ZOOM_ANIMATION = createClickAnimation();

    private final IField field;
    private final IFieldListener fieldListener;
    private final FieldView view;

    private static Animation createClickAnimation() {
        ScaleAnimation animation = new ScaleAnimation(1, TARGET_ZOOM, 1, TARGET_ZOOM, ScaleAnimation.RELATIVE_TO_SELF,
                .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
        animation.setZAdjustment(Animation.ZORDER_BOTTOM);
        animation.setFillEnabled(false);
        animation.setFillAfter(false);
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setDuration(ZOOM_DURATION);
        return animation;
    }

    public FieldController(final FieldView view, final IField field,
                           final IFieldListener fieldListener) {
        this.view = view;
        this.field = field;
        updateView();
        this.fieldListener = fieldListener;
        this.field.addListener(this.fieldListener);
    }

    public void updateView() {
        view.setImageId(field.getImage());
        view.setTouched(field.isTouched());
    }

    @SuppressLint("NewApi")
    public void showClickFeedback() {
        // ensure view is drawn fully without being clipped
        view.bringToFront();
        if (Build.VERSION.SDK_INT >= 17) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            CLICK_ZOOM_ANIMATION.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(final Animation animation) {
                }

                @Override
                public void onAnimationRepeat(final Animation animation) {
                }

                @Override
                public void onAnimationEnd(final Animation animation) {
                    view.setLayerType(View.LAYER_TYPE_NONE, null);
                }
            });
        }
        view.startAnimation(CLICK_ZOOM_ANIMATION);
    }

    public IField getField() {
        return field;
    }

    public void dispose() {
        field.removeListener(fieldListener);
    }

}
