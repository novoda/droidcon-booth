package com.novoda.canvas;

import android.app.Activity;

import com.novoda.canvas.base.NovodaActivityTest;

import static org.fest.assertions.api.Assertions.assertThat;

public class FooCheckTest extends NovodaActivityTest {

    @Override
    public void doTest(Activity activity) {
        assertThat(activity).isNotNull();
    }

}