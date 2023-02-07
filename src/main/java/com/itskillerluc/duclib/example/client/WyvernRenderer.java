package com.itskillerluc.duclib.example.client;

import com.itskillerluc.duclib.DucLib;
import com.itskillerluc.duclib.client.model.Ducling;
import com.itskillerluc.duclib.client.render.AnimatableDucRenderer;
import com.itskillerluc.duclib.example.entities.WyvernEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class WyvernRenderer extends AnimatableDucRenderer<WyvernEntity, WyvernModel> {
    /**
     * ResourceLocation of the entity texture.
     */
    public static final ResourceLocation LOCATION = new ResourceLocation(DucLib.MOD_ID, "textures/entity/wyvern.png");
    public WyvernRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, () -> new WyvernModel((Ducling) pContext.bakeLayer(WyvernModel.LAYER_LOCATION)), e -> LOCATION, 3);
    }
}
