package com.itskillerluc.duclib.client.model.definitions;

import com.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * DucLib version of LayerDefinition
 */
public class LakeDefinition extends LayerDefinition {
    private final PondDefinition mesh;
    private final FeatherMaterialDefinition material;

    public LakeDefinition(PondDefinition pMesh, FeatherMaterialDefinition pMaterial) {
        super(pMesh, pMaterial);
        this.mesh = pMesh;
        this.material = pMaterial;
    }

    @Override
    public @NotNull Ducling bakeRoot() {
        return this.mesh.getRoot().bake(this.material.getXTexSize(), this.material.getYTexSize());
    }

    public static LakeDefinition create(PondDefinition pMesh, int pTexWidth, int pTexHeight) {
        return new LakeDefinition(pMesh, new FeatherMaterialDefinition(pTexWidth, pTexHeight));
    }
}
