package com.deadlyllama.android5test.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ObsSceneList {

    @SerializedName("current-scene")
    public String currentScene;

    @SerializedName("message-id")
    public String messageId;

    public ArrayList<ObsScene> scenes;

    public static ObsSceneList fromJsonObject(JsonObject jsonObject) {
        ObsSceneList sceneList = new ObsSceneList();

        sceneList.currentScene = jsonObject.get("current-scene").getAsString();
        sceneList.messageId = jsonObject.get("message-id").getAsString();
        sceneList.scenes = new ArrayList<>();

        JsonArray list = jsonObject.get("scenes").getAsJsonArray();

        for (JsonElement item : list) {
            ObsScene scene = ObsScene.fromJsonElement(item);
            sceneList.scenes.add(scene);
        }

        return sceneList;
    }
}
