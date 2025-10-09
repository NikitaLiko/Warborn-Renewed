package ru.liko.warbornrenewed.setup;

import net.minecraft.world.item.Rarity;
import ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorRegistry;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorSet;
import ru.liko.warbornrenewed.registry.ModArmorMaterials;

/**
 * ========================================
 * РЕАЛИСТИЧНЫЕ НАБОРЫ БРОНИ NATO
 * ========================================
 * 
 * Все наборы используют реалистичные характеристики на основе:
 * - NIJ Standard 0101.06 (США)
 * - ГОСТ Р 50744-95 (Россия)
 * - VPAM (Германия)
 * 
 * Используются 5 атрибутов ModAttributes:
 * 1. bulletResistance - защита от пуль (0.0-1.0)
 * 2. protectionClass - класс защиты NIJ (0-6)
 * 3. effectiveThickness - толщина брони в мм
 * 4. blastResistance - защита от взрывов (множитель 0.0-2.0)
 * 5. movementSpeed - модификатор скорости (-0.5 до 0.2)
 * 
 * Материалы:
 * - KEVLAR - NIJ IIA/II - мягкая броня
 * - CERAMIC - NIJ III - керамические плиты
 * - AR500_STEEL - NIJ III - стальная броня
 * - UHMWPE - NIJ III/IV - современный полиэтилен (Dyneema)
 * - COMPOSITE - NIJ IV - многослойная композитная броня
 * - TITANIUM - NIJ III+ - титановые сплавы
 */
public final class WarbornArmorSets {
    private WarbornArmorSets() {
    }

    public static void bootstrap() {
        registerNATOwoodlandSet();
        registerNATOdesertSet();
    }

    private static void registerNATOwoodlandSet() {
        WarbornArmorRegistry.registerSet(
            WarbornArmorSet.builder("nato_woodland")
                .defaultMaterial(type -> ModArmorMaterials.UHMWPE)
                
                .helmet(piece -> piece
                    .registryName("nato_woodland_helmet")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/nato_helmet.geo.json")
                        .texture("warbornrenewed:textures/armor/nato_woodland.png"))
                    .bones(bones -> bones.head("armorHead"))
                    .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                    .attribute(ArmorAttributeSpec.bulletResistance(0.40))
                    .attribute(ArmorAttributeSpec.protectionClass(3))
                    .attribute(ArmorAttributeSpec.effectiveThickness(12.0))
                    .attribute(ArmorAttributeSpec.blastResistance(0.80))
                    .attribute(ArmorAttributeSpec.movementSpeed(-0.02)))
                
                .chestplate(piece -> piece
                    .registryName("nato_woodland_vest")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/nato_chest.geo.json")
                        .texture("warbornrenewed:textures/armor/nato_woodland.png"))
                    .bones(bones -> bones.body("armorBody").rightArm("armorRightArm").leftArm("armorLeftArm"))
                    .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                    .attribute(ArmorAttributeSpec.bulletResistance(0.55))
                    .attribute(ArmorAttributeSpec.protectionClass(3))
                    .attribute(ArmorAttributeSpec.effectiveThickness(18.0))
                    .attribute(ArmorAttributeSpec.blastResistance(0.70))
                    .attribute(ArmorAttributeSpec.movementSpeed(-0.05)))
                
                .leggings(piece -> piece
                    .registryName("nato_woodland_pants")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/nato_chest.geo.json")
                        .texture("warbornrenewed:textures/armor/nato_woodland.png"))
                    .bones(bones -> bones.body("armorBody").rightLeg("armorRightLeg").leftLeg("armorLeftLeg"))
                    .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                    .attribute(ArmorAttributeSpec.bulletResistance(0.35))
                    .attribute(ArmorAttributeSpec.protectionClass(2))
                    .attribute(ArmorAttributeSpec.effectiveThickness(8.0))
                    .attribute(ArmorAttributeSpec.blastResistance(0.85))
                    .attribute(ArmorAttributeSpec.movementSpeed(-0.03)))
                
                .boots(piece -> piece
                    .registryName("nato_woodland_boots")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/nato_chest.geo.json")
                        .texture("warbornrenewed:textures/armor/nato_woodland.png"))
                    .bones(bones -> bones.rightBoot("armorRightBoot").leftBoot("armorLeftBoot"))
                    .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                    .attribute(ArmorAttributeSpec.bulletResistance(0.25))
                    .attribute(ArmorAttributeSpec.protectionClass(1))
                    .attribute(ArmorAttributeSpec.effectiveThickness(5.0))
                    .attribute(ArmorAttributeSpec.blastResistance(0.90))
                    .attribute(ArmorAttributeSpec.movementSpeed(0.0)))
        );
    }

    private static void registerNATOdesertSet() {
        WarbornArmorRegistry.registerSet(
            WarbornArmorSet.builder("nato_desert")
                .defaultMaterial(type -> ModArmorMaterials.UHMWPE)
                
                .helmet(piece -> piece
                    .registryName("nato_desert_helmet")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/nato_helmet.geo.json")
                        .texture("warbornrenewed:textures/armor/nato_desert.png"))
                    .bones(bones -> bones.head("armorHead"))
                    .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                    .attribute(ArmorAttributeSpec.bulletResistance(0.40))
                    .attribute(ArmorAttributeSpec.protectionClass(3))
                    .attribute(ArmorAttributeSpec.effectiveThickness(12.0))
                    .attribute(ArmorAttributeSpec.blastResistance(0.80))
                    .attribute(ArmorAttributeSpec.movementSpeed(-0.02)))
                
                .chestplate(piece -> piece
                    .registryName("nato_desert_vest")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/nato_chest.geo.json")
                        .texture("warbornrenewed:textures/armor/nato_desert.png"))
                    .bones(bones -> bones.body("armorBody").rightArm("armorRightArm").leftArm("armorLeftArm"))
                    .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                    .attribute(ArmorAttributeSpec.bulletResistance(0.55))
                    .attribute(ArmorAttributeSpec.protectionClass(3))
                    .attribute(ArmorAttributeSpec.effectiveThickness(18.0))
                    .attribute(ArmorAttributeSpec.blastResistance(0.70))
                    .attribute(ArmorAttributeSpec.movementSpeed(-0.05)))
                
                .leggings(piece -> piece
                    .registryName("nato_desert_pants")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/nato_chest.geo.json")
                        .texture("warbornrenewed:textures/armor/nato_desert.png"))
                    .bones(bones -> bones.body("armorBody").rightLeg("armorRightLeg").leftLeg("armorLeftLeg"))
                    .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                    .attribute(ArmorAttributeSpec.bulletResistance(0.35))
                    .attribute(ArmorAttributeSpec.protectionClass(2))
                    .attribute(ArmorAttributeSpec.effectiveThickness(8.0))
                    .attribute(ArmorAttributeSpec.blastResistance(0.85))
                    .attribute(ArmorAttributeSpec.movementSpeed(-0.03)))
                
                .boots(piece -> piece
                    .registryName("nato_desert_boots")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/nato_chest.geo.json")
                        .texture("warbornrenewed:textures/armor/nato_desert.png"))
                    .bones(bones -> bones.rightBoot("armorRightBoot").leftBoot("armorLeftBoot"))
                    .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                    .attribute(ArmorAttributeSpec.bulletResistance(0.25))
                    .attribute(ArmorAttributeSpec.protectionClass(1))
                    .attribute(ArmorAttributeSpec.effectiveThickness(5.0))
                    .attribute(ArmorAttributeSpec.blastResistance(0.90))
                    .attribute(ArmorAttributeSpec.movementSpeed(0.0)))
        );
    }
}
