package com.mustang2207.productivitytimer.fragments;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustang2207.productivitytimer.R;
import com.mustang2207.productivitytimer.viewmodels.SettingsViewModel;
import com.mustang2207.productivitytimer.viewmodels.TimerViewModel;

public class MainFragment extends Fragment {

    private TimerViewModel mTimerViewModel;
    private SettingsViewModel mSettingsViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSettingsViewModel = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);
        mTimerViewModel = ViewModelProviders.of(getActivity()).get(TimerViewModel.class);
        mTimerViewModel.setInterval(mSettingsViewModel.getWorkSession().getValue());


        final AppCompatTextView timerTextView = getActivity().findViewById(R.id.mn_fr_tv_timer);
        mTimerViewModel.getTimer().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                timerTextView.setText(mTimerViewModel.getTimer().getValue());

            }
        });
        timerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int interval = mSettingsViewModel.getWorkSession().getValue();
                mTimerViewModel.startTimer(interval);
                mTimerViewModel.getTimer().observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String str) {
                        timerTextView.setText(mTimerViewModel.getTimer().getValue());

                    }
                });
            }
        });
    }
}
