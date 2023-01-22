package com.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

public record Cube(float[] origin, float[] size, float[] rotation, float[] pivot, float inflate, @SerializedName("uv2") int[] uv2, @SerializedName("uv") UV uv, boolean mirror) {
    public Cube {
        if (origin == null) {
            throw new JsonParseException("couldn't find origin field");
        }
        if (size == null) {
            throw new JsonParseException("couldn't find size field");
        }
        if (uv == null && uv2 == null) {
            throw new JsonParseException("couldn't find uv or uv2 field");
        }
    }
}
