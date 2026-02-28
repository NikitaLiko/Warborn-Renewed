package ru.liko.warbornrenewed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import net.minecraft.world.entity.EquipmentSlot;

public class WarbornCuriosArmorRenderer extends GeoArmorRenderer<WarbornArmorItem> {
    public WarbornCuriosArmorRenderer() {
        super(null); // The model will be injected via CustomPackArmorItem
    }

    public void actuallyRender(PoseStack poseStack, WarbornArmorItem animatable, BakedGeoModel model, RenderType renderType, 
                             MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, 
                             int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        // Render without modifications since we handle scale in bones/Cubes
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}


