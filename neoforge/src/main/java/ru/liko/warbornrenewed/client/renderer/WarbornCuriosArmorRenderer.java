package ru.liko.warbornrenewed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import ru.liko.warbornrenewed.content.armorset.ArmorBonesSpec;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * Простой рендерер для отображения брони Warborn через Curios
 */
public class WarbornCuriosArmorRenderer extends GeoArmorRenderer<WarbornArmorItem> {

    public WarbornCuriosArmorRenderer(WarbornArmorItem item) {
        super(new WarbornArmorModel(item.getVisuals()));
        // Применяем правильные кости на основе типа брони
        ArmorBonesSpec bones = item.getBones();
        if (bones != null) {
            bones.apply(this);
        } else {
            // Если кости не заданы, используем значения по умолчанию
            ArmorBonesSpec.defaults(item.getType()).apply(this);
        }
    }

    @Override
    public void actuallyRender(PoseStack poseStack, WarbornArmorItem animatable, BakedGeoModel model,
            RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
            boolean isReRender, float partialTick, int packedLight, int packedOverlay,
            int colour) {
        // Color is passed as packed ARGB in 1.21.1 GeckoLib
        // TODO: Implement custom color support with DataComponents when available
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, colour);
    }
}
