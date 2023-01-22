package com.itskillerluc.duclib;

import com.itskillerluc.duclib.data.animation.DucLibAnimationLoader;
import com.itskillerluc.duclib.data.model.DucLibModelLoader;
import com.mojang.logging.LogUtils;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DucLib.MODID)
public class DucLib
{
    public static final String MODID = "duclib";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public DucLib()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addReloadListener);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addReloadListener(final RegisterClientReloadListenersEvent event){
        event.registerReloadListener(new DucLibModelLoader());
        event.registerReloadListener(new DucLibAnimationLoader());
    }
}
