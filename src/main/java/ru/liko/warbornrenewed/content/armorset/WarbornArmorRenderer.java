package ru.liko.warbornrenewed.content.armorset;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WarbornArmorRenderer extends GeoArmorRenderer<WarbornArmorItem> {
    private final ArmorVisualSpec visuals;

    public WarbornArmorRenderer(ArmorVisualSpec visuals, ArmorBonesSpec bones) {
        super(new WarbornArmorModel(visuals));
        this.visuals = visuals;
        bones.apply(this);
    }

    @Override
    public RenderType getRenderType(WarbornArmorItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
