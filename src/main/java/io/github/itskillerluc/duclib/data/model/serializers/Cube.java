package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.*;
import com.mojang.datafixers.util.Either;

import java.lang.reflect.Type;

public record Cube(float[] origin, float[] size, float[] rotation, float[] pivot, float inflate, Either<UV, int[]> uv, boolean mirror) {
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
            if (parent.has("inflate")){
                inflate = parent.get("inflate").getAsFloat();
            }
            if (parent.has("mirror")){
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
                if (parent.get("uv").isJsonArray()){
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
