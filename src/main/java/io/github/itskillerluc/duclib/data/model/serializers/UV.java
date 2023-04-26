package io.github.itskillerluc.duclib.data.model.serializers;

import com.google.gson.JsonParseException;

import java.util.Objects;

public final class UV {
    private final FaceUV north;
    private final FaceUV east;
    private final FaceUV south;
    private final FaceUV west;
    private final FaceUV up;
    private final FaceUV down;

    public UV(FaceUV north, FaceUV east, FaceUV south, FaceUV west, FaceUV up, FaceUV down) {
        if (north == null) {
            throw new JsonParseException("couldn't find north field");
        }
        if (east == null) {
            throw new JsonParseException("couldn't find east field");
        }
        if (south == null) {
            throw new JsonParseException("couldn't find south field");
        }
        if (west == null) {
            throw new JsonParseException("couldn't find west field");
        }
        if (up == null) {
            throw new JsonParseException("couldn't find up field");
        }
        if (down == null) {
            throw new JsonParseException("couldn't find down field");
        }
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.up = up;
        this.down = down;
    }

    public FaceUV north() {
        return north;
    }

    public FaceUV east() {
        return east;
    }

    public FaceUV south() {
        return south;
    }

    public FaceUV west() {
        return west;
    }

    public FaceUV up() {
        return up;
    }

    public FaceUV down() {
        return down;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UV) obj;
        return Objects.equals(this.north, that.north) &&
                Objects.equals(this.east, that.east) &&
                Objects.equals(this.south, that.south) &&
                Objects.equals(this.west, that.west) &&
                Objects.equals(this.up, that.up) &&
                Objects.equals(this.down, that.down);
    }

    @Override
    public int hashCode() {
        return Objects.hash(north, east, south, west, up, down);
    }

    @Override
    public String toString() {
        return "UV[" +
                "north=" + north + ", " +
                "east=" + east + ", " +
                "south=" + south + ", " +
                "west=" + west + ", " +
                "up=" + up + ", " +
                "down=" + down + ']';
    }

}
