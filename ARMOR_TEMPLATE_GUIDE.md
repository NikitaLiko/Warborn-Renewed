# 🛡️ WarBorn Renewed - Руководство по созданию брони

## 📋 Содержание
1. [Введение](#введение)
2. [Быстрый старт](#быстрый-старт)
3. [Структура проекта](#структура-проекта)
4. [Создание набора брони](#создание-набора-брони)
5. [Подробная документация API](#подробная-документация-api)
6. [Примеры](#примеры)
7. [Интеграция с SuperbWarfare](#интеграция-с-superbwarfare)
8. [Часто задаваемые вопросы](#часто-задаваемые-вопросы)

---

## 🎯 Введение

**WarBorn Renewed** — это аддон для мода **SuperbWarfare (SBW)**, который предоставляет гибкую систему шаблонов для быстрого добавления новых наборов брони с использованием GeckoLib.

### Основные возможности:
- ✅ **Простой API** для создания наборов брони
- ✅ **Поддержка GeckoLib** (модели, текстуры, анимации)
- ✅ **Гибкая настройка** атрибутов (защита от пуль, прочность и т.д.)
- ✅ **Автоматическая регистрация** предметов
- ✅ **Интеграция с SuperbWarfare** (материалы, атрибуты)
- ✅ **Минимум кода** — максимум результата

---

## 🚀 Быстрый старт

### Шаг 1: Создайте новый набор брони

Откройте файл `WarbornArmorSets.java` и добавьте свой набор:

```java
private static void registerMyCustomSet() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("my_custom_armor")
            // Указываем материал из SuperbWarfare
            .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
            
            // Добавляем шлем
            .helmet(piece -> piece
                .registryName("my_custom_helmet")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                    .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
                .bulletResistance(0.25D))
            
            // Добавляем нагрудник
            .chestplate(piece -> piece
                .registryName("my_custom_chestplate")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_chest_iotv.geo.json")
                    .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                .bulletResistance(0.45D))
    );
}
```

### Шаг 2: Зарегистрируйте набор в `bootstrap()`

```java
public static void bootstrap() {
    registerMyCustomSet();
    // Добавьте другие наборы здесь
}
```

### Шаг 3: Готово! 🎉

Ваша броня автоматически зарегистрирована и доступна в игре.

---

## 📁 Структура проекта

```
src/main/java/ru/liko/warbornrenewed/
├── Warbornrenewed.java              # Главный класс мода
├── content/
│   └── armorset/
│       ├── WarbornArmorSet.java         # Основной класс набора брони
│       ├── WarbornArmorRegistry.java    # Регистрация наборов
│       ├── WarbornArmorItem.java        # Класс предмета брони
│       ├── WarbornArmorModel.java       # GeckoLib модель
│       ├── WarbornArmorRenderer.java    # GeckoLib рендерер
│       ├── ArmorVisualSpec.java         # Спецификация визуализации
│       ├── ArmorBonesSpec.java          # Спецификация костей модели
│       └── ArmorAttributeSpec.java      # Спецификация атрибутов
├── registry/
│   ├── ModItems.java                # Регистратор предметов
│   └── ModCreativeTabs.java         # Вкладки творческого режима
└── setup/
    └── WarbornArmorSets.java        # 🔥 ЗДЕСЬ ВЫ СОЗДАЁТЕ СВОИ НАБОРЫ
```

---

## 🛠️ Создание набора брони

### Базовая структура

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("unique_set_id")
        // Глобальные настройки для всего набора
        .defaultMaterial(type -> ModArmorMaterial.STEEL)
        .defaultVisuals(spec -> spec
            .model("warbornrenewed:geo/default_armor.geo.json")
            .texture("warbornrenewed:textures/armor/default_armor.png"))
        
        // Конкретные части брони
        .helmet(piece -> { /* настройка */ })
        .chestplate(piece -> { /* настройка */ })
        .leggings(piece -> { /* настройка */ })
        .boots(piece -> { /* настройка */ })
);
```

### Полный пример с всеми опциями

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("advanced_tactical")
        // === ГЛОБАЛЬНЫЕ НАСТРОЙКИ ===
        .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
        
        // Глобальные визуальные настройки (можно переопределить для каждой части)
        .defaultVisuals(spec -> spec
            .model("warbornrenewed:geo/tactical_base.geo.json")
            .texture("warbornrenewed:textures/armor/tactical_base.png")
            .animation("warbornrenewed:animations/tactical.animation.json")) // опционально
        
        // Глобальные настройки костей (можно переопределить)
        .defaultBones(bones -> bones
            .head("customHeadBone")
            .body("customBodyBone"))
        
        // === ШЛЕМ ===
        .helmet(piece -> piece
            .registryName("advanced_tactical_helmet")
            
            // Визуализация (переопределяет глобальные)
            .visuals(spec -> spec
                .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
            
            // Кастомные кости (опционально)
            .bones(bones -> bones
                .head("helmetBone"))
            
            // Свойства предмета
            .properties(props -> props
                .stacksTo(1)
                .rarity(Rarity.RARE)
                .fireResistant())
            
            // Атрибуты
            .bulletResistance(0.3D)  // С масштабированием по прочности
            .bulletResistance(0.1D, false)  // Без масштабирования
            
            // Кастомные атрибуты
            .attribute(ArmorAttributeSpec.of(
                ModAttributes.BULLET_RESISTANCE,
                "custom_bullet_res",
                AttributeModifier.Operation.ADDITION,
                0.15D,
                true)))
        
        // === НАГРУДНИК ===
        .chestplate(piece -> piece
            .registryName("advanced_tactical_carrier")
            .visuals(spec -> spec
                .model("superbwarfare:geo/us_chest_iotv.geo.json")
                .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
            .bulletResistance(0.5D))
        
        // === ПОНОЖИ ===
        .leggings(piece -> piece
            .registryName("advanced_tactical_pants")
            .visuals(spec -> spec
                .model("warbornrenewed:geo/tactical_leggings.geo.json")
                .texture("warbornrenewed:textures/armor/tactical_leggings.png"))
            .bulletResistance(0.25D))
        
        // === БОТИНКИ ===
        .boots(piece -> piece
            .registryName("advanced_tactical_boots")
            .visuals(spec -> spec
                .model("warbornrenewed:geo/tactical_boots.geo.json")
                .texture("warbornrenewed:textures/armor/tactical_boots.png"))
            .bulletResistance(0.15D))
);
```

---

## 📚 Подробная документация API

### 1. Builder методы набора (`WarbornArmorSet.Builder`)

#### `builder(String id)`
Создаёт новый билдер набора с уникальным ID.

```java
WarbornArmorSet.builder("my_armor_set")
```

#### `defaultMaterial(MaterialProvider material)`
Устанавливает материал по умолчанию для всех частей набора.

```java
.defaultMaterial(type -> ModArmorMaterial.STEEL)
// или
.defaultMaterial(type -> {
    return switch (type) {
        case HELMET -> ModArmorMaterial.CEMENTED_CARBIDE;
        case CHESTPLATE -> ModArmorMaterial.STEEL;
        default -> ModArmorMaterial.STEEL;
    };
})
```

**Доступные материалы из SuperbWarfare:**
- `ModArmorMaterial.STEEL` — сталь (прочность 35, защита: 2/5/7/2)
- `ModArmorMaterial.CEMENTED_CARBIDE` — цементированный карбид (прочность 50, защита: 3/6/8/3)

#### `defaultVisuals(Consumer<ArmorVisualSpec.Builder>)`
Устанавливает визуальные настройки по умолчанию.

```java
.defaultVisuals(spec -> spec
    .model("namespace:geo/model.geo.json")
    .texture("namespace:textures/armor/texture.png")
    .animation("namespace:animations/animation.animation.json")) // опционально
```

#### `defaultBones(UnaryOperator<ArmorBonesSpec.Builder>)`
Настраивает имена костей в GeckoLib модели.

```java
.defaultBones(bones -> bones
    .head("headBone")
    .body("bodyBone")
    .rightArm("rightArmBone")
    .leftArm("leftArmBone")
    .rightLeg("rightLegBone")
    .leftLeg("leftLegBone")
    .rightBoot("rightBootBone")
    .leftBoot("leftBootBone"))
```

**По умолчанию используются:**
- Шлем: `armorHead`
- Нагрудник: `armorBody`, `armorRightArm`, `armorLeftArm`
- Поножи: `armorBody`, `armorRightLeg`, `armorLeftLeg`
- Ботинки: `armorRightBoot`, `armorLeftBoot`

#### `helmet(Consumer<ArmorPieceBuilder>)` / `chestplate(...)` / `leggings(...)` / `boots(...)`
Добавляет конкретную часть брони.

---

### 2. Builder методы части брони (`ArmorPieceBuilder`)

#### `registryName(String name)`
⚠️ **Обязательно!** Устанавливает имя регистрации предмета.

```java
.helmet(piece -> piece
    .registryName("my_helmet"))
```

#### `visuals(Consumer<ArmorVisualSpec.Builder>)`
⚠️ **Обязательно!** Настройка визуализации.

```java
.visuals(spec -> spec
    .model("superbwarfare:geo/helmet.geo.json")
    .texture("superbwarfare:textures/armor/helmet.png"))
```

#### `material(MaterialProvider)`
Переопределяет материал для этой конкретной части.

```java
.material(type -> ModArmorMaterial.CEMENTED_CARBIDE)
```

#### `properties(UnaryOperator<Item.Properties>)`
Настройка свойств предмета.

```java
.properties(props -> props
    .stacksTo(1)
    .rarity(Rarity.EPIC)
    .fireResistant())
```

#### `bones(UnaryOperator<ArmorBonesSpec.Builder>)`
Переопределяет кости для этой части.

```java
.bones(bones -> bones.head("customHelmetBone"))
```

#### `bulletResistance(double value)` / `bulletResistance(double value, boolean scaleWithDurability)`
Добавляет защиту от пуль (атрибут из SuperbWarfare).

```java
.bulletResistance(0.25D)           // С масштабированием (урон изнашивается)
.bulletResistance(0.25D, false)   // Без масштабирования (постоянная защита)
```

#### `attribute(ArmorAttributeSpec)`
Добавляет кастомный атрибут.

```java
.attribute(ArmorAttributeSpec.of(
    ModAttributes.BULLET_RESISTANCE,
    "my_custom_modifier",
    AttributeModifier.Operation.ADDITION,
    0.2D,
    true))
```

---

### 3. Визуальная спецификация (`ArmorVisualSpec`)

#### Поддержка namespace
```java
// Автоматически использует namespace мода:
.model("geo/my_model.geo.json")  // → warbornrenewed:geo/my_model.geo.json

// Явное указание namespace:
.model("superbwarfare:geo/helmet.geo.json")
.texture("minecraft:textures/armor/iron.png")
```

---

### 4. Спецификация костей (`ArmorBonesSpec`)

Используется для связи GeckoLib костей с частями брони. Если имена костей в вашей модели отличаются от стандартных, переопределите их:

```java
.bones(bones -> bones
    .head("myCustomHeadBone")
    .body("myCustomBodyBone"))
```

**Когда это нужно:**
- Вы используете модели с нестандартными именами костей
- Вы хотите скрыть определённые части (передайте `null` или пустую строку)

---

### 5. Спецификация атрибутов (`ArmorAttributeSpec`)

#### Встроенный метод: `bulletResistance`
```java
ArmorAttributeSpec.bulletResistance(0.3D)         // С масштабированием
ArmorAttributeSpec.bulletResistance(0.3D, false) // Без масштабирования
```

#### Кастомный атрибут
```java
ArmorAttributeSpec.of(
    () -> Attributes.ARMOR_TOUGHNESS,  // Supplier атрибута
    "toughness_modifier",              // ID модификатора
    AttributeModifier.Operation.ADDITION,  // Операция
    2.0D,                              // Значение
    true)                              // Масштабировать по прочности?
```

**Масштабирование по прочности:**
- `true` — атрибут уменьшается с износом предмета
- `false` — атрибут остаётся постоянным

---

## 💡 Примеры

### Пример 1: Простой шлем

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("simple_helmet")
        .defaultMaterial(type -> ModArmorMaterial.STEEL)
        .helmet(piece -> piece
            .registryName("simple_helmet")
            .visuals(spec -> spec
                .model("superbwarfare:geo/ge_helmet_m_35.geo.json")
                .texture("superbwarfare:textures/armor/ge_helmet_m_35.png"))
            .bulletResistance(0.15D))
);
```

### Пример 2: Полный комплект с разными материалами

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("mixed_armor")
        .helmet(piece -> piece
            .registryName("heavy_helmet")
            .material(type -> ModArmorMaterial.CEMENTED_CARBIDE)
            .visuals(spec -> spec
                .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
            .bulletResistance(0.35D))
        
        .chestplate(piece -> piece
            .registryName("light_carrier")
            .material(type -> ModArmorMaterial.STEEL)
            .visuals(spec -> spec
                .model("superbwarfare:geo/us_chest_iotv.geo.json")
                .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
            .bulletResistance(0.3D))
);
```

### Пример 3: Использование моделей из SuperbWarfare

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("sbw_inspired")
        .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
        
        .helmet(piece -> piece
            .registryName("tactical_helmet")
            .visuals(spec -> spec
                .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
            .properties(props -> props.rarity(Rarity.UNCOMMON))
            .bulletResistance(0.2D))
        
        .chestplate(piece -> piece
            .registryName("tactical_vest")
            .visuals(spec -> spec
                .model("superbwarfare:geo/us_chest_iotv.geo.json")
                .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
            .properties(props -> props.rarity(Rarity.UNCOMMON))
            .bulletResistance(0.45D))
);
```

### Пример 4: Броня с кастомными свойствами

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("elite_armor")
        .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
        
        .helmet(piece -> piece
            .registryName("elite_helmet")
            .visuals(spec -> spec
                .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
            .properties(props -> props
                .rarity(Rarity.EPIC)
                .fireResistant())
            .bulletResistance(0.4D, false)  // Постоянная защита
            .attribute(ArmorAttributeSpec.of(
                () -> Attributes.KNOCKBACK_RESISTANCE,
                "elite_kb_res",
                AttributeModifier.Operation.ADDITION,
                0.5D,
                false)))
);
```

### Пример 5: Набор с анимацией

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("animated_armor")
        .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
        
        .chestplate(piece -> piece
            .registryName("animated_chestplate")
            .visuals(spec -> spec
                .model("warbornrenewed:geo/animated_chest.geo.json")
                .texture("warbornrenewed:textures/armor/animated_chest.png")
                .animation("warbornrenewed:animations/chest_idle.animation.json"))
            .bulletResistance(0.4D))
);
```

---

## 🔗 Интеграция с SuperbWarfare

### Доступные материалы

Все материалы из `com.atsuishio.superbwarfare.tiers.ModArmorMaterial`:

| Материал | Прочность | Защита (Ботинки/Поножи/Нагрудник/Шлем) | Чары | Прочность брони | Отталкивание |
|----------|-----------|----------------------------------------|------|-----------------|--------------|
| `STEEL` | 35 | 2 / 5 / 7 / 2 | 9 | 1.0F | 0.0F |
| `CEMENTED_CARBIDE` | 50 | 3 / 6 / 8 / 3 | 10 | 4.0F | 0.05F |

### Доступные атрибуты

Из `com.atsuishio.superbwarfare.init.ModAttributes`:
- `BULLET_RESISTANCE` — защита от пуль

### Использование моделей и текстур SBW

Вы можете напрямую использовать ресурсы из SuperbWarfare:

```java
.visuals(spec -> spec
    // Модели из SBW
    .model("superbwarfare:geo/ge_helmet_m_35.geo.json")
    .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
    .model("superbwarfare:geo/us_chest_iotv.geo.json")
    .model("superbwarfare:geo/ru_helmet_6b47.geo.json")
    .model("superbwarfare:geo/ru_chest_6b43.geo.json")
    
    // Текстуры из SBW
    .texture("superbwarfare:textures/armor/ge_helmet_m_35.png")
    .texture("superbwarfare:textures/armor/us_helmet_pasgt.png")
    .texture("superbwarfare:textures/armor/us_chest_iotv.png")
    .texture("superbwarfare:textures/armor/ru_helmet_6b47.png")
    .texture("superbwarfare:textures/armor/ru_chest_6b43.png"))
```

---

## ❓ Часто задаваемые вопросы

### Q: Как создать только шлем без других частей?
**A:** Просто укажите только `.helmet(...)` и не добавляйте другие части.

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("helmet_only")
        .defaultMaterial(type -> ModArmorMaterial.STEEL)
        .helmet(piece -> piece
            .registryName("my_helmet")
            .visuals(spec -> spec
                .model("...")
                .texture("...")))
);
```

---

### Q: Как использовать свои модели и текстуры?
**A:** Разместите файлы в ресурсах мода и укажите путь:

```
src/main/resources/
├── assets/
│   └── warbornrenewed/
│       ├── geo/
│       │   └── my_helmet.geo.json
│       └── textures/
│           └── armor/
│               └── my_helmet.png
```

```java
.model("geo/my_helmet.geo.json")  // Автоматически → warbornrenewed:geo/my_helmet.geo.json
.texture("textures/armor/my_helmet.png")
```

---

### Q: Как отключить масштабирование атрибута по прочности?
**A:** Передайте `false` вторым параметром:

```java
.bulletResistance(0.3D, false)  // Постоянная защита
```

---

### Q: Можно ли добавить кастомные атрибуты (например, скорость)?
**A:** Да! Используйте метод `.attribute()`:

```java
.attribute(ArmorAttributeSpec.of(
    () -> Attributes.MOVEMENT_SPEED,
    "speed_boost",
    AttributeModifier.Operation.MULTIPLY_TOTAL,
    0.1D,  // +10% скорости
    false))
```

---

### Q: Как сделать броню огнеупорной?
**A:** Используйте `.properties()`:

```java
.properties(props -> props.fireResistant())
```

---

### Q: Что делать, если модель не отображается?
**A:** Проверьте:
1. Путь к модели и текстуре
2. Имена костей в модели (используйте `.bones()` для переопределения)
3. Формат файлов (`.geo.json` для моделей)
4. Наличие файлов в папке `resources/assets/`

---

### Q: Можно ли использовать разные материалы для разных частей набора?
**A:** Да! Переопределите материал для конкретной части:

```java
.helmet(piece -> piece
    .material(type -> ModArmorMaterial.CEMENTED_CARBIDE)
    ...)
.chestplate(piece -> piece
    .material(type -> ModArmorMaterial.STEEL)
    ...)
```

---

## 🎓 Заключение

Теперь у вас есть всё необходимое для создания собственных наборов брони! 

### Основные шаги:
1. Откройте `WarbornArmorSets.java`
2. Создайте новый метод `registerMySet()`
3. Используйте `WarbornArmorRegistry.registerSet(...)`
4. Вызовите метод в `bootstrap()`
5. Запустите игру и наслаждайтесь результатом! 🎮

### Полезные ссылки:
- [SuperbWarfare GitHub](https://github.com/Mercurows/SuperbWarfare)
- [GeckoLib Documentation](https://github.com/bernie-g/geckolib)
- [Minecraft Forge Documentation](https://docs.minecraftforge.net/)

---

**Удачи в создании брони!** 🛡️⚔️
