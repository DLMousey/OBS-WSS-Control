package com.deadlyllama.android5test.models;

import androidx.annotation.Nullable;

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
}
