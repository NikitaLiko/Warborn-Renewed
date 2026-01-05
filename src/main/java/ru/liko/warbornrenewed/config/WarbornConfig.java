package ru.liko.warbornrenewed.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class WarbornConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
        COMMON = new Common(commonBuilder);
        COMMON_SPEC = commonBuilder.build();
    }

    @SuppressWarnings("removal")
    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue armorIsDamageable;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Warborn Renewed Configuration")
                    .push("general");

            armorIsDamageable = builder
                    .comment("Should armor take damage and break? If false, armor will have infinite durability.",
                            "Должна ли броня получать урон и ломаться? Если false, броня будет иметь бесконечную прочность.")
                    .translation("config.warbornrenewed.armor_is_damageable")
                    .define("armorIsDamageable", true);

            builder.pop();
        }
    }
}
