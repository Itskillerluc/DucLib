package com.itskillerluc.duclib.test.entities;

import com.itskillerluc.duclib.DucLib;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DucLib.MOD_ID);

    public static final RegistryObject<EntityType<WyvernEntity>> WYVERN =
            ENTITY_TYPES.register("wyvern",
                    () -> EntityType.Builder.of(WyvernEntity::new, MobCategory.MONSTER).build(new ResourceLocation(DucLib.MOD_ID, "wyvern").toString()));
}
