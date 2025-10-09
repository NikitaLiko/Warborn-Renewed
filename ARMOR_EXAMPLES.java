/*
 * ================================
 * ПРИМЕРЫ СОЗДАНИЯ НАБОРОВ БРОНИ
 * ================================
 * 
 * Этот файл содержит готовые примеры различных наборов брони.
 * Копируйте эти примеры в WarbornArmorSets.java и адаптируйте под свои нужды.
 */

// ==========================================
// ПРИМЕР 1: Простой шлем (минимальная конфигурация)
// ==========================================
private static void registerSimpleHelmet() {
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
}

// ==========================================
// ПРИМЕР 2: Полный комплект с одинаковыми настройками
// ==========================================
private static void registerFullUniformSet() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("tactical_uniform")
            // Общие настройки для всех частей
            .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
            .defaultVisuals(spec -> spec
                .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
            
            .helmet(piece -> piece
                .registryName("tactical_uniform_helmet")
                .bulletResistance(0.25D))
            
            .chestplate(piece -> piece
                .registryName("tactical_uniform_vest")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_chest_iotv.geo.json")
                    .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                .bulletResistance(0.45D))
    );
}

// ==========================================
// ПРИМЕР 3: Комплект с разными материалами
// ==========================================
private static void registerMixedMaterialSet() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("mixed_armor")
            // Тяжёлый шлем из карбида
            .helmet(piece -> piece
                .registryName("heavy_helmet")
                .material(type -> ModArmorMaterial.CEMENTED_CARBIDE)
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                    .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
                .properties(props -> props.rarity(Rarity.RARE))
                .bulletResistance(0.35D))
            
            // Лёгкий жилет из стали
            .chestplate(piece -> piece
                .registryName("light_carrier")
                .material(type -> ModArmorMaterial.STEEL)
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_chest_iotv.geo.json")
                    .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                .properties(props -> props.rarity(Rarity.UNCOMMON))
                .bulletResistance(0.3D))
    );
}

// ==========================================
// ПРИМЕР 4: Элитная броня с кастомными атрибутами
// ==========================================
private static void registerEliteArmorSet() {
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
                // Постоянная защита (не изнашивается)
                .bulletResistance(0.4D, false)
                // Добавляем сопротивление отталкиванию
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
                .properties(props -> props
                    .rarity(Rarity.EPIC)
                    .fireResistant())
                .bulletResistance(0.6D, false)
                // Добавляем прочность брони
                .attribute(ArmorAttributeSpec.of(
                    () -> Attributes.ARMOR_TOUGHNESS,
                    "elite_toughness",
                    AttributeModifier.Operation.ADDITION,
                    2.0D,
                    false)))
    );
}

// ==========================================
// ПРИМЕР 5: Скоростная броня (лёгкая)
// ==========================================
private static void registerSpeedArmorSet() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("speed_armor")
            .defaultMaterial(type -> ModArmorMaterial.STEEL)
            
            .helmet(piece -> piece
                .registryName("speed_helmet")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/ge_helmet_m_35.geo.json")
                    .texture("superbwarfare:textures/armor/ge_helmet_m_35.png"))
                .bulletResistance(0.1D)
                // Увеличиваем скорость передвижения на 10%
                .attribute(ArmorAttributeSpec.of(
                    () -> Attributes.MOVEMENT_SPEED,
                    "speed_boost",
                    AttributeModifier.Operation.MULTIPLY_TOTAL,
                    0.1D,
                    false)))
            
            .chestplate(piece -> piece
                .registryName("speed_vest")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_chest_iotv.geo.json")
                    .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                .bulletResistance(0.2D)
                .attribute(ArmorAttributeSpec.of(
                    () -> Attributes.MOVEMENT_SPEED,
                    "speed_boost",
                    AttributeModifier.Operation.MULTIPLY_TOTAL,
                    0.15D,
                    false)))
    );
}

// ==========================================
// ПРИМЕР 6: Адаптивная защита по типу части
// ==========================================
private static void registerAdaptiveArmorSet() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("adaptive_armor")
            // Разный материал в зависимости от типа
            .defaultMaterial(type -> switch (type) {
                case HELMET -> ModArmorMaterial.CEMENTED_CARBIDE;  // Защита головы важна
                case CHESTPLATE -> ModArmorMaterial.CEMENTED_CARBIDE;  // И груди тоже
                case LEGGINGS, BOOTS -> ModArmorMaterial.STEEL;  // Ноги - полегче
            })
            
            .helmet(piece -> piece
                .registryName("adaptive_helmet")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                    .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
                .bulletResistance(0.3D))
            
            .chestplate(piece -> piece
                .registryName("adaptive_vest")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_chest_iotv.geo.json")
                    .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                .bulletResistance(0.5D))
    );
}

// ==========================================
// ПРИМЕР 7: Использование всех доступных моделей SBW
// ==========================================
private static void registerAllSBWModelsExample() {
    // Немецкая броня
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("german_set")
            .defaultMaterial(type -> ModArmorMaterial.STEEL)
            .helmet(piece -> piece
                .registryName("german_helmet_m35")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/ge_helmet_m_35.geo.json")
                    .texture("superbwarfare:textures/armor/ge_helmet_m_35.png"))
                .bulletResistance(0.1D))
    );
    
    // Американская броня
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("american_set")
            .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
            .helmet(piece -> piece
                .registryName("us_helmet_pasgt")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                    .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
                .bulletResistance(0.2D))
            .chestplate(piece -> piece
                .registryName("us_chest_iotv")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_chest_iotv.geo.json")
                    .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                .bulletResistance(0.45D))
    );
    
    // Российская броня
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("russian_set")
            .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
            .helmet(piece -> piece
                .registryName("ru_helmet_6b47")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/ru_helmet_6b47.geo.json")
                    .texture("superbwarfare:textures/armor/ru_helmet_6b47.png"))
                .bulletResistance(0.25D))
            .chestplate(piece -> piece
                .registryName("ru_chest_6b43")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/ru_chest_6b43.geo.json")
                    .texture("superbwarfare:textures/armor/ru_chest_6b43.png"))
                .bulletResistance(0.5D))
    );
}

// ==========================================
// ПРИМЕР 8: Броня с анимацией
// ==========================================
private static void registerAnimatedArmorSet() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("animated_armor")
            .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
            
            .chestplate(piece -> piece
                .registryName("animated_chestplate")
                .visuals(spec -> spec
                    .model("warbornrenewed:geo/animated_chest.geo.json")
                    .texture("warbornrenewed:textures/armor/animated_chest.png")
                    // Добавляем анимацию (файл должен существовать!)
                    .animation("warbornrenewed:animations/chest_idle.animation.json"))
                .bulletResistance(0.4D))
    );
}

// ==========================================
// ПРИМЕР 9: Кастомные имена костей
// ==========================================
private static void registerCustomBonesArmorSet() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("custom_bones")
            .defaultMaterial(type -> ModArmorMaterial.STEEL)
            
            // Глобальные кастомные кости
            .defaultBones(bones -> bones
                .head("customHeadBone")
                .body("customBodyBone"))
            
            .helmet(piece -> piece
                .registryName("custom_bones_helmet")
                .visuals(spec -> spec
                    .model("warbornrenewed:geo/custom_helmet.geo.json")
                    .texture("warbornrenewed:textures/armor/custom_helmet.png"))
                // Переопределяем кости только для шлема
                .bones(bones -> bones.head("specialHelmetBone"))
                .bulletResistance(0.2D))
    );
}

// ==========================================
// ПРИМЕР 10: Набор для выживания (огнеупорный + высокая защита)
// ==========================================
private static void registerSurvivalArmorSet() {
    WarbornArmorRegistry.registerSet(
        WarbornArmorSet.builder("survival_armor")
            .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
            
            .helmet(piece -> piece
                .registryName("survival_helmet")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                    .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
                .properties(props -> props
                    .rarity(Rarity.RARE)
                    .fireResistant())
                .bulletResistance(0.3D)
                // Сопротивление урону от взрывов
                .attribute(ArmorAttributeSpec.of(
                    () -> Attributes.KNOCKBACK_RESISTANCE,
                    "explosion_res",
                    AttributeModifier.Operation.ADDITION,
                    0.3D,
                    false)))
            
            .chestplate(piece -> piece
                .registryName("survival_vest")
                .visuals(spec -> spec
                    .model("superbwarfare:geo/us_chest_iotv.geo.json")
                    .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                .properties(props -> props
                    .rarity(Rarity.RARE)
                    .fireResistant())
                .bulletResistance(0.5D)
                .attribute(ArmorAttributeSpec.of(
                    () -> Attributes.ARMOR_TOUGHNESS,
                    "survival_toughness",
                    AttributeModifier.Operation.ADDITION,
                    3.0D,
                    false))
                .attribute(ArmorAttributeSpec.of(
                    () -> Attributes.KNOCKBACK_RESISTANCE,
                    "explosion_res",
                    AttributeModifier.Operation.ADDITION,
                    0.5D,
                    false)))
    );
}

// ==========================================
// КАК ИСПОЛЬЗОВАТЬ ЭТИ ПРИМЕРЫ:
// ==========================================
/*
 * 1. Скопируйте нужный метод в WarbornArmorSets.java
 * 2. Измените ID набора (первый параметр builder())
 * 3. Настройте registryName для каждой части
 * 4. Измените модели и текстуры по необходимости
 * 5. Настройте атрибуты и свойства
 * 6. Вызовите метод в bootstrap():
 * 
 *     public static void bootstrap() {
 *         registerSimpleHelmet();
 *         registerEliteArmorSet();
 *         // ... другие наборы
 *     }
 */
