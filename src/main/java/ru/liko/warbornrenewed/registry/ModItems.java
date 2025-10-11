package ru.liko.warbornrenewed.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.item.AmmoPouchItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Warbornrenewed.MODID);
    private static final List<RegistryObject<? extends Item>> ARMOR_PIECES = new ArrayList<>();

    public static final RegistryObject<Item> AMMO_POUCH = ITEMS.register("ammo_pouch", () -> new AmmoPouchItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    private ModItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void trackArmorPiece(RegistryObject<? extends Item> item) {
        ARMOR_PIECES.add(item);
    }

    public static List<RegistryObject<? extends Item>> armorPieces() {
        return Collections.unmodifiableList(ARMOR_PIECES);
    }
}
