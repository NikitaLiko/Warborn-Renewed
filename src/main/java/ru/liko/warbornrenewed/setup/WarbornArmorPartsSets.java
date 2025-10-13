package ru.liko.warbornrenewed.setup;

import net.minecraft.world.item.Rarity;
import ru.liko.warbornrenewed.content.armorparts.WarbornArmorPart;
import ru.liko.warbornrenewed.content.armorparts.WarbornArmorPartsRegistry;

/**
 * ========================================
 * МЕСТО ДЛЯ РЕГИСТРАЦИИ ВАШИХ ЧАСТЕЙ БРОНИ (Curios)
 * ========================================
 * 
 * Здесь вы создаёте свои части брони для слотов Curios.
 * Просто копируйте примеры ниже и изменяйте под себя!
 * 
 * Доступные типы слотов:
 * - nvg()       - Прибор ночного видения (NVG)
 * - backpack()  - Рюкзак
 * - vest()      - Тактический жилет
 * - headset()   - Гарнитура
 * - goggles()   - Очки/защитные очки
 */
public final class WarbornArmorPartsSets {
    private WarbornArmorPartsSets() {
    }

    /**
     * Этот метод вызывается при загрузке мода.
     * Здесь регистрируются все части брони.
     */
    public static void bootstrap() {
        // ВАЖНО: Раскомментируйте примеры после создания моделей и текстур!
        // Пример регистрации NVG (Прибор ночного видения)
        // registerExampleNVG();
        
        // Добавьте здесь свои части брони!
        // registerMyBackpack();
        // registerMyVest();
    }

    /**
     * Пример регистрации NVG с поддержкой toggle (подъём/опускание)
     */
    private static void registerExampleNVG() {
        WarbornArmorPartsRegistry.registerPart(
            WarbornArmorPart.builder("example-nvg")
                .nvg(part -> part
                    .registryName("example_nvg")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/nvg.geo.json")
                        .texture("warbornrenewed:textures/parts/nvg.png")
                        .textureDown("warbornrenewed:textures/parts/nvg.png")) // Текстура в опущенном состоянии
                    .properties(props -> props
                        .stacksTo(1)
                        .rarity(Rarity.UNCOMMON))
                    .withNVGToggle()) // Включаем поддержку переключения
        );
    }

    /**
     * Пример регистрации рюкзака
     */
    @SuppressWarnings("unused")
    private static void registerExampleBackpack() {
        WarbornArmorPartsRegistry.registerPart(
            WarbornArmorPart.builder("example-backpack")
                .backpack(part -> part
                    .registryName("example_backpack")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/backpack.geo.json")
                        .texture("warbornrenewed:textures/parts/backpack.png"))
                    .properties(props -> props
                        .stacksTo(1)
                        .rarity(Rarity.COMMON)))
        );
    }

    /**
     * Пример регистрации тактического жилета
     */
    @SuppressWarnings("unused")
    private static void registerExampleVest() {
        WarbornArmorPartsRegistry.registerPart(
            WarbornArmorPart.builder("example-vest")
                .vest(part -> part
                    .registryName("example_vest")
                    .visuals(spec -> spec
                        .model("warbornrenewed:geo/vest.geo.json")
                        .texture("warbornrenewed:textures/parts/vest.png"))
                    .properties(props -> props
                        .stacksTo(1)
                        .rarity(Rarity.UNCOMMON)))
        );
    }
}
