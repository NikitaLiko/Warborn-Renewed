package ru.liko.warbornrenewed.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.sound.WarbornSoundPlayer;

import java.util.function.Supplier;

/**
 * Packet to toggle the vision attachment state (NVG/Thermal) for supported helmets.
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
                // Check helmet slot for supported vision capability
                ItemStack helmet = player.getInventory().getArmor(3); // Helmet slot
                
                if (!helmet.isEmpty() && helmet.getItem() instanceof WarbornArmorItem armorItem) {
                    boolean supportsNVG = armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG);
                    boolean supportsThermal = armorItem.hasVisionCapability(WarbornArmorItem.TAG_THERMAL);

                    if (supportsNVG || supportsThermal) {
                        // Update vision state on server
                        WarbornArmorItem.setNVGDown(helmet, nvgDown);

                        // Broadcast toggle sound to nearby players
                        WarbornSoundPlayer.playVisionToggle(player, helmet, nvgDown);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
