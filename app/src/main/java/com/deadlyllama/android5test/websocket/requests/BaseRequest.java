package com.deadlyllama.android5test.websocket.requests;

import com.google.gson.annotations.SerializedName;

public class BaseRequest {

    @SerializedName("request-type")
    public String requestType;

    @SerializedName("message-id")
    public String messageId = "qweqwe";
}
