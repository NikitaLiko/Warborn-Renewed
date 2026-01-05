package ru.liko.warbornrenewed.content.armorset;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WarbornArmorRenderer extends GeoArmorRenderer<WarbornArmorItem> {
    private final ArmorVisualSpec visuals;

    public WarbornArmorRenderer(ArmorVisualSpec visuals, ArmorBonesSpec bones) {
        super(new WarbornArmorModel(visuals));
        this.visuals = visuals;
        bones.apply(this);
    }

    @Override
    public ResourceLocation getTextureLocation(WarbornArmorItem animatable) {
        ItemStack stack = this.currentStack;
        if (stack != null && stack.hasTag() && stack.getTag().contains("Variant")) {
            String variant = stack.getTag().getString("Variant");
            if (visuals.variants().containsKey(variant)) {
                return visuals.variants().get(variant);
            }
        }
        return super.getTextureLocation(animatable);
    }

    @Override
    public RenderType getRenderType(WarbornArmorItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, WarbornArmorItem animatable, BakedGeoModel model,
                               RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                               boolean isReRender, float partialTick, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        // Применяем цвет из DyeableLeatherItem если предмет покрашен
        ItemStack stack = this.currentStack;
        if (stack != null) {
            int color = animatable.getColor(stack);
            if (color != 0xA06540) { // Default leather color
                red = ((color >> 16) & 0xFF) / 255.0F;
                green = ((color >> 8) & 0xFF) / 255.0F;
                blue = (color & 0xFF) / 255.0F;
            }
        }

        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
