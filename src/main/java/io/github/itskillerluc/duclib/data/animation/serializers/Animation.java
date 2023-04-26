package io.github.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.JsonParseException;

import java.util.Map;
import java.util.Objects;

public final class Animation {
    private final boolean loop;
    private final float animationLength;
    private final float speed;
    private final Map<String, Bone> bones;

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

    public boolean loop() {
        return loop;
    }

    public float animationLength() {
        return animationLength;
    }

    public float speed() {
        return speed;
    }

    public Map<String, Bone> bones() {
        return bones;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Animation) obj;
        return this.loop == that.loop &&
                Float.floatToIntBits(this.animationLength) == Float.floatToIntBits(that.animationLength) &&
                Float.floatToIntBits(this.speed) == Float.floatToIntBits(that.speed) &&
                Objects.equals(this.bones, that.bones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loop, animationLength, speed, bones);
    }

    @Override
    public String toString() {
        return "Animation[" +
                "loop=" + loop + ", " +
                "animationLength=" + animationLength + ", " +
                "speed=" + speed + ", " +
                "bones=" + bones + ']';
    }

}
