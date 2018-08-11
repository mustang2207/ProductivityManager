package com.mustang2207.productivitytimer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.mustang2207.productivitytimer.R;
import com.mustang2207.productivitytimer.viewmodels.SettingsViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SettingsFragment extends Fragment {

    private final int WORK_SESSION_BASE_VALUE = 15;
    private final int WORK_SESSION_INCREMENT = 5;
    private final int BREAK_INTERVAL_BASE_VALUE = 5;
    private final int BREAK_INTERVAL_INCREMENT = 1;
    private final int LONG_BREAK_INTERVAL_BASE_VALUE = 5;
    private final int LONG_BREAK_INTERVAL_INCREMENT = 5;

    private SettingsViewModel mSettingsViewModel;

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
        mSettingsViewModel = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);
        initUI();
    }

    private void initUI() {
        initWorkSessionSeekBar();
        initWorkSessionTextView();

        initBreakIntervalSeekBar();
        initBreakIntervalTextView();

        initLongBreakIntervalSeekBar();
        initLongBreakIntervalTextView();
    }

    private void initWorkSessionSeekBar() {
        AppCompatSeekBar seekBar = getActivity().findViewById(R.id.st_fr_sb_work_session);
        seekBar.setProgress(mSettingsViewModel.getWorkSession().getValue());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mSettingsViewModel.setWorkSession(
                        WORK_SESSION_BASE_VALUE + progress * WORK_SESSION_INCREMENT);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.getProgress();
            }
        });
    }

    private void initWorkSessionTextView() {
        final AppCompatTextView textView = getActivity().findViewById(R.id.st_fr_tv_work_session_value);
        mSettingsViewModel.getWorkSession().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer progress) {
                textView.setText(progress.toString());
            }
        });
    }

    private void initBreakIntervalSeekBar() {
        AppCompatSeekBar seekBar = getActivity().findViewById(R.id.st_fr_sb_break_interval);
        seekBar.setProgress(mSettingsViewModel.getBreakInterval().getValue());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mSettingsViewModel.setBreakInterval(
                        BREAK_INTERVAL_BASE_VALUE + progress * BREAK_INTERVAL_INCREMENT);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.getProgress();
            }
        });
    }

    private void initBreakIntervalTextView() {
        final AppCompatTextView textView = getActivity().findViewById(R.id.st_fr_tv_break_interval_value);
        mSettingsViewModel.getBreakInterval().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer progress) {
                textView.setText(progress.toString());
            }
        });
    }

    private void initLongBreakIntervalSeekBar() {
        AppCompatSeekBar seekBar = getActivity().findViewById(R.id.st_fr_sb_long_break_interval);
        seekBar.setProgress(mSettingsViewModel.getLongBreakInterval().getValue());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mSettingsViewModel.setLongBreakInterval(
                        LONG_BREAK_INTERVAL_BASE_VALUE + progress * LONG_BREAK_INTERVAL_INCREMENT);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.getProgress();
            }
        });
    }

    private void initLongBreakIntervalTextView() {
        final AppCompatTextView textView = getActivity().findViewById(R.id.st_fr_tv_long_break_interval_value);
        mSettingsViewModel.getLongBreakInterval().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer progress) {
                textView.setText(progress.toString());
            }
        });
    }
}