package com.itskillerluc.duclib.client.model;

import com.itskillerluc.duclib.client.model.definitions.*;
import com.itskillerluc.duclib.data.model.*;
import com.itskillerluc.duclib.data.model.serializers.Bone;
import com.itskillerluc.duclib.data.model.serializers.Cube;
import com.itskillerluc.duclib.data.model.serializers.Geometry;
import com.itskillerluc.duclib.data.model.serializers.GeometryHolder;
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
    static LakeDefinition generateLakeDefinition(ResourceLocation entity){
        GeometryHolder holder = DucLibModelLoader.getModel(entity);
        Geometry geometry = holder.geometry()[0];
        List<Bone> bones = Arrays.stream(geometry.bones()).collect(Collectors.toCollection(ArrayList::new));
        PondDefinition pondDefinition = new PondDefinition();
        float[] offset = new float[3];

        if (geometry.hasRoot()) {
            Bone root = bones.stream().filter(bone -> bone.name().equals("root")).findFirst().orElseThrow();
            bones.removeIf(bone -> bone.name().equals("root"));
            offset = new float[]{root.pivot()[0], root.pivot()[1], root.pivot()[2]};
        }

        DuclingDefinition root = pondDefinition.getRoot().addOrReplaceChild("root", WingListBuilder.create(), PartPose.offset(0 + offset[0], 24 - offset[1] , 0 + offset[2]));
        int count = 0;

        if (bones.get(0).cubes()[0].uv().left().isPresent()){
            generateLakeDefinitionRecursively("root", count, bones, root, offset);
        } else {
            generateLakeDefinitionRecursivelyUV2("root", count, bones, root, offset);
        }

        return LakeDefinition.create(pondDefinition, geometry.description().textureWidth(), geometry.description().textureHeight());
    }

    private static DuclingDefinition generateLakeDefinitionRecursively(String parent, int count, List<Bone> bones, DuclingDefinition parentDucling, float[] offset){
        for (Bone bone : bones) {
            if (bone.parent().equals(parent)){
                DuclingDefinition parentDef = parentDucling.addOrReplaceChild(bone.name(), createWings(bone, parentDucling, count, new float[]{bone.pivot()[0], bone.pivot()[1], bone.pivot()[2]}), PartPose.offsetAndRotation(bone.pivot()[0] - offset[0], -(bone.pivot()[1] - offset[1]), bone.pivot()[2] - offset[2], Mth.DEG_TO_RAD * bone.rotation()[0], Mth.DEG_TO_RAD * bone.rotation()[1], Mth.DEG_TO_RAD * bone.rotation()[2]));
                generateLakeDefinitionRecursively(bone.name(), count, bones, parentDef, new float[]{bone.pivot()[0], bone.pivot()[1], bone.pivot()[2]});
            }
        }
        return parentDucling;
    }

    private static DuclingDefinition generateLakeDefinitionRecursivelyUV2(String parent, int count, List<Bone> bones, DuclingDefinition parentDucling, float[] offset){
        for (Bone bone : bones) {
            if (bone.parent().equals(parent)){
                DuclingDefinition parentDef = parentDucling.addOrReplaceChild(bone.name(), createWingsUV2(bone, parentDucling, count, new float[]{bone.pivot()[0], bone.pivot()[1], bone.pivot()[2]}), PartPose.offsetAndRotation(bone.pivot()[0] - offset[0], -(bone.pivot()[1] - offset[1]), bone.pivot()[2] - offset[2], Mth.DEG_TO_RAD * bone.rotation()[0], Mth.DEG_TO_RAD * bone.rotation()[1], Mth.DEG_TO_RAD * bone.rotation()[2]));
                generateLakeDefinitionRecursivelyUV2(bone.name(), count, bones, parentDef, new float[]{bone.pivot()[0], bone.pivot()[1], bone.pivot()[2]});
            }
        }
        return parentDucling;
    }

    private static WingListBuilder createWings(Bone bone, DuclingDefinition parentDucling, int count, float[] offset){
        WingListBuilder builder = WingListBuilder.create();
        if (bone.cubes() != null) {
            for (Cube cube : bone.cubes()) {
                if (cube.rotation() == null) {
                    builder.addBox(null, cube.origin()[0] - offset[0], cube.origin()[1] - offset[1], cube.origin()[2] - offset[2],
                            cube.size()[0], cube.size()[1], cube.size()[2],
                            new CubeDeformation(cube.inflate()), new AdvancedUV[]{
                                    new AdvancedUV(Direction.NORTH, new UVPair(cube.uv().left().get().north().uv()[0], cube.uv().left().get().north().uv()[1]), new UVPair(cube.uv().left().get().north().uvSize()[0], cube.uv().left().get().north().uvSize()[1])),
                                    new AdvancedUV(Direction.EAST, new UVPair(cube.uv().left().get().east().uv()[0], cube.uv().left().get().east().uv()[1]), new UVPair(cube.uv().left().get().east().uvSize()[0], cube.uv().left().get().east().uvSize()[1])),
                                    new AdvancedUV(Direction.SOUTH, new UVPair(cube.uv().left().get().south().uv()[0], cube.uv().left().get().south().uv()[1]), new UVPair(cube.uv().left().get().south().uvSize()[0], cube.uv().left().get().south().uvSize()[1])),
                                    new AdvancedUV(Direction.WEST, new UVPair(cube.uv().left().get().west().uv()[0], cube.uv().left().get().west().uv()[1]), new UVPair(cube.uv().left().get().west().uvSize()[0], cube.uv().left().get().west().uvSize()[1])),
                                    new AdvancedUV(Direction.UP, new UVPair(cube.uv().left().get().up().uv()[0], cube.uv().left().get().up().uv()[1]), new UVPair(cube.uv().left().get().up().uvSize()[0], cube.uv().left().get().up().uvSize()[1])),
                                    new AdvancedUV(Direction.DOWN, new UVPair(cube.uv().left().get().down().uv()[0], cube.uv().left().get().down().uv()[1]), new UVPair(cube.uv().left().get().down().uvSize()[0], cube.uv().left().get().down().uvSize()[1]))
                            }, cube.mirror());
                } else {
                    parentDucling.addOrReplaceChild("cube_" + count, createWings(new Bone("cube_" + count, bone.name(), new float[]{}, null, new Cube[]{new Cube(cube.origin(), cube.size(), null, null, cube.inflate(), cube.uv(), cube.mirror())}), parentDucling, ++count, new float[]{bone.pivot()[0], bone.pivot()[1], bone.pivot()[2]}), PartPose.offsetAndRotation(0, 0, 0, Mth.DEG_TO_RAD * cube.rotation()[0], Mth.DEG_TO_RAD * cube.rotation()[1], Mth.DEG_TO_RAD * cube.rotation()[2]));
                }
            }
        }
        return builder;
    }

    private static WingListBuilder createWingsUV2(Bone bone, DuclingDefinition parentDucling, int count, float[] offset){
        WingListBuilder builder = WingListBuilder.create();
        if (bone.cubes() != null) {
            for (Cube cube : bone.cubes()) {
                if (cube.rotation() == null) {
                    builder.addBox(null, cube.origin()[0] - offset[0], -(cube.origin()[1] - offset[1] + cube.size()[1]), cube.origin()[2] - offset[2],
                            cube.size()[0], cube.size()[1], cube.size()[2],
                            new CubeDeformation(cube.inflate()), cube.uv().right().get()[0], cube.uv().right().get()[1], cube.mirror());
                } else {
                    parentDucling.addOrReplaceChild("cube_" + count, createWingsUV2(new Bone("cube_" + count , bone.name(), new float[]{}, null, new Cube[]{new Cube(cube.origin(), cube.size(), null, null, cube.inflate(), cube.uv(), cube.mirror())}), parentDucling, ++count, new float[]{bone.pivot()[0], bone.pivot()[1], bone.pivot()[2]}), PartPose.offsetAndRotation(0, 0, 0, Mth.DEG_TO_RAD * cube.rotation()[0], Mth.DEG_TO_RAD * cube.rotation()[1], Mth.DEG_TO_RAD * cube.rotation()[2]));
                }
            }
        }
        return builder;
    }

    public static LakeDefinition getLakeDefinition(ResourceLocation location){
        return BaseDucModel.generateLakeDefinition(location);
    }
}
