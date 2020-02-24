package com.deadlyllama.android5test.models;

import androidx.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ObsSource {

    public Double cx;

    public Double cy;

    public Integer id;

    public Boolean locked;

    public String name;

    @Nullable
    public String parentGroupName;

    public Boolean render;

    @SerializedName("source_cx")
    public Double sourceCx;

    @SerializedName("source_cy")
    public Double sourceCy;

    public String type;

    public Double volume;

    public Double x;

    public Double y;

    public ArrayList<ObsSource> groupChildren;

    public static ObsSource fromJsonElement(JsonElement element) {
        ObsSource source = new ObsSource();
        JsonObject obj = element.getAsJsonObject();

        source.cx = obj.get("cx").getAsDouble();
        source.cy = obj.get("cy").getAsDouble();
        source.id = obj.get("id").getAsInt();
        source.locked = obj.get("locked").getAsBoolean();
        source.name = obj.get("name").getAsString();
        source.render = obj.get("render").getAsBoolean();
        source.sourceCx = obj.get("source_cx").getAsDouble();
        source.sourceCy = obj.get("source_cy").getAsDouble();
        source.type = obj.get("type").getAsString();
        source.volume = obj.get("volume").getAsDouble();
        source.x = obj.get("x").getAsDouble();
        source.y = obj.get("y").getAsDouble();

        return source;
    }

    public static ArrayList<ObsSource> fromJsonArray(JsonArray array) {
        ArrayList<ObsSource> sources = new ArrayList<ObsSource>();
        for (JsonElement object: array) {
            ObsSource source = new ObsSource();
            JsonObject obj = object.getAsJsonObject();

            source.cx = obj.get("cx").getAsDouble();
            source.cy = obj.get("cy").getAsDouble();
            source.id = obj.get("id").getAsInt();
            source.locked = obj.get("locked").getAsBoolean();
            source.name = obj.get("name").getAsString();
            source.render = obj.get("render").getAsBoolean();
            source.sourceCx = obj.get("source_cx").getAsDouble();
            source.sourceCy = obj.get("source_cy").getAsDouble();
            source.type = obj.get("type").getAsString();
            source.volume = obj.get("volume").getAsDouble();
            source.x = obj.get("x").getAsDouble();
            source.y = obj.get("y").getAsDouble();

            sources.add(source);
        }

        return sources;
    }
}
