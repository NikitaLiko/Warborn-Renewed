package ru.liko.warbornrenewed.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import ru.liko.warbornrenewed.Warbornrenewed;

/**
 * Network handler for client-server communication
 */
public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        ResourceLocation.fromNamespaceAndPath(Warbornrenewed.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(NVGTogglePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
            .decoder(NVGTogglePacket::new)
            .encoder(NVGTogglePacket::toBytes)
            .consumerMainThread(NVGTogglePacket::handle)
            .add();

        INSTANCE.messageBuilder(RebBackpackTogglePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
            .decoder(RebBackpackTogglePacket::new)
            .encoder(RebBackpackTogglePacket::toBytes)
            .consumerMainThread(RebBackpackTogglePacket::handle)
            .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
