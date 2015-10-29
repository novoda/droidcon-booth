package com.novoda.canvas;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.novoda.canvas.base.NovodaActivityTest;

import github.cesarferreira.catkit.CatKit;

public class CatzTest extends NovodaActivityTest {

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent();

        loadCatz(activity, root);
    }

    private void loadCatz(final Activity activity, final ViewGroup root) {
        final ImageView cat = new ImageView(activity);
        cat.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        root.addView(cat);

        CatKit.with(activity).dp(360, 360).into(cat);
    }
}
