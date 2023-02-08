package io.github.itskillerluc.duclib.client.model.definitions;

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

    /**
     * mirror the uv
     */
    @Override
    public @NotNull WingListBuilder mirror() {
        return this.mirror(true);
    }

    /**
     * mirror the uv... or not.
     * @param pMirror if you want to mirror the uv or not.
     */
    @Override
    public @NotNull WingListBuilder mirror(boolean pMirror) {
        this.mirror = pMirror;
        return this;
    }

    /**
     * add a cube
     * @param pComment useless...
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     * @param pCubeDeformation scale multiplier
     * @param pXTexOffs x uv offset
     * @param pYTexOffs y uv offset
     */
    @Override
    public @NotNull WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, int pDimensionX, int pDimensionY, int pDimensionZ, @NotNull CubeDeformation pCubeDeformation, int pXTexOffs, int pYTexOffs) {
        this.texOffs(pXTexOffs, pYTexOffs);
        this.wings.add(new WingDefinition(pComment, advancedUV, pOriginX, pOriginY, pOriginZ, (float)pDimensionX, (float)pDimensionY, (float)pDimensionZ, pCubeDeformation, this.mirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * add a cube
     * @param pComment useless...
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     * @param pCubeDeformation scale multiplier
     * @param advancedUVS The per face UV
     * @param mirror Mirror or not
     */
    public WingListBuilder addBox(String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, CubeDeformation pCubeDeformation, AdvancedUV[] advancedUVS, boolean mirror) {
        this.texOffs(advancedUVS);
        this.wings.add(new WingDefinition(pComment, advancedUVS, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pCubeDeformation, mirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * add a cube
     * @param pComment useless...
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     * @param pXTexOffs x uv offset
     * @param pYTexOffs y uv offset
     */
    @Override
    public @NotNull WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, int pDimensionX, int pDimensionY, int pDimensionZ, int pXTexOffs, int pYTexOffs) {
        this.texOffs(pXTexOffs, pYTexOffs);
        this.wings.add(new WingDefinition(pComment, this.advancedUV, pOriginX, pOriginY, pOriginZ, (float)pDimensionX, (float)pDimensionY, (float)pDimensionZ, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * add a cube
     * @param pComment useless...
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     * @param deformation scale multiplier
     * @param pXTexOffs x uv offset
     * @param pYTexOffs y uv offset
     * @param mirror mirror or not
     */
    public @NotNull WingListBuilder addBox(String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, CubeDeformation deformation, int pXTexOffs, int pYTexOffs, boolean mirror) {
        this.texOffs(pXTexOffs, pYTexOffs);
        this.wings.add(new WingDefinition(pComment, this.advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, deformation, mirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * add a cube
     * @param pComment useless...
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     * @param advancedUVs per face uv
     */
    public WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, int pDimensionX, int pDimensionY, int pDimensionZ, AdvancedUV[] advancedUVs) {
        this.texOffs(advancedUVs);
        this.wings.add(new WingDefinition(pComment, this.advancedUV, pOriginX, pOriginY, pOriginZ, (float)pDimensionX, (float)pDimensionY, (float)pDimensionZ, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * add a cube
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     */
    @Override
    public @NotNull WingListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
        this.wings.add(new WingDefinition(null, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * add a cube
     * @param pComment useless...
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     */
    @Override
    public @NotNull WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ) {
        this.wings.add(new WingDefinition(pComment, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * add a cube
     * @param pComment useless...
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     * @param pCubeDeformation scale multiplier
     */
    @Override
    public @NotNull WingListBuilder addBox(@NotNull String pComment, float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, @NotNull CubeDeformation pCubeDeformation) {
        this.wings.add(new WingDefinition(pComment, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pCubeDeformation, this.mirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * add a cube
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     * @param pMirror mirror or not
     */
    @Override
    public @NotNull WingListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, boolean pMirror) {
        this.wings.add(new WingDefinition(null, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, CubeDeformation.NONE, pMirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * add a cube
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     * @param pCubeDeformation scale multiplier
     * @param pTexScaleU UV x scale
     * @param pTexScaleV UV y scale
     */
    @Override
    public @NotNull WingListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, @NotNull CubeDeformation pCubeDeformation, float pTexScaleU, float pTexScaleV) {
        this.wings.add(new WingDefinition(null, this.advancedUV,  pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pCubeDeformation, this.mirror, pTexScaleU, pTexScaleV));
        return this;
    }

    /**
     * add a cube
     * @param pOriginX x pos
     * @param pOriginY y pos
     * @param pOriginZ z pos
     * @param pDimensionX x size
     * @param pDimensionY y size
     * @param pDimensionZ z size
     * @param pCubeDeformation scale multiplier
     */
    @Override
    public @NotNull WingListBuilder addBox(float pOriginX, float pOriginY, float pOriginZ, float pDimensionX, float pDimensionY, float pDimensionZ, @NotNull CubeDeformation pCubeDeformation) {
        this.wings.add(new WingDefinition(null, advancedUV, pOriginX, pOriginY, pOriginZ, pDimensionX, pDimensionY, pDimensionZ, pCubeDeformation, this.mirror, 1.0F, 1.0F));
        return this;
    }

    /**
     * @return get all of the cubes in this builder.
     */
    @Override
    public @NotNull List<CubeDefinition> getCubes() {
        return ImmutableList.copyOf(this.wings);
    }

    /**
     * @return get all the wings in this builder, you most of the time want to use this one instead of the getCubes()
     */
    public @NotNull List<WingDefinition> getWings() {
        return ImmutableList.copyOf(this.wings);
    }

    /**
     * static factory for WingListBuilder.
     * @return a new WingListBuilder instance.
     */
    public static WingListBuilder create() {
        return new WingListBuilder();
    }
}
