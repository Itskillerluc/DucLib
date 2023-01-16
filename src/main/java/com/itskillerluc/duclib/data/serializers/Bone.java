package com.itskillerluc.duclib.data.serializers;

import com.google.gson.JsonParseException;

public record Bone(String name, String parent, float[] pivot, Cube[] cubes) {
    public Bone {
        if (name == null) {
            throw new JsonParseException("couldn't find name field");
        }
        if (!name.equals("root") && parent == null){
            throw new JsonParseException("couldn't find parent field (parent field is required on non root bones)");
        }
        if (pivot.length == 0) {
            throw new JsonParseException("couldn't find pivot field");
        }
        if (!name.equals("root") && cubes == null) {
            throw new JsonParseException("couldn't find cubes field");
        }
    }
}
