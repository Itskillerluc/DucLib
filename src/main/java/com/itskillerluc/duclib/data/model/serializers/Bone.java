package com.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

import java.util.Objects;

public record Bone(String name, String parent, float[] pivot, float[] rotation, Cube[] cubes) {
    public Bone(String name, String parent, float[] pivot, float[] rotation, com.itskillerluc.duclib.data.model.serializers.Cube[] cubes) {
        if (name == null) {
            throw new JsonParseException("couldn't find name field");
        }
        if (!name.equals("root") && parent == null){
            throw new JsonParseException("couldn't find parent field (parent field is required on non root bones)");
        }
        if (pivot == null) {
            throw new JsonParseException("couldn't find pivot field");
        }
        this.rotation = Objects.requireNonNullElseGet(rotation, () -> new float[]{0, 0, 0});
        if (cubes == null) {
            if (!name.equals("root")) {
                this.parent = "root";
            } else {
                this.parent = null;
            }
        } else {
            this.parent = parent;
        }
        this.name = name;
        this.pivot = pivot;
        this.cubes = cubes;
    }
}
