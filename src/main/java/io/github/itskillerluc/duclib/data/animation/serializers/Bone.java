package io.github.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Bone {
    private final Map<String, KeyFrame> rotation;
    private final Map<String, KeyFrame> position;
    private final Map<String, KeyFrame> scale;

    public Bone(Map<String, KeyFrame> rotation, Map<String, KeyFrame> position, Map<String, KeyFrame> scale) {
        this.rotation = rotation;
        this.position = position;
        this.scale = scale;
    }

    public Map<String, KeyFrame> rotation() {
        return rotation;
    }

    public Map<String, KeyFrame> position() {
        return position;
    }

    public Map<String, KeyFrame> scale() {
        return scale;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Bone) obj;
        return Objects.equals(this.rotation, that.rotation) &&
                Objects.equals(this.position, that.position) &&
                Objects.equals(this.scale, that.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rotation, position, scale);
    }

    @Override
    public String toString() {
        return "Bone[" +
                "rotation=" + rotation + ", " +
                "position=" + position + ", " +
                "scale=" + scale + ']';
    }

    public static class adapter implements JsonDeserializer<Bone> {

        @Override
        public Bone deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<String, KeyFrame> rotations = new HashMap<>();
            Map<String, KeyFrame> positions = new HashMap<>();
            Map<String, KeyFrame> scales = new HashMap<>();

            if (json.getAsJsonObject().get("rotation") != null) {
                if (json.getAsJsonObject().get("rotation").isJsonObject()) {
                    for (Map.Entry<String, JsonElement> rotation : json.getAsJsonObject().get("rotation").getAsJsonObject().entrySet()) {
                        rotations.put(rotation.getKey().equals("vector") ? "0.0" : rotation.getKey(), createFrame(rotation));
                    }
                } else {
                    rotations.put("0.0", new KeyFrame(null, new double[]{json.getAsJsonObject().get("rotation").getAsJsonArray().get(0).getAsDouble(), json.getAsJsonObject().get("rotation").getAsJsonArray().get(1).getAsDouble(), json.getAsJsonObject().get("rotation").getAsJsonArray().get(2).getAsDouble()}, "linear"));
                }
            }
            if (json.getAsJsonObject().get("position") != null) {
                if (json.getAsJsonObject().get("position").isJsonObject()) {
                    for (Map.Entry<String, JsonElement> position : json.getAsJsonObject().get("position").getAsJsonObject().entrySet()) {
                        positions.put(position.getKey().equals("vector") ? "0.0" : position.getKey(), createFrame(position));
                    }
                } else {
                    positions.put("0.0", new KeyFrame(null, new double[]{json.getAsJsonObject().get("position").getAsJsonArray().get(0).getAsDouble(), json.getAsJsonObject().get("position").getAsJsonArray().get(1).getAsDouble(), json.getAsJsonObject().get("position").getAsJsonArray().get(2).getAsDouble()}, "linear"));
                }
            }
            if (json.getAsJsonObject().get("scale") != null) {
                if (json.getAsJsonObject().get("scale").isJsonObject()) {
                    for (Map.Entry<String, JsonElement> scale : json.getAsJsonObject().get("scale").getAsJsonObject().entrySet()) {
                        scales.put(scale.getKey().equals("vector") ? "0.0" : scale.getKey(), createFrame(scale));
                    }
                } else {
                    scales.put("0.0", new KeyFrame(null, new double[]{json.getAsJsonObject().get("scale").getAsJsonArray().get(0).getAsDouble(), json.getAsJsonObject().get("scale").getAsJsonArray().get(1).getAsDouble(), json.getAsJsonObject().get("scale").getAsJsonArray().get(2).getAsDouble()}, "linear"));
                }
            }
            return new Bone(rotations, positions, scales);
        }

        private KeyFrame createFrame(Map.Entry<String, JsonElement> element) {
            JsonObject object = new JsonObject();
            object.add(element.getKey(), element.getValue());
            return KeyFrame.deserialize(element.getValue());
        }
    }
}
