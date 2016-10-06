package com.example.trackermvc.app.manager.permissions;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

public class PermissionManagerImpl implements PermissionManager {
    private AppCompatActivity mActivity;

    @Inject
    public PermissionManagerImpl(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void requestPermissions(int requestCode, @NonNull String... permissions) {
        ActivityCompat.requestPermissions(mActivity, permissions, requestCode);
    }

    @Override
    public boolean shouldShowPermissionRequestRationale(@NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
    }

    @Override
    public boolean hasPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean hasPermissions(@NonNull String... permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (!hasPermission(permissions[i])) {
                return false;
            }
        }

        return true;
    }
}
