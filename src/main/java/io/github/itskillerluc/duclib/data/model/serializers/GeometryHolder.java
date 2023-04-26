package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public final class GeometryHolder {
    @SerializedName("minecraft:geometry")
    private final Geometry[] geometry;
    private final boolean override;

    public GeometryHolder(Geometry[] geometry, boolean override) {
        if (geometry == null) {
            throw new JsonParseException("couldn't find geometry field");
        }
        this.geometry = geometry;
        this.override = override;
    }

    @SerializedName("minecraft:geometry")
    public Geometry[] geometry() {
        return geometry;
    }

    public boolean override() {
        return override;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GeometryHolder) obj;
        return Arrays.equals(this.geometry, that.geometry) &&
                this.override == that.override;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(geometry), override);
    }

    @Override
    public String toString() {
        return "GeometryHolder[" +
                "geometry=" + Arrays.toString(geometry) + ", " +
                "override=" + override + ']';
    }

}
