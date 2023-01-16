package com.itskillerluc.duclib.data.serializers;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

public record GeometryHolder(@SerializedName("minecraft:geometry") Geometry[] geometry) {
    public GeometryHolder {
        if (geometry == null) {
            throw new JsonParseException("couldn't find geometry field");
        }
    }
}
