package com.novoda.canvas.base;

import android.app.Activity;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.res.ResourcesCompat;
import android.view.ViewGroup;
import android.widget.Toast;

import com.novoda.canvas.NovodaActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public abstract class NovodaActivityTest {

    protected static final int TIME_LIMIT_FOR_TEST_IN_SECONDS = 10;

    @Rule
    public ActivityTestRule<NovodaActivity> activityRule = new ActivityTestRule<>(NovodaActivity.class);

    private NovodaActivity activity;

    @Before
    public final void setUp() {
        activity = activityRule.getActivity();
    }

    @Test
    public final void theTest() throws InterruptedException {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startTestFor(activity);
            }
        });
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(TIME_LIMIT_FOR_TEST_IN_SECONDS));
    }

    public abstract void startTestFor(Activity activity);

    protected ViewGroup getParent() {
        return (ViewGroup) activity.findViewById(android.R.id.content);
    }

    @ColorInt
    protected int getColor(@ColorRes int color) {
        return ResourcesCompat.getColor(activity.getResources(), color, activity.getTheme());
    }

    protected final void ensurePermissions(String[] permissions, String rationale) {
        activity.ensurePermissions(
                permissions,
                rationale,
                new NovodaActivity.Callback() {

            @Override
            public void onPermissionsNotGranted(String[] permissions) {
                Toast.makeText(activity, "Needing permissions: " + Arrays.toString(permissions) +
                        ", please approve for next run to work", Toast.LENGTH_LONG).show();
            }
        }
        );
    }

}
