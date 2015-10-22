package com.novoda.canvas.faceoff;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.Typeface;
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

    private static final int MOVEMENT_DURATION_MS = 800;
    private static final int WORD_COUNT = 12;
    private static final Random RANDOM = new Random();
    private static final Pools.Pool<Rect> RECT_POOL = new Pools.SimplePool<>(WORD_COUNT + 2);

    private final TextView[] words = new TextView[WORD_COUNT];

    private View colt;
    private View jake;

    @Override
    public void startTestFor(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        addWords(parent);

        colt = makeFace(parent, Player.COLT);
        jake = makeFace(parent, Player.JAKE);
        startAnimations(colt, Player.COLT);
        startAnimations(jake, Player.JAKE);

        scheduleVictoryToast(parent);
    }

    private void addWords(ViewGroup parent) {
        for (int i = 0; i < WORD_COUNT; i++) {
            TextView word = new TextView(parent.getContext());
            word.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            if (RANDOM.nextBoolean()) {
                word.setTag(ImplType.ENUM);
                word.setText(R.string.face_off_enum);
            } else {
                word.setTag(ImplType.INT);
                word.setText(R.string.face_off_int);
            }
            word.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            word.setX(RANDOM.nextInt(parent.getWidth()));
            word.setY(i * parent.getHeight() / WORD_COUNT);
            words[i] = word;
            parent.addView(word);
        }
    }

    private View makeFace(ViewGroup parent, Player player) {
        View view = new ImageView(parent.getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        view.setBackgroundResource(player.getDrawableId());
        int xPosition = player.getXSide().getCentreOfSide(parent.getWidth());
        view.setX(xPosition);
        parent.addView(view);
        return view;
    }

    private void startAnimations(View view, Player player) {
        rotate(view, player);
        moveX(view);
        moveY(view, player.getYSide());
    }

    private void rotate(View view, Player player) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 30, -30);
        rotation.setDuration(400);
        if (player == Player.COLT) {
            rotation.setFloatValues(-30, 30);
            rotation.addUpdateListener(
                    new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            onEachFrame();
                        }
                    }
            );
        }
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setRepeatMode(ObjectAnimator.REVERSE);
        rotation.start();
    }

    private void onEachFrame() {
        for (TextView word : words) {
            if (checkCollision(colt, word)) {
                word.setTag(ImplType.INT);
                word.setText(R.string.face_off_int);
            } else if (checkCollision(jake, word)) {
                word.setTag(ImplType.ENUM);
                word.setText(R.string.face_off_enum);
            }
        }
    }

    private static boolean checkCollision(View a, View b) {
        Rect aBounds = getBounds(a);
        Rect bBounds = getBounds(b);
        boolean viewsDoCollide = aBounds.intersect(bBounds);
        RECT_POOL.release(aBounds);
        RECT_POOL.release(bBounds);
        return viewsDoCollide;
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

    private void moveX(final View view) {
        int maxX = getParent(view).getWidth();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "x", view.getX(), RANDOM.nextInt(maxX));
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

    private static ViewGroup getParent(View view) {
        return (ViewGroup) view.getParent();
    }

    private void moveY(final View view, @YSide final int ySide) {
        int maxY = getParent(view).getHeight() - view.getHeight();
        int y = ySide == YSide.TOP ? 0 : maxY;
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

    private void scheduleVictoryToast(final View view) {
        view.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(), getVictoryText(), Toast.LENGTH_SHORT).show();
                    }
                }, 8000
        );
    }

    @StringRes
    private int getVictoryText() {
        int enumCount = 0;
        int intDefCount = 0;
        for (TextView word : words) {
            ImplType tag = (ImplType) word.getTag();
            if (tag == ImplType.ENUM) {
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

}
