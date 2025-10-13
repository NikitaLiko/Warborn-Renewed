package ru.liko.warbornrenewed.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.armorparts.WarbornArmorPartItem;
import ru.liko.warbornrenewed.network.NVGTogglePacket;
import ru.liko.warbornrenewed.network.NetworkHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

/**
 * Client-side handler for NVG toggle key press
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Warbornrenewed.MODID, value = Dist.CLIENT)
public class NVGToggleHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        
        if (player == null) {
            return;
        }

        // Check if NVG toggle key was pressed
        if (KeyBindings.NVG_TOGGLE.consumeClick()) {
            handleNVGToggle(player);
        }
    }

    private static void handleNVGToggle(Player player) {
        // Find equipped NVG with toggle support
        List<SlotResult> nvgSlots = CuriosApi.getCuriosInventory(player)
            .map(handler -> handler.findCurios(stack -> {
                if (stack.getItem() instanceof WarbornArmorPartItem partItem) {
                    return partItem.hasNVGToggle();
                }
                return false;
            }))
            .orElse(List.of());

        if (!nvgSlots.isEmpty()) {
            SlotResult result = nvgSlots.get(0);
            ItemStack nvgStack = result.stack();
            
            // Toggle NVG state
            boolean currentState = nvgStack.getOrCreateTag().getBoolean("nvg_down");
            nvgStack.getOrCreateTag().putBoolean("nvg_down", !currentState);
            
            // Send packet to server to sync state
            NetworkHandler.sendToServer(new NVGTogglePacket(!currentState));
            
            // Play sound
            player.playSound(
                net.minecraft.sounds.SoundEvents.ARMOR_EQUIP_GENERIC,
                0.5f,
                currentState ? 1.2f : 0.8f
            );
        }
    }
}
