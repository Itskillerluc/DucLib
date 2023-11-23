package io.github.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public record KeyFrame(double[] pre, double[] post, String lerpMode) {
    public static KeyFrame deserialize(JsonElement json) throws JsonParseException {
        if (!json.isJsonObject()) {
            return new KeyFrame(null, json.getAsJsonArray().asList().stream().mapToDouble(JsonElement::getAsDouble).toArray(), "linear");
        }
        JsonElement preJson = json.getAsJsonObject().get("pre");
        double[] pre;
        if (preJson == null) {
            pre = null;
        } else if (preJson.isJsonArray()) {
            pre = preJson.getAsJsonArray().asList().stream().mapToDouble(JsonElement::getAsDouble).toArray();
        } else if (preJson.isJsonObject()) {
            pre = preJson.getAsJsonObject().get("vector").getAsJsonArray().asList().stream().mapToDouble(JsonElement::getAsDouble).toArray();
        } else {
            pre = null;
        }
        JsonElement postJson = json.getAsJsonObject().get("post");
        double[] post;
        if (postJson == null) {
            if (json.getAsJsonObject().has("vector")) {
                post = json.getAsJsonObject().get("vector").getAsJsonArray().asList().stream().mapToDouble(JsonElement::getAsDouble).toArray();
            } else {
                post = null;
            }
        } else if (postJson.isJsonArray()) {
            post = postJson.getAsJsonArray().asList().stream().mapToDouble(JsonElement::getAsDouble).toArray();
        } else if (postJson.isJsonObject()) {
            post = postJson.getAsJsonObject().get("vector").getAsJsonArray().asList().stream().mapToDouble(JsonElement::getAsDouble).toArray();
        } else {
            post = null;
        }
        JsonElement lerpModeJson = json.getAsJsonObject().get("lerp_mode");
        String lerpMode = lerpModeJson != null ? lerpModeJson.getAsString() : "linear";
        return new KeyFrame(pre, post, lerpMode);
    }
}
