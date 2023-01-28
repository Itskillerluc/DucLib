package com.itskillerluc.duclib.data.model;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.itskillerluc.duclib.data.model.serializers.Cube;
import com.itskillerluc.duclib.data.model.serializers.GeometryHolder;
import com.itskillerluc.duclib.util.ClearableLazy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * load the models into the game.
 */
public class DucLibModelLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Cube.class, new Cube.adapter()).setPrettyPrinting().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private static final Map<ResourceLocation, GeometryHolder> MODELS = new HashMap<>();
    private static final ClearableLazy<Map<ResourceLocation, GeometryHolder>> OVERRIDES = ClearableLazy.of(() -> MODELS.entrySet().stream().filter(entry -> entry.getValue().override()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

    public static GeometryHolder getModel(ResourceLocation key){
        for (Map.Entry<ResourceLocation, GeometryHolder> resourceLocationGeometryHolderEntry : MODELS.entrySet()) {
            if(resourceLocationGeometryHolderEntry.getKey().equals(key)){
                return resourceLocationGeometryHolderEntry.getValue();
            }
        }
        return null;
    }

    public static Map<ResourceLocation, GeometryHolder> getOverrides(){
        return OVERRIDES.get();
    }

    public DucLibModelLoader() {
        super(GSON, "duclings/models");
    }
    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, @NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pProfiler) {
        for (Map.Entry<ResourceLocation, JsonElement> resourceLocationJsonElementEntry : pObject.entrySet()) {
            GeometryHolder holder = GsonHelper.fromJson(GSON, resourceLocationJsonElementEntry.getValue().toString(), GeometryHolder.class);
            MODELS.put(resourceLocationJsonElementEntry.getKey(), holder);
        }
        OVERRIDES.invalidate();
    }
}
