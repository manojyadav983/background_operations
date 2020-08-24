package com.back_ground_operations.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.back_ground_operations.R;
import com.back_ground_operations.databinding.ActivityMainBinding;
import com.back_ground_operations.fragment.AsynFragment;
import com.back_ground_operations.fragment.HandlerFragment;
import com.back_ground_operations.fragment.ServiceFragment;
import com.back_ground_operations.utils.CommonUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clickListener();

        onDrawerSelected(1);

        binding.navigationView.tvService.setOnClickListener(this);
        binding.navigationView.tvAsyn.setOnClickListener(this);
        binding.navigationView.tvThread.setOnClickListener(this);
        binding.mainToolbar.ivLeft.setOnClickListener(this);
    }

    private void clickListener() {
        binding.navigationView.tvService.setOnClickListener(this);
        binding.navigationView.tvAsyn.setOnClickListener(this);
        binding.navigationView.tvThread.setOnClickListener(this);
    }

    private void onDrawerSelected(int position) {
        String title = "";
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        switch (position) {
            case 1:
                title = getString(R.string.txt_service);
                fragment = new ServiceFragment();
                break;
            case 2:
                title = getString(R.string.text_asyn_task);
                fragment = new AsynFragment();
                break;
            case 3:
                title = getString(R.string.txt_thread);
                fragment = new HandlerFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            CommonUtils.setFragment(fragment, true, this, R.id.flContainer, title);
        }
        binding.mainToolbar.tvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        CommonUtils.hideSoftInput(this);
        switch (v.getId()) {
            case R.id.tvService:
                onDrawerSelected(1);
                break;
            case R.id.tvAsyn:
                onDrawerSelected(2);
                break;
            case R.id.tvThread:
                onDrawerSelected(3);
                break;
            case R.id.ivLeft:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }
}
