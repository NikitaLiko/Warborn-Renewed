package ru.liko.warbornrenewed;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import ru.liko.warbornrenewed.packs.WarbornPackManager;

@Mod(WarbornRenewed.MODID)
public class WarbornRenewed {
    public static final String MODID = "warbornrenewed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WarbornRenewed() {
        // Initialization code for Forge goes here
        // e.g. registering to IEventBus: FMLJavaModLoadingContext.get().getModEventBus()
        MinecraftForge.EVENT_BUS.register(this);

        // Load custom packs from warbornrenewed/packs directory
        WarbornPackManager.loadPacks();
    }
    
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
