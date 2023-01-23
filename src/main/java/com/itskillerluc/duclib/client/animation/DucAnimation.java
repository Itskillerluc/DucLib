package com.itskillerluc.duclib.client.animation;

import com.itskillerluc.duclib.data.animation.DucLibAnimationLoader;
import com.itskillerluc.duclib.data.animation.serializers.Animation;
import com.itskillerluc.duclib.data.animation.serializers.Bone;
import com.itskillerluc.duclib.data.animation.serializers.KeyFrame;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.joml.Vector3f;

import java.util.*;

public abstract class DucAnimation {
    private final Lazy<Map<String, AnimationHolder>> ANIMATIONS = Lazy.of(this::createAnimations);
    Map<String, AnimationHolder> createAnimations(){
        com.itskillerluc.duclib.data.animation.serializers.AnimationHolder holder = DucLibAnimationLoader.getAnimation(getLocation());
        Map<String, AnimationHolder> holderMap = new HashMap<>();
        for (Map.Entry<String, Animation> stringAnimationEntry : holder.animations().entrySet()) {
            AnimationDefinition.Builder builder = AnimationDefinition.Builder.withLength(stringAnimationEntry.getValue().animationLength());
            for (Map.Entry<String, Bone> bones : stringAnimationEntry.getValue().bones().entrySet()) {
                List<Keyframe> positions = new ArrayList<>();
                List<Keyframe> rotations = new ArrayList<>();
                List<Keyframe> scales = new ArrayList<>();
                for (int i = 0; i < bones.getValue().position().entrySet().size(); i++) {
                    List<Map.Entry<String, KeyFrame>> entries = bones.getValue().position().entrySet().stream().toList();
                    positions.add(createKeyFrame(i, entries));
                }
                for (int i = 0; i < bones.getValue().rotation().entrySet().size(); i++) {
                    List<Map.Entry<String, KeyFrame>> entries = bones.getValue().rotation().entrySet().stream().toList();
                    rotations.add(createKeyFrame(i, entries));
                }
                for (int i = 0; i < bones.getValue().scale().entrySet().size(); i++) {
                    List<Map.Entry<String, KeyFrame>> entries = bones.getValue().scale().entrySet().stream().toList();
                    scales.add(createKeyFrame(i, entries));
                }
                AnimationChannel positionChannel = new AnimationChannel(AnimationChannel.Targets.POSITION, positions.toArray(new Keyframe[0]));
                AnimationChannel rotationChannel = new AnimationChannel(AnimationChannel.Targets.ROTATION, rotations.toArray(new Keyframe[0]));
                AnimationChannel scaleChannel = new AnimationChannel(AnimationChannel.Targets.SCALE, scales.toArray(new Keyframe[0]));

                builder.addAnimation(bones.getKey(), positionChannel).addAnimation(bones.getKey(), rotationChannel).addAnimation(bones.getKey(), scaleChannel);
            }
            if (stringAnimationEntry.getValue().loop()){
                builder.looping();
            }
            holderMap.put(stringAnimationEntry.getKey(), new AnimationHolder(stringAnimationEntry.getValue().speed(), builder.build()));
        }
        return holderMap;
    }

    private static Keyframe createKeyFrame(int i, List<Map.Entry<String, KeyFrame>> entries) {
        return new Keyframe(Float.parseFloat(entries.get(i).getKey()),
                new Vector3f(((float) entries.get(i).getValue().vector()[0]), ((float) entries.get(i).getValue().vector()[1]), ((float) entries.get(i).getValue().vector()[2])),
                entries.get(i).getValue().lerpMode().equals("catmullrom") ? AnimationChannel.Interpolations.CATMULLROM : AnimationChannel.Interpolations.LINEAR);
    }

    public final Map<String, AnimationHolder> getAnimations(){
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
