package com.itskillerluc.duclib.client.animation;

import net.minecraft.client.animation.AnimationDefinition;


/**
 * A class to bundle the speed with the animation.
 * @param speed the speed of animation, default is 1.
 * @param animation the animationDefinition of the animation
 */
public record AnimationHolder(float speed, AnimationDefinition animation) {
}
