package com.itskillerluc.duclib.client.model;

import com.itskillerluc.duclib.client.model.definitions.LakeDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Create the animation in here and specify the model.
 * just use this one, it's the best, and you can't animate the others.
 */
abstract public class AnimatableDucModel <T extends Entity> extends HierarchicalModel<T> {
    Ducling root;
    Map<String, Ducling> duclings;

    public AnimatableDucModel(Ducling ducling) {
        this.root = ducling.getChild("root");
    }

    public LakeDefinition getLakeDefinition(){
        return BaseDucModel.generateLakeDefinition(getModelLocation());
    }

    protected abstract ResourceLocation getModelLocation();

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
