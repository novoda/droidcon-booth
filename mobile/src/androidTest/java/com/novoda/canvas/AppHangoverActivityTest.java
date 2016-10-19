package com.novoda.canvas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.novoda.canvas.base.NovodaActivityTest;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static java.util.concurrent.TimeUnit.SECONDS;

public class AppHangoverActivityTest extends NovodaActivityTest {

    @Override
    public void startTestFor(Activity activity) {
        final ViewGroup root = getParent();
        addBar(root);
        showDialog(root);
    }

    private void addBar(final ViewGroup root) {
        ProgressBar bar = new ProgressBar(root.getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        bar.setLayoutParams(layoutParams);
        bar.setIndeterminate(true);
        root.addView(bar);
    }

    private void showDialog(final ViewGroup root) {
        final AlertDialog dialog = new AlertDialog.Builder(root.getContext()).create();
        dialog.setTitle("Demo Wall isn't responding");
        dialog.setMessage("It's probably too hungover from last night");
        dialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "Me too",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );

        root.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                    }
                }, SECONDS.toMillis(4)
        );
    }

}
