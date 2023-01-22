package com.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

public record FaceUV(int[] uv, int[] uvSize) {
    public FaceUV {
        if (uv.length == 0) {
            throw new JsonParseException("couldn't find uv field");
        }
        if (uvSize.length == 0) {
            throw new JsonParseException("couldn't find uv_size field");
        }
    }
}
