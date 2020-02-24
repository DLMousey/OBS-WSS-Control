package com.deadlyllama.android5test.websocket.requests;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class SetCurrentScene {

    @SerializedName("message-id")
    public String messageId;

    @SerializedName("request-type")
    public String requestType;

    @SerializedName("scene-name")
    public String sceneName;

    public SetCurrentScene(String sceneName) {
        this.requestType = "SetCurrentScene";
        this.messageId = "SetCurrentScene";
        this.sceneName = sceneName;
    }

    public String serialise() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
