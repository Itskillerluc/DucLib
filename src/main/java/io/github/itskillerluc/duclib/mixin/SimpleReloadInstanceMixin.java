package io.github.itskillerluc.duclib.mixin;

import io.github.itskillerluc.duclib.data.animation.DucLibAnimationLoader;
import io.github.itskillerluc.duclib.data.model.DucLibModelLoader;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.SimpleReloadInstance;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

/**
 * This changes the order of the game loading so that the DucLib loaders fire before the models are baked.
 */
@Mixin(SimpleReloadInstance.class)
public abstract class SimpleReloadInstanceMixin implements ReloadInstance {

    @ModifyVariable(method = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;<init>(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Lnet/minecraft/server/packs/resources/SimpleReloadInstance$StateFactory;Ljava/util/concurrent/CompletableFuture;)V",at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static List<PreparableReloadListener> injected(List<PreparableReloadListener> pListeners){
        try {
            int model = pListeners.indexOf(pListeners.stream().filter(listen -> listen instanceof EntityModelSet).findFirst().orElseThrow());
            int ducModel = pListeners.indexOf(pListeners.stream().filter(listen -> listen instanceof DucLibModelLoader).findFirst().orElseThrow());

            pListeners.add(model-1, pListeners.remove(ducModel));

            int ducAnimation = pListeners.indexOf(pListeners.stream().filter(listen -> listen instanceof DucLibAnimationLoader).findFirst().orElseThrow());
            model = pListeners.indexOf(pListeners.stream().filter(listen -> listen instanceof EntityModelSet).findFirst().orElseThrow());

            pListeners.add(model-1, pListeners.remove(ducAnimation));
        } catch (Exception except){
            LogManager.getLogger().debug("EntityModelSet or DucLibModel not found, using regular reload order instead.");
            return pListeners;
        }
        return pListeners;
    }
}
