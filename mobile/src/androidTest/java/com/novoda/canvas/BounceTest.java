package com.novoda.canvas;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.novoda.canvas.NovodaActivity.RANDOM;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BounceTest extends NovodaActivityTest {

    private static final int MAX_BALL_SIZE = 100;

    private static final int BACKGROUND_COLOR = R.color.bounce_background;

    private static final int[] COLOR_VARIANTS = {
            R.color.bounce_yellow,
            R.color.bounce_red,
            R.color.bounce_pink,
            R.color.bounce_purple,
            R.color.bounce_blue,
            R.color.bounce_cyan,
            R.color.bounce_light,
            R.color.bounce_teal,
            R.color.bounce_green,
            R.color.bounce_white,
    };

    private static final int BATCHES_COUNT = 20;

    @Override
    public void startTestFor(final Activity activity) {
        final ViewGroup parent = getParent();
        parent.setBackgroundColor(getColor(BACKGROUND_COLOR));

        List<Circle> allCircles = new ArrayList<>();

        CirclesView circlesView = new CirclesView(activity, allCircles);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        circlesView.setLayoutParams(lp);
        parent.addView(circlesView);

        Size screenSize = getScreenSize(activity.getWindowManager());
        final int screenWidth = screenSize.getWidth();
        final int screenHeight = screenSize.getHeight();

        for (int batchIndex = 0; batchIndex < BATCHES_COUNT; batchIndex++) {
            final int batchSize = batchIndex * 2;
            final List<Circle> circles = createCircles(batchSize);
            allCircles.addAll(circles);

            parent.postDelayed(
                    animateBatch(circles, screenWidth, screenHeight),
                    delayFor(batchIndex)
            );
        }
    }

    @NonNull
    private Runnable animateBatch(final List<Circle> circles, final int screenWidth, final int screenHeight) {
        return new Runnable() {
            @Override
            public void run() {
                int batchSize = circles.size();
                for (int ballIndex = 0; ballIndex < batchSize; ballIndex++) {
                    Circle circle = circles.get(ballIndex);

                    boolean throwLeft = ballIndex % 2 == 0;
                    if (throwLeft) {
                        circle.setXAnimator(throwBallLeft(circle.size(), screenWidth));
                    } else {
                        circle.setXAnimator(throwBallRight(circle.size(), screenWidth));
                    }

                    circle.setYAnimator(tossBallUp(circle.size(), screenHeight));
                }
            }
        };
    }

    private int delayFor(int batchIndex) {
        return batchIndex * 500;
    }

    private Size getScreenSize(WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new Size(size.x, size.y);
    }

    private List<Circle> createCircles(int count) {
        ArrayList<Circle> circles = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            int ballColor = getColor(getBallColorResId());
            Circle circle = Circle.create(ballColor, getBallSize());
            circles.add(circle);
        }

        return circles;
    }

    private ValueAnimator throwBallLeft(int size, @Px int screenWidth) {
        float start = getDropPoint();
        float end = screenWidth + size;

        ValueAnimator animator = new ValueAnimator();
        startThrowAnimation(animator, start, end);
        return animator;
    }

    private ValueAnimator throwBallRight(int size, @Px int screenWidth) {
        float start = screenWidth + size;
        float end = getDropPoint();

        ValueAnimator animator = new ValueAnimator();
        startThrowAnimation(animator, start, end);
        return animator;
    }

    private void startThrowAnimation(ValueAnimator animator, float start, float end) {
        animator.setFloatValues(start, end);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(forwardTossDuration());
        animator.start();
    }

    private ValueAnimator tossBallUp(@Px int size, @Px int screenHeight) {
        ValueAnimator animator = new ValueAnimator();
        animator.setFloatValues(getDropPoint(), screenHeight - size);
        animator.setDuration(tossDuration());
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
        return animator;
    }

    private int getDropPoint() {
        return -(RANDOM.nextInt(500) + MAX_BALL_SIZE);
    }

    private int getBallSize() {
        return RANDOM.nextInt(MAX_BALL_SIZE - 10) + 10;
    }

    @ColorRes
    private int getBallColorResId() {
        int index = RANDOM.nextInt(COLOR_VARIANTS.length);
        return COLOR_VARIANTS[index];
    }

    private int tossDuration() {
        return RANDOM.nextInt(5000) + 1000;
    }

    private int forwardTossDuration() {
        return RANDOM.nextInt(1500) + 1500;
    }

    private static class Circle {

        private final Paint paint;
        private final int size;

        private ValueAnimator xAnimator;
        private ValueAnimator yAnimator;

        static Circle create(@ColorInt int color, @Px int ballSize) {
            Paint circlePaint = createCirclePaint(color);
            return new Circle(circlePaint, ballSize);
        }

        private static Paint createCirclePaint(@ColorInt int color) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(color);
            return paint;
        }

        private Circle(Paint paint, int size) {
            this.paint = paint;
            this.size = size;
        }

        @Px
        int size() {
            return size;
        }

        Paint paint() {
            return paint;
        }

        void setXAnimator(ValueAnimator xAnimator) {
            this.xAnimator = xAnimator;
        }

        void setYAnimator(ValueAnimator yAnimator) {
            this.yAnimator = yAnimator;
        }

        ValueAnimator xAnimator() {
            return xAnimator;
        }

        ValueAnimator yAnimator() {
            return yAnimator;
        }

        boolean readyToDraw() {
            return xAnimator != null/* && xAnimator.isStarted()*/ &&
                    yAnimator != null/* && yAnimator.isStarted()*/;
        }

        @Override
        public String toString() {
            String x = String.valueOf(xAnimator != null ? xAnimator.getAnimatedValue() : "N/A");
            String y = String.valueOf(yAnimator != null ? yAnimator.getAnimatedValue() : "N/A");
            return "Circle{" +
                    "size=" + size() +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private static class CirclesView extends View {

        private final List<Circle> circles;

        CirclesView(Context context, List<Circle> circles) {
            super(context);
            this.circles = circles;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (Circle circle : circles) {
                if (!circle.readyToDraw()) {
                    Log.i("cirkles", "Skipping circle " + circle.toString());
                    continue;
                }

                Log.v("cirkles", "Drawing circle " + circle.toString());
                float x = (float) circle.xAnimator().getAnimatedValue();
                float y = (float) circle.yAnimator().getAnimatedValue();

                canvas.drawCircle(x, y, circle.size() / 2, circle.paint());
            }

            invalidate();
        }
    }
}
