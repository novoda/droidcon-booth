package com.novoda.canvas.base;

import android.app.Activity;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.ViewGroup;

import com.novoda.canvas.NovodaActivity;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public abstract class NovodaActivityTest {

    public static final int TIME_LIMIT_FOR_TEST_IN_SECONDS = 10;
    @Rule
    public ActivityTestRule<NovodaActivity> activityRule = new ActivityTestRule<>(NovodaActivity.class);

    private NovodaActivity activity;

    @Before
    public void setUp() {
        activity = activityRule.getActivity();
    }

    @Test
    public void theTest() throws InterruptedException {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startTestFor(activity);
            }
        });
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(TIME_LIMIT_FOR_TEST_IN_SECONDS));
    }

    public abstract void startTestFor(Activity activity);

    protected ViewGroup getParent(Activity activity) {
        return (ViewGroup) activity.findViewById(android.R.id.content);
    }

    @ColorInt
    protected int getColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.getResources().getColor(color, activity.getTheme());
        } else {
            return activity.getResources().getColor(color);
        }
    }

}
