package com.novoda.canvas;

import android.content.Intent;
import android.os.Environment;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.yalantis.cameramodule.activity.CameraActivity;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CameraActivityTest {

    @Rule
    public ActivityTestRule<CameraActivity> activityRule = new ActivityTestRule<CameraActivity>(CameraActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = super.getActivityIntent();
            intent.putExtra(CameraActivity.PATH, Environment.getExternalStorageDirectory().getPath() + "/droidcon-booth");
            intent.putExtra(CameraActivity.OPEN_PHOTO_PREVIEW, true);
            //intent.putExtra(CameraActivity.LAYOUT_ID, R.layout.fragment_camera_custom);
            intent.putExtra(CameraActivity.USE_FRONT_CAMERA, true);
            return intent;
        }
    };
    private CameraActivity activity;

    @Before
    public void setUp() {
        activity = activityRule.getActivity();
    }

    @Test
    public void theTest() throws InterruptedException {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        startTestFor(activity);
                    }
                }
        );
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(10));
    }

    public void startTestFor(CameraActivity activity) {
        activity.findViewById(R.id.capture).performClick();
    }
}
