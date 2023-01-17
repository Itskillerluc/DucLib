package com.itskillerluc.duclib.client.model;

import com.itskillerluc.duclib.client.model.definitions.LakeDefinition;
import com.itskillerluc.duclib.client.model.definitions.PondDefinition;
import com.itskillerluc.duclib.client.model.definitions.WingListBuilder;
import com.itskillerluc.duclib.data.DucLibModelLoader;
import com.itskillerluc.duclib.data.serializers.Bone;
import com.itskillerluc.duclib.data.serializers.Geometry;
import com.itskillerluc.duclib.data.serializers.GeometryHolder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create the animation in here and specify the model.
 * if you use this you probably just want to use AnimatableDucModel... unless you don't.
 */
abstract class DucModel <T extends Entity> extends EntityModel<T> {

    public LakeDefinition getLakeDefinition(){
        return BaseDucModel.generateLakeDefinition(getModelLocation());
    }

    protected abstract ResourceLocation getModelLocation();
}
