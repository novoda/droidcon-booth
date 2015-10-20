package com.novoda.canvas;

import android.app.Activity;

import com.novoda.canvas.base.NovodaActivityTest;

import static org.fest.assertions.api.Assertions.assertThat;

public class NullActivityTest extends NovodaActivityTest {

    @Override
    public void startTestFor(Activity activity) {
        assertThat(activity).isNotNull();
    }

}