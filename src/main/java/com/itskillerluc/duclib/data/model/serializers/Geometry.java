package com.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

import java.util.Arrays;

public record Geometry(Description description, Bone[] bones) {
    public Geometry {
        if (description == null) {
            throw new JsonParseException("couldn't find description field");
        }
        if (bones == null) {
            throw new JsonParseException("couldn't find bones field");
        }
    }

    public boolean hasRoot(){
        return Arrays.stream(bones).anyMatch(bone -> bone.name().equals("root"));
    }
}
