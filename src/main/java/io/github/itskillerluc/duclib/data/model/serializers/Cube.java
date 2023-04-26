package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public final class Cube {
    private final float[] origin;
    private final float[] size;
    private final float[] rotation;
    private final float[] pivot;
    private final float inflate;
    private final Either<UV, int[]> uv;
    private final boolean mirror;

    public Cube(float[] origin, float[] size, float[] rotation, float[] pivot, float inflate, Either<UV, int[]> uv, boolean mirror) {
        this.origin = origin;
        this.size = size;
        this.rotation = rotation;
        this.pivot = pivot;
        this.inflate = inflate;
        this.uv = uv;
        this.mirror = mirror;
    }

    public float[] origin() {
        return origin;
    }

    public float[] size() {
        return size;
    }

    public float[] rotation() {
        return rotation;
    }

    public float[] pivot() {
        return pivot;
    }

    public float inflate() {
        return inflate;
    }

    public Either<UV, int[]> uv() {
        return uv;
    }

    public boolean mirror() {
        return mirror;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Cube) obj;
        return Arrays.equals(this.origin, that.origin) &&
                Arrays.equals(this.size, that.size) &&
                Arrays.equals(this.rotation, that.rotation) &&
                Arrays.equals(this.pivot, that.pivot) &&
                Float.floatToIntBits(this.inflate) == Float.floatToIntBits(that.inflate) &&
                Objects.equals(this.uv, that.uv) &&
                this.mirror == that.mirror;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(origin), Arrays.hashCode(size), Arrays.hashCode(rotation), Arrays.hashCode(pivot), inflate, uv, mirror);
    }

    @Override
    public String toString() {
        return "Cube[" +
                "origin=" + Arrays.toString(origin) + ", " +
                "size=" + Arrays.toString(size) + ", " +
                "rotation=" + Arrays.toString(rotation) + ", " +
                "pivot=" + Arrays.toString(pivot) + ", " +
                "inflate=" + inflate + ", " +
                "uv=" + uv + ", " +
                "mirror=" + mirror + ']';
    }

    public static class adapter implements JsonDeserializer<Cube> {
        private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        @Override
        public Cube deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            var parent = json.getAsJsonObject();
            float[] origin, size, rotation, pivot;
            JsonArray originArray, sizeArray, rotationArray, pivotArray;
            float inflate = 0;
            boolean mirror = false;
            Either<UV, int[]> uv;
            if (parent.has("inflate")) {
                inflate = parent.get("inflate").getAsFloat();
            }
            if (parent.has("mirror")) {
                mirror = parent.get("mirror").getAsBoolean();
            }
            if (parent.has("origin")) {
                originArray = parent.getAsJsonArray("origin");
                origin = new float[]{originArray.get(0).getAsFloat(), originArray.get(1).getAsFloat(), originArray.get(2).getAsFloat()};
            } else {
                origin = null;
            }
            if (parent.has("size")) {
                sizeArray = parent.getAsJsonArray("size");
                size = new float[]{sizeArray.get(0).getAsFloat(), sizeArray.get(1).getAsFloat(), sizeArray.get(2).getAsFloat()};
            } else {
                size = null;
            }
            if (parent.has("rotation")) {
                rotationArray = parent.getAsJsonArray("rotation");
                rotation = new float[]{rotationArray.get(0).getAsFloat(), rotationArray.get(1).getAsFloat(), rotationArray.get(2).getAsFloat()};
            } else {
                rotation = null;
            }
            if (parent.has("pivot")) {
                pivotArray = parent.getAsJsonArray("pivot");
                pivot = new float[]{pivotArray.get(0).getAsFloat(), pivotArray.get(1).getAsFloat(), pivotArray.get(2).getAsFloat()};
            } else {
                pivot = null;
            }

            if (parent.has("uv")) {
                if (parent.get("uv").isJsonArray()) {
                    uv = Either.right(new int[]{parent.getAsJsonArray("uv").get(0).getAsInt(), parent.getAsJsonArray("uv").get(1).getAsInt()});
                } else {
                    uv = Either.left(GSON.fromJson(parent.get("uv"), UV.class));
                }
            } else {
                throw new JsonParseException("couldn't find uv or uv2 field");
            }

            if (origin == null) {
                throw new JsonParseException("couldn't find origin field");
            }
            if (size == null) {
                throw new JsonParseException("couldn't find size field");
            }
            return new Cube(origin, size, rotation, pivot, inflate, uv, mirror);
        }
    }
}
