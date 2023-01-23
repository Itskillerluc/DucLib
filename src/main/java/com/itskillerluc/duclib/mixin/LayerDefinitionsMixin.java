package com.itskillerluc.duclib.mixin;

import com.itskillerluc.duclib.client.model.BaseDucModel;
import com.itskillerluc.duclib.data.model.DucLibModelLoader;
import com.itskillerluc.duclib.data.model.serializers.GeometryHolder;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(LayerDefinitions.class)
public class LayerDefinitionsMixin {
    @Inject(method = "Lnet/minecraft/client/model/geom/LayerDefinitions;createRoots()Ljava/util/Map;",at = @At("TAIL"), cancellable = true)
    private static void injected(CallbackInfoReturnable<Map<ModelLayerLocation, LayerDefinition>> cir){
        Map<ModelLayerLocation, LayerDefinition> map = new HashMap<>(cir.getReturnValue());
        for (Map.Entry<ResourceLocation, GeometryHolder> resourceLocationGeometryHolderEntry : DucLibModelLoader.getOverrides().entrySet()) {
            var key = cir.getReturnValue().keySet().stream().filter(i -> i.getModel().equals(resourceLocationGeometryHolderEntry.getKey())).findFirst();
            key.ifPresent(modelLayerLocation -> map.replace(modelLayerLocation, BaseDucModel.generateLakeDefinition(resourceLocationGeometryHolderEntry.getKey())));
        }
        cir.setReturnValue(map);
    }
}
