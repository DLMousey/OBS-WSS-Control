package com.deadlyllama.android5test.websocket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public final class ObsEndpoint extends WebSocketListener {
    private static final String TAG = "ObsEndpoint";
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private Handler handler;

    public ObsEndpoint(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onOpen(WebSocket websocket, Response response) {
        Log.d(TAG, "onOpen: fired");
    }

    @Override
    public void onMessage(WebSocket websocket, ByteString bytes) {
        Log.d(TAG, "onMessage: fired");
        Log.d(TAG, "onMessage: received! " + bytes.toString());

        Message msg = handler.obtainMessage();
        msg.what = 0;
        msg.obj = bytes.toString();
        handler.sendMessage(msg);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d(TAG, "onMessage: fired");
        Log.d(TAG, "onMessage: received! " + text);

        Message msg = handler.obtainMessage();
        msg.what = 0;
        msg.obj = text;
        handler.sendMessage(msg);
    }

    @Override
    public void onClosing(WebSocket websocket, int code, String reason) {
        Log.d(TAG, "onClosing: fired");
        websocket.close(NORMAL_CLOSURE_STATUS, null);
    }

    @Override
    public void onFailure(WebSocket websocket, Throwable t, Response response) {
        Log.d(TAG, "onFailure: fired");
        Message msg = handler.obtainMessage();
        msg.what = -1;
        handler.sendMessage(msg);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}