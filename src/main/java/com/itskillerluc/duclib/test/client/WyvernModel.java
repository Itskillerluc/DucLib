package com.itskillerluc.duclib.test.client;

import com.itskillerluc.duclib.DucLib;
import com.itskillerluc.duclib.client.model.AnimatableDucModel;
import com.itskillerluc.duclib.client.model.Ducling;
import com.itskillerluc.duclib.test.entities.WyvernEntity;
import net.minecraft.client.animation.definitions.FrogAnimation;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class WyvernModel extends AnimatableDucModel<WyvernEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DucLib.MOD_ID, "wyvern.png"), "all");

    @Override
    protected Set<String> excludeAnimations() {
        return Set.of("animation.frog.walk");
    }

    @Override
    public void setupAnim(@NotNull WyvernEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }

    public WyvernModel(Ducling ducling) {
        super(ducling, RenderType::entityTranslucent);
    }
}
