package io.github.itskillerluc.duclib.client.model.definitions;

import net.minecraft.client.model.geom.builders.MaterialDefinition;

/**
 * DucLib version of MaterialDefinition
 */
public class FeatherMaterialDefinition extends MaterialDefinition{
    final int xTexSize;
    final int yTexSize;

    public FeatherMaterialDefinition(int pXTexSize, int pYTexSize) {
        super(pXTexSize, pYTexSize);
        this.xTexSize = pXTexSize;
        this.yTexSize = pYTexSize;
    }

    public int getXTexSize() {
        return xTexSize;
    }

    public int getYTexSize() {
        return yTexSize;
    }
}
