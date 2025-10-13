package ru.liko.warbornrenewed.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;

import java.util.function.Supplier;

/**
 * Packet to toggle NVG state (up/down) for helmet-integrated NVG
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
                // Check helmet slot for NVG capability
                ItemStack helmet = player.getInventory().getArmor(3); // Helmet slot
                
                if (!helmet.isEmpty() && helmet.getItem() instanceof WarbornArmorItem armorItem) {
                    // Check if helmet has NVG capability
                    if (armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG)) {
                        // Update NVG state on server
                        WarbornArmorItem.setNVGDown(helmet, nvgDown);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
