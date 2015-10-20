package com.novoda.canvas.base;

import android.app.Activity;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.novoda.canvas.NovodaActivity;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public abstract class NovodaActivityTest {

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
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(10));
    }

    public abstract void startTestFor(Activity activity);

}