package com.itskillerluc.duclib.client.render;

import com.itskillerluc.duclib.client.model.BaseDucModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * use this if you don't need animations for your entity.
 */
public class DucRenderer <T extends Entity, M extends BaseDucModel> extends EntityRenderer<T> {
    private final Function<T, ResourceLocation> textureLocation;
    private final M ducModel;
    protected DucRenderer(EntityRendererProvider.Context pContext, Function<T, ResourceLocation> textureFunction, Supplier<M> ducModel) {
        super(pContext);
        this.textureLocation = textureFunction;
        this.ducModel = ducModel.get();
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = !pEntity.isInvisible();
        boolean flag1 = !flag && !pEntity.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(pEntity);
        RenderType rendertype = this.getRenderType(pEntity, flag, flag1, flag2);

        if (rendertype != null) {
            VertexConsumer vertexconsumer = pBuffer.getBuffer(rendertype);
            this.ducModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
        }
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T pEntity) {
        return textureLocation.apply(pEntity);
    }

    @Nullable
    protected RenderType getRenderType(T pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) {
        ResourceLocation resourcelocation = this.getTextureLocation(pLivingEntity);
        if (pTranslucent) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (pBodyVisible) {
            return this.ducModel.renderType(resourcelocation);
        } else {
            return pGlowing ? RenderType.outline(resourcelocation) : null;
        }
    }
}
