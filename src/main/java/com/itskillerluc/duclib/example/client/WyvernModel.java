package com.itskillerluc.duclib.example.client;

import com.itskillerluc.duclib.DucLib;
import com.itskillerluc.duclib.client.model.AnimatableDucModel;
import com.itskillerluc.duclib.client.model.Ducling;
import com.itskillerluc.duclib.example.entities.WyvernEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class WyvernModel extends AnimatableDucModel<WyvernEntity> {

    /**
     * texture location for the entity model, you can use variants if you want.
     */
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DucLib.MOD_ID, "wyvern.png"), "all");

    public WyvernModel(Ducling ducling) {
        super(ducling, RenderType::entityTranslucent);
    }
}
