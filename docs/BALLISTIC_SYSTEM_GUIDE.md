# Реалистичная баллистическая система

## 🎯 Обзор

Warborn-Renewed теперь включает реалистичную систему баллистической защиты, основанную на реальных стандартах:
- **NIJ Standard 0101.06** (США)
- **ГОСТ Р 50744-95** (Россия)  
- **VPAM** (Германия)

Система полностью совместима с **TACZ** и **SuperbWarfare**.

---

## 📊 Атрибуты брони

### 1. BULLET_RESISTANCE (Защита от пуль)
- **Диапазон**: 0.0 - 1.0 (0% - 100%)
- **Назначение**: Процент снижения урона от пуль
- **Масштабирование**: По прочности брони (опционально)

```java
.attributes(List.of(
    ArmorAttributeSpec.bulletResistance(0.45)  // 45% защиты от пуль
))
```

### 2. PROTECTION_CLASS (Класс защиты NIJ)
- **Диапазон**: 0 - 6
- **Назначение**: Определяет класс баллистической защиты

| Класс | NIJ уровень | Защита от | Энергия (Дж) |
|-------|-------------|-----------|--------------|
| 0 | Нет | Нет защиты | 0 |
| 1 | IIA | 9mm, .40 S&W | 600 |
| 2 | II | 9mm +P, .357 Magnum | 800 |
| 3 | IIIA | .44 Magnum, .357 SIG | 1000 |
| 4 | III | 7.62x51mm NATO | 3500 |
| 5 | IV | .30-06 AP, 7.62x54mmR AP | 5000 |
| 6 | Специальная | Экспериментальная | 8000 |

```java
.attributes(List.of(
    ArmorAttributeSpec.protectionClass(3)  // NIJ Level IIIA
))
```

### 3. EFFECTIVE_THICKNESS (Эффективная толщина)
- **Диапазон**: 0.0 - 100.0 мм
- **Назначение**: Эквивалент толщины стальной брони

| Материал | Толщина (мм) |
|----------|--------------|
| Кевлар (мягкая броня) | 2-5 |
| Стальная броня | 6-12 |
| Керамическая плита | 15-25 |
| Композитная броня | 20-40 |

```java
.attributes(List.of(
    ArmorAttributeSpec.effectiveThickness(15.0)  // 15 мм
))
```

### 4. BLAST_DAMAGE_MULTIPLIER (Защита от взрывов)
- **Диапазон**: 0.0 - 2.0
- **Назначение**: Множитель урона от взрывов

| Значение | Эффект |
|----------|--------|
| 0.5 | -50% урона от взрывов |
| 1.0 | Нормальный урон |
| 1.5 | +50% урона от взрывов |

```java
.attributes(List.of(
    ArmorAttributeSpec.blastResistance(0.7)  // 30% снижение урона
))
```

### 5. ARMOR_MOVEMENT_SPEED (Скорость движения)
- **Диапазон**: -0.5 - 0.2 (-50% до +20%)
- **Назначение**: Модификатор скорости движения

```java
.attributes(List.of(
    ArmorAttributeSpec.movementSpeed(-0.1)  // -10% скорости
))
```

---

## 🔫 Примеры реалистичных комплектов

### Легкая разведывательная броня (NIJ II)
```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("recon_vest")
        .defaultMaterial(type -> ModArmorMaterials.LIGHT)
        .chestplate(piece -> piece
            .registryName("recon_vest")
            .visuals(spec -> spec
                .model("warbornrenewed:geo/recon_vest.geo.json")
                .texture("warbornrenewed:textures/armor/recon_vest.png"))
            .bones(bones -> bones.body("armorBody"))
            .attributes(List.of(
                ArmorAttributeSpec.bulletResistance(0.25),      // 25% защита
                ArmorAttributeSpec.protectionClass(2),          // NIJ Level II
                ArmorAttributeSpec.effectiveThickness(4.0),     // 4 мм кевлар
                ArmorAttributeSpec.blastResistance(0.9),        // Слабая защита от взрывов
                ArmorAttributeSpec.movementSpeed(0.05)          // +5% скорость
            )))
);
```

### Средняя тактическая броня (NIJ IIIA)
```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("tactical_vest")
        .defaultMaterial(type -> ModArmorMaterials.MEDIUM)
        .chestplate(piece -> piece
            .registryName("tactical_vest")
            .visuals(spec -> spec
                .model("warbornrenewed:geo/tactical_vest.geo.json")
                .texture("warbornrenewed:textures/armor/tactical_vest.png"))
            .bones(bones -> bones.body("armorBody"))
            .attributes(List.of(
                ArmorAttributeSpec.bulletResistance(0.45),      // 45% защита
                ArmorAttributeSpec.protectionClass(3),          // NIJ Level IIIA
                ArmorAttributeSpec.effectiveThickness(15.0),    // 15 мм керамика
                ArmorAttributeSpec.blastResistance(0.75),       // Умеренная защита
                ArmorAttributeSpec.movementSpeed(-0.02)         // -2% скорость
            )))
);
```

### Тяжелая боевая броня (NIJ III)
```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("combat_vest")
        .defaultMaterial(type -> ModArmorMaterials.HEAVY)
        .chestplate(piece -> piece
            .registryName("combat_vest")
            .visuals(spec -> spec
                .model("warbornrenewed:geo/combat_vest.geo.json")
                .texture("warbornrenewed:textures/armor/combat_vest.png"))
            .bones(bones -> bones.body("armorBody"))
            .attributes(List.of(
                ArmorAttributeSpec.bulletResistance(0.65),      // 65% защита
                ArmorAttributeSpec.protectionClass(4),          // NIJ Level III
                ArmorAttributeSpec.effectiveThickness(25.0),    // 25 мм композит
                ArmorAttributeSpec.blastResistance(0.6),        // Хорошая защита
                ArmorAttributeSpec.movementSpeed(-0.08)         // -8% скорость
            )))
);
```

### Элитная штурмовая броня (NIJ IV)
```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("assault_vest")
        .defaultMaterial(type -> ModArmorMaterials.ELITE)
        .chestplate(piece -> piece
            .registryName("assault_vest")
            .visuals(spec -> spec
                .model("warbornrenewed:geo/assault_vest.geo.json")
                .texture("warbornrenewed:textures/armor/assault_vest.png"))
            .bones(bones -> bones.body("armorBody"))
            .attributes(List.of(
                ArmorAttributeSpec.bulletResistance(0.85),      // 85% защита
                ArmorAttributeSpec.protectionClass(5),          // NIJ Level IV
                ArmorAttributeSpec.effectiveThickness(40.0),    // 40 мм супер-композит
                ArmorAttributeSpec.blastResistance(0.5),        // Отличная защита
                ArmorAttributeSpec.movementSpeed(-0.12)         // -12% скорость
            )))
);
```

---

## ⚙️ Как работает система пробития

### 1. Расчет пробития
Система использует энергию пули (в джоулях) для определения пробития:

```java
boolean isPenetrated = ModAttributes.isPenetrated(protectionClass, bulletEnergy);
```

**Примеры:**
- 9mm FMJ (600 J) НЕ пробивает NIJ IIIA (1000 J)
- 7.62x51mm NATO (3500 J) ПРОБИВАЕТ NIJ IIIA (1000 J)
- .30-06 AP (5000 J) НЕ пробивает NIJ IV (5000 J)

### 2. Расчет урона
```java
double finalDamage = ModAttributes.calculateDamage(
    initialDamage,
    bulletResistance,
    isPenetrated
);
```

**Логика:**
- **Не пробита**: Урон снижается на `bulletResistance` (полная эффективность)
- **Пробита**: Урон снижается на `bulletResistance × 0.5` (50% эффективности)

**Пример:**
```
Исходный урон: 20 HP
Защита: 60% (0.6)

Если НЕ пробита:
  Финальный урон = 20 × (1 - 0.6) = 8 HP

Если ПРОБИТА:
  Финальный урон = 20 × (1 - 0.6 × 0.5) = 14 HP
```

---

## 🔧 Совместимость с модами

### TACZ (Timeless and Classics Zero)
Атрибуты автоматически считываются через систему Forge attributes.

**Интеграция:**
1. TACZ наносит урон пулей
2. Система проверяет `BULLET_RESISTANCE` и `PROTECTION_CLASS`
3. Вычисляется пробитие и финальный урон

### SuperbWarfare
Используется совместимый атрибут `BULLET_RESISTANCE`.

**Интеграция:**
1. SuperbWarfare проверяет атрибут `bullet_resistance`
2. Система Warborn-Renewed предоставляет значение
3. Урон снижается согласно защите

---

## 📈 Таблица калибров и энергий

| Калибр | Энергия (Дж) | Пробивает класс |
|--------|--------------|-----------------|
| 9mm Parabellum | 500-600 | 0-1 |
| .40 S&W | 500-700 | 0-1 |
| .45 ACP | 500-600 | 0-1 |
| 9mm +P | 700-800 | 0-2 |
| .357 Magnum | 800-900 | 0-2 |
| .44 Magnum | 1000-1200 | 0-3 |
| 5.56x45mm NATO | 1700-1800 | 0-3 |
| 5.45x39mm | 1400-1500 | 0-3 |
| 7.62x39mm | 2000-2100 | 0-3 |
| 7.62x51mm NATO | 3500-3700 | 0-4 |
| .30-06 Springfield | 3900-4000 | 0-4 |
| 7.62x54mmR | 3600-3800 | 0-4 |
| .30-06 AP | 5000-5200 | 0-5 |
| 12.7x108mm | 15000+ | 0-6 |
| .50 BMG | 18000+ | 0-6 |

---

## 🎯 Рекомендации по балансу

### Легкая броня (Разведка)
```java
bulletResistance: 0.15 - 0.30
protectionClass: 1-2
effectiveThickness: 2-6 мм
movementSpeed: +5% до +10%
```

### Средняя броня (Тактическая)
```java
bulletResistance: 0.35 - 0.50
protectionClass: 2-3
effectiveThickness: 10-20 мм
movementSpeed: -2% до -5%
```

### Тяжелая броня (Боевая)
```java
bulletResistance: 0.55 - 0.70
protectionClass: 3-4
effectiveThickness: 20-30 мм
movementSpeed: -8% до -12%
```

### Элитная броня (Специальная)
```java
bulletResistance: 0.75 - 0.90
protectionClass: 4-5
effectiveThickness: 35-45 мм
movementSpeed: -12% до -20%
```

---

## 🛠️ API для разработчиков

### Получение атрибутов
```java
// Получить защиту от пуль
double resistance = ModAttributes.getBulletResistance(player);

// Получить класс защиты
int protectionClass = ModAttributes.getProtectionClass(player);

// Получить эффективную толщину
double thickness = ModAttributes.getEffectiveThickness(player);
```

### Проверка пробития
```java
int armorClass = ModAttributes.getProtectionClass(player);
double bulletEnergy = 3500.0; // 7.62x51mm NATO

boolean penetrated = ModAttributes.isPenetrated(armorClass, bulletEnergy);
```

### Расчет урона
```java
double initialDamage = 20.0;
double resistance = ModAttributes.getBulletResistance(player);
boolean penetrated = ModAttributes.isPenetrated(
    ModAttributes.getProtectionClass(player),
    bulletEnergy
);

double finalDamage = ModAttributes.calculateDamage(
    initialDamage,
    resistance,
    penetrated
);

player.hurt(DamageSource.GENERIC, (float) finalDamage);
```

---

## 📚 Дополнительная информация

- [Основное руководство по API](ARMOR_TEMPLATE_GUIDE.md)
- [Справочник костей](BONES_REFERENCE.md)
- [Характеристики материалов](ARMOR_STATS_GUIDE.md)
- [Примеры брони](../src/main/java/ru/liko/warbornrenewed/setup/WarbornArmorSets.java)

---

**Система полностью независима и работает без SuperbWarfare/TACZ, но обеспечивает полную совместимость с ними!** 🎉🛡️⚔️
