package com.mustang2207.productivitytimer.viewmodels;

import androidx.lifecycle.ViewModel;

public class TimerNavigatiorViewModel extends ViewModel {
    private enum State{
        workState, breakState, longBreakState
    }
    private State state;


}
