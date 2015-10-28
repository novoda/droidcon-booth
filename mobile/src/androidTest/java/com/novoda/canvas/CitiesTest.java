package com.novoda.canvas;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.novoda.canvas.base.NovodaActivityTest;

public class CitiesTest extends NovodaActivityTest {

    private static final int CITY_CHANGE_INTERVAL = 2000;

    private static int[] CITIES = {
            R.drawable.london,
            R.drawable.berlin,
            R.drawable.barcelona,
            R.drawable.liverpool,
            R.drawable.new_york
    };

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent();

        loadCities(activity, root);
    }

    private void loadCities(Activity activity, final ViewGroup root) {

        final ImageView cityImageView = new ImageView(activity);
        cityImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        cityImageView.setAdjustViewBounds(true);
        cityImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        root.addView(cityImageView);

        for (int i = 0; i < CITIES.length; i++) {
            final int nextCity = CITIES[i];
            root.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cityImageView.setImageResource(nextCity);
                }
            }, i * CITY_CHANGE_INTERVAL);
        }
    }


}
