package ru.liko.warbornrenewed.setup;

import com.atsuishio.superbwarfare.tiers.ModArmorMaterial;
import net.minecraft.world.item.Rarity;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorRegistry;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorSet;

public final class WarbornArmorSets {
    private static final boolean REGISTER_EXAMPLE_SET = Boolean.getBoolean("warbornrenewed.enableExampleArmor");

    private WarbornArmorSets() {
    }

    public static void bootstrap() {
        if (REGISTER_EXAMPLE_SET) {
            registerExampleSet();
        }
    }

    private static void registerExampleSet() {
        WarbornArmorRegistry.registerSet(
                WarbornArmorSet.builder("pasgt_refit")
                        .defaultMaterial(type -> ModArmorMaterial.CEMENTED_CARBIDE)
                        .helmet(piece -> piece
                                .registryName("pasgt_refit_helmet")
                                .visuals(spec -> spec
                                        .model("superbwarfare:geo/us_helmet_pasgt.geo.json")
                                        .texture("superbwarfare:textures/armor/us_helmet_pasgt.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.2D))
                        .chestplate(piece -> piece
                                .registryName("pasgt_refit_carrier")
                                .visuals(spec -> spec
                                        .model("superbwarfare:geo/us_chest_iotv.geo.json")
                                        .texture("superbwarfare:textures/armor/us_chest_iotv.png"))
                                .properties(props -> props.stacksTo(1).rarity(Rarity.RARE))
                                .bulletResistance(0.45D))
        );
    }
}
