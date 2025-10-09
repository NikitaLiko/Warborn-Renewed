# 🦴 Справка по костям (Bones Reference)

## Что такое кости (bones)?

**Кости (bones)** - это имена костей из GeckoLib модели (`.geo.json`), которые привязывают модель брони к частям тела игрока. Без правильной настройки костей модель не будет двигаться вместе с игроком!

---

## 📋 Все доступные кости

В системе Warborn-Renewed доступны **8 костей** для привязки брони к игроку:

| № | Метод | Описание | Часть тела |
|---|-------|----------|------------|
| 1 | `.head(String name)` | Голова | Шлем |
| 2 | `.body(String name)` | Торс/тело | Жилет, штаны |
| 3 | `.rightArm(String name)` | Правая рука | Жилет |
| 4 | `.leftArm(String name)` | Левая рука | Жилет |
| 5 | `.rightLeg(String name)` | Правая нога | Штаны |
| 6 | `.leftLeg(String name)` | Левая нога | Штаны |
| 7 | `.rightBoot(String name)` | Правый ботинок | Ботинки |
| 8 | `.leftBoot(String name)` | Левый ботинок | Ботинки |

---

## 🎯 Стандартные имена костей

По умолчанию система использует следующие имена костей (если вы создаёте модель в Blockbench, используйте эти имена):

```java
// Шлем (HELMET)
.bones(bones -> bones.head("armorHead"))

// Жилет (CHESTPLATE)
.bones(bones -> bones
    .body("armorBody")
    .rightArm("armorRightArm")
    .leftArm("armorLeftArm"))

// Штаны (LEGGINGS)
.bones(bones -> bones
    .body("armorBody")
    .rightLeg("armorRightLeg")
    .leftLeg("armorLeftLeg"))

// Ботинки (BOOTS)
.bones(bones -> bones
    .rightBoot("armorRightBoot")
    .leftBoot("armorLeftBoot"))
```

---

## 💡 Примеры использования

### Пример 1: Простой шлем
```java
.helmet(piece -> piece
    .registryName("my_helmet")
    .visuals(spec -> spec
        .model("warbornrenewed:geo/my_helmet.geo.json")
        .texture("warbornrenewed:textures/armor/my_helmet.png"))
    // Привязываем к голове
    .bones(bones -> bones.head("armorHead")))
```

### Пример 2: Полный жилет с руками
```java
.chestplate(piece -> piece
    .registryName("my_vest")
    .visuals(spec -> spec
        .model("warbornrenewed:geo/my_vest.geo.json")
        .texture("warbornrenewed:textures/armor/my_vest.png"))
    // Привязываем к телу и рукам
    .bones(bones -> bones
        .body("armorBody")           // Торс
        .rightArm("armorRightArm")   // Правая рука
        .leftArm("armorLeftArm")))   // Левая рука
```

### Пример 3: Штаны с ногами
```java
.leggings(piece -> piece
    .registryName("my_pants")
    .visuals(spec -> spec
        .model("warbornrenewed:geo/my_pants.geo.json")
        .texture("warbornrenewed:textures/armor/my_pants.png"))
    // Привязываем к телу и ногам
    .bones(bones -> bones
        .body("armorBody")           // Торс (пояс штанов)
        .rightLeg("armorRightLeg")   // Правая нога
        .leftLeg("armorLeftLeg")))   // Левая нога
```

### Пример 4: Ботинки
```java
.boots(piece -> piece
    .registryName("my_boots")
    .visuals(spec -> spec
        .model("warbornrenewed:geo/my_boots.geo.json")
        .texture("warbornrenewed:textures/armor/my_boots.png"))
    // Привязываем к ступням
    .bones(bones -> bones
        .rightBoot("armorRightBoot")  // Правая ступня
        .leftBoot("armorLeftBoot")))  // Левая ступня
```

### Пример 5: Частичная броня (только тело, без рук)
```java
.chestplate(piece -> piece
    .registryName("chest_plate_only")
    .visuals(spec -> spec
        .model("warbornrenewed:geo/chest_plate.geo.json")
        .texture("warbornrenewed:textures/armor/chest_plate.png"))
    // Только торс, без рук
    .bones(bones -> bones.body("armorBody")))
```

---

## 🔍 Как узнать имена костей в вашей модели?

### Способ 1: Blockbench
1. Откройте вашу `.geo.json` модель в Blockbench
2. Посмотрите в панель **Outliner** (справа)
3. Там будут перечислены все кости (bones) вашей модели

### Способ 2: Открыть .geo.json файл
1. Откройте `.geo.json` файл в текстовом редакторе
2. Найдите раздел `"bones": [`
3. Там будут объекты с полем `"name": "имя_кости"`

Пример содержимого `.geo.json`:
```json
{
  "format_version": "1.12.0",
  "minecraft:geometry": [{
    "description": {
      "identifier": "geometry.my_helmet"
    },
    "bones": [
      {
        "name": "armorHead",    // ← ВОТ ЭТО ИМЯ
        "pivot": [0, 24, 0],
        "cubes": [
          // ... кубы модели
        ]
      }
    ]
  }]
}
```

---

## ⚠️ Частые проблемы и решения

### Проблема 1: Модель не двигается с игроком
**Причина:** Не указаны кости или указаны неправильные имена.

**Решение:**
```java
// ❌ НЕПРАВИЛЬНО - забыли bones
.helmet(piece -> piece
    .visuals(spec -> spec
        .model("...")
        .texture("...")))

// ✅ ПРАВИЛЬНО - указали bones
.helmet(piece -> piece
    .visuals(spec -> spec
        .model("...")
        .texture("..."))
    .bones(bones -> bones.head("armorHead")))
```

### Проблема 2: Модель в неправильном месте
**Причина:** Имена костей в `.bones()` не совпадают с именами в `.geo.json` модели.

**Решение:** Проверьте имена костей в вашей модели и используйте точно такие же:
```java
// Если в модели кость называется "head" вместо "armorHead"
.bones(bones -> bones.head("head"))  // Используйте точное имя!
```

### Проблема 3: Только часть модели двигается
**Причина:** Не все нужные кости указаны.

**Решение:**
```java
// ❌ НЕПРАВИЛЬНО - забыли руки
.chestplate(piece -> piece
    // ...
    .bones(bones -> bones.body("armorBody")))

// ✅ ПРАВИЛЬНО - указали все части
.chestplate(piece -> piece
    // ...
    .bones(bones -> bones
        .body("armorBody")
        .rightArm("armorRightArm")
        .leftArm("armorLeftArm")))
```

### Проблема 4: Модель невидима
**Причина:** Кости есть, но модель или текстура не загружается.

**Решение:**
1. Проверьте пути к модели и текстуре
2. Убедитесь, что файлы существуют
3. Проверьте логи на ошибки загрузки ресурсов

---

## 🎨 Работа с моделями SuperbWarfare

Модели из SuperbWarfare могут использовать **другие имена костей**!

### Возможные варианты имён:
- `head` вместо `armorHead`
- `body` или `chest` вместо `armorBody`
- `rightArm` или `arm_right` вместо `armorRightArm`
- И т.д.

### Как проверить:
1. Найдите исходники SuperbWarfare
2. Откройте `.geo.json` модели, которые вы используете
3. Посмотрите раздел `"bones"` и используйте точные имена

**Пример для SuperbWarfare:**
```java
// Если модель SuperbWarfare использует "head" вместо "armorHead"
.helmet(piece -> piece
    .visuals(spec -> spec
        .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
        .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
    .bones(bones -> bones.head("head")))  // Используйте правильное имя!
```

---

## 📦 Defaults (автоматические значения)

Если вы **не указываете** `.bones()`, система автоматически использует стандартные имена:

| Тип брони | Автоматические кости |
|-----------|----------------------|
| HELMET | `head("armorHead")` |
| CHESTPLATE | `body("armorBody")` + `rightArm("armorRightArm")` + `leftArm("armorLeftArm")` |
| LEGGINGS | `body("armorBody")` + `rightLeg("armorRightLeg")` + `leftLeg("armorLeftLeg")` |
| BOOTS | `rightBoot("armorRightBoot")` + `leftBoot("armorLeftBoot")` |

Это работает, только если ваша модель использует **стандартные имена костей GeckoLib**!

---

## 🛠️ Создание модели с правильными костями

### В Blockbench:

1. Создайте новый **GeckoLib 3D Model**
2. Добавьте кости (Bones) с правильными именами:
   - Для шлема: `armorHead`
   - Для жилета: `armorBody`, `armorRightArm`, `armorLeftArm`
   - Для штанов: `armorBody`, `armorRightLeg`, `armorLeftLeg`
   - Для ботинок: `armorRightBoot`, `armorLeftBoot`
3. Добавьте кубы (Cubes) к костям
4. Экспортируйте как **GeckoLib Animated Model**
5. Сохраните `.geo.json` и текстуру

---

## 🔗 Полезные ссылки

- [GeckoLib Wiki](https://wiki.geckolib.com/)
- [Blockbench](https://www.blockbench.net/)
- [ARMOR_TEMPLATE_GUIDE.md](ARMOR_TEMPLATE_GUIDE.md) - Полное руководство по системе

---

## 📝 Краткая памятка

```java
// ШЛ��М - только голова
.bones(bones -> bones.head("armorHead"))

// ЖИЛЕТ - тело + руки
.bones(bones -> bones.body("armorBody").rightArm("armorRightArm").leftArm("armorLeftArm"))

// ШТАНЫ - тело + ноги
.bones(bones -> bones.body("armorBody").rightLeg("armorRightLeg").leftLeg("armorLeftLeg"))

// БОТИНКИ - ступни
.bones(bones -> bones.rightBoot("armorRightBoot").leftBoot("armorLeftBoot"))
```

**Важно:** Имена в `.bones()` должны **точно совпадать** с именами костей в вашей `.geo.json` модели!

---

✅ **Готово!** Теперь вы знаете всё о костях в Warborn-Renewed!
