package ru.liko.warbornrenewed;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import ru.liko.warbornrenewed.config.WarbornConfig;
import ru.liko.warbornrenewed.network.NetworkHandler;
import ru.liko.warbornrenewed.registry.ModAttributes;
import ru.liko.warbornrenewed.registry.ModCreativeTabs;
import ru.liko.warbornrenewed.registry.ModItems;
import ru.liko.warbornrenewed.registry.ModRecipes;
import ru.liko.warbornrenewed.registry.ModSoundEvents;
import ru.liko.warbornrenewed.setup.WarbornArmorSets;

@Mod(Warbornrenewed.MODID)
public class Warbornrenewed {
    public static final String MODID = "warbornrenewed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Warbornrenewed() {
        @SuppressWarnings("removal")
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register config first
        WarbornConfig.register();

        ModAttributes.register(modEventBus);
        ModItems.register(modEventBus);
        ModSoundEvents.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        NetworkHandler.register();
        WarbornArmorSets.bootstrap();

        LOGGER.debug("WarBorn Renewed core initialised");
    }

    @SuppressWarnings("removal")
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
