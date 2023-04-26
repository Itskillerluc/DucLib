package io.github.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.Util;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.StreamSupport;

public final class KeyFrame {
    private final double[] pre;
    private final double[] post;
    private final String lerpMode;

    public KeyFrame(double[] pre, double[] post, String lerpMode) {
        this.pre = pre;
        this.post = post;
        this.lerpMode = lerpMode;
    }

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

    public double[] pre() {
        return pre;
    }

    public double[] post() {
        return post;
    }

    public String lerpMode() {
        return lerpMode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (KeyFrame) obj;
        return Arrays.equals(this.pre, that.pre) &&
                Arrays.equals(this.post, that.post) &&
                Objects.equals(this.lerpMode, that.lerpMode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(pre), Arrays.hashCode(post), lerpMode);
    }

    @Override
    public String toString() {
        return "KeyFrame[" +
                "pre=" + Arrays.toString(pre) + ", " +
                "post=" + Arrays.toString(post) + ", " +
                "lerpMode=" + lerpMode + ']';
    }

}
