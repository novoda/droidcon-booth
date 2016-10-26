package com.novoda.canvas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.Random;

import pub.devrel.easypermissions.EasyPermissions;

public class NovodaActivity extends AppCompatActivity {

    private static final int RC_PERMISSIONS = 12345;

    public static final Random RANDOM = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novoda);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void ensurePermissions(String[] permissions, String rationale, Callback callback) {
        if (!EasyPermissions.hasPermissions(this, permissions)) {
            callback.onPermissionsNotGranted(permissions);
            EasyPermissions.requestPermissions(this, rationale, RC_PERMISSIONS, permissions);
        }
    }

    public interface Callback {

        void onPermissionsNotGranted(String[] permissions);

    }

}
