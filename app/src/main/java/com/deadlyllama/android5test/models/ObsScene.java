package com.deadlyllama.android5test.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ObsScene {

    public String name;

    public ArrayList<ObsSource> sources;

    public static ObsScene fromJsonElement(JsonElement element) {
        ObsScene scene = new ObsScene();

        JsonObject sceneObject = element.getAsJsonObject();
        scene.name = sceneObject.get("name").getAsString();
        scene.sources = ObsSource.fromJsonArray(sceneObject.get("sources").getAsJsonArray());

        return scene;
    }
}
