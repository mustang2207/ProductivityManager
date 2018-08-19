package com.mustang2207.productivitytimer.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.mustang2207.productivitytimer.R;
import com.mustang2207.productivitytimer.utilities.Constants;
import com.mustang2207.productivitytimer.viewmodels.SettingsViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SettingsFragment extends Fragment {
    // TODO: 8/16/2018 implement mvp

    private SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settingsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SettingsViewModel.class);
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

    @SuppressWarnings("ConstantConditions")
    private void initWorkSessionSeekBar() {
        AppCompatSeekBar seekBar = Objects.requireNonNull(getActivity()).findViewById(R.id.st_fr_sb_work_session);
        seekBar.setProgress(
                (settingsViewModel.getWorkSession().getValue() - Constants.WORK_SESSION_BASE_VALUE)
                / Constants.WORK_SESSION_INCREMENT);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                settingsViewModel.setWorkSession(
                        Constants.WORK_SESSION_BASE_VALUE + progress * Constants.WORK_SESSION_INCREMENT);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull SeekBar seekBar) {
                seekBar.getProgress();
            }
        });
    }

    private void initWorkSessionTextView() {
        final AppCompatTextView textView = Objects.requireNonNull(getActivity()).findViewById(R.id.st_fr_tv_work_session_value);
        settingsViewModel.getWorkSession().observe(getActivity(), new Observer<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(@NonNull Integer progress) {
                textView.setText(progress.toString());
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void initBreakIntervalSeekBar() {
        AppCompatSeekBar seekBar = Objects.requireNonNull(getActivity()).findViewById(R.id.st_fr_sb_break_interval);
        seekBar.setProgress(
                (settingsViewModel.getBreakInterval().getValue() - Constants.BREAK_INTERVAL_BASE_VALUE)
                / Constants.BREAK_INTERVAL_INCREMENT);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                settingsViewModel.setBreakInterval(
                        Constants.BREAK_INTERVAL_BASE_VALUE + progress * Constants.BREAK_INTERVAL_INCREMENT);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull SeekBar seekBar) {
                seekBar.getProgress();
            }
        });
    }

    private void initBreakIntervalTextView() {
        final AppCompatTextView textView = Objects.requireNonNull(getActivity()).findViewById(R.id.st_fr_tv_break_interval_value);
        settingsViewModel.getBreakInterval().observe(getActivity(), new Observer<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(@NonNull Integer progress) {
                textView.setText(progress.toString());
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void initLongBreakIntervalSeekBar() {
        AppCompatSeekBar seekBar = Objects.requireNonNull(getActivity()).findViewById(R.id.st_fr_sb_long_break_interval);
        seekBar.setProgress(
                (settingsViewModel.getLongBreakInterval().getValue() - Constants.LONG_BREAK_INTERVAL_BASE_VALUE)
                / Constants.LONG_BREAK_INTERVAL_INCREMENT);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                settingsViewModel.setLongBreakInterval(
                        Constants.LONG_BREAK_INTERVAL_BASE_VALUE + progress * Constants.LONG_BREAK_INTERVAL_INCREMENT);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull SeekBar seekBar) {
                seekBar.getProgress();
            }
        });
    }

    private void initLongBreakIntervalTextView() {
        final AppCompatTextView textView = Objects.requireNonNull(getActivity()).findViewById(R.id.st_fr_tv_long_break_interval_value);
        settingsViewModel.getLongBreakInterval().observe(getActivity(), new Observer<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(@NonNull Integer progress) {
                textView.setText(progress.toString());
            }
        });
    }
}