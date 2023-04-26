package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

import java.util.Arrays;
import java.util.Objects;

public final class FaceUV {
    private final int[] uv;
    private final int[] uvSize;

    public FaceUV(int[] uv, int[] uvSize) {
        if (uv.length == 0) {
            throw new JsonParseException("couldn't find uv field");
        }
        if (uvSize.length == 0) {
            throw new JsonParseException("couldn't find uv_size field");
        }
        this.uv = uv;
        this.uvSize = uvSize;
    }

    public int[] uv() {
        return uv;
    }

    public int[] uvSize() {
        return uvSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FaceUV) obj;
        return Arrays.equals(this.uv, that.uv) &&
                Arrays.equals(this.uvSize, that.uvSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(uv), Arrays.hashCode(uvSize));
    }

    @Override
    public String toString() {
        return "FaceUV[" +
                "uv=" + Arrays.toString(uv) + ", " +
                "uvSize=" + Arrays.toString(uvSize) + ']';
    }

}
