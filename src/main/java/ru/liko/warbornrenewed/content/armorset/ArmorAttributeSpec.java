package ru.liko.warbornrenewed.content.armorset;

import com.google.common.base.Preconditions;
import com.atsuishio.superbwarfare.init.ModAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public final class ArmorAttributeSpec {
    private final Supplier<Attribute> attribute;
    private final String idSuffix;
    private final AttributeModifier.Operation operation;
    private final double baseValue;
    private final boolean scaleWithDurability;

    private ArmorAttributeSpec(Supplier<Attribute> attribute, String idSuffix, AttributeModifier.Operation operation, double baseValue, boolean scaleWithDurability) {
        this.attribute = Objects.requireNonNull(attribute, "attribute");
        this.idSuffix = Objects.requireNonNull(idSuffix, "idSuffix");
        this.operation = Objects.requireNonNull(operation, "operation");
        this.baseValue = baseValue;
        this.scaleWithDurability = scaleWithDurability;
    }

    public Attribute attribute() {
        return attribute.get();
    }

    public AttributeModifier createModifier(UUID uuid, ItemStack stack, String itemId, EquipmentSlot slot) {
        Preconditions.checkNotNull(uuid, "uuid");
        Preconditions.checkNotNull(stack, "stack");
        Preconditions.checkNotNull(itemId, "itemId");
        double amount = baseValue;
        if (scaleWithDurability && stack.isDamageableItem() && stack.getMaxDamage() > 0) {
            double ratio = 1.0D - (double) stack.getDamageValue() / stack.getMaxDamage();
            amount *= Math.max(0.0D, ratio);
        }
        return new AttributeModifier(uuid, itemId + "/" + idSuffix, amount, operation);
    }

    public static ArmorAttributeSpec bulletResistance(double value) {
        return bulletResistance(value, true);
    }

    public static ArmorAttributeSpec bulletResistance(double value, boolean scaleWithDurability) {
        return new ArmorAttributeSpec(ModAttributes.BULLET_RESISTANCE, "bullet_resistance", AttributeModifier.Operation.ADDITION, value, scaleWithDurability);
    }

    public static ArmorAttributeSpec of(Supplier<Attribute> attribute, String idSuffix, AttributeModifier.Operation operation, double value, boolean scaleWithDurability) {
        return new ArmorAttributeSpec(attribute, idSuffix, operation, value, scaleWithDurability);
    }
}
