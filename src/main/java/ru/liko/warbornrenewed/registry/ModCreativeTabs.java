package ru.liko.warbornrenewed.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ru.liko.warbornrenewed.Warbornrenewed;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Warbornrenewed.MODID);

    public static final RegistryObject<CreativeModeTab> ARMOR = TABS.register("armor", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.warbornrenewed.armor"))
            .icon(() -> new ItemStack(Items.NETHERITE_HELMET))
            .displayItems((parameters, output) -> {
                ModItems.armorPieces().forEach(entry -> output.accept(entry.get().getDefaultInstance()));
                output.accept(ModItems.AMMO_POUCH.get());
            })
            .build());

    private ModCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
