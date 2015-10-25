package com.novoda.canvas;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.SystemClock;

import com.novoda.canvas.base.NovodaActivityTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AppMasterTest extends NovodaActivityTest {

    private static final Random RANDOM = new Random();

    @Override
    public void startTestFor(Activity activity) {
        Intent launchIntent = new Intent(Intent.ACTION_MAIN);
        List<ResolveInfo> activities = activity.getPackageManager().queryIntentActivities(launchIntent, PackageManager.MATCH_DEFAULT_ONLY);
        List<ResolveInfo> filteredActivities = filterList(activity, activities);
        for (int i = 0; i < 20; i++) {
            maybeLaunchRandomActivity(activity, launchIntent, filteredActivities);
        }
    }

    private List<ResolveInfo> filterList(Context context, List<ResolveInfo> activities) {
        List<ResolveInfo> filteredList = new ArrayList<>();
        for (ResolveInfo activity: activities) {
            if (isWhiteListed(context, activity)){
                filteredList.add(activity);
            }
        }
        return filteredList;
    }

    private boolean isWhiteListed(Context context, ResolveInfo resolveInfo) {
        return Arrays.binarySearch(context.getResources().getStringArray(R.array.white_listed_apps), resolveInfo.activityInfo.packageName) >= 0;
    }

    private void maybeLaunchRandomActivity(Activity activity, Intent launchIntent, List<ResolveInfo> activities) {
        int randomIndex = RANDOM.nextInt(activities.size());
        ActivityInfo randomActivity = activities.get(randomIndex).activityInfo;
        if (noPermissionRequired(randomActivity.permission)) {
            launchIntent.setComponent(new ComponentName(randomActivity.packageName, randomActivity.name));
            activity.startActivity(launchIntent);
            sleepForAWhile();
        }
    }

    private void sleepForAWhile() {
        SystemClock.sleep(1000);
    }

    private boolean noPermissionRequired(String permission) {
        return permission == null || permission.length() == 0;
    }
}
