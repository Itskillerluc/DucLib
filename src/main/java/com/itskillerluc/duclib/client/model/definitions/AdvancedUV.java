package com.itskillerluc.duclib.client.model.definitions;

import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.core.Direction;


/**
 * Used to store the per face uv values.
 * @param direction The Face of the cube.
 * @param uv uv offset
 * @param uvSize uv size from top left going right and down.
 */
public record AdvancedUV(Direction direction, UVPair uv, UVPair uvSize) {
}
