package com.itskillerluc.duclib.example;

import com.itskillerluc.duclib.DucLib;
import com.itskillerluc.duclib.client.model.BaseDucModel;
import com.itskillerluc.duclib.example.client.WyvernModel;
import com.itskillerluc.duclib.example.client.WyvernRenderer;
import com.itskillerluc.duclib.example.entities.ModEntities;
import com.itskillerluc.duclib.example.entities.WyvernEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DucLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.WYVERN.get(), WyvernRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(WyvernModel.LAYER_LOCATION, () -> BaseDucModel.getLakeDefinition(WyvernEntity.LOCATION));
    }
}
