package com.back_ground_operations.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CommonUtils {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void setFragment(Fragment fragment, boolean removeStack, FragmentActivity activity, int mContainer, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            if (tag != null)
                ftTransaction.replace(mContainer, fragment, tag);
            else
                ftTransaction.replace(mContainer, fragment);
        } else {
            if (tag != null)
                ftTransaction.replace(mContainer, fragment, tag);
            else
                ftTransaction.replace(mContainer, fragment);
            ftTransaction.addToBackStack(null);
        }
        ftTransaction.commit();
    }

    public static void hideSoftInput(Activity activity) {
        try {
            if (activity == null || activity.isFinishing()) return;
            Window window = activity.getWindow();
            if (window == null) return;
            View view = window.getCurrentFocus();
            //give decorView a chance
            if (view == null) view = window.getDecorView();
            if (view == null) return;

            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null || !imm.isActive()) return;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}





