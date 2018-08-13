package com.mustang2207.productivitytimer.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mustang2207.productivitytimer.R;
import com.mustang2207.productivitytimer.viewmodels.SettingsViewModel;
import com.mustang2207.productivitytimer.viewmodels.TimerViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainFragment extends Fragment {

    private TimerViewModel timerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUi();
    }

    private void initUi() {
        SettingsViewModel settingsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SettingsViewModel.class);
        timerViewModel = ViewModelProviders.of(getActivity()).get(TimerViewModel.class);
        timerViewModel.setTimerInterval(settingsViewModel);

        final AppCompatTextView timerTextView = getActivity().findViewById(R.id.mn_fr_tv_timer);
        final AppCompatTextView headerTextView = getActivity().findViewById(R.id.mn_fr_tv_header);
        timerViewModel.getHeader().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String str) {
                headerTextView.setText(str);
            }
        });
        timerViewModel.getTimer().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                timerTextView.setText(str);
            }
        });
        timerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerViewModel.startTimer();
            }
        });

        final Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        timerViewModel.setTimerLister(new TimerViewModel.TimerListener() {
            @Override
            public void onTimerStatusChanged() {
                alertView(timerViewModel.getAlertTitle(),timerViewModel.getAlertMessage());
                if(vibrator != null) {
                    if (android.os.Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(2000);
                    }
                }
            }
        });
    }

    private void alertView(String title, String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        dialog.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(@NonNull DialogInterface dialoginterface, int i) {
                        //timerViewModel.updateTimerInterval();
                        dialoginterface.cancel();
                }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(@NonNull DialogInterface dialoginterface, int i) {
                        timerViewModel.updateTimerInterval();
                        timerViewModel.startTimer();
                        dialoginterface.cancel();
                    }
                }).show();
    }
}
