package io.github.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.JsonParseException;

import java.util.Map;

public record AnimationHolder(Map<String, Animation> animations) {
    public AnimationHolder {
        if (animations == null) {
            throw new JsonParseException("couldn't find animations field");
        }
    }
}
