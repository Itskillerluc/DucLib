package com.itskillerluc.duclib.client.animation;

import com.google.common.base.Suppliers;
import com.itskillerluc.duclib.data.animation.DucLibAnimationLoader;
import com.itskillerluc.duclib.data.animation.serializers.Animation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

import java.util.Map;
import java.util.function.Supplier;

public abstract class DucAnimation {
    private final Lazy<Map<String, AnimationHolder>> ANIMATIONS = Lazy.of(this::createAnimations);
    Map<String, AnimationHolder> createAnimations(){
        com.itskillerluc.duclib.data.animation.serializers.AnimationHolder holder = DucLibAnimationLoader.getAnimation(getLocation());
        for (Map.Entry<String, Animation> stringAnimationEntry : holder.animations().entrySet()) {
            stringAnimationEntry.getKey()
        }
    }

    public Map<String, AnimationHolder> getAnimations(){
        return ANIMATIONS.get();
    }

    abstract ResourceLocation getLocation();

    public static DucAnimation create(ResourceLocation location){
        return new DucAnimation(){
            @Override
            ResourceLocation getLocation() {
                return location;
            }
        };
    }
}
