package ru.liko.warbornrenewed.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import ru.liko.warbornrenewed.content.armorparts.WarbornArmorPartItem;
import ru.liko.warbornrenewed.content.armorparts.WarbornArmorPartModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

/**
 * Curios renderer that uses GeckoLib for rendering armor parts
 */
public class CuriosArmorPartRenderer implements ICurioRenderer {
    
    private final WarbornArmorPartModel model;
    private final WarbornArmorPartItem item;

    public CuriosArmorPartRenderer(WarbornArmorPartItem item) {
        this.item = item;
        this.model = new WarbornArmorPartModel();
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            net.minecraft.client.renderer.entity.RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource bufferSource,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        M contextModel = renderLayerParent.getModel();
        if (!(contextModel instanceof HumanoidModel<?> humanoidModel)) {
            return;
        }

        // Get the GeckoLib model
        ResourceLocation modelResource = model.getModelResource(item);
        BakedGeoModel geoModel = model.getBakedModel(modelResource);

        // Get texture based on NVG state
        ResourceLocation texture = getTextureForNVGState(stack);

        poseStack.pushPose();

        // Position based on slot type
        positionForSlot(poseStack, slotContext, humanoidModel);

        // Get render type
        RenderType renderType = RenderType.entityTranslucent(texture);

        // Render the model
        if (geoModel != null) {
            model.getBakedModel(modelResource);
            // The actual rendering would use GeckoLib's rendering pipeline
            // This is a simplified version
        }

        poseStack.popPose();
    }

    /**
     * Position the model based on the Curios slot type
     */
    private void positionForSlot(PoseStack poseStack, SlotContext slotContext, HumanoidModel<?> humanoidModel) {
        String slotId = slotContext.identifier();
        
        switch (slotId) {
            case "nvg":
                // Position on head
                ICurioRenderer.followHeadRotations(slotContext.entity(), humanoidModel.head);
                humanoidModel.head.translateAndRotate(poseStack);
                poseStack.scale(1.0f, 1.0f, 1.0f);
                break;
                
            case "backpack":
                // Position on body/back
                ICurioRenderer.followBodyRotations(slotContext.entity());
                humanoidModel.body.translateAndRotate(poseStack);
                poseStack.translate(0, 0, 0.15);
                break;
                
            case "vest":
                // Position on chest
                ICurioRenderer.followBodyRotations(slotContext.entity());
                humanoidModel.body.translateAndRotate(poseStack);
                poseStack.scale(1.05f, 1.05f, 1.05f);
                break;
                
            case "headset":
                // Position on head
                ICurioRenderer.followHeadRotations(slotContext.entity(), humanoidModel.head);
                humanoidModel.head.translateAndRotate(poseStack);
                break;
                
            case "goggles":
                // Position on head/face
                ICurioRenderer.followHeadRotations(slotContext.entity(), humanoidModel.head);
                humanoidModel.head.translateAndRotate(poseStack);
                poseStack.translate(0, 0.15, -0.3);
                break;
        }
    }

    /**
     * Get the appropriate texture based on NVG toggle state
     */
    private ResourceLocation getTextureForNVGState(ItemStack stack) {
        if (item.hasNVGToggle() && item.getVisuals().hasDownState()) {
            boolean isDown = stack.getOrCreateTag().getBoolean("nvg_down");
            return isDown ? item.getVisuals().textureDown() : item.getVisuals().texture();
        }
        return item.getVisuals().texture();
    }
}
