package com.itskillerluc.duclib.mixin;

import com.itskillerluc.duclib.client.model.BaseDucModel;
import com.itskillerluc.duclib.client.model.definitions.*;
import com.itskillerluc.duclib.data.model.DucLibModelLoader;
import com.itskillerluc.duclib.data.model.serializers.Bone;
import com.itskillerluc.duclib.data.model.serializers.Cube;
import com.itskillerluc.duclib.data.model.serializers.Geometry;
import com.itskillerluc.duclib.data.model.serializers.GeometryHolder;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(LayerDefinitions.class)
public class LayerDefinitionsMixin {
    @Inject(method = "Lnet/minecraft/client/model/geom/LayerDefinitions;createRoots()Ljava/util/Map;",at = @At("TAIL"), cancellable = true)
    private static void injected(CallbackInfoReturnable<Map<ModelLayerLocation, LayerDefinition>> cir){
        Map<ModelLayerLocation, LayerDefinition> map = new HashMap<>(cir.getReturnValue());
        for (Map.Entry<ResourceLocation, GeometryHolder> resourceLocationGeometryHolderEntry : DucLibModelLoader.getOverrides().entrySet()) {
            var key = cir.getReturnValue().keySet().stream().filter(i -> i.getModel().equals(resourceLocationGeometryHolderEntry.getKey())).findFirst();
            key.ifPresent(modelLayerLocation -> map.replace(modelLayerLocation, generateLakeDefinition(resourceLocationGeometryHolderEntry.getKey())));
        }
        cir.setReturnValue(map);
    }

    private static LakeDefinition generateLakeDefinition(ResourceLocation entity){
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
        if (bones.get(0).cubes()[0].uv().left().isPresent()){
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
                offset = new float[]{0 - 0,0 - 0,0 - 0};
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
                offset = new float[]{0 - 0,0 - 0,0 - 0};
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
                builder.addBox(null, -(cube.origin()[0] + cube.size()[0]), -(cube.origin()[1] + cube.size()[1]), -(cube.origin()[2] + cube.size()[2]),
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
                parentDucling.addOrReplaceChild("cube_" + count, createWings(new Bone("", "", new float[]{}, null, new Cube[]{new Cube(cube.origin(), cube.size(), null, null, cube.inflate(), cube.uv(), cube.mirror())}), parentDucling, ++count, offset), PartPose.offsetAndRotation(cube.pivot()[0] - offset[0], cube.pivot()[1] - offset[1], cube.pivot()[2] - offset[2], Mth.DEG_TO_RAD * cube.rotation()[0], Mth.DEG_TO_RAD * cube.rotation()[1], Mth.DEG_TO_RAD * cube.rotation()[2]));
            }
        }
        return builder;
    }

    private static WingListBuilder createWingsUV2(Bone bone, DuclingDefinition parentDucling, int count, float[] offset){
        WingListBuilder builder = WingListBuilder.create();
        for (Cube cube : bone.cubes()) {
            if (cube.rotation() == null) {
                builder.addBox(null, -(cube.origin()[0] + cube.size()[0]), -(cube.origin()[1] + cube.size()[1]), -(cube.origin()[2] + cube.size()[2]),
                        cube.size()[0], cube.size()[1], cube.size()[2],
                        new CubeDeformation(cube.inflate()), cube.uv().right().get()[0], cube.uv().right().get()[1], cube.mirror());
            } else {
                parentDucling.addOrReplaceChild("cube_" + count, createWingsUV2(new Bone("", "", new float[]{}, null, new Cube[]{new Cube(cube.origin(), cube.size(), null, null, cube.inflate(), cube.uv(), cube.mirror())}), parentDucling, ++count, offset), PartPose.offsetAndRotation(cube.pivot()[0] - offset[0], cube.pivot()[1] - offset[1], cube.pivot()[2] - offset[2], Mth.DEG_TO_RAD * cube.rotation()[0], Mth.DEG_TO_RAD * cube.rotation()[1], Mth.DEG_TO_RAD * cube.rotation()[2]));
            }
        }
        return builder;
    }
}
