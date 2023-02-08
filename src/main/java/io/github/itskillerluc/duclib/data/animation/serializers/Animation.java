package io.github.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.JsonParseException;

import java.util.Map;

public record Animation(boolean loop, float animationLength, float speed, Map<String, Bone> bones) {
    public Animation(boolean loop, float animationLength, float speed, Map<String, Bone> bones) {
        if (bones == null) {
            throw new JsonParseException("field bones is missing");
        }
        if (speed == 0) {
            this.speed = 1;
        } else {
            this.speed = speed;
        }
        this.loop = loop;
        this.animationLength = animationLength;
        this.bones = bones;
    }
}
