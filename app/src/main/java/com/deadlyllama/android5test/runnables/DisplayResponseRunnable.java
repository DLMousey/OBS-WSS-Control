package com.deadlyllama.android5test.runnables;

import android.widget.TextView;

public class DisplayResponseRunnable implements Runnable {

    private String message;
    private TextView responseTextView;

    DisplayResponseRunnable(String response, TextView view) {
        message = response;
        responseTextView = view;
    }

    public void run() {
        responseTextView.setText(message);
    }
}
