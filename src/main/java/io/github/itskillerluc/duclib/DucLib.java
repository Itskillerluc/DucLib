package io.github.itskillerluc.duclib;

import io.github.itskillerluc.duclib.data.animation.DucLibAnimationLoader;
import io.github.itskillerluc.duclib.data.model.DucLibModelLoader;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//TODO: somehow make some kind of interpolation for when you have multiple animations running at once.
@Mod(DucLib.MOD_ID)
public class DucLib
{
    public static final String MOD_ID = "duclib";
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
