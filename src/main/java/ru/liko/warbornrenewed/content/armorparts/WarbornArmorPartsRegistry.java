package ru.liko.warbornrenewed.content.armorparts;

import ru.liko.warbornrenewed.registry.ModItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registry for armor parts (Curios items).
 * Similar to WarbornArmorRegistry but for parts.
 */
public final class WarbornArmorPartsRegistry {
    private static final List<WarbornArmorPart> REGISTERED_PARTS = new ArrayList<>();

    private WarbornArmorPartsRegistry() {
    }

    public static WarbornArmorPart registerPart(WarbornArmorPart.Builder builder) {
        WarbornArmorPart part = builder.register(ModItems.ITEMS);
        REGISTERED_PARTS.add(part);
        return part;
    }

    public static List<WarbornArmorPart> registeredParts() {
        return Collections.unmodifiableList(REGISTERED_PARTS);
    }
}
