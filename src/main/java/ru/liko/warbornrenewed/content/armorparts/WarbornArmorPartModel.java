package ru.liko.warbornrenewed.content.armorparts;

import net.minecraft.resources.ResourceLocation;
import ru.liko.warbornrenewed.Warbornrenewed;
import software.bernie.geckolib.model.GeoModel;

/**
 * GeckoLib model for armor parts (NVG, backpacks, etc.)
 */
public class WarbornArmorPartModel extends GeoModel<WarbornArmorPartItem> {

    @Override
    public ResourceLocation getModelResource(WarbornArmorPartItem item) {
        return item.getVisuals().model();
    }

    @Override
    public ResourceLocation getTextureResource(WarbornArmorPartItem item) {
        // Check NBT state for NVG toggle
        if (item.hasNVGToggle() && item.getVisuals().hasDownState()) {
            // We'll check the actual state in the renderer
            return item.getVisuals().texture();
        }
        return item.getVisuals().texture();
    }

    @Override
    @SuppressWarnings("removal")
    public ResourceLocation getAnimationResource(WarbornArmorPartItem item) {
        // Animation file path based on item ID
        String itemId = item.getItemId();
        return new ResourceLocation(Warbornrenewed.MODID, "animations/item/" + itemId + ".animation.json");
    }
}
