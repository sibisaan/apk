package com.videocalsecret;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}