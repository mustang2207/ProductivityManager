package com.mustang2207.productivitytimer.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustang2207.productivitytimer.R;
import com.mustang2207.productivitytimer.viewmodels.SettingsViewModel;
import com.mustang2207.productivitytimer.viewmodels.TimerViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainFragment extends Fragment {

    private TimerViewModel timerViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsViewModel settingsViewModel = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);
        timerViewModel = ViewModelProviders.of(getActivity()).get(TimerViewModel.class);

        timerViewModel.setTimerInterval(settingsViewModel);

        final AppCompatTextView timerTextView = getActivity().findViewById(R.id.mn_fr_tv_timer);
        timerViewModel.getTimer().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                timerTextView.setText(timerViewModel.getTimer().getValue());
            }
        });
        timerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerViewModel.startTimer();
            }
        });

        timerViewModel.getIsAlert().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                alertView(timerViewModel.getAlertTitle(),timerViewModel.getAlertMessage());
            }
        });
    }

    private void alertView(String title, String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        timerViewModel.updateTimerInterval();
                        timerViewModel.startTimer();
                    }
                }).show();
    }
}
