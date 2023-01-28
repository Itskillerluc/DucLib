package com.itskillerluc.duclib.entity;

import com.itskillerluc.duclib.client.animation.DucAnimation;
import com.itskillerluc.duclib.client.model.AnimatableDucModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.Lazy;

import java.util.Map;

public interface Animatable <T extends AnimatableDucModel<?>> {
    ResourceLocation getModelLocation();
    DucAnimation getAnimation();

    Lazy<Map<String, AnimationState>> getAnimations();

    static boolean isMoving(LivingEntity entity) {
        return entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    default void playAnimation(String animation, int tickCount){
        getAnimationState(animation).startIfStopped(tickCount);
    }

    default void replayAnimation(String animation, int tickCount){
        getAnimationState(animation).start(tickCount);
    }

    default void stopAnimation(String animation){
        getAnimationState(animation).stop();
    }

    AnimationState getAnimationState(String animation);
}
