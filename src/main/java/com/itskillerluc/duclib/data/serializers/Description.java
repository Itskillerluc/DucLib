package com.itskillerluc.duclib.data.serializers;

import com.google.gson.JsonParseException;

public record Description(String identifier, int textureWidth, int textureHeight, float visibleBoundsWidth, float visibleBoundsHeight, float[] visibleBoundsOffset) {
    public Description {
        if (identifier == null) {
            throw new JsonParseException("couldn't find identifier field");
        }
        if (textureHeight == 0) {
            throw new JsonParseException("texture_height can't be 0");
        }
        if (textureWidth == 0) {
            throw new JsonParseException("texture_width can't be 0");
        }
    }
}
