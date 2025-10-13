package ru.liko.warbornrenewed.content.armorparts;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Objects;

/**
 * Armor part item with Curios API integration and GeckoLib animation support.
 * Supports NVG toggle functionality.
 */
public class WarbornArmorPartItem extends Item implements ICurioItem, GeoItem {
    private final String itemId;
    private final WarbornArmorPart.PartSlotType slotType;
    private final ArmorPartVisualSpec visuals;
    private final boolean hasNVGToggle;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WarbornArmorPartItem(String itemId, WarbornArmorPart.PartSlotType slotType,
                                Properties properties, ArmorPartVisualSpec visuals, boolean hasNVGToggle) {
        super(properties);
        this.itemId = Objects.requireNonNull(itemId, "itemId");
        this.slotType = Objects.requireNonNull(slotType, "slotType");
        this.visuals = Objects.requireNonNull(visuals, "visuals");
        this.hasNVGToggle = hasNVGToggle;
    }

    public String getItemId() {
        return itemId;
    }

    public WarbornArmorPart.PartSlotType getSlotType() {
        return slotType;
    }

    public ArmorPartVisualSpec getVisuals() {
        return visuals;
    }

    public boolean hasNVGToggle() {
        return hasNVGToggle;
    }

    // ==================== ICurioItem Implementation ====================

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(
            net.minecraft.sounds.SoundEvents.ARMOR_EQUIP_GENERIC,
            1.0f,
            1.0f
        );
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        // Called when the item is equipped in a Curio slot
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        // Called when the item is unequipped from a Curio slot
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        // Called every tick while the item is equipped
        // You can add effects here (e.g., night vision when NVG is active)
        ICurioItem.super.curioTick(slotContext, stack);
    }

    /**
     * Whether this curio item can be equipped in the given slot context
     */
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return slotContext.identifier().equals(slotType.getSlotName());
    }

    // ==================== Client-Side Rendering ====================

    // ВАЖНО: Раскомментируйте после создания 3D моделей в Blockbench!
    // GeckoLib рендерер будет пытаться загрузить .geo.json файлы при старте игры.
    // Если файлов нет - игра упадет с ошибкой GeckoLibCache.
    
    /*
    @OnlyIn(Dist.CLIENT)
    public top.theillusivec4.curios.api.client.ICurioRenderer createRenderer() {
        // Return our custom GeckoLib-based renderer
        return new ru.liko.warbornrenewed.client.CuriosArmorPartRenderer(this);
    }
    */

    // ==================== GeckoLib Animation ====================

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        if (hasNVGToggle) {
            // Add NVG toggle animation controller
            controllers.add(new software.bernie.geckolib.core.animation.AnimationController<>(
                this,
                "nvg_toggle_controller",
                0,
                state -> {
                    // Default idle animation
                    // You can customize these animation names in your .animation.json file
                    state.getController().setAnimation(
                        software.bernie.geckolib.core.animation.RawAnimation.begin()
                            .thenPlay("idle")
                    );
                    return software.bernie.geckolib.core.object.PlayState.CONTINUE;
                }
            ));
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
