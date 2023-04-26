package io.github.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.JsonParseException;

import java.util.Map;
import java.util.Objects;

public final class AnimationHolder {
    private final Map<String, Animation> animations;

    public AnimationHolder(Map<String, Animation> animations) {
        if (animations == null) {
            throw new JsonParseException("couldn't find animations field");
        }
        this.animations = animations;
    }

    public Map<String, Animation> animations() {
        return animations;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AnimationHolder) obj;
        return Objects.equals(this.animations, that.animations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animations);
    }

    @Override
    public String toString() {
        return "AnimationHolder[" +
                "animations=" + animations + ']';
    }

}
