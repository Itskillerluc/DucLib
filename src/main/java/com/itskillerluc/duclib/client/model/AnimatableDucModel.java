package com.itskillerluc.duclib.client.model;

import com.itskillerluc.duclib.client.model.definitions.LakeDefinition;
import com.itskillerluc.duclib.client.model.definitions.PondDefinition;
import com.itskillerluc.duclib.client.model.definitions.WingListBuilder;
import com.itskillerluc.duclib.data.DucLibModelLoader;
import com.itskillerluc.duclib.data.serializers.Bone;
import com.itskillerluc.duclib.data.serializers.Geometry;
import com.itskillerluc.duclib.data.serializers.GeometryHolder;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create the animation in here and specify the  model.
 */
abstract public class AnimatableDucModel <T extends Entity> extends HierarchicalModel<T> {
    Map<String, Ducling> duclings;

    public AnimatableDucModel(Ducling ducling) {

    }

    public static LakeDefinition generateLakeDefinition(ResourceLocation entity){
        GeometryHolder holder = DucLibModelLoader.getModel(entity);
        Geometry geometry = holder.geometry()[0];
        List<Bone> bones = Arrays.stream(geometry.bones()).collect(Collectors.toCollection(ArrayList::new));
        PondDefinition pondDefinition = new PondDefinition();
        float offset = 0;

        if (geometry.hasRoot()) {
            Bone root = bones.stream().filter(bone -> bone.name().equals("root")).findFirst().get();
            bones.removeIf(bone -> bone.name().equals("root"));
            offset = root.pivot()[1];
        }

        pondDefinition.getRoot().addOrReplaceChild("root", WingListBuilder.create(), PartPose.offset(0, 24 - offset , 0));


        return LakeDefinition.create(pondDefinition, geometry.description().textureWidth(), geometry.description().textureHeight());
    }



    @Override
    public @NotNull Ducling root() {
        Ducling root = duclings.get("root");
        if(root == null){
            throw new NullPointerException("model does not contain a root.");
        }
        return root;
    }

    @Override
    public abstract void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch);
}
