# 🌍 Руководство по локализации / Localization Guide

## 📚 Обзор / Overview

Warborn Renewed поддерживает полную локализацию на **русский** и **английский** языки.  
Warborn Renewed supports full localization in **Russian** and **English** languages.

---

## 📂 Файлы локализации / Localization Files

### Расположение / Location:
```
src/main/resources/assets/warbornrenewed/lang/
├── en_us.json  (English - США)
├── ru_ru.json  (Русский - Россия)
```

---

## 🎯 Содержание локализации / Localization Content

### 1. **Названия предметов / Item Names**

#### NATO Woodland (Лесной камуфляж)
| English | Русский |
|---------|---------|
| NATO Woodland Helmet | Шлем NATO Woodland |
| NATO Woodland Vest | Бронежилет NATO Woodland |
| NATO Woodland Pants | Штаны NATO Woodland |
| NATO Woodland Boots | Ботинки NATO Woodland |

#### NATO Desert (Пустынный камуфляж)
| English | Русский |
|---------|---------|
| NATO Desert Helmet | Шлем NATO Desert |
| NATO Desert Vest | Бронежилет NATO Desert |
| NATO Desert Pants | Штаны NATO Desert |
| NATO Desert Boots | Ботинки NATO Desert |

---

### 2. **Материалы брони / Armor Materials**

| Материал | English | Русский | Описание |
|----------|---------|---------|----------|
| KEVLAR | Kevlar | Кевлар | Aramid fiber ballistic protection |
| CERAMIC | Ceramic | Керамика | Ceramic plates (Al2O3, SiC, B4C) |
| AR500_STEEL | AR500 Steel | Сталь AR500 | Ballistic steel plates |
| UHMWPE | UHMWPE (Dyneema) | СВМПЭ (Dyneema) | Ultra-high molecular weight polyethylene |
| COMPOSITE | Composite | Композитная броня | Multi-layer composite armor |
| TITANIUM | Titanium | Титан | Titanium alloys (Ti-6Al-4V) |

---

### 3. **Тултипы / Tooltips**

#### Основной тултип материала / Main Material Tooltip
```
English: "Material: [Material Name]"
Русский: "Материал: [Название материала]"
```

#### Атрибуты брони / Armor Attributes
| Attribute | English | Русский |
|-----------|---------|---------|
| Bullet Protection | Bullet Protection: X% | Защита от пуль: X% |
| Protection Class | Protection Class: NIJ Level X | Класс защиты: NIJ Level X |
| Armor Thickness | Armor Thickness: X mm | Толщина брони: X мм |
| Blast Protection | Blast Protection: X% | Защита от взрывов: X% |
| Speed Modifier | Speed: X% | Скорость: X% |

---

### 4. **Классы защиты NIJ / NIJ Protection Classes**

| Класс | English | Русский | Описание |
|-------|---------|---------|----------|
| 0 | None | Нет защиты | No protection |
| 1 | IIA | IIA | 9mm, .40 S&W (~600 J) |
| 2 | II | II | 9mm +P, .357 Magnum (~800 J) |
| 3 | IIIA | IIIA | .44 Magnum, .357 SIG (~1000 J) |
| 4 | III | III | 7.62x51mm NATO (~3500 J) |
| 5 | IV | IV | .30-06 AP (~5000 J) |
| 6 | Special | Специальный | Special protection (~8000 J) |

---

## 🔧 Как добавить новый язык / How to Add a New Language

### 1. Создайте новый файл / Create a new file:
```
src/main/resources/assets/warbornrenewed/lang/[locale].json
```

Примеры / Examples:
- `de_de.json` (Немецкий / German)
- `fr_fr.json` (Французский / French)
- `es_es.json` (Испанский / Spanish)
- `zh_cn.json` (Китайский упрощенный / Chinese Simplified)

### 2. Скопируйте структуру из `en_us.json`

### 3. Переведите все строки на ваш язык

### 4. Отправьте Pull Request с вашими изменениями

---

## 📝 Структура ключей локализации / Localization Key Structure

### Предметы / Items
```json
"item.warbornrenewed.[item_id]": "Item Name"
```

### Материалы / Materials
```json
"material.warbornrenewed.[material_name]": "Material Display Name"
```

### Тултипы / Tooltips
```json
"tooltip.warbornrenewed.[tooltip_key]": "Tooltip Text with %s placeholder"
```

### Классы защиты / Protection Classes
```json
"protection_class.warbornrenewed.[class_number]": "Class Name"
```

### Атрибуты / Attributes
```json
"attribute.name.warbornrenewed.[attribute_name]": "Attribute Display Name"
```

---

## 🎮 Примеры использования в игре / In-Game Usage Examples

### Пример 1: Шлем NATO Woodland
```
Название: NATO Woodland Helmet (EN) / Шлем NATO Woodland (RU)
Тултип:
  Material: UHMWPE (Dyneema) (EN) / Материал: СВМПЭ (Dyneema) (RU)
```

### Пример 2: Бронежилет NATO Desert
```
Название: NATO Desert Vest (EN) / Бронежилет NATO Desert (RU)
Тултип:
  Material: UHMWPE (Dyneema) (EN) / Материал: СВМПЭ (Dyneema) (RU)
```

---

## 🔍 Тестирование локализации / Testing Localization

### 1. В игре / In-Game:
- Измените язык в настройках: **Options → Language → Русский/English**
- Перезапустите игру
- Проверьте названия предметов и тултипы

### 2. Проверка файлов / File Validation:
```bash
# Проверка JSON синтаксиса
python3 -m json.tool src/main/resources/assets/warbornrenewed/lang/ru_ru.json
python3 -m json.tool src/main/resources/assets/warbornrenewed/lang/en_us.json
```

---

## 🎨 Форматирование тултипов / Tooltip Formatting

### Цвета / Colors:
- **Материал / Material**: `ChatFormatting.GRAY` (Серый / Gray)
- Другие атрибуты используют цвета по умолчанию / Other attributes use default colors

### Плейсхолдеры / Placeholders:
- `%s` - Замена на строку / String replacement
- `%d` - Замена на число / Number replacement
- `%.1f` - Число с одним знаком после запятой / Number with 1 decimal place

---

## ❓ FAQ

### Q: Как изменить цвет тултипа материала?
**A:** Измените `ChatFormatting.GRAY` в `WarbornArmorItem.appendHoverText()` на желаемый цвет.

### Q: Можно ли добавить больше информации в тултип?
**A:** Да! Добавьте новые строки в метод `appendHoverText()` и соответствующие ключи в lang файлы.

### Q: Поддерживаются ли другие языки?
**A:** Да! Создайте новый файл с соответствующим locale кодом (например, `de_de.json` для немецкого).

---

## 📞 Поддержка / Support

Если у вас возникли вопросы по локализации:
1. Откройте Issue на GitHub
2. Создайте Pull Request с исправлениями
3. Свяжитесь с командой разработки

---

## 🏆 Благодарности / Credits

Спасибо всем переводчикам и контрибьюторам!  
Thanks to all translators and contributors!

---

**Последнее обновление / Last Update:** October 9, 2025  
**Версия документации / Documentation Version:** 1.0.0
