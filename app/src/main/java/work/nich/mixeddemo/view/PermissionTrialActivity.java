package work.nich.mixeddemo.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.tools.PermissionUtils;

/**
 * This file is created by nich
 * on 2017/3/21 in MixedDemo
 */

public class PermissionTrialActivity extends BaseActivity {

    private final String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CALENDAR};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    public void requestPermission(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.hasPermissions(this, PERMISSIONS)) {
                showPermissionGrantedToast();
            } else {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length >= 3) {
                    boolean permissionAllGranted = true;
                    for (int permission : grantResults){
                        if (permission != PackageManager.PERMISSION_GRANTED)
                            permissionAllGranted = false;
                    }
                    if (permissionAllGranted) {
                        showPermissionGrantedToast();
                    } else {
                        showNoPermissionToast();
                    }
                }
                break;
        }
    }

    private void showNoPermissionToast() {
        Toast.makeText(this, "You gotta grant me permission.", Toast.LENGTH_SHORT).show();
    }

    private void showPermissionGrantedToast() {
        Toast.makeText(this, "I've got permission!", Toast.LENGTH_SHORT).show();
    }
}
