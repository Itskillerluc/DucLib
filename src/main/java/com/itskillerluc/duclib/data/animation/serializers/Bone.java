package com.itskillerluc.duclib.data.animation.serializers;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record Bone(Map<String, KeyFrame> rotation, Map<String, KeyFrame> position, Map<String, KeyFrame> scale) {
    public static class adapter implements JsonDeserializer<Bone> {

        @Override
        public Bone deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<String, KeyFrame> rotations = new HashMap<>();
            Map<String, KeyFrame> positions = new HashMap<>();
            Map<String, KeyFrame> scales = new HashMap<>();

            if (json.getAsJsonObject().get("rotation") != null) {
                for (Map.Entry<String, JsonElement> rotation : json.getAsJsonObject().get("rotation").getAsJsonObject().asMap().entrySet()) {
                    getKeyframes(rotations, rotation);
                }
            }
            if (json.getAsJsonObject().get("position") != null) {
                for (Map.Entry<String, JsonElement> position : json.getAsJsonObject().get("position").getAsJsonObject().asMap().entrySet()) {
                    getKeyframes(positions, position);
                }
            }
            if (json.getAsJsonObject().get("scale") != null) {
                for (Map.Entry<String, JsonElement> scale : json.getAsJsonObject().get("scale").getAsJsonObject().asMap().entrySet()) {
                    getKeyframes(scales, scale);
                }
            }
            return new Bone(rotations, positions, scales);
        }

        private void getKeyframes(Map<String, KeyFrame> rotations, Map.Entry<String, JsonElement> rotation) {
            rotations.put(rotation.getKey(), rotation.getValue().isJsonArray() ? new KeyFrame((rotation.getValue().getAsJsonArray().asList().stream().mapToDouble(JsonElement::getAsDouble).toArray()), "linear") :
                    new KeyFrame(Optional.ofNullable(rotation.getValue().getAsJsonObject().get("vector")).orElse(rotation.getValue().getAsJsonObject().get("post")).getAsJsonArray().asList().stream().mapToDouble(JsonElement::getAsDouble).toArray(), rotation.getValue().getAsJsonObject().get("lerp_mode").getAsString()));
        }
    }
}
