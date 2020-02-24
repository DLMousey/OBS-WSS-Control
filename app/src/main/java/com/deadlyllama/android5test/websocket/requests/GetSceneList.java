package com.deadlyllama.android5test.websocket.requests;

import com.deadlyllama.android5test.models.ObsSceneList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class GetSceneList extends BaseRequest {

    public GetSceneList() {
        this.requestType = "GetSceneList";
        this.messageId = "GetSceneList";
    }

    public String serialise() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
