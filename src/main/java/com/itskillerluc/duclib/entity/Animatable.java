package com.itskillerluc.duclib.entity;

import com.itskillerluc.duclib.client.animation.DucAnimation;
import com.itskillerluc.duclib.client.model.AnimatableDucModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraftforge.common.util.Lazy;

import java.util.Map;

public interface Animatable <T extends AnimatableDucModel<?>> {
    ResourceLocation getModelLocation();
    DucAnimation getAnimation();

    Lazy<Map<String, AnimationState>> getAnimations();
}
