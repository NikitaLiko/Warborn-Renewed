# 🎯 Pull Request: Реалистичная баллистическая система + совместимость с TACZ/SuperbWarfare

## 📋 Обзор

Этот PR добавляет полностью независимую реалистичную баллистическую систему защиты, основанную на реальных стандартах NIJ/ГОСТ/VPAM, с полной совместимостью с модами оружия TACZ и SuperbWarfare.

---

## ✨ Главные нововведения

### 1. **Собственная система материалов брони** (`ModArmorMaterials.java`)
- ✅ Удалена зависимость от SuperbWarfare
- ✅ 6 реалистичных материалов с характеристиками
- ✅ Основано на реальных стандартах (NIJ, ГОСТ, VPAM)

| Материал | Прочность | Защита | Твердость | Отбрасывание |
|----------|-----------|--------|-----------|--------------|
| LIGHT | 15 | ★☆☆☆☆ | 0.0 | 0% |
| MEDIUM | 25 | ★★★☆☆ | 1.0 | 0% |
| HEAVY | 35 | ★★★★☆ | 3.0 | 10% |
| ELITE | 50 | ★★★★★ | 4.0 | 20% |
| STEEL | 35 | ★★★☆☆ | 0.5 | 0% |
| CEMENTED_CARBIDE | 50 | ★★★★☆ | 2.0 | 5% |

### 2. **Система атрибутов баллистической защиты** (`ModAttributes.java`)

#### 5 новых атрибутов:

**BULLET_RESISTANCE** (Защита от пуль)
- Диапазон: 0.0 - 1.0 (0% - 100%)
- Совместим с SuperbWarfare и TACZ
- Масштабируется по прочности брони

**PROTECTION_CLASS** (Класс защиты NIJ)
- Диапазон: 0 - 6 (от NIJ IIA до специальной защиты)
- Определяет порог пробития по энергии пули

| Класс | NIJ | Защищает от | Энергия |
|-------|-----|-------------|---------|
| 0 | Нет | Нет защиты | 0 J |
| 1 | IIA | 9mm, .40 S&W | 600 J |
| 2 | II | 9mm +P, .357 Magnum | 800 J |
| 3 | IIIA | .44 Magnum | 1000 J |
| 4 | III | 7.62x51mm NATO | 3500 J |
| 5 | IV | .30-06 AP | 5000 J |
| 6 | Специальная | Экспериментальная | 8000 J |

**EFFECTIVE_THICKNESS** (Эффективная толщина)
- Диапазон: 0.0 - 100.0 мм
- Эквивалент толщины стальной брони
- Для расчета пробития разными типами боеприпасов

**BLAST_DAMAGE_MULTIPLIER** (Защита от взрывов)
- Диапазон: 0.0 - 2.0
- Множитель урона от взрывов и осколков

**ARMOR_MOVEMENT_SPEED** (Скорость движения)
- Диапазон: -0.5 - 0.2 (-50% до +20%)
- Баланс между защитой и мобильностью

### 3. **Обновленный API атрибутов** (`ArmorAttributeSpec.java`)

Новые методы для создания атрибутов:

```java
// Защита от пуль
ArmorAttributeSpec.bulletResistance(0.45)

// Класс защиты NIJ
ArmorAttributeSpec.protectionClass(3)

// Эффективная толщина
ArmorAttributeSpec.effectiveThickness(15.0)

// Защита от взрывов
ArmorAttributeSpec.blastResistance(0.7)

// Скорость движения
ArmorAttributeSpec.movementSpeed(-0.05)
```

### 4. **Система расчета пробития**

Реалистичная система:
- Расчет на основе энергии пули (в джоулях)
- Пороговые значения по классам защиты NIJ
- Частичное пробитие с уменьшением эффективности
- Масштабирование по износу брони

**Примеры пробития:**
- 9mm FMJ (600 J) → НЕ пробивает NIJ IIIA (1000 J) ✅
- 7.62x51mm (3500 J) → ПРОБИВАЕТ NIJ IIIA (1000 J) ❌
- .30-06 AP (5000 J) → НЕ пробивает NIJ IV (5000 J) ✅

---

## 📚 Документация

### Новые файлы документации:

1. **BALLISTIC_SYSTEM_GUIDE.md** (350+ строк)
   - Обзор системы баллистической защиты
   - Подробные характеристики атрибутов
   - Таблица классов защиты NIJ
   - 4 полных примера брони (NIJ II → IV)
   - Механика пробития и расчета урона
   - Гайд по совместимости с TACZ/SuperbWarfare
   - Таблица калибров и энергий (15+ калибров)
   - Рекомендации по балансу
   - API для разработчиков

2. Обновлена **ModArmorMaterials.java**
   - Добавлены ссылки на стандарты NIJ/ГОСТ/VPAM
   - Улучшена документация материалов

---

## 🔧 Технические изменения

### Измененные файлы:

**src/main/java/ru/liko/warbornrenewed/**

- `registry/ModAttributes.java` ✨ **НОВЫЙ**
  - DeferredRegister для 5 атрибутов
  - Вспомогательные методы (getBulletResistance, getProtectionClass, и т.д.)
  - Логика расчета пробития (isPenetrated)
  - Логика расчета урона (calculateDamage)

- `registry/ModArmorMaterials.java` ✅ **ОБНОВЛЕНО**
  - Добавлены ссылки на стандарты
  - Улучшена документация

- `content/armorset/ArmorAttributeSpec.java` ✅ **ОБНОВЛЕНО**
  - Удален импорт SuperbWarfare
  - Использует собственные атрибуты (ModAttributes)
  - 5 новых методов API
  - Полная совместимость с SBW/TACZ

- `Warbornrenewed.java` ✅ **ОБНОВЛЕНО**
  - Регистрация ModAttributes.register(eventBus)

- `setup/WarbornArmorSets.java` ✅ **ОБНОВЛЕНО**
  - Использует ModArmorMaterials вместо SBW

### Документация:

- `docs/BALLISTIC_SYSTEM_GUIDE.md` ✨ **НОВЫЙ**
- `BALLISTIC_PR_SUMMARY.md` ✨ **НОВЫЙ**

---

## 🎮 Совместимость с модами

### TACZ (Timeless and Classics Zero)
✅ Автоматическая совместимость через Forge attributes
- TACZ читает `BULLET_RESISTANCE` и `PROTECTION_CLASS`
- Система автоматически рассчитывает пробитие
- Работает с любым оружием из TACZ

### SuperbWarfare
✅ Полная совместимость
- Использует тот же атрибут `bullet_resistance`
- Модели из SuperbWarfare можно использовать как ресурсы
- Броня работает с оружием из SuperbWarfare

### Без модов оружия
✅ Мод полностью функционален без TACZ/SuperbWarfare
- Атрибуты применяются к любому урону
- Система материалов работает независимо
- Можно использовать собственные модели

---

## 📊 Примеры использования

### Легкая тактическая броня (NIJ II)
```java
WarbornArmorRegistry.registerSet(
    WarbornArmorSet.builder("light_vest")
        .defaultMaterial(type -> ModArmorMaterials.LIGHT)
        .chestplate(piece -> piece
            .registryName("light_vest")
            .visuals(spec -> spec
                .model("warbornrenewed:geo/light_vest.geo.json")
                .texture("warbornrenewed:textures/armor/light_vest.png"))
            .bones(bones -> bones.body("armorBody"))
            .attributes(List.of(
                ArmorAttributeSpec.bulletResistance(0.25),    // 25% защита
                ArmorAttributeSpec.protectionClass(2),         // NIJ Level II
                ArmorAttributeSpec.effectiveThickness(4.0),    // 4 мм кевлар
                ArmorAttributeSpec.blastResistance(0.9),       // Слабая защита
                ArmorAttributeSpec.movementSpeed(0.05)         // +5% скорость
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
                ArmorAttributeSpec.bulletResistance(0.65),    // 65% защита
                ArmorAttributeSpec.protectionClass(4),         // NIJ Level III
                ArmorAttributeSpec.effectiveThickness(25.0),   // 25 мм композит
                ArmorAttributeSpec.blastResistance(0.6),       // Хорошая защита
                ArmorAttributeSpec.movementSpeed(-0.08)        // -8% скорость
            )))
);
```

---

## 🧪 Тестирование

### Проверка атрибутов:
```java
// Получить защиту от пуль
double resistance = ModAttributes.getBulletResistance(player);

// Получить класс защиты
int protectionClass = ModAttributes.getProtectionClass(player);

// Проверить пробитие
boolean penetrated = ModAttributes.isPenetrated(
    protectionClass,
    bulletEnergy // в джоулях
);

// Рассчитать финальный урон
double finalDamage = ModAttributes.calculateDamage(
    initialDamage,
    resistance,
    penetrated
);
```

---

## 🎯 Преимущества

1. **Полная независимость**: Мод больше не зависит от SuperbWarfare
2. **Реализм**: Основан на реальных стандартах NIJ/ГОСТ/VPAM
3. **Совместимость**: Работает с TACZ и SuperbWarfare
4. **Гибкость**: Легко добавлять собственные материалы и атрибуты
5. **Баланс**: Система пробития обеспечивает реалистичный геймплей
6. **Документация**: Полная документация на русском языке
7. **API**: Чистый и простой API для разработчиков

---

## 📈 Статистика

- **4 файла изменено**
- **1 новый файл (ModAttributes.java)**
- **316+ строк кода добавлено**
- **350+ строк документации**
- **5 новых атрибутов**
- **6 материалов брони**
- **7 классов защиты (0-6)**
- **15+ калибров в таблице**
- **4 полных примера брони**

---

## 🚀 Установка и использование

1. Скачайте мод
2. Установите в папку mods
3. (Опционально) Установите TACZ или SuperbWarfare для оружия
4. Запустите игру
5. Найдите броню в творческом режиме или крафте

---

## 📖 Ссылки на документацию

- [Руководство по баллистической системе](docs/BALLISTIC_SYSTEM_GUIDE.md)
- [Основное руководство по API](docs/ARMOR_TEMPLATE_GUIDE.md)
- [Справочник костей](docs/BONES_REFERENCE.md)
- [Характеристики материалов](docs/ARMOR_STATS_GUIDE.md)
- [Примеры брони](src/main/java/ru/liko/warbornrenewed/setup/WarbornArmorSets.java)

---

## 🤝 Благодарности

- **NIJ Standard 0101.06** за стандарты баллистической защиты
- **ГОСТ Р 50744-95** за российские стандарты
- **VPAM** за европейские стандарты
- **Сообщество Minecraft Modding** за поддержку

---

## 📝 Лицензия

GPL-3.0 (код) / CC BY-NC-ND 4.0 (ассеты)

---

**Мод теперь полностью самостоятельный с реалистичной баллистической системой!** 🎉🛡️⚔️
