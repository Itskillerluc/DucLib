package com.itskillerluc.duclib.test;

import com.itskillerluc.duclib.DucLib;
import com.itskillerluc.duclib.test.entities.ModEntities;
import com.itskillerluc.duclib.test.entities.WyvernEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DucLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.WYVERN.get(), WyvernEntity.attributes().build());
    }
}
