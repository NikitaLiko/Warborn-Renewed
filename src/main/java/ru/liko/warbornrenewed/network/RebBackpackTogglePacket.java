package ru.liko.warbornrenewed.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

/**
 * Клиент -> Сервер: переключение состояния РЭБ на надетом рюкзаке.
 */
public class RebBackpackTogglePacket {
    private final boolean enabled;

    public RebBackpackTogglePacket(boolean enabled) {
        this.enabled = enabled;
    }

    public RebBackpackTogglePacket(net.minecraft.network.FriendlyByteBuf buf) {
        this.enabled = buf.readBoolean();
    }

    public void toBytes(net.minecraft.network.FriendlyByteBuf buf) {
        buf.writeBoolean(enabled);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var sender = ctx.get().getSender();
            if (sender == null) return;

            if (!ModList.get().isLoaded("curios")) {
                return;
            }

            // Ищем надетый рюкзак РЭБ в Curios backpack
            ItemStack backpack = CuriosApi.getCuriosInventory(sender)
                    .map(inv -> inv.getStacksHandler("back"))
                    .filter(java.util.Optional::isPresent)
                    .map(java.util.Optional::get)
                    .map(handler -> handler.getStacks())
                    .map(stacks -> {
                        for (int i = 0; i < stacks.getSlots(); i++) {
                            ItemStack stack = stacks.getStackInSlot(i);
                            if (!stack.isEmpty() && stack.getItem() instanceof RebBackpackItem) {
                                return stack;
                            }
                        }
                        return ItemStack.EMPTY;
                    })
                    .orElse(ItemStack.EMPTY);

            if (backpack.isEmpty() || !(backpack.getItem() instanceof RebBackpackItem)) {
                return;
            }

            RebBackpackItem.setRebEnabled(backpack, enabled);

            // Проигрываем звуки WRBDrones (если мод установлен)
            playWrbDronesToggleSound(sender, enabled);
        });

        ctx.get().setPacketHandled(true);
    }

    private static void playWrbDronesToggleSound(net.minecraft.world.entity.player.Player player, boolean enabled) {
        if (!ModList.get().isLoaded("wrbdrones")) {
            return;
        }

        ResourceLocation soundId = enabled
                ? ResourceLocation.fromNamespaceAndPath("wrbdrones", "reb_toggle_on")
                : ResourceLocation.fromNamespaceAndPath("wrbdrones", "reb_toggle_off");

        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(soundId);
        if (sound == null) {
            return;
        }

        player.level().playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                sound,
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );
    }
}


