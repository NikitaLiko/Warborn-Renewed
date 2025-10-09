# ⚔️ Руководство по характеристикам брони

## 📊 Какие характеристики можно настроить?

В Warborn-Renewed вы можете настроить следующие параметры брони:

1. **Базовая защита** (Armor Points) - через материал
2. **Прочность** (Durability) - через материал
3. **Твердость** (Toughness) - через материал
4. **Защита от отбрасывания** (Knockback Resistance) - через материал
5. **Защита от пуль** (Bullet Resistance) - кастомный атрибут
6. **Редкость** (Rarity) - цвет названия предмета
7. **Размер стака** (Stack Size)

---

## 🛡️ Способ 1: Использование готовых материалов из SuperbWarfare

### Доступные материалы:

```java
// Из мода SuperbWarfare
import com.atsuishio.superbwarfare.tiers.ModArmorMaterial;

// СТАЛЬ - базовый материал
ModArmorMaterial.STEEL
// Прочность: 35
// Защита: Шлем +2, Жилет +5, Штаны +4, Ботинки +2
// Твердость: низкая

// КАРБИД - улучшенный материал
ModArmorMaterial.CEMENTED_CARBIDE
// Прочность: 50
// Защита: Шлем +3, Жилет +6, Штаны +5, Ботинки +3
// Твердость: средняя
```

### Пример использования:

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("my_armor")
        // Все части используют CEMENTED_CARBIDE
        .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
        
        .helmet(piece -> piece
            .registryName("my_helmet")
            .visuals(spec -> spec
                .model("...")
                .texture("..."))
            .bones(bones -> bones.head("armorHead"))
            .bulletResistance(0.25D))  // 25% защиты от пуль
);
```

---

## 🎨 Способ 2: Разные материалы для разных частей

Вы можете использовать разные материалы для каждой части брони:

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("mixed_armor")
        // Шлем - дешевая сталь
        .helmet(piece -> piece
            .registryName("steel_helmet")
            .material(type -> ModArmorMaterial.STEEL)  // ← Только для шлема
            .visuals(spec -> spec.model("...").texture("..."))
            .bones(bones -> bones.head("armorHead"))
            .bulletResistance(0.15D))
        
        // Жилет - дорогой карбид (лучшая защита)
        .chestplate(piece -> piece
            .registryName("carbide_vest")
            .material(type -> ModArmorMaterial.CEMENTED_CARBIDE)  // ← Только для жилета
            .visuals(spec -> spec.model("...").texture("..."))
            .bones(bones -> bones.body("armorBody"))
            .bulletResistance(0.45D))
);
```

---

## 🔧 Способ 3: Создание собственного материала

Если вам нужны кастомные характеристики, создайте свой ArmorMaterial:

### Создайте класс ModArmorMaterials.java:

```java
package ru.liko.warbornrenewed.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public enum ModArmorMaterials implements ArmorMaterial {
    
    // ЛЕГКАЯ БРОНЯ - высокая подвижность, низкая защита
    LIGHT(
        "light",                    // Имя материала
        15,                         // Прочность множитель (15 x slots = durability)
        new int[]{1, 3, 4, 1},      // Защита [boots, legs, chest, helmet]
        12,                         // Enchantability (шанс хороших зачарований)
        SoundEvents.ARMOR_EQUIP_LEATHER,  // Звук надевания
        0.0F,                       // Toughness (твердость)
        0.0F,                       // Knockback Resistance (защита от отбрасывания)
        () -> Ingredient.EMPTY      // Материал ремонта
    ),
    
    // СРЕДНЯЯ БРОНЯ - баланс защиты и прочности
    MEDIUM(
        "medium",
        25,                         // Прочность выше
        new int[]{2, 5, 6, 2},      // Больше защиты
        15,
        SoundEvents.ARMOR_EQUIP_IRON,
        1.0F,                       // Небольшая твердость
        0.0F,
        () -> Ingredient.EMPTY
    ),
    
    // ТЯЖЕЛАЯ БРОНЯ - максимальная защита и прочность
    HEAVY(
        "heavy",
        35,                         // Высокая прочность
        new int[]{3, 6, 8, 3},      // Максимальная защита
        10,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        3.0F,                       // Высокая твердость
        0.1F,                       // 10% защиты от отбрасывания
        () -> Ingredient.EMPTY
    ),
    
    // ЭЛИТНАЯ БРОНЯ - всё по максимуму
    ELITE(
        "elite",
        50,                         // Очень высокая прочность
        new int[]{4, 7, 9, 4},      // Очень высокая защита
        20,                         // Отличные зачарования
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        4.0F,                       // Очень высокая твердость
        0.2F,                       // 20% защиты от отбрасывания
        () -> Ingredient.EMPTY
    );

    // ========================================
    // Технические методы (не изменяйте)
    // ========================================
    
    private static final int[] DURABILITY_PER_SLOT = {13, 15, 16, 11};  // [boots, legs, chest, helmet]
    
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionPerSlot;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final java.util.function.Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionPerSlot, 
                      int enchantability, SoundEvent equipSound, float toughness, 
                      float knockbackResistance, java.util.function.Supplier<Ingredient> repairIngredient) {
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
```

### Использование своего материала:

```java
import ru.liko.warbornrenewed.registry.ModArmorMaterials;

WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("elite_armor")
        // Используем свой элитный материал
        .defaultMaterial(type -> ModArmorMaterials.ELITE)
        
        .helmet(piece -> piece
            .registryName("elite_helmet")
            .visuals(spec -> spec.model("...").texture("..."))
            .bones(bones -> bones.head("armorHead"))
            .bulletResistance(0.5D))  // 50% защиты от пуль
);
```

---

## 📊 Таблица характеристик материалов

### Стандартные материалы Minecraft:

| Материал | Прочность | Защита | Твердость | Отбрасывание |
|----------|-----------|--------|-----------|--------------|
| Leather | 5 | 5 (★☆☆☆☆) | 0.0 | 0% |
| Gold | 7 | 11 (★★☆☆☆) | 0.0 | 0% |
| Chainmail | 15 | 12 (★★☆☆☆) | 0.0 | 0% |
| Iron | 15 | 15 (★★★☆☆) | 0.0 | 0% |
| Diamond | 33 | 20 (★★★★☆) | 2.0 | 0% |
| Netherite | 37 | 20 (★★★★☆) | 3.0 | 10% |

### Материалы SuperbWarfare:

| Материал | Прочность | Защита | Твердость | Отбрасывание |
|----------|-----------|--------|-----------|--------------|
| STEEL | ~35 | ~13 (★★★☆☆) | ~0.5 | 0% |
| CEMENTED_CARBIDE | ~50 | ~17 (★★★★☆) | ~1.0 | 0% |

---

## 💪 Настройка дополнительных характеристик

### Защита от пуль (Bullet Resistance):

```java
.helmet(piece -> piece
    .registryName("my_helmet")
    // ...
    .bulletResistance(0.35D))  // 35% защиты от пуль
```

**Значения:**
- `0.0` - нет защиты (0%)
- `0.25` - легкая защита (25%)
- `0.5` - средняя защита (50%)
- `0.75` - высокая защита (75%)
- `1.0` - максимальная защита (100%)

### Редкость (цвет названия):

```java
import net.minecraft.world.item.Rarity;

.helmet(piece -> piece
    .registryName("my_helmet")
    // ...
    .properties(props -> props
        .stacksTo(1)
        .rarity(Rarity.EPIC)))  // Фиолетовое название
```

**Доступные редкости:**
- `Rarity.COMMON` - белый (обычный)
- `Rarity.UNCOMMON` - желтый (необычный)
- `Rarity.RARE` - голубой (редкий)
- `Rarity.EPIC` - фиолетовый (эпический)

### Размер стака:

```java
.helmet(piece -> piece
    .registryName("my_helmet")
    // ...
    .properties(props -> props
        .stacksTo(1)        // Максимум 1 в стаке (стандарт для брони)
        .fireResistant()))  // Не горит в огне/лаве
```

---

## 🎯 Примеры наборов с разными характеристиками

### Пример 1: Легкая тактическая броня

```java
private static void registerLightArmor() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("light_tactical")
            .defaultMaterial(type -> ModArmorMaterials.LIGHT)  // Легкий материал
            
            .helmet(piece -> piece
                .registryName("light_helmet")
                .visuals(spec -> spec.model("...").texture("..."))
                .bones(bones -> bones.head("armorHead"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.COMMON))
                .bulletResistance(0.15D))  // Низкая защита от пуль
            
            .chestplate(piece -> piece
                .registryName("light_vest")
                .visuals(spec -> spec.model("...").texture("..."))
                .bones(bones -> bones.body("armorBody"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.COMMON))
                .bulletResistance(0.25D))
    );
}
```

### Пример 2: Тяжелая штурмовая броня

```java
private static void registerHeavyArmor() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("heavy_assault")
            .defaultMaterial(type -> ModArmorMaterials.HEAVY)  // Тяжелый материал
            
            .helmet(piece -> piece
                .registryName("heavy_helmet")
                .visuals(spec -> spec.model("...").texture("..."))
                .bones(bones -> bones.head("armorHead"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .fireResistant())  // Огнеупорная
                .bulletResistance(0.45D))  // Высокая защита от пуль
            
            .chestplate(piece -> piece
                .registryName("heavy_plate")
                .visuals(spec -> spec.model("...").texture("..."))
                .bones(bones -> bones
                    .body("armorBody")
                    .rightArm("armorRightArm")
                    .leftArm("armorLeftArm"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .fireResistant())
                .bulletResistance(0.65D))  // Очень высокая защита
    );
}
```

### Пример 3: Элитная броня спецназа

```java
private static void registerEliteArmor() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("elite_spec_ops")
            .defaultMaterial(type -> ModArmorMaterials.ELITE)  // Элитный материал
            
            .helmet(piece -> piece
                .registryName("elite_helmet")
                .visuals(spec -> spec.model("...").texture("..."))
                .bones(bones -> bones.head("armorHead"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant())
                .bulletResistance(0.6D))  // 60% защиты от пуль
            
            .chestplate(piece -> piece
                .registryName("elite_vest")
                .visuals(spec -> spec.model("...").texture("..."))
                .bones(bones -> bones
                    .body("armorBody")
                    .rightArm("armorRightArm")
                    .leftArm("armorLeftArm"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant())
                .bulletResistance(0.8D))  // 80% защиты от пуль
            
            .leggings(piece -> piece
                .registryName("elite_pants")
                .visuals(spec -> spec.model("...").texture("..."))
                .bones(bones -> bones
                    .body("armorBody")
                    .rightLeg("armorRightLeg")
                    .leftLeg("armorLeftLeg"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant())
                .bulletResistance(0.5D))
            
            .boots(piece -> piece
                .registryName("elite_boots")
                .visuals(spec -> spec.model("...").texture("..."))
                .bones(bones -> bones
                    .rightBoot("armorRightBoot")
                    .leftBoot("armorLeftBoot"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant())
                .bulletResistance(0.3D))
    );
}
```

---

## 📖 Краткая памятка

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("my_armor")
        // МАТЕРИАЛ (защита, прочность, твердость)
        .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
        // или
        .defaultMaterial(type -> ModArmorMaterials.HEAVY)
        
        .helmet(piece -> piece
            .registryName("my_helmet")
            
            // ВИЗУАЛЫ
            .visuals(spec -> spec.model("...").texture("..."))
            
            // КОСТИ
            .bones(bones -> bones.head("armorHead"))
            
            // СВОЙСТВА
            .properties(props -> props
                .stacksTo(1)              // Размер стака
                .rarity(Rarity.EPIC)      // Редкость
                .fireResistant())         // Огнеупорная
            
            // ЗАЩИТА ОТ ПУЛЬ
            .bulletResistance(0.5D))      // 50% защиты
);
```

---

## 🔗 Полезные ссылки

- [ARMOR_TEMPLATE_GUIDE.md](ARMOR_TEMPLATE_GUIDE.md) - Полное руководство по API
- [BONES_REFERENCE.md](BONES_REFERENCE.md) - Справка по костям
- [ARMOR_EXAMPLES.java](ARMOR_EXAMPLES.java) - Готовые примеры

---

✅ **Теперь вы знаете, как настраивать все характеристики брони!**
