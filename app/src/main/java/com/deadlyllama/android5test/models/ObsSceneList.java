package com.deadlyllama.android5test.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ObsSceneList {

    @SerializedName("current-scene")
    public String currentScene;

    @SerializedName("message-id")
    public String messageId;

    public ArrayList<ObsScene> scenes;
}
