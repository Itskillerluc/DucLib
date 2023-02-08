package io.github.itskillerluc.duclib.client.model.definitions;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * DucLib version of MeshDefinition
 */
public class PondDefinition extends MeshDefinition {
    private final DuclingDefinition root = new DuclingDefinition(ImmutableList.of(), PartPose.ZERO);

    @Override
    public @NotNull DuclingDefinition getRoot() {
        return this.root;
    }
}
