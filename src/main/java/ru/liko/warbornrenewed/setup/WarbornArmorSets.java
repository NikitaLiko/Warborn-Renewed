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
 */
public class WarbornArmorSets {
    public static void bootstrap() {

        registerNATOwoodSet();
        registerNATOsandSet();
        registerRatniksandSet();
        registerNVGSet();
        registerjpcfmaSet();
        registeropscoreuwinSet();
        registerRatnikwoodSet();
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
                                        .rarity(Rarity.COMMON))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(3))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(3.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.02D))
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
                                        .rarity(Rarity.UNCOMMON))
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
                                        .rarity(Rarity.COMMON))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(3))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(3.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.02D))
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
                                        .rarity(Rarity.RARE))
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
                                            .model("warbornrenewed:geo/ratnik-helmet-desert.geo.json")
                                            .texture("warbornrenewed:textures/ratnik-desert.png"))
                                    .properties(props -> props
                                            .stacksTo(1)
                                            .rarity(Rarity.COMMON))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                    .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(1))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(2.0D))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.02D))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.02D)))

                            .chestplate(piece -> piece
                                    .registryName("ratnik-sand-chestplate")
                                    .material(type -> ModArmorMaterials.UHMWPE)
                                    .visuals(spec -> spec
                                            .model("warbornrenewed:geo/ratnik-chest-desert.geo.json")
                                            .texture("warbornrenewed:textures/ratnik-desert.png"))
                                    .bones(bones -> bones
                                            .body("armorBody")
                                            .rightArm("armorRightArm")
                                            .leftArm("armorLeftArm"))
                                    .properties(props -> props
                                            .stacksTo(1)
                                            .rarity(Rarity.UNCOMMON))
                                    .bulletResistance(0.6D)
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(5))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(20.0D))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.55D))
                                    .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.09D)))
            );
        }
    private static void registerRatnikwoodSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("ratnik-wood")

                        .helmet(piece -> piece
                                .registryName("ratnik-wood-helmet")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik-helmet.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-wood.png"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.COMMON))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(1))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(2.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.02D)))

                        .chestplate(piece -> piece
                                .registryName("ratnik-wood-chestplate")
                                .material(type -> ModArmorMaterials.UHMWPE)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik-chest.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-wood.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.RARE))
                                .bulletResistance(0.6D)
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(5))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(20.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.55D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.09D)))
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
                                        .texture("warbornrenewed:textures/nato-wood.png")
                                        .animation("warbornrenewed:animations/gpngv-nato-helmet-woodland.animation.json"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(3))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(3.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.03D)))

                        .helmet(piece -> piece
                                .registryName("gpngv-nato-helmet-desert")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/gpngv-nato-helmet-woodland.geo.json")
                                        .texture("warbornrenewed:textures/nato-desert.png")
                                        .animation("warbornrenewed:animations/gpngv-nato-helmet-woodland.animation.json"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(3))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(3.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.03D)))

                        .helmet(piece -> piece
                                .registryName("ratnik-helmet-10t-wood")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik10t.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-wood.png")
                                        .animation("warbornrenewed:animations/ratnik10t.animation.json"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(3))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(1.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.03D)))

                        .helmet(piece -> piece
                                .registryName("ratnik10tdesert")
                                .withNVG()
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/ratnik10tdesert.geo.json")
                                        .texture("warbornrenewed:textures/ratnik-desert.png")
                                        .animation("warbornrenewed:animations/ratnik10t.animation.json"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(3))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(1.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.03D)))
        );
    }

private static void registerjpcfmaSet() {
    WarbornArmorRegistry.registerSet(
            WarbornArmorSet.builder("jpc-fma")

                    .helmet(piece -> piece
                            .registryName("fma")
                            .material(type -> ModArmorMaterials.KEVLAR)
                            .visuals(spec -> spec
                                    .model("warbornrenewed:geo/fma.geo.json")
                                    .texture("warbornrenewed:textures/jpc-fma.png"))
                            .properties(props -> props
                                    .stacksTo(1)
                                    .rarity(Rarity.UNCOMMON))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                            .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(1))
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(1.0D))
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.01D))
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.01D)))

                    .chestplate(piece -> piece
                            .registryName("jpc")
                            .material(type -> ModArmorMaterials.AR500_STEEL)
                            .visuals(spec -> spec
                                    .model("warbornrenewed:geo/jpc.geo.json")
                                    .texture("warbornrenewed:textures/jpc-fma.png"))
                            .bones(bones -> bones
                                    .body("armorBody")
                                    .rightArm("armorRightArm")
                                    .leftArm("armorLeftArm"))
                            .properties(props -> props
                                    .stacksTo(1)
                                    .rarity(Rarity.RARE))
                            .bulletResistance(0.6D)
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(4))
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(10.0D))
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.2D))
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.05D)))

                    .chestplate(piece -> piece
                            .registryName("jpc-desert")
                            .material(type -> ModArmorMaterials.AR500_STEEL)
                            .visuals(spec -> spec
                                    .model("warbornrenewed:geo/jpc.geo.json")
                                    .texture("warbornrenewed:textures/jpc-fma-desert.png"))
                            .bones(bones -> bones
                                    .body("armorBody")
                                    .rightArm("armorRightArm")
                                    .leftArm("armorLeftArm"))
                            .properties(props -> props
                                    .stacksTo(1)
                                    .rarity(Rarity.RARE))
                            .bulletResistance(0.6D)
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(4))
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(10.0D))
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.2D))
                            .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.05D)))
    );
}

    private static void registeropscoreuwinSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("opscore-uwin")

                        .helmet(piece -> piece
                                .registryName("opscore")
                                .material(type -> ModArmorMaterials.KEVLAR)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/opscore.geo.json")
                                        .texture("warbornrenewed:textures/opscore-uwin.png"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.RARE))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D)  // 👈 ИЗМЕНИТЕ ЗАЩИТУ
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(1))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(2.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.02D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.03D)))

                        .chestplate(piece -> piece
                                .registryName("uwin")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/uwin.geo.json")
                                        .texture("warbornrenewed:textures/opscore-uwin.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))
                                .bulletResistance(0.6D)
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(5))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(12.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.1D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.04D)))

                        .chestplate(piece -> piece
                                .registryName("uwin-desert")
                                .material(type -> ModArmorMaterials.CERAMIC)
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/uwin.geo.json")
                                        .texture("warbornrenewed:textures/opscore-uwin-desert.png"))
                                .bones(bones -> bones
                                        .body("armorBody")
                                        .rightArm("armorRightArm")
                                        .leftArm("armorLeftArm"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))
                                .bulletResistance(0.6D)
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.protectionClass(5))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.effectiveThickness(12.0D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.blastResistance(0.1D))
                                .attribute(ru.liko.warbornrenewed.content.armorset.ArmorAttributeSpec.movementSpeed(-0.04D)))






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
