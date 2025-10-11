package ru.liko.warbornrenewed.content.pouch;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.item.AmmoPouchItem;

/**
 * Lightweight compatibility: when the player is holding a gun from TACZ/SBW and has no ammo in inventory,
 * automatically move one stored ammo stack from any pouch in inventory to the player's inventory.
 * This avoids hard dependencies and lets reload logic of those mods find ammo normally.
 */
@Mod.EventBusSubscriber(modid = Warbornrenewed.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class AmmoPouchRefillHandler {
    private AmmoPouchRefillHandler() {}

    private static boolean isLikelyGun(ItemStack stack) {
        if (stack.isEmpty()) return false;
        var key = stack.getItemHolder().unwrapKey();
        if (key.isPresent()) {
            String ns = key.get().location().getNamespace();
            String path = key.get().location().getPath();
            if ("superbwarfare".equals(ns) || "tacz".equals(ns)) {
                return path.contains("gun") || path.contains("weapon") || path.contains("rifle") || path.contains("pistol");
            }
        }
        return false;
    }

    private static boolean hasAmmoInInventory(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (AmmoPouchItem.isAmmo(player.getInventory().getItem(i))) return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide) return;

        // Throttle: run roughly every 10 ticks
        if (player.tickCount % 10 != 0) return;

        ItemStack main = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack off = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!isLikelyGun(main) && !isLikelyGun(off)) return;

        if (hasAmmoInInventory(player)) return; // already has ammo accessible

        // Find any pouch and move one stack out
        boolean moved = false;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack st = player.getInventory().getItem(slot);
            if (st.getItem() instanceof AmmoPouchItem) {
                if (AmmoPouchItem.extractOneStackToInventory(player, st)) {
                    moved = true;
                    break;
                }
            }
        }
        if (!moved) {
            // Try from equipped vest (internal pouch)
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            if (!chest.isEmpty() && chest.getItem() instanceof WarbornArmorItem armor && armor.getType() == net.minecraft.world.item.ArmorItem.Type.CHESTPLATE) {
                java.util.List<ItemStack> current = AmmoPouchItem.getItems(chest);
                if (!current.isEmpty()) {
                    ItemStack first = current.remove(0);
                    boolean added = player.addItem(first);
                    if (!added) player.drop(first, true);
                    AmmoPouchItem.setItems(chest, current);
                }
            }
        }
    }
}
