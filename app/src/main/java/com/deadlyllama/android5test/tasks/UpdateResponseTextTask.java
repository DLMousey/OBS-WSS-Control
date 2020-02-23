package com.deadlyllama.android5test.tasks;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.TimerTask;

public class UpdateResponseTextTask extends TimerTask {
    private Handler handler = new Handler(Looper.getMainLooper());
    private String message;
    private TextView textView;

    @Override
    public void run() {
        handler.post(new Runnable() {
            public void run() {
                textView.setText(message);
            }
        });
    }
}
