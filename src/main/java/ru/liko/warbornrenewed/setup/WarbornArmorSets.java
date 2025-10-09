package ru.liko.warbornrenewed.setup;

import com.atsuishio.superbwarfare.tiers.ModArmorMaterial;
import net.minecraft.world.item.Rarity;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorRegistry;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorSet;

/**
 * ========================================
 * МЕСТО ДЛЯ РЕГИСТРАЦИИ ВАШИХ НАБОРОВ БРОНИ
 * ========================================
 * 
 * Здесь вы создаёте свои наборы брони.
 * Просто копируйте примеры ниже и изменяйте под себя!
 */
public final class WarbornArmorSets {
    private WarbornArmorSets() {
    }

    /**
     * Этот метод вызывается при загрузке мода.
     * Здесь регистрируются все наборы брони.
     */
    public static void bootstrap() {
        // ✅ ГОТОВЫЕ ПРИМЕРЫ - раскомментируйте нужные:
        
        registerTacticalSet();      // Американская тактическая броня
        // registerRussianSet();    // Российская броня (раскомментируйте чтобы активировать)
        // registerGermanSet();     // Немецкая броня (раскомментируйте чтобы активировать)
        
        // ============================================
        // 👇 ДОБАВЛЯЙТЕ СВОИ НАБОРЫ ЗДЕСЬ:
        // ============================================
        // registerMyCustomSet();
    }

    // ========================================
    // ПРИМЕР 1: АМЕРИКАНСКАЯ ТАКТИЧЕСКАЯ БРОНЯ
    // ========================================
    /**
     * Американская тактическая броня из SuperbWarfare.
     * Полный комплект: шлем PASGT + жилет IOTV
     * 
     * КАК ИСПОЛЬЗОВАТЬ:
     * 1. Этот набор уже активирован в bootstrap()
     * 2. Запустите игру
     * 3. Найдите предметы: "tactical_helmet" и "tactical_vest" в творческом режиме
     */
    private static void registerTacticalSet() {
        WarbornArmorRegistry.registerSet(
            WarbornArmorSet.builder("tactical")  // ID набора - используется внутри мода
                // Материал для всех частей (можно переопределить для каждой части)
                .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
                
                // ШЛЕМ
                .helmet(piece -> piece
                    .registryName("tactical_helmet")  // Имя предмета в игре
                    .visuals(spec -> spec
                        // Используем модель из SuperbWarfare
                        .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                        .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
                    .properties(props -> props
                        .stacksTo(1)           // Макс. стак = 1
                        .rarity(Rarity.UNCOMMON))  // Редкость (цвет названия)
                    .bulletResistance(0.25D))  // Защита от пуль 25%
                
                // НАГРУДНИК
                .chestplate(piece -> piece
                    .registryName("tactical_vest")
                    .visuals(spec -> spec
                        .model("superbwarfare:geo/us_chest_iotv.geo.json")
                        .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                    .properties(props -> props
                        .stacksTo(1)
                        .rarity(Rarity.UNCOMMON))
                    .bulletResistance(0.45D))  // Защита от пуль 45%
        );
    }

    // ========================================
    // ПРИМЕР 2: РОССИЙСКАЯ БРОНЯ
    // ========================================
    /**
     * Российская броня из SuperbWarfare.
     * Комплект: шлем 6B47 + жилет 6B43
     */
    private static void registerRussianSet() {
        WarbornArmorRegistry.registerSet(
            WarbornArmorSet.builder("russian")
                .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
                
                .helmet(piece -> piece
                    .registryName("russian_helmet_6b47")
                    .visuals(spec -> spec
                        .model("superbwarfare:geo/ru_helmet_6b47.geo.json")
                        .texture("superbwarfare:textures/armor/ru_helmet_6b47.png"))
                    .properties(props -> props
                        .stacksTo(1)
                        .rarity(Rarity.UNCOMMON))
                    .bulletResistance(0.3D))
                
                .chestplate(piece -> piece
                    .registryName("russian_vest_6b43")
                    .visuals(spec -> spec
                        .model("superbwarfare:geo/ru_chest_6b43.geo.json")
                        .texture("superbwarfare:textures/armor/ru_chest_6b43.png"))
                    .properties(props -> props
                        .stacksTo(1)
                        .rarity(Rarity.UNCOMMON))
                    .bulletResistance(0.5D))
        );
    }

    // ========================================
    // ПРИМЕР 3: НЕМЕЦКАЯ БРОНЯ
    // ========================================
    /**
     * Немецкая броня из SuperbWarfare.
     * Только шлем M35 (простой пример)
     */
    private static void registerGermanSet() {
        WarbornArmorRegistry.registerSet(
            WarbornArmorSet.builder("german")
                .defaultMaterial(type -> ModArmorMaterial.STEEL)  // Сталь (дешевле)
                
                .helmet(piece -> piece
                    .registryName("german_helmet_m35")
                    .visuals(spec -> spec
                        .model("superbwarfare:geo/ge_helmet_m_35.geo.json")
                        .texture("superbwarfare:textures/armor/ge_helmet_m_35.png"))
                    .properties(props -> props
                        .stacksTo(1)
                        .rarity(Rarity.COMMON))  // Обычная редкость
                    .bulletResistance(0.15D))  // Меньше защиты
        );
    }

    // ========================================
    // 👇 ВАШИ НАБОРЫ БРОНИ ЗДЕСЬ:
    // ========================================
    
    /**
     * ШАБЛОН ДЛЯ ВАШЕЙ БРОНИ - скопируйте и измените!
     * 
     * Шаги:
     * 1. Скопируйте этот метод
     * 2. Переименуйте метод (например: registerMyAwesomeArmor)
     * 3. Измените builder("my_custom_name") - уникальный ID
     * 4. Измените registryName для каждой части
     * 5. Выберите модели и текстуры (свои или из SuperbWarfare)
     * 6. Настройте свойства (материал, редкость, защита)
     * 7. Раскомментируйте вызов в bootstrap()
     */
    @SuppressWarnings("unused")
    private static void registerMyCustomSet() {
        WarbornArmorRegistry.registerSet(
            WarbornArmorSet.builder("my_custom_armor")  // 👈 ИЗМЕНИТЕ ЭТО
                .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
                
                .helmet(piece -> piece
                    .registryName("my_custom_helmet")  // 👈 ИЗМЕНИТЕ ЭТО
                    .visuals(spec -> spec
                        // 👇 ВЫБЕРИТЕ СВОЮ МОДЕЛЬ ИЛИ ИСПОЛЬЗУЙТЕ ИЗ SUPERBWARFARE
                        .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                        .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
                    .properties(props -> props
                        .stacksTo(1)
                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                    .bulletResistance(0.4D))  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                
                .chestplate(piece -> piece
                    .registryName("my_custom_vest")  // 👈 ИЗМЕНИТЕ ЭТО
                    .visuals(spec -> spec
                        .model("superbwarfare:geo/us_chest_iotv.geo.json")
                        .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                    .properties(props -> props
                        .stacksTo(1)
                        .rarity(Rarity.EPIC))
                    .bulletResistance(0.6D))
        );
    }
}

// ========================================
// ДОСТУПНЫЕ МОДЕЛИ ИЗ SUPERBWARFARE:
// ========================================
// 
// ШЛЕМЫ:
// - superbwarfare:geo/ge_helmet_m_35.geo.json (Немецкий M35)
// - superbwarfare:geo/us_helmet_pasgt.geo.json (Американский PASGT)
// - superbwarfare:geo/ru_helmet_6b47.geo.json (Российский 6B47)
//
// ЖИЛЕТЫ:
// - superbwarfare:geo/us_chest_iotv.geo.json (Американский IOTV)
// - superbwarfare:geo/ru_chest_6b43.geo.json (Российский 6B43)
//
// ТЕКСТУРЫ: замените geo/ на textures/armor/ и .geo.json на .png
// Например: superbwarfare:textures/armor/us_helmet_pasgt.png
//
// ========================================
// ДОСТУПНЫЕ МАТЕРИАЛЫ:
// ========================================
// - ModArmorMaterial.STEEL - сталь (прочность 35)
// - ModArmorMaterial.CEMENTED_CARBIDE - карбид (прочность 50)
//
// ========================================
// РЕДКОСТИ (ЦВЕТ НАЗВАНИЯ):
// ========================================
// - Rarity.COMMON - белый
// - Rarity.UNCOMMON - жёлтый
// - Rarity.RARE - голубой
// - Rarity.EPIC - фиолетовый
