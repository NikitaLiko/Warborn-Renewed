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
     * КЕВЛАР (KEVLAR) - NIJ Level IIA/II
     * Мягкая баллистическая защита из арамидных волокон
     * Используется в: легких бронежилетах, полицейской броне
     * Реальные аналоги: DuPont Kevlar 29, Twaron
     */
    LEATHER(
            "leather",
            5,                         // Прочность
            new int[]{1, 1, 1, 1},      // Защита [ботинки, штаны, жилет, шлем]
            0,                         // Зачаровываемость
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.1F,                       // Твердость: 0.5 (мягкая броня)
            0.0F,                       // Защита от отбрасывания: 0%
            () -> Ingredient.EMPTY
    ),
    KEVLAR(
        "kevlar",
        20,                         // Прочность: 20 (легкий износ)
        new int[]{1, 3, 5, 2},      // Защита [ботинки, штаны, жилет, шлем]
        15,                         // Зачаровываемость: высокая
        SoundEvents.ARMOR_EQUIP_LEATHER,
        0.5F,                       // Твердость: 0.5 (мягкая броня)
        0.0F,                       // Защита от отбрасывания: 0%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * КЕРАМИКА (CERAMIC) - NIJ Level III
     * Керамические пластины (Al2O3, SiC, B4C)
     * Используется в: тактических бронеплитах, военных жилетах
     * Реальные аналоги: alumina plates, silicon carbide
     */
    CERAMIC(
        "ceramic",
        30,                         // Прочность: 30 (средняя, хрупкая при множественных попаданиях)
        new int[]{2, 5, 7, 3},      // Защита [ботинки, штаны, жилет, шлем]
        12,                         // Зачаровываемость: средняя
        SoundEvents.ARMOR_EQUIP_IRON,
        2.5F,                       // Твердость: 2.5 (жесткая броня)
        0.05F,                      // Защита от отбрасывания: 5%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * СТАЛЬ AR500 - NIJ Level III
     * Высокопрочная баллистическая сталь
     * Используется в: дешевых бронеплитах, тренировочных жилетах
     * Реальные аналоги: AR500, AR550 steel plates
     */
    AR500_STEEL(
        "ar500_steel",
        40,                         // Прочность: 40 (высокая, но тяжелая)
        new int[]{2, 4, 6, 2},      // Защита [ботинки, штаны, жилет, шлем]
        10,                         // Зачаровываемость: низкая
        SoundEvents.ARMOR_EQUIP_IRON,
        2.0F,                       // Твердость: 2.0
        0.1F,                       // Защита от отбрасывания: 10%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * ПОЛИЭТИЛЕН UHMWPE - NIJ Level III/IV
     * Ультравысокомолекулярный полиэтилен
     * Используется в: современных легких плитах, элитной броне
     * Реальные аналоги: Dyneema, Spectra Shield
     */
    UHMWPE(
        "uhmwpe",
        45,                         // Прочность: 45 (высокая + легкая)
        new int[]{3, 6, 8, 3},      // Защита [ботинки, штаны, жилет, шлем]
        18,                         // Зачаровываемость: очень высокая
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        3.0F,                       // Твердость: 3.0
        0.08F,                      // Защита от отбрасывания: 8%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * КОМПОЗИТ (COMPOSITE) - NIJ Level IV
     * Многослойная композитная броня (керамика + UHMWPE + сталь)
     * Используется в: военных плитах ESAPI/XSAPI, спецназовской броне
     * Реальные аналоги: ESAPI plates, XSAPI plates
     */
    COMPOSITE(
        "composite",
        55,                         // Прочность: 55 (очень высокая)
        new int[]{3, 7, 9, 4},      // Защита [ботинки, штаны, жилет, шлем]
        15,                         // Зачаровываемость: средняя
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        4.0F,                       // Твердость: 4.0
        0.15F,                      // Защита от отбрасывания: 15%
        () -> Ingredient.EMPTY
    ),
    
    /**
     * ТИТАН (TITANIUM) - NIJ Level III+
     * Титановые сплавы (Ti-6Al-4V)
     * Используется в: авиационной броне, элитных шлемах, спецназе
     * Реальные аналоги: titanium alloy plates
     */
    TITANIUM(
        "titanium",
        60,                         // Прочность: 60 (максимальная)
        new int[]{4, 7, 9, 4},      // Защита [ботинки, штаны, жилет, шлем]
        20,                         // Зачаровываемость: максимальная
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        4.5F,                       // Твердость: 4.5
        0.2F,                       // Защита от отбрасывания: 20%
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
