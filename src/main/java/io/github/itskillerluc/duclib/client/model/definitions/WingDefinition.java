package io.github.itskillerluc.duclib.client.model.definitions;

import io.github.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.UVPair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

/**
 * DucLib version of CubeDefinition
 */
public class WingDefinition extends CubeDefinition {
    @javax.annotation.Nullable
    private final String comment;
    private final Vector3f origin;
    private final Vector3f dimensions;
    private final CubeDeformation grow;
    private final boolean mirror;
    private final AdvancedUV[] featherUVs;
    private final UVPair texScale;

    protected WingDefinition(@Nullable String pComment, AdvancedUV[] featherUVs, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, CubeDeformation pGrow, boolean pMirror, float pTexScaleU, float pTexScaleV) {
        super(pComment, featherUVs[0].uv().u(), featherUVs[0].uv().v(), pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pGrow, pMirror, pTexScaleU, pTexScaleV);
        this.comment = pComment;
        this.featherUVs = featherUVs;
        this.origin = new Vector3f(pOriginX, pOriginY, pOriginZ);
        this.dimensions = new Vector3f(pDimensionX, pDimensionY, pDimensionZ);
        this.grow = pGrow;
        this.mirror = pMirror;
        this.texScale = new UVPair(pTexScaleU, pTexScaleV);
    }

    /**
     * Bakes teh WingDefinition into an actual wing.
     * @param pTexWidth texture width
     * @param pTexHeight texture height
     * @return the baked Ducling wing.
     */
    @Override
    public Ducling.@NotNull Wing bake(int pTexWidth, int pTexHeight) {
        return this.featherUVs[0].uvSize() != null ? new Ducling.Wing(this.featherUVs,this.origin.x(), this.origin.y(), this.origin.z(), this.dimensions.x(), this.dimensions.y(), this.dimensions.z(), this.grow.growX, this.grow.growY, this.grow.growZ, this.mirror, (float)pTexWidth * this.texScale.u(), (float)pTexHeight * this.texScale.v()) :
                new Ducling.Wing(((int) this.featherUVs[0].uv().u()), ((int) this.featherUVs[0].uv().v()), this.origin.x(), this.origin.y(), this.origin.z(), this.dimensions.x(), this.dimensions.y(), this.dimensions.z(), this.grow.growX, this.grow.growY, this.grow.growZ, this.mirror, (float)pTexWidth * this.texScale.u(), (float)pTexHeight * this.texScale.v());
    }
}
