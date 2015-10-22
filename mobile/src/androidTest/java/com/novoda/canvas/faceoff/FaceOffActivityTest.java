package com.novoda.canvas.faceoff;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.novoda.canvas.R;
import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class FaceOffActivityTest extends NovodaActivityTest {

    private static final Random RANDOM = new Random();
    private static final int WORD_COUNT = 12;
    @DrawableRes
    private static final int DRAWABLE_ID_COLT = R.drawable.colt;
    @DrawableRes
    private static final int DRAWABLE_ID_JAKE = R.drawable.jake;
    private static final int MOVEMENT_DURATION_MS = 800;

    private final List<TextView> words = new ArrayList<>(WORD_COUNT);

    private ImageView colt;
    private ImageView jake;
    private ViewGroup parent;

    @Override
    public void startTestFor(Activity activity) {
        parent = (ViewGroup) activity.findViewById(android.R.id.content);
        parent.setBackgroundResource(android.R.color.holo_red_dark);
        addWords(activity);

        colt = makeFace(activity, parent, DRAWABLE_ID_COLT, XSide.LEFT);
        jake = makeFace(activity, parent, DRAWABLE_ID_JAKE, XSide.RIGHT);
        rotate(colt);
        rotate(jake);
        move(colt, YSide.TOP);
        move(jake, YSide.BOTTOM);
    }

    private void addWords(Activity activity) {
        for (int i = 0; i < WORD_COUNT; i++) {
            TextView word = new TextView(activity);
            word.setText(RANDOM.nextBoolean() ? R.string.face_off_enum : R.string.face_off_int);
            word.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            word.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            word.setX(i * parent.getWidth() / WORD_COUNT);
            word.setY(RANDOM.nextInt(parent.getHeight()));
            words.add(word);
            parent.addView(word);
        }
    }

    private ImageView makeFace(Activity activity, ViewGroup parent, int drawable, XSide xSide) {
        ImageView view = new ImageView(activity);
        view.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        view.setBackgroundResource(drawable);
        int quarterWidth = parent.getWidth() / 4;
        if (xSide == XSide.LEFT) {
            view.setX(quarterWidth);
        } else {
            view.setX(3 * quarterWidth);
        }
        parent.addView(view);
        return view;
    }

    private void rotate(View view) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 30, -30);
        rotation.setDuration(400);
        if (colt == view) {
            rotation.setFloatValues(-30, 30);
            rotation.addUpdateListener(
                    new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            for (TextView word : words) {
                                if (checkCollision(colt, word)) {
                                    word.setText(R.string.face_off_int);
                                } else if (checkCollision(jake, word)) {
                                    word.setText(R.string.face_off_enum);
                                }
                            }
                        }
                    }
            );
        }
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setRepeatMode(ObjectAnimator.REVERSE);
        rotation.start();
    }

    private void move(View view, @YSide int ySide) {
        moveX(view);
        moveY(view, ySide);
    }

    private void moveX(final View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "x", view.getX(), RANDOM.nextInt(parent.getWidth()));
        animator.setDuration(MOVEMENT_DURATION_MS);
        animator.addListener(
                new SimpleAnimationListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        moveX(view);
                    }
                }
        );
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void moveY(final View view, @YSide final int ySide) {
        int y = ySide == YSide.TOP ? 0 : parent.getHeight() - view.getHeight();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "y", view.getY(), y);
        animator.setDuration(MOVEMENT_DURATION_MS);
        animator.addListener(
                new SimpleAnimationListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        moveY(view, ySide == YSide.TOP ? YSide.BOTTOM : YSide.TOP);
                    }
                }
        );
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private static boolean checkCollision(View v1, View v2) {
        return getBounds(v1).intersect(getBounds(v2));
    }

    private static Rect getBounds(View v) {
        int x = (int) v.getTranslationX();
        int y = (int) v.getTranslationY();
        return new Rect(x, y, x + v.getWidth(), y + v.getHeight());
    }

}
