package com.itskillerluc.duclib.client.render;

import com.itskillerluc.duclib.client.model.AnimatableDucModel;
import com.itskillerluc.duclib.entity.Animatable;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Doesn't really serve a use as of right-now, but it might in the future, so preferably use it when you can.
 */
public class AnimatableDucRenderer<T extends LivingEntity & Animatable<M>, M extends AnimatableDucModel<T>> extends LivingEntityRenderer<T, M> {
    private final Function<T, ResourceLocation> textureFunction;

    public AnimatableDucRenderer(EntityRendererProvider.Context pContext, Supplier<M> model, Function<T, ResourceLocation> textureFunction, float pShadowRadius) {
        super(pContext, model.get(), pShadowRadius);
        this.textureFunction = textureFunction;
    }

    @Override
    protected boolean shouldShowName(T pEntity) {
        return super.shouldShowName(pEntity) && (pEntity.shouldShowName() || pEntity.hasCustomName() && pEntity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T pEntity) {
        return textureFunction.apply(pEntity);
    }
}
