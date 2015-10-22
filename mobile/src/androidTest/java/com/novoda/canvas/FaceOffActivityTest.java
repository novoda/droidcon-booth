package com.novoda.canvas;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class FaceOffActivityTest extends NovodaActivityTest {

    private static final Random RANDOM = new Random();
    private static final int WORD_COUNT = 12;
    @DrawableRes
    public static final int DRAWABLE_ID_COLT = R.drawable.colt;
    @DrawableRes
    public static final int DRAWABLE_ID_JAKE = R.drawable.jake;
    private ImageView colt;
    private ImageView jake;
    private List<TextView> words;

    @Override
    public void startTestFor(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        words = createWords(activity, parent);

        createTheColt(activity, parent);
        makeTheJake(activity, parent);
        rotate(colt);
        rotate(jake);
        move(colt, parent);
        move(jake, parent);

        for (TextView word : words) {
            parent.addView(word);
        }
    }

    private List<TextView> createWords(Activity activity, View parent) {
        List<TextView> words = new ArrayList<>(WORD_COUNT);
        for (int i = 0; i < WORD_COUNT; i++) {
            final TextView word = new TextView(activity);
            word.setText("ENUM");
            word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            int height = (int) (activity.getResources().getDisplayMetrics().density * 50);
            word.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, height));
            word.setX(i * (parent.getWidth() / WORD_COUNT));
            word.setY(RANDOM.nextInt(parent.getHeight()));
            words.add(word);
        }
        return words;
    }

    private void createTheColt(Activity activity, ViewGroup parent) {
        colt = new ImageView(activity);
        colt.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        colt.setBackgroundResource(DRAWABLE_ID_COLT);
        colt.setX(RANDOM.nextInt(parent.getWidth() / 2));
        colt.setY(parent.getHeight() / 2);
        parent.addView(colt);
    }

    private void makeTheJake(Activity activity, ViewGroup parent) {
        jake = new ImageView(activity);
        jake.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        jake.setBackgroundResource(DRAWABLE_ID_JAKE);
        jake.setX(RANDOM.nextInt(parent.getWidth() / 2));
        jake.setY(parent.getHeight() / 2);
        parent.addView(jake);
    }

    private void rotate(View view) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 45, -45);
        rotation.setDuration(500);
        if (jake == view) {
            rotation.reverse();
        }
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setRepeatMode(ObjectAnimator.REVERSE);
        rotation.start();
    }

    private void move(final View view, final View parent) {
        Path path = new Path();

        path.lineTo(view.getWidth(), RANDOM.nextInt(parent.getHeight()));
        path.lineTo(RANDOM.nextInt(parent.getWidth()), view.getHeight());
        path.lineTo(parent.getWidth(), RANDOM.nextInt(parent.getHeight()));
        path.lineTo(RANDOM.nextInt(parent.getWidth()), parent.getHeight());

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "x", "y", path);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        for (TextView word : words) {
                            if (checkCollision(colt, word)) {
                                word.setText("INT");
                            } else if (checkCollision(jake, word)) {
                                word.setText("ENUM");
                            }
                        }
                    }
                }
        );
        animator.setDuration(2000 + RANDOM.nextInt(1000));
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
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
