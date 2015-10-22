package com.novoda.canvas.faceoff;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.util.Pools;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.novoda.canvas.R;
import com.novoda.canvas.base.NovodaActivityTest;

import java.util.Random;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class FaceOffActivityTest extends NovodaActivityTest {

    @DrawableRes
    private static final int DRAWABLE_ID_COLT = R.drawable.colt;
    @DrawableRes
    private static final int DRAWABLE_ID_JAKE = R.drawable.jake;
    private static final int MOVEMENT_DURATION_MS = 800;
    private static final int WORD_COUNT = 12;
    private static final Random RANDOM = new Random();

    private static final Pools.Pool<Rect> RECT_POOL = new Pools.SimplePool<>(WORD_COUNT + 2);

    private final TextView[] words = new TextView[WORD_COUNT];

    private ImageView colt;
    private ImageView jake;
    private ViewGroup parent;

    @Override
    public void startTestFor(final Activity activity) {
        parent = (ViewGroup) activity.findViewById(android.R.id.content);
        addWords(activity);

        colt = makeFace(activity, DRAWABLE_ID_COLT, XSide.LEFT);
        jake = makeFace(activity, DRAWABLE_ID_JAKE, XSide.RIGHT);
        rotate(colt);
        moveX(colt);
        moveY(colt, YSide.TOP);
        rotate(jake);
        moveX(jake);
        moveY(jake, YSide.BOTTOM);

        parent.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, getVictoryText(activity), Toast.LENGTH_SHORT).show();
                    }
                }, 8000
        );
    }

    @StringRes
    private int getVictoryText(Activity activity) {
        int enumCount = 0;
        int intDefCount = 0;
        for (TextView word : words) {
            if (word.getText().toString().equals(activity.getString(R.string.face_off_enum))) {
                enumCount++;
            } else {
                intDefCount++;
            }
        }
        if (enumCount > intDefCount) {
            return R.string.face_off_enum_win;
        } else if (enumCount < intDefCount) {
            return R.string.face_off_int_win;
        } else {
            return R.string.face_off_draw;
        }
    }

    private void addWords(Activity activity) {
        for (int i = 0; i < WORD_COUNT; i++) {
            TextView word = new TextView(activity);
            word.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            word.setText(RANDOM.nextBoolean() ? R.string.face_off_enum : R.string.face_off_int);
            word.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            word.setX(RANDOM.nextInt(parent.getWidth()));
            word.setY(i * parent.getHeight() / WORD_COUNT);
            words[i] = word;
            parent.addView(word);
        }
    }

    private ImageView makeFace(Activity activity, int drawable, XSide xSide) {
        ImageView view = new ImageView(activity);
        view.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        view.setBackgroundResource(drawable);
        int xPosition = xSide.getCentreOfSide(parent.getWidth());
        view.setX(xPosition);
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

    private static boolean checkCollision(View a, View b) {
        Rect aBounds = getBounds(a);
        Rect bBounds = getBounds(b);
        boolean intersect = aBounds.intersect(bBounds);
        RECT_POOL.release(aBounds);
        RECT_POOL.release(bBounds);
        return intersect;
    }

    private static Rect getBounds(View v) {
        Rect rect = RECT_POOL.acquire();
        if (rect == null) {
            rect = new Rect();
        }
        int x = (int) v.getTranslationX();
        int y = (int) v.getTranslationY();
        rect.left = x;
        rect.top = y;
        rect.right = x + v.getWidth();
        rect.bottom = y + v.getHeight();
        return rect;
    }

}
