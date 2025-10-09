package ru.liko.warbornrenewed.content.armorset;

import com.google.common.base.Preconditions;
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

    /**
     * Защита от пуль (Bullet Resistance).
     * Использует собственный атрибут из ModAttributes.
     * Совместим с SuperbWarfare и TACZ.
     * 
     * @param value Значение защиты (0.0 - 1.0)
     * @return Спецификация атрибута
     */
    public static ArmorAttributeSpec bulletResistance(double value) {
        return bulletResistance(value, true);
    }

    /**
     * Защита от пуль (Bullet Resistance) с настройкой масштабирования по прочности.
     * 
     * @param value Значение защиты (0.0 - 1.0)
     * @param scaleWithDurability Масштабировать ли значение в зависимости от прочности брони
     * @return Спецификация атрибута
     */
    public static ArmorAttributeSpec bulletResistance(double value, boolean scaleWithDurability) {
        return new ArmorAttributeSpec(
            ru.liko.warbornrenewed.registry.ModAttributes.BULLET_RESISTANCE::get,
            "bullet_resistance",
            AttributeModifier.Operation.ADDITION,
            value,
            scaleWithDurability
        );
    }

    /**
     * Класс защиты (Protection Class) по стандартам NIJ/ГОСТ.
     * 
     * @param protectionClass Класс защиты (0-6)
     * @return Спецификация атрибута
     */
    public static ArmorAttributeSpec protectionClass(int protectionClass) {
        return new ArmorAttributeSpec(
            ru.liko.warbornrenewed.registry.ModAttributes.PROTECTION_CLASS::get,
            "protection_class",
            AttributeModifier.Operation.ADDITION,
            protectionClass,
            false
        );
    }

    /**
     * Эффективная толщина брони (Effective Thickness) в мм стальной эквивалентности.
     * 
     * @param thickness Толщина в мм (0.0 - 100.0)
     * @return Спецификация атрибута
     */
    public static ArmorAttributeSpec effectiveThickness(double thickness) {
        return new ArmorAttributeSpec(
            ru.liko.warbornrenewed.registry.ModAttributes.EFFECTIVE_THICKNESS::get,
            "effective_thickness",
            AttributeModifier.Operation.ADDITION,
            thickness,
            false
        );
    }

    /**
     * Множитель урона от взрывов (Blast Damage Multiplier).
     * 
     * @param multiplier Множитель (0.0 - 2.0), где 1.0 = нормальный урон
     * @return Спецификация атрибута
     */
    public static ArmorAttributeSpec blastResistance(double multiplier) {
        return new ArmorAttributeSpec(
            ru.liko.warbornrenewed.registry.ModAttributes.BLAST_DAMAGE_MULTIPLIER::get,
            "blast_damage_multiplier",
            AttributeModifier.Operation.MULTIPLY_BASE,
            multiplier - 1.0, // Конвертируем в модификатор
            false
        );
    }

    /**
     * Модификатор скорости движения (Movement Speed).
     * 
     * @param speedModifier Модификатор скорости (-0.5 - 0.2)
     * @return Спецификация атрибута
     */
    public static ArmorAttributeSpec movementSpeed(double speedModifier) {
        return new ArmorAttributeSpec(
            ru.liko.warbornrenewed.registry.ModAttributes.ARMOR_MOVEMENT_SPEED::get,
            "armor_movement_speed",
            AttributeModifier.Operation.ADDITION,
            speedModifier,
            false
        );
    }

    /**
     * Создание кастомного атрибута.
     * 
     * @param attribute Поставщик атрибута
     * @param idSuffix Суффикс ID для модификатора
     * @param operation Операция модификатора
     * @param value Значение
     * @param scaleWithDurability Масштабировать ли по прочности
     * @return Спецификация атрибута
     */
    public static ArmorAttributeSpec of(Supplier<Attribute> attribute, String idSuffix, AttributeModifier.Operation operation, double value, boolean scaleWithDurability) {
        return new ArmorAttributeSpec(attribute, idSuffix, operation, value, scaleWithDurability);
    }
}
