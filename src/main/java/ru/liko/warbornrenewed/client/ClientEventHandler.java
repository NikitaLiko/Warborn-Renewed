package ru.liko.warbornrenewed.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.client.shader.VisionShaderManager;

/**
 * Client-side event handler for vision shader integration
 */
@Mod.EventBusSubscriber(modid = Warbornrenewed.MODID, value = Dist.CLIENT)
public class ClientEventHandler {
    
    /**
     * Called every client tick to update vision shaders
     */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null && mc.player != null) {
                VisionShaderManager.tick(mc);
            }
        }
    }
}
