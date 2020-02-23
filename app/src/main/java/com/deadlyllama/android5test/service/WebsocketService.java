package com.deadlyllama.android5test.service;

import com.deadlyllama.android5test.websocket.ObsEndpoint;

import okhttp3.WebSocket;

public class WebsocketService {

    private static volatile WebsocketService INSTANCE;

    private WebSocket websocket;
    private ObsEndpoint listener;

    private WebsocketService() {}

    /**
     * Threadsafe method of instantiating/returning this service
     * @return WebsocketService
     */
    public static WebsocketService getInstance() {
        if (INSTANCE == null) {
            synchronized (WebsocketService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WebsocketService();
                }
            }
        }

        return INSTANCE;
    }

    public WebSocket getWebsocket() {
        return websocket;
    }

    public void setWebsocket(WebSocket websocket) {
        this.websocket = websocket;
    }

    public ObsEndpoint getListener() {
        return listener;
    }

    public void setListener(ObsEndpoint listener) {
        this.listener = listener;
    }
}
