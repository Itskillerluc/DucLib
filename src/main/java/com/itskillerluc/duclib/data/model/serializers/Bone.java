package com.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;
import org.apache.logging.log4j.LogManager;

import java.util.Objects;

public record Bone(String name, String parent, float[] pivot, float[] rotation, Cube[] cubes) {
    public Bone(String name, String parent, float[] pivot, float[] rotation, com.itskillerluc.duclib.data.model.serializers.Cube[] cubes) {
        if (name == null) {
            throw new JsonParseException("couldn't find name field");
        }
        if (!name.equals("root") && parent == null){
            LogManager.getLogger().warn("couldn't find parent field (parent field is required on non root bones), defaulting to root.");
            this.parent = "root";
            this.pivot = new float[]{pivot[0], -(24 - pivot[1]), pivot[2]};
        } else {
            this.parent = parent;
            this.pivot = pivot;
        }
        if (pivot == null) {
            throw new JsonParseException("couldn't find pivot field");
        }
        this.rotation = Objects.requireNonNullElseGet(rotation, () -> new float[]{0, 0, 0});
        this.name = name;
        this.cubes = cubes;
    }
}
