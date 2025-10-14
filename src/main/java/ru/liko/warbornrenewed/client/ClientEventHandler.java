package ru.liko.warbornrenewed.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import ru.liko.warbornrenewed.Warbornrenewed;

/**
 * Client-side event handler
 * NOTE: Vision shader management is handled by GameRendererMixin
 * to ensure it applies to all rendering including first-person hands
 */
@Mod.EventBusSubscriber(modid = Warbornrenewed.MODID, value = Dist.CLIENT)
public class ClientEventHandler {
    
    // Vision shader tick is now handled by GameRendererMixin
    // to ensure proper rendering order and coverage of first-person hands
    
}
