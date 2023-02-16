package io.github.itskillerluc.duclib.entity;

import io.github.itskillerluc.duclib.client.animation.DucAnimation;
import io.github.itskillerluc.duclib.client.model.AnimatableDucModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.Lazy;

import java.util.Map;
import java.util.Objects;

/**
 * For proper instructions on how to use, check out the wiki on GitHub!
 */
public interface Animatable <T extends AnimatableDucModel<?>> {
    /**
     * @return the model location
     */
    ResourceLocation getModelLocation();

    /**
     * @return the DucAnimation
     */
    DucAnimation getAnimation();

    /**
     * @return Get a lazy with all animations and their keys.
     */
    Lazy<Map<String, AnimationState>> getAnimations();

    /**
     * @return true if the entity is moving.
     */
    default boolean isMoving(LivingEntity entity) {
        return entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    /**
     * Start the animation if it's not already playing
     */
    default void playAnimation(String animation){
        Objects.requireNonNull(getAnimationState(animation), "Coudln't find animation: \"" + animation + "\"" ).startIfStopped(tickCount());
    }

    /**
     * Start regardless of if it's already playing.
     */
    default void replayAnimation(String animation){
        getAnimationState(animation).start(tickCount());
    }

    /**
     * Animate when the condition is true
     */
    default void animateWhen(String animation, boolean condition){
        getAnimationState(animation).animateWhen(condition, tickCount());
    }

    /**
     * stop an animation
     */
    default void stopAnimation(String animation){
        getAnimationState(animation).stop();
    }

    /**
     * get the animation state for a key.
     * @param animation animation key
     * @return the animation state corresponding to the key.
     */
    AnimationState getAnimationState(String animation);

    int tickCount();
}
