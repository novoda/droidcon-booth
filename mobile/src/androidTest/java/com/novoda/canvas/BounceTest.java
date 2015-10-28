package com.novoda.canvas;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.List;

import static com.novoda.canvas.NovodaActivity.RANDOM;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BounceTest extends NovodaActivityTest {

    private static final int MAX_BALL_SIZE = 100;
    private static final int BATCH_SIZE = 25;
    private static final int BACKGROUND_COLOR = R.color.bounce_background;
    private static final int[] COLOR_VARIANTS = {
            R.color.bounce_yellow,
            R.color.bounce_red,
            R.color.bounce_pink,
            R.color.bounce_purple,
            R.color.bounce_purple_deep,
            R.color.bounce_indigo,
            R.color.bounce_blue,
            R.color.bounce_cyan,
            R.color.bounce_light,
            R.color.bounce_teal,
            R.color.bounce_green,
            R.color.bounce_white,
    };

    @Override
    public void startTestFor(final Activity activity) {
        final ViewGroup parent = getParent(activity);
        parent.setBackgroundColor(activity.getResources().getColor(BACKGROUND_COLOR));
        for (int batchIndex = 0; batchIndex < 20; batchIndex++) {
            final int finalBatchIndex = batchIndex * 2;
            parent.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            final List<ImageView> views = createObjects(finalBatchIndex, activity, parent);
                            for (int starIndex = 0; starIndex < finalBatchIndex; starIndex++) {
                                ImageView ball = views.get(starIndex);
                                parent.addView(ball);
                                throwBallForward(starIndex % 2 == 0, ball, parent);
                                tossBallUp(ball, parent);
                            }
                        }
                    }
                    , batchIndex * 500
            );
        }

    }

    private List<ImageView> createObjects(int count, Activity activity, ViewGroup parent) {
        ArrayList<ImageView> objects = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            ImageView object = new ImageView(activity);
            int size = getBallSize();
            Bitmap circle = createCircle(activity.getResources(), size, size);
            object.setImageBitmap(circle);
            object.setLayoutParams(new ActionBar.LayoutParams(size, size));
            object.setX(RANDOM.nextInt(parent.getWidth()));
            object.setY(parent.getHeight());
            objects.add(object);

            object.setX(RANDOM.nextInt(parent.getWidth()));
        }
        return objects;
    }

    private Bitmap createCircle(Resources resources, int width, int height) {
        final Bitmap output = Bitmap.createBitmap(
                width, height, Bitmap.Config.ARGB_8888
        );
        final Canvas canvas = new Canvas(output);

        final int color = getBallColor();
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(resources.getColor(COLOR_VARIANTS[color]));
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        return output;
    }

    private void throwBallForward(boolean leftToRight, ImageView ball, ViewGroup parent) {
        float start = getDropPoint();
        float end = parent.getWidth() + ball.getWidth();
        if (!leftToRight) {
            float temp = start;
            start = end;
            end = temp;
        }

        ObjectAnimator tossForward = ObjectAnimator.ofFloat(ball, "x", start, end);
        tossForward.setDuration(forwardTossDuration());
        tossForward.setInterpolator(new AccelerateDecelerateInterpolator());
        tossForward.start();

    }

    private void tossBallUp(ImageView ball, ViewGroup parent) {
        ObjectAnimator tossUp = ObjectAnimator.ofFloat(ball, "y", getDropPoint(), parent.getHeight() - MAX_BALL_SIZE);
        tossUp.setDuration(tossDuration());
        tossUp.setInterpolator(new BounceInterpolator());
        tossUp.start();
    }

    private int getDropPoint() {
        return -(RANDOM.nextInt(500) + MAX_BALL_SIZE);
    }

    private int getBallSize() {
        return RANDOM.nextInt(MAX_BALL_SIZE - 10) + 10;
    }

    private int getBallColor() {
        return RANDOM.nextInt(COLOR_VARIANTS.length);
    }

    private int tossDuration() {
        return RANDOM.nextInt(5000) + 1000;
    }

    private int forwardTossDuration() {
        return RANDOM.nextInt(1500) + 1500;
    }
}
