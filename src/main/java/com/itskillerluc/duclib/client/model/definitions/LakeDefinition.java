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

    /**
     * @return The baked Lake definition.
     */
    @Override
    public @NotNull Ducling bakeRoot() {
        return this.mesh.getRoot().bake(this.material.getXTexSize(), this.material.getYTexSize());
    }

    /**
     * Static factory method for LakeDefinition.
     * @param pMesh The Definition containing all the DuclingDefinitions.
     * @param pTexWidth texture width
     * @param pTexHeight texture height
     * @return a new instance of a LakeDefinition.
     */
    public static LakeDefinition create(PondDefinition pMesh, int pTexWidth, int pTexHeight) {
        return new LakeDefinition(pMesh, new FeatherMaterialDefinition(pTexWidth, pTexHeight));
    }
}
