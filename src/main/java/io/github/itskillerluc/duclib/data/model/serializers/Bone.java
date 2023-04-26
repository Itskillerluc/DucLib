package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

import java.util.Arrays;
import java.util.Objects;

public final class Bone {
    private final String name;
    private final String parent;
    private final float[] pivot;
    private final float[] rotation;
    private final Cube[] cubes;

    public Bone(String name, String parent, float[] pivot, float[] rotation, Cube[] cubes) {
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

    public String name() {
        return name;
    }

    public String parent() {
        return parent;
    }

    public float[] pivot() {
        return pivot;
    }

    public float[] rotation() {
        return rotation;
    }

    public Cube[] cubes() {
        return cubes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Bone) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.parent, that.parent) &&
                Arrays.equals(this.pivot, that.pivot) &&
                Arrays.equals(this.rotation, that.rotation) &&
                Arrays.equals(this.cubes, that.cubes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parent, Arrays.hashCode(pivot), Arrays.hashCode(rotation), Arrays.hashCode(cubes));
    }

    @Override
    public String toString() {
        return "Bone[" +
                "name=" + name + ", " +
                "parent=" + parent + ", " +
                "pivot=" + Arrays.toString(pivot) + ", " +
                "rotation=" + Arrays.toString(rotation) + ", " +
                "cubes=" + Arrays.toString(cubes) + ']';
    }

}
