package com.lchrislee.longjourney.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONObject;

public abstract class BaseModel {
    public abstract @Nullable BaseModel fromJSONString(@NonNull String inputData);
    public abstract @NonNull JSONObject toJSON();
}
