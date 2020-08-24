package com.back_ground_operations.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.back_ground_operations.R;
import com.back_ground_operations.utils.AppConstant;

public class SplashActivity extends AppCompatActivity {

    private Context mContext;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        handler = new Handler();
        requestContactPermission();
    }

    private void nextActivity() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void requestContactPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            nextActivity();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, AppConstant.CONTACT_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstant.CONTACT_REQUEST) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    nextActivity();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    requestContactPermission();
                }
            }
        }
    }
}
