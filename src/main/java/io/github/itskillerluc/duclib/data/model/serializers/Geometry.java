package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

import java.util.Arrays;
import java.util.Objects;

public final class Geometry {
    private Description description;
    private Bone[] bones;

    public Geometry(Description description, Bone[] bones) {
        if (description == null) {
            throw new JsonParseException("couldn't find description field");
        }
        if (bones == null) {
            throw new JsonParseException("couldn't find bones field");
        }
        this.description = description;
        this.bones = bones;
    }

    public Description description() {
        return description;
    }

    public Bone[] bones() {
        return bones;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Geometry) obj;
        return Objects.equals(this.description, that.description) &&
                Arrays.equals(this.bones, that.bones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, Arrays.hashCode(bones));
    }

    @Override
    public String toString() {
        return "Geometry[" +
                "description=" + description + ", " +
                "bones=" + Arrays.toString(bones) + ']';
    }

}
