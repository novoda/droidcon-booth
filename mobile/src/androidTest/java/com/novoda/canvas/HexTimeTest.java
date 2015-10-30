package com.novoda.canvas;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novoda.canvas.drawingmachine.noise.NoiseActivityTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * HexTimeTest
 *
 * Show the current time with a background colour generated from the time.
 *
 * Created by Luke Dixon at DroidCon on 30/10/15.
 */
public class HexTimeTest extends NoiseActivityTest{

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup parent = getParent();

        final TextView timeText = new TextView(parent.getContext());
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        timeText.setLayoutParams(params);
        timeText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        timeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
        timeText.setTextColor(Color.WHITE);

        //Show for 10 seconds
        int howLongToDisplay = 10;

        for (int i = 0; i < howLongToDisplay; i++) {
            final int finalI = i;
            getParent().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //Update the time and show it
                    mCalendar.setTimeInMillis(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.UK);
                    timeText.setText(sdf.format(mCalendar.getTime()));

                    //Split out the time
                    int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
                    int minute = mCalendar.get(Calendar.MINUTE);
                    int second = 25 * mCalendar.get(Calendar.SECOND); //*25 to get more dramatic colour change

                    //Set the old and new colours
                    Integer currentColour = Color.rgb(hour, minute, second);
                    Integer newColour = Color.rgb(hour, minute, second+25);

                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), currentColour, newColour);
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            timeText.setBackgroundColor((Integer) animator.getAnimatedValue());
                        }

                    });

                    //Show the pretty colour transition
                    colorAnimation.start();
                }
            }, i * 1000);
        }

        parent.addView(timeText);
    }

}
