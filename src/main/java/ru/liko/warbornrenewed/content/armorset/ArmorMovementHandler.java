package ru.liko.warbornrenewed.content.armorset;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.registry.ModAttributes;

import java.util.UUID;

/**
 * Applies movement speed penalties/bonuses from armor to the vanilla movement speed attribute.
 *
 * We keep using the custom attribute (armor_movement_speed) on items for clean data separation and tooltips,
 * then translate the summed value into a single modifier on Attributes.MOVEMENT_SPEED so it actually affects gameplay.
 */
@Mod.EventBusSubscriber(modid = Warbornrenewed.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ArmorMovementHandler {
    // Stable UUID to update the same modifier every tick
    private static final UUID ARMOR_SPEED_UUID = UUID.fromString("5d3f8f8a-8e2e-4e9f-9f2f-9f7e1cc9e6b1");

    private ArmorMovementHandler() {}

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity == null || entity.level().isClientSide) {
            // Only run on server to avoid double-applying; attribute is synced to client
            return;
        }

        // Sum movement speed modifiers from worn armor items.
        double totalSpeedModifier = 0.0D; // e.g., -0.12 for -12%

        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof WarbornArmorItem armorItem)) continue;

            Multimap<Attribute, AttributeModifier> itemMods = armorItem.getAttributeModifiers(slot, stack);
            Attribute moveAttr = ModAttributes.ARMOR_MOVEMENT_SPEED.get();
            if (itemMods == null || itemMods.isEmpty()) continue;

            for (var entry : itemMods.entries()) {
                if (entry.getKey() == moveAttr) {
                    AttributeModifier mod = entry.getValue();
                    // We author movement speed on items as ADDITION of percentage points (e.g., -0.12 = -12%)
                    if (mod.getOperation() == AttributeModifier.Operation.ADDITION) {
                        totalSpeedModifier += mod.getAmount();
                    } else if (mod.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                        totalSpeedModifier *= (1.0 + mod.getAmount());
                    }
                }
            }
        }

        // Apply to vanilla movement speed as a MULTIPLY_BASE modifier
        AttributeInstance movement = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movement == null) return;

        // Remove previous modifier
        AttributeModifier existing = movement.getModifier(ARMOR_SPEED_UUID);
        if (existing != null) {
            movement.removeModifier(existing);
        }

        // Only apply if non-zero to avoid clutter
        if (Math.abs(totalSpeedModifier) > 1.0E-6) {
            // Negative value slows down, positive speeds up
            AttributeModifier applied = new AttributeModifier(
                    ARMOR_SPEED_UUID,
                    Warbornrenewed.MODID + ":armor_movement_speed",
                    totalSpeedModifier,
                    AttributeModifier.Operation.MULTIPLY_BASE
            );
            movement.addPermanentModifier(applied);
        }
    }
}
