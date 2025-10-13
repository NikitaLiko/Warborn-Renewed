package ru.liko.warbornrenewed.setup;

import net.minecraft.world.item.Rarity;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorRegistry;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorSet;
import ru.liko.warbornrenewed.registry.ModArmorMaterials;

/**
 * ========================================
 * МЕСТО ДЛЯ РЕГИСТРАЦИИ ВАШИХ НАБОРОВ БРОНИ
 * ========================================
 *
 * Здесь вы создаёте свои наборы брони.
 * Просто копируйте примеры ниже и изменяйте под себя!
 * 
 * АНИМАЦИИ ШЛЕМОВ (GeckoLib):
 * Чтобы добавить анимацию визора/козырька:
 * 1. Создайте .animation.json файл (например, gpngv-nato-helmet-woodland.animation.json)
 * 2. Добавьте .animation("warbornrenewed:animations/gpngv-nato-helmet-woodland.animation.json") в visuals
 * 3. Анимация будет автоматически использоваться при регистрации контроллера
 * 
 * Пример шлема с анимацией:
 * .helmet(piece -> piece
 *     .registryName("beta7_helmet")
 *     .material(type -> ModArmorMaterials.KEVLAR)
 *     .visuals(spec -> spec
 *         .model("warbornrenewed:geo/beta7-helmet.geo.json")
 *         .texture("warbornrenewed:textures/beta7.png")
 *         .animation("warbornrenewed:animations/gpngv-nato-helmet-woodland.animation.json")) // 👈 АНИМАЦИЯ!
 *     .properties(props -> props.stacksTo(1).rarity(Rarity.EPIC))
 *     .bulletResistance(0.5D))
 */
public final class WarbornArmorSets {
    private WarbornArmorSets() {
    }

    /**
     * Этот метод вызывается при загрузке мода.
     * Здесь регистрируются все наборы брони.
     */
    public static void bootstrap() {

        registerNATOwoodSet();
        registerNATOsandSet();
        registerRatniksandSet();
        registerRatniksandghSet();
        registerNVGSet();
    }


    private static void registerNATOwoodSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("nato-wood")

                        .helmet(piece -> piece
                                .registryName("nato-wood-helmet")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/nato-helmet.geo.json")
                                        .texture("warbornrenewed:textures/nato-wood.png"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(3))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(3.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.9D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.03D)))

                        .chestplate(piece -> piece
                                .registryName("nato-wood-chestplate")
                                .material(type -> ModArmorMaterials.UHMWPE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/nato-chest.geo.json")
                                        .texture("warbornrenewed:textures/nato-wood.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))
                                .bulletResistance(0.6D)
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(4))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(22.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.65D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.10D)))
        );
    }

    private static void registerNATOsandSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("nato-sand")

                        .helmet(piece -> piece
                                .registryName("nato-sand-helmet")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/nato-helmet.geo.json")
                                        .texture("warbornrenewed:textures/nato-sand.png"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(3))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(3.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.9D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.03D)))

                        .chestplate(piece -> piece
                                .registryName("nato-sand-chestplate")
                                .material(type -> ModArmorMaterials.UHMWPE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/nato-chest.geo.json")
                                        .texture("warbornrenewed:textures/nato-sand.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))
                                .bulletResistance(0.6D)
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(4))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(22.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.65D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.10D)))
        );
    }
        private static void registerRatniksandSet() {
            WarbornArmorRegistry.registerSet(
                    WarbornArmorSet.builder("ratnik-sand")

                            .helmet(piece -> piece
                                    .registryName("ratnik-sand-helmet")
                                    .material(type -> ModArmorMaterials.KEVLAR)
                                    .visuals(spec -> spec
                                            .model("warbornrenewed:geo/ratnik-helmet.geo.json")
                                            .texture("warbornrenewed:textures/ratnik-desert.png"))
                                    .properties(props -> props
                                            .stacksTo(1)
                                            .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                    .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(1))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(2.0D))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.9D))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.02D)))

                            .chestplate(piece -> piece
                                    .registryName("ratnik-sand-chestplate")
                                    .material(type -> ModArmorMaterials.UHMWPE)
                                    .visuals(spec -> spec
                                            .model("warbornrenewed:geo/ratnik-chest.geo.json")
                                            .texture("warbornrenewed:textures/ratnik-desert.png"))
                                    .bones(bones -> bones
                                            .body("armorBody")
                                            .rightArm("armorRightArm")
                                            .leftArm("armorLeftArm"))
                                    .properties(props -> props
                                            .stacksTo(1)
                                            .rarity(Rarity.EPIC))
                                    .bulletResistance(0.6D)
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(5))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(20.0D))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.55D))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.09D)))
            );
        }

    private static void registerRatniksandghSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("ratnik-sand-gh")

                        .helmet(piece -> piece
                                .registryName("ratnik-sand-helmet-gh")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik-helmet-gh.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-desert-gh.png"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(1))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(2.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.9D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.02D)))

                        .chestplate(piece -> piece
                                .registryName("ratnik-sand-chest-gh")
                                .material(type -> ModArmorMaterials.UHMWPE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik-chest-gh.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-desert-gh.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))
                                .bulletResistance(0.6D)
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(5))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(20.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.55D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.09D)))


                        .leggings(piece -> piece
                                .material(type -> ModArmorMaterials.LEATHER)
                                .registryName("ratnik-sand-leggings-gh")
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik-sand-leggings-gh.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-desert-gh.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightLeg("armorRightLeg")
                                        .leftLeg("armorLeftLeg"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.UNCOMMON))
                                .bulletResistance(0.0D))
        );
    }

    private static void registerNVGSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("nvgs")

                        .helmet(piece -> piece
                                .registryName("gpngv-nato-wood-helmet")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/gpngv-nato-helmet-woodland.geo.json")
                                        .texture("warbornrenewed:textures/gpngv-nato-helmet-woodland.png"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(3))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(3.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.9D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.03D)))





                // 👇 БОТИНКИ (необязательно, можете удалить если не нужны)
                //.boots(piece -> piece
                //.registryName("my_custom_boots")
                //.visuals(spec -> spec
                //.model("superbwarfare:geo/us_chest_iotv.geo.json")
                //.texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                //.bones(bones -> bones
                //.rightBoot("armorRightBoot")
                //.leftBoot("armorLeftBoot"))
                //.properties(props -> props
                //.stacksTo(1)
                //.rarity(Rarity.EPIC))
                //.bulletResistance(0.3D))
        );
    }
}