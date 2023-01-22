package com.itskillerluc.duclib.client.model;

import com.itskillerluc.duclib.client.model.definitions.LakeDefinition;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

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
