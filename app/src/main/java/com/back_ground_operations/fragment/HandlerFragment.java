package com.back_ground_operations.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.back_ground_operations.R;
import com.back_ground_operations.databinding.FragmentHandlerBinding;

public class HandlerFragment extends Fragment implements View.OnClickListener {

    private FragmentHandlerBinding binding;
    private Thread mThread;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_handler, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnProgress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnProgress) {
            binding.btnProgress.setVisibility(View.GONE);
            startProgress();
        }
    }

    private void startProgress() {
        binding.seekBar.setProgress(0);
        mThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    binding.seekBar.setProgress(i);
                }
                stopThread();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.btnProgress.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        };
        mThread.start();
    }

    private synchronized void stopThread() {
        mThread = null;
    }

    @Override
    public void onStop() {
        if (mThread != null && mThread.isAlive()) {
            stopThread();
        }
        super.onStop();
    }
}
