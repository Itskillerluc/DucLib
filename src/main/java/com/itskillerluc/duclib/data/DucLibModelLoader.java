package com.itskillerluc.duclib.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.itskillerluc.duclib.client.model.Ducling;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

/**
 * load the models into the game.
 */
public class DucLibModelLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public DucLibModelLoader() {
        super(GSON, "duclings");
    }
    record test(int[] item, int count, String test){}

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        for (Map.Entry<ResourceLocation, JsonElement> resourceLocationJsonElementEntry : pObject.entrySet()) {
            var test = GsonHelper.fromJson(GSON, resourceLocationJsonElementEntry.getValue().toString(), Ducling.class);
            System.out.println(test);
        }
    }
}
