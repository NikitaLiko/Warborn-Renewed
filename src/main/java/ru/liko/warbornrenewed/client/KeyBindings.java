package ru.liko.warbornrenewed.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import ru.liko.warbornrenewed.Warbornrenewed;

/**
 * Client-side key bindings for WarBorn Renewed
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Warbornrenewed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyBindings {
    
    public static final String CATEGORY = "key.categories." + Warbornrenewed.MODID;
    
    public static final KeyMapping NVG_TOGGLE = new KeyMapping(
        "key." + Warbornrenewed.MODID + ".nvg_toggle",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_N, // Default: N key
        CATEGORY
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(NVG_TOGGLE);
    }
}
