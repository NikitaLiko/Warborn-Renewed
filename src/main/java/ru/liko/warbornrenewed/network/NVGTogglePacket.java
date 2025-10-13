package ru.liko.warbornrenewed.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import ru.liko.warbornrenewed.content.armorparts.WarbornArmorPartItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.function.Supplier;

/**
 * Packet to toggle NVG state (up/down)
 */
public class NVGTogglePacket {
    private final boolean nvgDown;

    public NVGTogglePacket(boolean nvgDown) {
        this.nvgDown = nvgDown;
    }

    public NVGTogglePacket(FriendlyByteBuf buf) {
        this.nvgDown = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(nvgDown);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
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
                    
                    // Update NVG state on server
                    nvgStack.getOrCreateTag().putBoolean("nvg_down", nvgDown);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
