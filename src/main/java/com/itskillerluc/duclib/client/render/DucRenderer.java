package com.itskillerluc.duclib.client.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

/**
 * use this if you don't need animations for your entity.
 */
public class DucRenderer <T extends Entity> extends EntityRenderer<T> {
    protected DucRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return null;
    }
}
