package com.novoda.canvas;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private static String[] CITY_NAMES = {
            "LONDON",
            "BERLIN",
            "BARCELONA",
            "LIVERPOOL",
            "NEW YORK"
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

        final TextView cityTextView = new TextView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 20, 0, 20);
        cityTextView.setLayoutParams(params);
        cityTextView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        cityTextView.setTextColor(Color.WHITE);
        cityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48);
        root.addView(cityTextView);

        for (int i = 0; i < CITIES.length; i++) {
            final int nextCity = CITIES[i];
            final String nextCityName = CITY_NAMES[i];
            root.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cityImageView.setImageResource(nextCity);
                    cityTextView.setText(nextCityName);
                }
            }, i * CITY_CHANGE_INTERVAL);
        }
    }

}
