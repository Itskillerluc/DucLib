package io.github.itskillerluc.duclib.client.model.definitions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import io.github.itskillerluc.duclib.client.model.Ducling;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DucLib version of PartDefinition
 */
public class DuclingDefinition extends PartDefinition {
    private final List<WingDefinition> wings;
    private final PartPose partPose;
    private final Map<String, DuclingDefinition> children = Maps.newHashMap();
    public DuclingDefinition(List<WingDefinition> wings, PartPose pPartPose) {
        super((List<CubeDefinition>)(List<?>) wings, pPartPose);
        this.wings = wings;
        this.partPose = pPartPose;
    }

    /**
     * don't use this.
     */
    @Override
    public @NotNull DuclingDefinition addOrReplaceChild(@NotNull String pName, @NotNull CubeListBuilder pCubes, @NotNull PartPose pPartPose) {
        return (DuclingDefinition) super.addOrReplaceChild(pName, pCubes, pPartPose);
    }

    /**
     * use this instead
     */
    public @NotNull DuclingDefinition addOrReplaceChild(@NotNull String pName, @NotNull WingListBuilder wings, @NotNull PartPose pPartPose) {
        DuclingDefinition duclingDefinition = new DuclingDefinition(wings.getWings(), pPartPose);
        DuclingDefinition duclingDefinition1 = this.children.put(pName, duclingDefinition);
        if (duclingDefinition1 != null) {
            duclingDefinition.children.putAll(duclingDefinition1.children);
        }
        return duclingDefinition;
    }

    /**
     * Bake the DuclingDefinition into a Ducling, roasted Duc, yum.
     * @param pTexWidth texture width
     * @param pTexHeight texture height
     * @return the baked Ducling
     */
    @Override
    public @NotNull Ducling bake(int pTexWidth, int pTexHeight) {
        Object2ObjectArrayMap<String, Ducling> object2objectarraymap = this.children.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (p_171593_) -> p_171593_.getValue().bake(pTexWidth, pTexHeight), (p_171595_, p_171596_) -> p_171595_, Object2ObjectArrayMap::new));
        List<Ducling.Wing> list = this.wings.stream().map(wing -> wing.bake(pTexWidth, pTexHeight)).collect(ImmutableList.toImmutableList());
        Ducling ducling = new Ducling(list, object2objectarraymap);
        ducling.setInitialPose(this.partPose);
        ducling.loadPose(this.partPose);
        return ducling;
    }

    /**
     * gets a child with a key.
     * @param pName name of the child
     * @return the child with the corresponding name.
     */
    @Override
    public @NotNull DuclingDefinition getChild(@NotNull String pName) {
        return this.children.get(pName);
    }
}
