package ru.liko.warbornrenewed.content.armorparts;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

/**
 * GeckoLib renderer for armor parts with NVG toggle support
 */
public class WarbornArmorPartRenderer extends GeoItemRenderer<WarbornArmorPartItem> {

    public WarbornArmorPartRenderer() {
        super(new WarbornArmorPartModel());
    }

    @Override
    public ResourceLocation getTextureLocation(WarbornArmorPartItem animatable) {
        // This method is called during rendering, but we override preRender to handle texture switching
        return animatable.getVisuals().texture();
    }

    @Override
    public void preRender(PoseStack poseStack, WarbornArmorPartItem animatable, BakedGeoModel model,
                         MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender,
                         float partialTick, int packedLight, int packedOverlay, float red, float green,
                         float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender,
            partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(WarbornArmorPartItem animatable, ResourceLocation texture,
                                   @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    /**
     * Get the texture based on NVG state from ItemStack NBT
     */
    public ResourceLocation getTextureForStack(ItemStack stack, WarbornArmorPartItem item) {
        if (item.hasNVGToggle() && item.getVisuals().hasDownState()) {
            boolean isDown = stack.getOrCreateTag().getBoolean("nvg_down");
            return isDown ? item.getVisuals().textureDown() : item.getVisuals().texture();
        }
        return item.getVisuals().texture();
    }
}
