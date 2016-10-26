package com.novoda.canvas;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Px;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.novoda.canvas.NovodaActivity.RANDOM;

public class SuperStarActivityTest extends NovodaActivityTest {

    private static final int[] STARS_RESOURCES = {
            android.R.drawable.star_big_on,
            android.R.drawable.star_on,
            android.R.drawable.btn_star_big_on

    };
    private static final int BATCH_SIZE = 20;

    @Override
    public void startTestFor(final Activity activity) {
        ViewGroup parent = getParent();
        parent.setBackgroundColor(getColor(android.R.color.black));

        List<Star> allStars = new ArrayList<>();

        StarsView starsView = new StarsView(activity, allStars);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        starsView.setLayoutParams(lp);
        parent.addView(starsView);

        Drawable[] starDrawables = loadStarDrawables(activity, STARS_RESOURCES);
        Size screenSize = getScreenSize(activity.getWindowManager());
        int screenWidth = screenSize.width();
        int screenHeight = screenSize.height();
        int maxStarSize = getMaxStarSize(screenWidth);

        for (int batchIndex = 0; batchIndex < BATCH_SIZE; batchIndex++) {
            List<Star> stars = createStars(BATCH_SIZE, maxStarSize, starDrawables, screenWidth);
            allStars.addAll(stars);

            parent.postDelayed(
                    animateBatch(stars, screenHeight),
                    delayFor(batchIndex)
            );
        }
    }

    private int delayFor(int batchIndex) {
        return batchIndex * 500;
    }

    private Runnable animateBatch(final List<Star> stars, @Px final int screenHeight) {
        return new Runnable() {
            @Override
            public void run() {
                int batchSize = stars.size();
                for (int i = 0; i < batchSize; i++) {
                    Star star = stars.get(i);

                    star.setRotateAnimator(rotateStar());
                    star.setYAnimator(makeStarFall(star.size(), screenHeight));
                }
            }
        };
    }

    @SuppressWarnings("ConstantConditions") // getDrawable() should never return null
    private Drawable[] loadStarDrawables(Activity activity, int[] starsResourceIds) {
        Drawable[] drawables = new Drawable[starsResourceIds.length];
        for (int i = 0; i < starsResourceIds.length; i++) {
            int starsResource = starsResourceIds[i];
            drawables[i] = ResourcesCompat.getDrawable(
                    activity.getResources(),
                    starsResource,
                    activity.getTheme()
            )
                    .mutate();
            setupBoundsOn(drawables[i]);
        }
        return drawables;
    }

    private void setupBoundsOn(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    private List<Star> createStars(int batchSize, @Px int maxStarSize, Drawable[] starDrawables, @Px int screenWidth) {
        List<Star> stars = new ArrayList<>(batchSize);

        for (int i = 0; i < batchSize; i++) {
            Drawable starDrawable = getRandomStarDrawableFrom(starDrawables);
            int x = getX(screenWidth);
            Star star = new Star(starDrawable, getRandomStarSize(maxStarSize), x);
            stars.add(star);
        }

        return stars;
    }

    private Drawable getRandomStarDrawableFrom(Drawable[] starDrawables) {
        return starDrawables[RANDOM.nextInt(starDrawables.length)];
    }

    @Px
    private int getRandomStarSize(int maxStarSize) {
        return RANDOM.nextInt(maxStarSize);
    }

    private int getX(int width) {
        return RANDOM.nextInt(width);
    }

    private int getMaxStarSize(int screenWidth) {
        return screenWidth / 10;
    }

    private ValueAnimator rotateStar() {
        ValueAnimator animator = new ValueAnimator();
        animator.setFloatValues(0, getRotation());
        animator.setDuration(getRotationDuration());
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        return animator;
    }

    @SuppressWarnings("NumericOverflow")  // No it's not
    private float getRotation() {
        float direction = RANDOM.nextBoolean() ? 1f : -1f;
        return (RANDOM.nextInt(720) + 360) * direction;
    }

    private int getRotationDuration() {
        return RANDOM.nextInt(12000) + 6000;
    }

    private ValueAnimator makeStarFall(@Px int starSize, @Px int screenHeight) {
        ValueAnimator animator = new ValueAnimator();
        animator.setFloatValues(-starSize, screenHeight + starSize);
        animator.setDuration(getFallDuration());
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        return animator;
    }

    private int getFallDuration() {
        return RANDOM.nextInt(6000) + 3000;
    }

    private static class Star {

        private final Drawable drawable;
        private final int size;
        private final int x;

        private ValueAnimator yAnimator;
        private ValueAnimator rotateAnimator;

        private Star(Drawable drawable, @Px int starSize, int x) {
            this.drawable = drawable;
            this.size = starSize;
            this.x = x;
        }

        @Px
        int size() {
            return size;
        }

        @Px
        int x() {
            return x;
        }

        Drawable drawable() {
            return drawable;
        }

        void setYAnimator(ValueAnimator yAnimator) {
            this.yAnimator = yAnimator;
        }

        ValueAnimator yAnimator() {
            return yAnimator;
        }

        void setRotateAnimator(ValueAnimator rotateAnimator) {
            this.rotateAnimator = rotateAnimator;
        }

        ValueAnimator rotateAnimator() {
            return rotateAnimator;
        }

        boolean readyToDraw() {
            return yAnimator != null && yAnimator.isStarted() &&
                    rotateAnimator != null && rotateAnimator.isStarted();
        }
    }

    private static class StarsView extends View {

        private final List<Star> circles;

        StarsView(Context context, List<Star> circles) {
            super(context);
            this.circles = circles;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (Star star : circles) {
                if (!star.readyToDraw()) {
                    continue;
                }

                float rotation = (float) star.rotateAnimator().getAnimatedValue();
                float x = star.x();
                float y = (float) star.yAnimator().getAnimatedValue();

                int state = canvas.save();

                canvas.translate(x, y);

                int rotationPivot = star.size() / 2;
                canvas.translate(rotationPivot, rotationPivot);
                canvas.rotate(rotation);
                canvas.translate(-rotationPivot, -rotationPivot);

                star.drawable().draw(canvas);

                canvas.restoreToCount(state);
            }

            invalidate();
        }
    }

}
