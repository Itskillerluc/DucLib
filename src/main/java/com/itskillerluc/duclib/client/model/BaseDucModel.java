package com.itskillerluc.duclib.client.model;

import com.itskillerluc.duclib.client.model.definitions.*;
import com.itskillerluc.duclib.data.DucLibModelLoader;
import com.itskillerluc.duclib.data.serializers.Bone;
import com.itskillerluc.duclib.data.serializers.Cube;
import com.itskillerluc.duclib.data.serializers.Geometry;
import com.itskillerluc.duclib.data.serializers.GeometryHolder;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * this is here in case you don't want any of the other stuff and just want a raw model.
 */
public abstract class BaseDucModel extends Model {
    public BaseDucModel(Function<ResourceLocation, RenderType> pRenderType) {
        super(pRenderType);
    }

    public static LakeDefinition generateLakeDefinition(ResourceLocation entity){
        GeometryHolder holder = DucLibModelLoader.getModel(entity);
        Geometry geometry = holder.geometry()[0];
        List<Bone> bones = Arrays.stream(geometry.bones()).collect(Collectors.toCollection(ArrayList::new));
        PondDefinition pondDefinition = new PondDefinition();
        float offset = 0;

        if (geometry.hasRoot()) {
            Bone root = bones.stream().filter(bone -> bone.name().equals("root")).findFirst().orElseThrow();
            bones.removeIf(bone -> bone.name().equals("root"));
            offset = root.pivot()[1];
        }

        DuclingDefinition root = pondDefinition.getRoot().addOrReplaceChild("root", WingListBuilder.create(), PartPose.offset(0, 24 - offset , 0));
        int count = 0;
        if (bones.get(0).cubes()[0].uv2() == null){
            generateLakeDefinitionRecursively("root", count, bones, root, new float[]{0, 0, 0});
        } else {
            generateLakeDefinitionRecursivelyUV2("root", count, bones, root, new float[]{0, 0, 0});
        }

        return LakeDefinition.create(pondDefinition, geometry.description().textureWidth(), geometry.description().textureHeight());
    }

    private static DuclingDefinition generateLakeDefinitionRecursively(String parent, int count, List<Bone> bones, DuclingDefinition parentDucling, float[] offset){
        for (Bone bone : bones) {
            if (bone.parent().equals(parent)){
                DuclingDefinition parentDef = parentDucling.addOrReplaceChild(bone.name(), createWings(bone, parentDucling, count, offset), PartPose.offsetAndRotation(bone.pivot()[0] - offset[0], bone.pivot()[1] - offset[1], bone.pivot()[2] - offset[2], Mth.DEG_TO_RAD * bone.rotation()[0], Mth.DEG_TO_RAD * bone.rotation()[1], Mth.DEG_TO_RAD * bone.rotation()[2]));
                offset = new float[]{offset[0] + bone.pivot()[0], offset[1] + bone.pivot()[1], offset[2] + bone.pivot()[2]};
                generateLakeDefinitionRecursively(bone.name(), count, bones, parentDef, offset);
                return parentDef;
            }
        }
        return parentDucling;
    }

    private static DuclingDefinition generateLakeDefinitionRecursivelyUV2(String parent, int count, List<Bone> bones, DuclingDefinition parentDucling, float[] offset){
        for (Bone bone : bones) {
            if (bone.parent().equals(parent)){
                DuclingDefinition parentDef = parentDucling.addOrReplaceChild(bone.name(), createWingsUV2(bone, parentDucling, count, offset), PartPose.offsetAndRotation(bone.pivot()[0] - offset[0], bone.pivot()[1] - offset[1], bone.pivot()[2] - offset[2], Mth.DEG_TO_RAD * bone.rotation()[0], Mth.DEG_TO_RAD * bone.rotation()[1], Mth.DEG_TO_RAD * bone.rotation()[2]));
                offset = new float[]{offset[0] + bone.pivot()[0], offset[1] + bone.pivot()[1], offset[2] + bone.pivot()[2]};
                generateLakeDefinitionRecursivelyUV2(bone.name(), count, bones, parentDef, offset);
                return parentDef;
            }
        }
        return parentDucling;
    }

    private static WingListBuilder createWings(Bone bone, DuclingDefinition parentDucling, int count, float[] offset){
        WingListBuilder builder = WingListBuilder.create();
        for (Cube cube : bone.cubes()) {
            if (cube.rotation() == null) {
                builder.addBox(null, cube.origin()[0], cube.origin()[1], cube.origin()[2],
                        cube.size()[0], cube.size()[1], cube.size()[2],
                        new CubeDeformation(cube.inflate()), new AdvancedUV[]{
                                new AdvancedUV(Direction.NORTH, new UVPair(cube.uv().north().uv()[0], cube.uv().north().uv()[1]), new UVPair(cube.uv().north().uvSize()[0], cube.uv().north().uvSize()[1])),
                                new AdvancedUV(Direction.EAST, new UVPair(cube.uv().east().uv()[0], cube.uv().east().uv()[1]), new UVPair(cube.uv().east().uvSize()[0], cube.uv().east().uvSize()[1])),
                                new AdvancedUV(Direction.SOUTH, new UVPair(cube.uv().south().uv()[0], cube.uv().south().uv()[1]), new UVPair(cube.uv().south().uvSize()[0], cube.uv().south().uvSize()[1])),
                                new AdvancedUV(Direction.WEST, new UVPair(cube.uv().west().uv()[0], cube.uv().west().uv()[1]), new UVPair(cube.uv().west().uvSize()[0], cube.uv().west().uvSize()[1])),
                                new AdvancedUV(Direction.UP, new UVPair(cube.uv().up().uv()[0], cube.uv().up().uv()[1]), new UVPair(cube.uv().up().uvSize()[0], cube.uv().up().uvSize()[1])),
                                new AdvancedUV(Direction.DOWN, new UVPair(cube.uv().down().uv()[0], cube.uv().down().uv()[1]), new UVPair(cube.uv().down().uvSize()[0], cube.uv().down().uvSize()[1]))
                        }, cube.mirror());
            } else {
                parentDucling.addOrReplaceChild("cube_" + count, createWings(new Bone("", "", new float[]{}, null, new Cube[]{new Cube(cube.origin(), cube.size(), null, null, cube.inflate(), cube.uv2(), cube.uv(), cube.mirror())}), parentDucling, ++count, offset), PartPose.offsetAndRotation(cube.pivot()[0] - offset[0], cube.pivot()[1] - offset[1], cube.pivot()[2] - offset[2], Mth.DEG_TO_RAD * cube.rotation()[0], Mth.DEG_TO_RAD * cube.rotation()[1], Mth.DEG_TO_RAD * cube.rotation()[2]));
            }
        }
        return builder;
    }

    private static WingListBuilder createWingsUV2(Bone bone, DuclingDefinition parentDucling, int count, float[] offset){
        WingListBuilder builder = WingListBuilder.create();
        for (Cube cube : bone.cubes()) {
            if (cube.rotation() == null) {
                builder.addBox(null, cube.origin()[0], cube.origin()[1], cube.origin()[2],
                        cube.size()[0], cube.size()[1], cube.size()[2],
                        new CubeDeformation(cube.inflate()), cube.uv2()[0], cube.uv2()[1], cube.mirror());
            } else {
                parentDucling.addOrReplaceChild("cube_" + count, createWingsUV2(new Bone("", "", new float[]{}, null, new Cube[]{new Cube(cube.origin(), cube.size(), null, null, cube.inflate(), cube.uv2(), cube.uv(), cube.mirror())}), parentDucling, ++count, offset), PartPose.offsetAndRotation(cube.pivot()[0] - offset[0], cube.pivot()[1] - offset[1], cube.pivot()[2] - offset[2], Mth.DEG_TO_RAD * cube.rotation()[0], Mth.DEG_TO_RAD * cube.rotation()[1], Mth.DEG_TO_RAD * cube.rotation()[2]));
            }
        }
        return builder;
    }

    public LakeDefinition getLakeDefinition(){
        return BaseDucModel.generateLakeDefinition(getModelLocation());
    }

    protected abstract ResourceLocation getModelLocation();
}
