package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

public record UV(FaceUV north, FaceUV east, FaceUV south, FaceUV west, FaceUV up, FaceUV down) {
    public UV {
        if (north == null) {
            throw new JsonParseException("couldn't find north field");
        }
        if (east == null) {
            throw new JsonParseException("couldn't find east field");
        }
        if (south == null) {
            throw new JsonParseException("couldn't find south field");
        }
        if (west == null) {
            throw new JsonParseException("couldn't find west field");
        }
        if (up == null) {
            throw new JsonParseException("couldn't find up field");
        }
        if (down == null) {
            throw new JsonParseException("couldn't find down field");
        }
    }
}
