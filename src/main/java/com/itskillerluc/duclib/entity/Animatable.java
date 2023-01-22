package com.itskillerluc.duclib.entity;

import com.itskillerluc.duclib.client.model.AnimatableDucModel;
import net.minecraft.world.entity.AnimationState;
import net.minecraftforge.common.util.Lazy;

import java.util.HashMap;
import java.util.Map;

interface Animatable <T extends AnimatableDucModel<?>> {
    T getModel();

    Lazy<Map<String, AnimationState>> getLazy();

    default Map<String, AnimationState> getAnimations() {
            return getLazy().get();
    }

    /**
     * only use this for creating the lazy.
     */
    default Map<String, AnimationState> animationSupplier(){
        Map<String, AnimationState> states = new HashMap<>();
        for (String s : getModel().getAnimation().getAnimations().keySet()) {
            states.put(s, new AnimationState());
        }
        return states;
    }
}