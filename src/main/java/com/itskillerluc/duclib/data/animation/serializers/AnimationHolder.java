package com.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.itskillerluc.duclib.data.model.serializers.Geometry;

import java.util.Map;

public record AnimationHolder(Map<String, Animation> animations) {
    public AnimationHolder {
        if (animations == null) {
            throw new JsonParseException("couldn't find animations field");
        }
    }
}
