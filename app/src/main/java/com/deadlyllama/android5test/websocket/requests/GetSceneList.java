package com.deadlyllama.android5test.websocket.requests;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class GetSceneList extends BaseRequest {

    public GetSceneList() {
        this.requestType = "GetSceneList";
    }

    public String serialise() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
