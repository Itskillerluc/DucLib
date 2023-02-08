package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

public record Geometry(Description description, Bone[] bones) {
    public Geometry {
        if (description == null) {
            throw new JsonParseException("couldn't find description field");
        }
        if (bones == null) {
            throw new JsonParseException("couldn't find bones field");
        }
    }
}
