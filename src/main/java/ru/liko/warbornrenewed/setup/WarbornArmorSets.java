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
    }

    private static void registerNATOwoodSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("nato-wood")  // 👈 ИЗМЕНИТЕ ЭТО
                        .defaultMaterial(type -> ModArmorMaterials.CEMENTED_CARBIDE)

                        .helmet(piece -> piece
                                .registryName("nato-wood-helmet")  // 👈 ИЗМЕНИТЕ ЭТО
                                .visuals(spec -> spec
                                        .model("warbornrenewed:geo/nato-helmet.geo.json")
                                        .texture("warbornrenewed:textures/armor/nato-wood.png"))
                                .properties(props -> props
                                        .stacksTo(1)
                                        .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                .bulletResistance(0.4D))  // 👈 ИЗМЕНИТЕ ЗАЩИТУ

                        .chestplate(piece -> piece
                                .registryName("nato-wood-chestplate")  // 👈 ИЗМЕНИТЕ ЭТО
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
                                .bulletResistance(0.6D))

        private static void registerNATOsandSet() {
            WarbornArmorRegistry.registerSet(
                    WarbornArmorSet.builder("nato-sand-helmet")  // 👈 ИЗМЕНИТЕ ЭТО
                            .defaultMaterial(type -> ModArmorMaterials.CEMENTED_CARBIDE)

                            .helmet(piece -> piece
                                    .registryName("nato-sand-helmet")  // 👈 ИЗМЕНИТЕ ЭТО
                                    .visuals(spec -> spec
                                            .model("warbornrenewed:geo/nato-helmet.geo.json")
                                            .texture("warbornrenewed:textures/armor/nato-wood.png"))
                                    .properties(props -> props
                                            .stacksTo(1)
                                            .rarity(Rarity.EPIC))  // 👈 ИЗМЕНИТЕ РЕДКОСТЬ
                                    .bulletResistance(0.4D))  // 👈 ИЗМЕНИТЕ ЗАЩИТУ

                            .chestplate(piece -> piece
                                    .registryName("nato-sand-chestplate")  // 👈 ИЗМЕНИТЕ ЭТО
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
                                    .bulletResistance(0.6D))



                    // 👇 ШТАНЫ (необязательно, можете удалить если не нужны)
                //.leggings(piece -> piece
                //.registryName("my_custom_pants")  // 👈 ИЗМЕНИТЕ ЭТО
                //.visuals(spec -> spec
                //.model("superbwarfare:geo/us_chest_iotv.geo.json")
                //.texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                //.bones(bones -> bones
                //.body("armorBody")
                //.rightLeg("armorRightLeg")
                //.leftLeg("armorLeftLeg"))
                //.properties(props -> props
                //.stacksTo(1)
                //.rarity(Rarity.EPIC))
                //.bulletResistance(0.5D))

                // 👇 БОТИНКИ (необязательно, можете удалить если не нужны)
                //.boots(piece -> piece
                //.registryName("my_custom_boots")  // 👈 ИЗМЕНИТЕ ЭТО
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