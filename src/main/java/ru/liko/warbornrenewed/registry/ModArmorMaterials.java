package ru.liko.warbornrenewed.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

/**
 * Собственные материалы брони для Warborn-Renewed.
 * Полностью независимая система без зависимостей от других модов.
 * 
 * Материалы основаны на реальных стандартах:
 * - NIJ Standard 0101.06 (США)
 * - ГОСТ Р 50744-95 (Россия)
 * - VPAM (Германия)
 * 
 * Реалистичные характеристики материалов и классов защиты.
 */
public enum ModArmorMaterials implements ArmorMaterial {
    
    /**
     * ЛЕГКАЯ БРОНЯ - для разведки и скорости
     * - Низкая защита, но высокая подвижность
     * - Подходит для легких боевых задач
     */
    LIGHT(
        "light",
        15,                         // Прочность: 15 x slot multiplier
        new int[]{1, 3, 4, 1},      // Защита [ботинки, штаны, жилет, шлем]
        12,                         // Зачаровываемость
        SoundEvents.ARMOR_EQUIP_LEATHER,
        0.0F,                       // Твердость: 0
        0.0F,                       // Защита от отбрасывания: 0%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * СРЕДНЯЯ БРОНЯ - баланс защиты и мобильности
     * - Стандартная тактическая броня
     * - Оптимальное соотношение защиты и веса
     */
    MEDIUM(
        "medium",
        25,                         // Прочность: 25 x slot multiplier
        new int[]{2, 5, 6, 2},      // Защита [ботинки, штаны, жилет, шлем]
        15,                         // Зачаровываемость
        SoundEvents.ARMOR_EQUIP_IRON,
        1.0F,                       // Твердость: 1.0
        0.0F,                       // Защита от отбрасывания: 0%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * ТЯЖЕЛАЯ БРОНЯ - максимальная защита
     * - Высокая защита и прочность
     * - Снижает подвижность, но обеспечивает отличную защиту
     */
    HEAVY(
        "heavy",
        35,                         // Прочность: 35 x slot multiplier
        new int[]{3, 6, 8, 3},      // Защита [ботинки, штаны, жилет, шлем]
        10,                         // Зачаровываемость
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        3.0F,                       // Твердость: 3.0
        0.1F,                       // Защита от отбрасывания: 10%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * ЭЛИТНАЯ БРОНЯ - для спецопераций
     * - Максимальная защита и прочность
     * - Лучшие характеристики во всех аспектах
     * - Самая редкая и эффективная броня
     */
    ELITE(
        "elite",
        50,                         // Прочность: 50 x slot multiplier
        new int[]{4, 7, 9, 4},      // Защита [ботинки, штаны, жилет, шлем]
        20,                         // Зачаровываемость
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        4.0F,                       // Твердость: 4.0
        0.2F,                       // Защита от отбрасывания: 20%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * СТАЛЬНАЯ БРОНЯ - базовый военный материал
     * - Классическая стальная броня
     * - Доступная и надежная
     */
    STEEL(
        "steel",
        35,                         // Прочность: 35 x slot multiplier
        new int[]{2, 5, 6, 2},      // Защита [ботинки, штаны, жилет, шлем]
        12,                         // Зачаровываемость
        SoundEvents.ARMOR_EQUIP_IRON,
        0.5F,                       // Твердость: 0.5
        0.0F,                       // Защита от отбрасывания: 0%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * КАРБИДНАЯ БРОНЯ - улучшенный материал
     * - Современная композитная броня
     * - Высокая прочность и защита
     */
    CEMENTED_CARBIDE(
        "cemented_carbide",
        50,                         // Прочность: 50 x slot multiplier
        new int[]{3, 6, 8, 3},      // Защита [ботинки, штаны, жилет, шлем]
        15,                         // Зачаровываемость
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        2.0F,                       // Твердость: 2.0
        0.05F,                      // Защита от отбрасывания: 5%
        () -> Ingredient.EMPTY
    );

    // ========================================
    // Технические поля и методы
    // ========================================
    
    /**
     * Базовая прочность для каждого слота брони.
     * Порядок: [BOOTS, LEGGINGS, CHESTPLATE, HELMET]
     */
    private static final int[] DURABILITY_PER_SLOT = {13, 15, 16, 11};
    
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionPerSlot;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final java.util.function.Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(
            String name,
            int durabilityMultiplier,
            int[] protectionPerSlot,
            int enchantability,
            SoundEvent equipSound,
            float toughness,
            float knockbackResistance,
            java.util.function.Supplier<Ingredient> repairIngredient
    ) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionPerSlot = protectionPerSlot;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(@NotNull ArmorItem.Type type) {
        return DURABILITY_PER_SLOT[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(@NotNull ArmorItem.Type type) {
        return this.protectionPerSlot[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
