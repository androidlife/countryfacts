package com.countryfacts.base;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity is created only for adding runtime permission
 * to access SDCard
 * This permission will be helpful for our current image loading library
 * GLIDE to implement DISK cache or write in SDCard
 * Even the user denies for the permission, the app can run and
 * caching is done in memory at runtime only
 */

public abstract class BaseActivity extends Activity {

    protected abstract void onPermissionChecked();

    protected abstract void onCreated(Bundle savedInstanceState);

    private final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int RETURN_FROM_PERMISSIONS = 0x1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreated(savedInstanceState);
        checkPermissions();
    }

    private void checkPermissions() {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    permission) != PackageManager.PERMISSION_GRANTED)
                permissionList.add(permission);
        }
        if (permissionList.size() == 0) {
            onPermissionChecked();
            return;
        }
        ActivityCompat.requestPermissions(this,
                permissionList.toArray(new String[permissionList.size()]), RETURN_FROM_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        onPermissionChecked();
    }


}
