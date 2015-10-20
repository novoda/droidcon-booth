package com.ataulm.basic;

import android.app.Activity;

import com.ataulm.basic.base.NovodaActivityTest;

import static org.fest.assertions.api.Assertions.assertThat;

public class NullActivityCheckTest extends NovodaActivityTest {

    @Override
    public void doTest(Activity activity) {
        assertThat(activity).isNotNull();
    }

}