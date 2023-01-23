package com.itskillerluc.duclib.client.model;

import com.itskillerluc.duclib.client.animation.AnimationHolder;
import com.itskillerluc.duclib.client.animation.DucAnimation;
import com.itskillerluc.duclib.client.model.definitions.LakeDefinition;
import com.itskillerluc.duclib.entity.Animatable;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Create the animation in here and specify the model.
 * just use this one, it's the best, and you can't animate the others.
 */
abstract public class AnimatableDucModel <T extends Entity & Animatable<?>> extends HierarchicalModel<T> {
    Ducling root;
    Map<String, Ducling> duclings;

    public AnimatableDucModel(Ducling ducling, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
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

    protected Set<String> excludeAnimations(){
        return Collections.emptySet();
    }
    @Override
    public void setupAnim(@NotNull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        for (Map.Entry<String, AnimationState> stringAnimationStateEntry : pEntity.getAnimations().get().entrySet()) {
            if (!excludeAnimations().contains(stringAnimationStateEntry.getKey())) {
                AnimationHolder animation = pEntity.getAnimation().getAnimations().get(stringAnimationStateEntry.getKey());
                this.animate(stringAnimationStateEntry.getValue(), animation.animation(), pAgeInTicks, animation.speed());
            }
        }
    }

    public static Map<String, AnimationState> createStateMap(DucAnimation animation){
        Map<String, AnimationState> states = new HashMap<>();
        for (String key : animation.getAnimations().keySet()) {
            states.put(key, new AnimationState());
        }
        return states;
    }
}
