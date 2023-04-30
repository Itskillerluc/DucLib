package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("unused")
public final class Description {
    private String identifier;
    private int textureWidth;
    private int textureHeight;
    private float visibleBoundsWidth;
    private float visibleBoundsHeight;
    private float[] visibleBoundsOffset;

    public Description(String identifier, int textureWidth, int textureHeight, float visibleBoundsWidth, float visibleBoundsHeight, float[] visibleBoundsOffset) {
        if (identifier == null) {
            throw new JsonParseException("couldn't find identifier field");
        }
        if (textureHeight == 0) {
            throw new JsonParseException("texture_height can't be 0");
        }
        if (textureWidth == 0) {
            throw new JsonParseException("texture_width can't be 0");
        }
        this.identifier = identifier;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.visibleBoundsWidth = visibleBoundsWidth;
        this.visibleBoundsHeight = visibleBoundsHeight;
        this.visibleBoundsOffset = visibleBoundsOffset;
    }

    public String identifier() {
        return identifier;
    }

    public int textureWidth() {
        return textureWidth;
    }

    public int textureHeight() {
        return textureHeight;
    }

    public float visibleBoundsWidth() {
        return visibleBoundsWidth;
    }

    public float visibleBoundsHeight() {
        return visibleBoundsHeight;
    }

    public float[] visibleBoundsOffset() {
        return visibleBoundsOffset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Description) obj;
        return Objects.equals(this.identifier, that.identifier) &&
                this.textureWidth == that.textureWidth &&
                this.textureHeight == that.textureHeight &&
                Float.floatToIntBits(this.visibleBoundsWidth) == Float.floatToIntBits(that.visibleBoundsWidth) &&
                Float.floatToIntBits(this.visibleBoundsHeight) == Float.floatToIntBits(that.visibleBoundsHeight) &&
                Arrays.equals(this.visibleBoundsOffset, that.visibleBoundsOffset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, textureWidth, textureHeight, visibleBoundsWidth, visibleBoundsHeight, Arrays.hashCode(visibleBoundsOffset));
    }

    @Override
    public String toString() {
        return "Description[" +
                "identifier=" + identifier + ", " +
                "textureWidth=" + textureWidth + ", " +
                "textureHeight=" + textureHeight + ", " +
                "visibleBoundsWidth=" + visibleBoundsWidth + ", " +
                "visibleBoundsHeight=" + visibleBoundsHeight + ", " +
                "visibleBoundsOffset=" + Arrays.toString(visibleBoundsOffset) + ']';
    }

}
