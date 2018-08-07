package com.mustang2207.productivitytimer.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceFragmentCompat;

import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.mustang2207.productivitytimer.R;
import com.mustang2207.productivitytimer.viewmodels.MainViewModel;

public class SettingsFragment extends Fragment {

    private MainViewModel mViewModel;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        initUI();
    }

    private void initUI() {
        initWorkDurationSeekBar();
        initWorkDurationTextView();
    }

    private void initWorkDurationSeekBar() {
        AppCompatSeekBar seekBar1 = getActivity().findViewById(R.id.seekBar);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mViewModel.setWorkDuration(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initWorkDurationTextView() {
        final AppCompatTextView textView = getActivity().findViewById(R.id.workSessionTextView);
        mViewModel.getWorkDuration().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                textView.setText(i.toString());
            }
        });
    }
}