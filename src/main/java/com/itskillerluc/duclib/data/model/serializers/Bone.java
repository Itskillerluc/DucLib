package com.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;
import org.apache.logging.log4j.LogManager;

import java.util.Objects;

public record Bone(String name, String parent, float[] pivot, float[] rotation, Cube[] cubes) {
    public Bone(String name, String parent, float[] pivot, float[] rotation, com.itskillerluc.duclib.data.model.serializers.Cube[] cubes) {
        if (name == null) {
            throw new JsonParseException("couldn't find name field");
        }
        this.pivot = pivot;
        if (pivot == null) {
            throw new JsonParseException("couldn't find pivot field");
        }
        this.rotation = Objects.requireNonNullElseGet(rotation, () -> new float[]{0, 0, 0});
        this.name = name;
        this.cubes = cubes;
        this.parent = parent;
    }
}
