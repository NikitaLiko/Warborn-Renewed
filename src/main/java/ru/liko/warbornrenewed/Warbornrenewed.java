package ru.liko.warbornrenewed;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import ru.liko.warbornrenewed.network.NetworkHandler;
import ru.liko.warbornrenewed.registry.ModAttributes;
import ru.liko.warbornrenewed.registry.ModCreativeTabs;
import ru.liko.warbornrenewed.registry.ModItems;
import ru.liko.warbornrenewed.setup.WarbornArmorSets;
import ru.liko.warbornrenewed.setup.WarbornArmorPartsSets;

@Mod(Warbornrenewed.MODID)
public class Warbornrenewed {
    public static final String MODID = "warbornrenewed";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Warbornrenewed() {
        @SuppressWarnings("removal")
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModAttributes.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        NetworkHandler.register();
        WarbornArmorSets.bootstrap();
        WarbornArmorPartsSets.bootstrap();

        LOGGER.debug("WarBorn Renewed core initialised");
    }

    @SuppressWarnings("removal")
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
