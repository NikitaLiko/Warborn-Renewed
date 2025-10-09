# 🛡️ WarBorn Renewed

**WarBorn Renewed** — гибкая система шаблонов для создания брони к моду **SuperbWarfare (SBW)** на Minecraft 1.20.1 Forge.

[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-green.svg)](https://www.minecraft.net/)
[![Forge](https://img.shields.io/badge/Forge-47.4.9-orange.svg)](https://files.minecraftforge.net/)
[![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red.svg)]()

---

## 📖 О проекте

Этот мод предоставляет простую и мощную систему для добавления новых наборов брони с интеграцией SuperbWarfare и GeckoLib. Вместо создания множества классов для каждой части брони, вы используете удобный Builder API.

### ✨ Ключевые особенности

- 🎯 **Простой API** — создавайте наборы брони в несколько строк кода
- 🔧 **Гибкая настройка** — материалы, атрибуты, визуализация
- 🎨 **GeckoLib поддержка** — модели, текстуры, анимации
- 🔫 **SuperbWarfare интеграция** — защита от пуль, материалы брони
- 🚀 **Автоматическая регистрация** — не нужно писать бойлерплейт код
- 📦 **Готовые примеры** — 10+ примеров наборов брони

---

## 🚀 Быстрый старт

### Шаг 1: Откройте `WarbornArmorSets.java`

Файл находится в `src/main/java/ru/liko/warbornrenewed/setup/WarbornArmorSets.java`

### Шаг 2: Добавьте свой набор

```java
private static void registerMyArmor() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("my_armor")
            .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
            .helmet(piece -> piece
                .registryName("my_helmet")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                    .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
                .bulletResistance(0.25D))
            .chestplate(piece -> piece
                .registryName("my_vest")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_chest_iotv.geo.json")
                    .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                .bulletResistance(0.45D))
    );
}
```

### Шаг 3: Зарегистрируйте в `bootstrap()`

```java
public static void bootstrap() {
    registerMyArmor();
}
```

### Готово! 🎉

Ваша броня автоматически зарегистрирована и доступна в игре.

---

## 📚 Документация

Подробная документация доступна в файлах:

- **[ARMOR_TEMPLATE_GUIDE.md](ARMOR_TEMPLATE_GUIDE.md)** — полное руководство по API
- **[ARMOR_EXAMPLES.java](ARMOR_EXAMPLES.java)** — 10+ готовых примеров

### Основные возможности

#### 1. Материалы брони

Используйте материалы из SuperbWarfare:

```java
.defaultMaterial(type -> ModArmorMaterial.STEEL)
.defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
```

#### 2. Визуализация

Модели и текстуры из SuperbWarfare или свои:

```java
.visuals(spec -> spec
    .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
    .texture("superbwarfare:textures/armor/us_helmet_pasgt.png")
    .animation("animations/helmet.animation.json")) // опционально
```

#### 3. Атрибуты

Защита от пуль и другие атрибуты:

```java
.bulletResistance(0.3D)  // С масштабированием по прочности
.bulletResistance(0.3D, false)  // Постоянная защита

// Кастомные атрибуты
.attribute(ArmorAttributeSpec.of(
    () -> Attributes.MOVEMENT_SPEED,
    "speed_boost",
    AttributeModifier.Operation.MULTIPLY_TOTAL,
    0.1D,
    false))
```

#### 4. Свойства предметов

```java
.properties(props -> props
    .rarity(Rarity.EPIC)
    .fireResistant())
```

---

## 🎨 Доступные ресурсы из SuperbWarfare

### Модели

- `superbwarfare:geo/ge_helmet_m_35.geo.json` — Немецкий шлем M35
- `superbwarfare:geo/us_helmet_pasgt.geo.json` — Американский шлем PASGT
- `superbwarfare:geo/us_chest_iotv.geo.json` — Американский жилет IOTV
- `superbwarfare:geo/ru_helmet_6b47.geo.json` — Российский шлем 6B47
- `superbwarfare:geo/ru_chest_6b43.geo.json` — Российский жилет 6B43

### Материалы

| Материал | Прочность | Защита | Чары | Прочность брони | Отталкивание |
|----------|-----------|--------|------|-----------------|--------------|
| `STEEL` | 35 | 2/5/7/2 | 9 | 1.0F | 0.0F |
| `CEMENTED_CARBIDE` | 50 | 3/6/8/3 | 10 | 4.0F | 0.05F |

### Атрибуты

- `BULLET_RESISTANCE` — защита от пуль (из SuperbWarfare)

---

## 💡 Примеры

### Простой шлем

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

### Элитный комплект

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("elite_armor")
        .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
        .helmet(piece -> piece
            .registryName("elite_helmet")
            .visuals(spec -> spec
                .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
            .properties(props -> props.rarity(Rarity.EPIC).fireResistant())
            .bulletResistance(0.4D, false)
            .attribute(ArmorAttributeSpec.of(
                () -> Attributes.KNOCKBACK_RESISTANCE,
                "elite_kb_res",
                AttributeModifier.Operation.ADDITION,
                0.5D,
                false)))
        .chestplate(piece -> piece
            .registryName("elite_vest")
            .visuals(spec -> spec
                .model("superbwarfare:geo/us_chest_iotv.geo.json")
                .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
            .properties(props -> props.rarity(Rarity.EPIC).fireResistant())
            .bulletResistance(0.6D, false))
);
```

Больше примеров в **[ARMOR_EXAMPLES.java](ARMOR_EXAMPLES.java)**!

---

## 📁 Структура проекта

```
src/main/java/ru/liko/warbornrenewed/
├── Warbornrenewed.java              # Главный класс мода
├── content/armorset/
│   ├── WarbornArmorSet.java         # Builder API для наборов
│   ├── WarbornArmorRegistry.java    # Регистрация наборов
│   ├── WarbornArmorItem.java        # Класс предмета брони
│   ├── WarbornArmorModel.java       # GeckoLib модель
│   ├── WarbornArmorRenderer.java    # GeckoLib рендерер
│   ├── ArmorVisualSpec.java         # Визуализация (модель/текстура)
│   ├── ArmorBonesSpec.java          # Кости модели
│   └── ArmorAttributeSpec.java      # Атрибуты
├── registry/
│   ├── ModItems.java                # Регистр предметов
│   └── ModCreativeTabs.java         # Вкладки
└── setup/
    └── WarbornArmorSets.java        # 🔥 СОЗДАВАЙТЕ БРОНЮ ЗДЕСЬ
```

---

## 🔧 Разработка

### Требования

- Minecraft 1.20.1
- Forge 47.4.9
- Java 17
- Gradle 8+

### Сборка

```bash
./gradlew build
```

### Запуск клиента

```bash
./gradlew runClient
```

---

## 🤝 Зависимости

- **[SuperbWarfare](https://github.com/Mercurows/SuperbWarfare)** — базовый мод
- **[GeckoLib](https://github.com/bernie-g/geckolib)** — система анимаций и моделей
- **[Minecraft Forge](https://files.minecraftforge.net/)** — мод-лоадер

---

## ❓ FAQ

### Как добавить свои модели?

Поместите `.geo.json` файлы в `src/main/resources/assets/warbornrenewed/geo/` и используйте:

```java
.model("geo/my_helmet.geo.json")  // Автоматически warbornrenewed:geo/...
```

### Как создать только шлем без других частей?

Просто укажите только `.helmet(...)`:

```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("helmet_only")
        .defaultMaterial(type -> ModArmorMaterial.STEEL)
        .helmet(piece -> piece
            .registryName("my_helmet")
            .visuals(spec -> spec.model("...").texture("...")))
);
```

### Как сделать броню огнеупорной?

```java
.properties(props -> props.fireResistant())
```

### Больше вопросов?

Смотрите **[ARMOR_TEMPLATE_GUIDE.md](ARMOR_TEMPLATE_GUIDE.md)** — там есть ответы на все вопросы!

---

## 📄 Лицензия

All Rights Reserved

---

## 👤 Автор

**Liko** — создатель мода

---

## 🌟 Благодарности

- **Mercurows** — за мод SuperbWarfare
- **bernie-g** — за GeckoLib
- **MinecraftForge Team** — за Forge

---

**Удачи в создании брони!** 🛡️⚔️
