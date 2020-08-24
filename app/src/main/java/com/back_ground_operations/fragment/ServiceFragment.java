package com.back_ground_operations.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.back_ground_operations.R;
import com.back_ground_operations.databinding.FragmentServiceBinding;
import com.back_ground_operations.utils.MyService;

public class ServiceFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private FragmentServiceBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.playMusic.setOnClickListener(this);
        binding.stopMusic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_music:
                mContext.startService(new Intent(getActivity(), MyService.class));
                break;
            case R.id.stop_music:
                mContext.stopService(new Intent(getActivity(), MyService.class));
                break;
            default:
                break;
        }
    }
}
