package io.github.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.Util;

import java.util.stream.StreamSupport;

public record KeyFrame(double[] pre, double[] post, String lerpMode) {
    public static KeyFrame deserialize(JsonElement json) throws JsonParseException {
        if (!json.isJsonObject()) {
            return new KeyFrame(null, StreamSupport.stream(json.getAsJsonArray().spliterator(), false).mapToDouble(JsonElement::getAsDouble).toArray(), "linear");
        }
        JsonElement preJson = json.getAsJsonObject().get("pre");
        double[] pre;
        if (preJson == null) {
            pre = null;
        } else if (preJson.isJsonArray()) {
            pre = StreamSupport.stream(preJson.getAsJsonArray().spliterator(), false).mapToDouble(JsonElement::getAsDouble).toArray();
        } else if (preJson.isJsonObject()) {
            pre = StreamSupport.stream(preJson.getAsJsonObject().get("vector").getAsJsonArray().spliterator(), false).mapToDouble(JsonElement::getAsDouble).toArray();
        } else {
            pre = null;
        }
        JsonElement postJson = json.getAsJsonObject().get("post");
        double[] post;
        if (postJson == null) {
            if (json.getAsJsonObject().has("vector")) {
                post = StreamSupport.stream(json.getAsJsonObject().get("vector").getAsJsonArray().spliterator(), false).mapToDouble(JsonElement::getAsDouble).toArray();
            } else {
                post = null;
            }
        } else if (postJson.isJsonArray()) {
            post = StreamSupport.stream(postJson.getAsJsonArray().spliterator(), false).mapToDouble(JsonElement::getAsDouble).toArray();
        } else if (postJson.isJsonObject()) {
            post = StreamSupport.stream(postJson.getAsJsonObject().get("vector").getAsJsonArray().spliterator(), false).mapToDouble(JsonElement::getAsDouble).toArray();
        } else {
            post = null;
        }
        JsonElement lerpModeJson = json.getAsJsonObject().get("lerp_mode");
        String lerpMode = Util.mapNullable(lerpModeJson, JsonElement::getAsString);
        return new KeyFrame(pre, post, lerpMode);
    }
}
