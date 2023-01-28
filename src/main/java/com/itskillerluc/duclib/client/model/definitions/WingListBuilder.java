package com.itskillerluc.duclib.client.model.definitions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * DucLib version of CubeListBuilder
 */
public class WingListBuilder extends CubeListBuilder {
    private final List<WingDefinition> wings = Lists.newArrayList();
    public AdvancedUV[] advancedUV;
    private boolean mirror;

    /**
     *use the one below instead unless you absolutely cant. this one will mess with the texture tho, so I don't recommend using it at all.
     */
    @Override
    public @NotNull WingListBuilder texOffs(int u, int v) {
        this.advancedUV = new AdvancedUV[]{
                new AdvancedUV(Direction.NORTH, new UVPair(u, v), null),
                new AdvancedUV(Direction.EAST, new UVPair(u, v), null),
                new AdvancedUV(Direction.SOUTH, new UVPair(u, v), null),
                new AdvancedUV(Direction.WEST, new UVPair(u, v), null),
                new AdvancedUV(Direction.UP, new UVPair(u, v), null),
                new AdvancedUV(Direction.DOWN, new UVPair(u, v), null)
        };
        return this;
    }

    /**
     * use this
     */
    public WingListBuilder texOffs(AdvancedUV[] advancedUVS) {
        this.advancedUV = advancedUVS;
        return this;
    }

    @Override
    public @NotNull WingListBuilder mirror() {
        return this.mirror(true);
    }

    @Override
    public @NotNull WingListBuilder mirror(boolean pMirror) {
        this.mirror = pMirror;
        return this;
    }

    @Override
    public @NotNull WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, int pDimensionX, int pDimensionY, int pDimensionZ, @NotNull CubeDeformation pCubeDeformation, int pXTexOffs, int pYTexOffs) {
        this.texOffs(pXTexOffs, pYTexOffs);
        this.wings.add(new WingDefinition(pComment, advancedUV, pOriginX, pOriginY, pOriginZ, (float)pDimensionX, (float)pDimensionY, (float)pDimensionZ, pCubeDeformation, this.mirror, 1.0F, 1.0F));
        return this;
    }

    public WingListBuilder addBox(String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, CubeDeformation pCubeDeformation, AdvancedUV[] advancedUVS, boolean mirror) {
        this.texOffs(advancedUVS);
        this.wings.add(new WingDefinition(pComment, advancedUVS, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pCubeDeformation, mirror, 1.0F, 1.0F));
        return this;
    }

    @Override
    public @NotNull WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, int pDimensionX, int pDimensionY, int pDimensionZ, int pXTexOffs, int pYTexOffs) {
        this.texOffs(pXTexOffs, pYTexOffs);
        this.wings.add(new WingDefinition(pComment, this.advancedUV, pOriginX, pOriginY, pOriginZ, (float)pDimensionX, (float)pDimensionY, (float)pDimensionZ, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F));
        return this;
    }

    public @NotNull WingListBuilder addBox(String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, CubeDeformation deformation, int pXTexOffs, int pYTexOffs, boolean mirror) {
        this.texOffs(pXTexOffs, pYTexOffs);
        this.wings.add(new WingDefinition(pComment, this.advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, deformation, mirror, 1.0F, 1.0F));
        return this;
    }

    public WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, int pDimensionX, int pDimensionY, int pDimensionZ, AdvancedUV[] advancedUVs) {
        this.texOffs(advancedUVs);
        this.wings.add(new WingDefinition(pComment, this.advancedUV, pOriginX, pOriginY, pOriginZ, (float)pDimensionX, (float)pDimensionY, (float)pDimensionZ, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F));
        return this;
    }

    @Override
    public @NotNull WingListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
        this.wings.add(new WingDefinition(null, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F));
        return this;
    }

    @Override
    public @NotNull WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
        this.wings.add(new WingDefinition(pComment, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F));
        return this;
    }

    @Override
    public @NotNull WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, @NotNull CubeDeformation pCubeDeformation) {
        this.wings.add(new WingDefinition(pComment, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pCubeDeformation, this.mirror, 1.0F, 1.0F));
        return this;
    }

    @Override
    public @NotNull WingListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, boolean pMirror) {
        this.wings.add(new WingDefinition(null, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, CubeDeformation.NONE, pMirror, 1.0F, 1.0F));
        return this;
    }

    @Override
    public @NotNull WingListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, @NotNull CubeDeformation pCubeDeformation, float pTexScaleU, float pTexScaleV) {
        this.wings.add(new WingDefinition(null, this.advancedUV,  pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pCubeDeformation, this.mirror, pTexScaleU, pTexScaleV));
        return this;
    }

    @Override
    public @NotNull WingListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, @NotNull CubeDeformation pCubeDeformation) {
        this.wings.add(new WingDefinition(null, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pCubeDeformation, this.mirror, 1.0F, 1.0F));
        return this;
    }

    @Override
    public @NotNull List<CubeDefinition> getCubes() {
        return ImmutableList.copyOf(this.wings);
    }

    public @NotNull List<WingDefinition> getWings() {
        return ImmutableList.copyOf(this.wings);
    }

    public static WingListBuilder create() {
        return new WingListBuilder();
    }
}
