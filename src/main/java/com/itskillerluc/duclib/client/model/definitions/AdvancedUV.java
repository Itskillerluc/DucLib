package com.itskillerluc.duclib.client.model.definitions;

import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.core.Direction;

public record AdvancedUV(Direction direction, UVPair uv, UVPair uvSize) {
}
