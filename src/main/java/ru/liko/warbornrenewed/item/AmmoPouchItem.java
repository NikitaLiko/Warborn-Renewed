package ru.liko.warbornrenewed.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple ammo pouch that stores ammo items from SuperbWarfare or TACZ (heuristic by registry name/namespace).
 * Behavior is similar to the Minecraft bundle principle: right-click to collect ammo from inventory, shift-right-click to dump back.
 *
 * No hard dependencies on those mods. Contents are stored in NBT under "Items" list.
 */
public class AmmoPouchItem extends Item {
    private static final String NBT_ITEMS = "Items";
    private static final int MAX_STACKS = 8; // simple cap: up to 8 stacks inside

    public AmmoPouchItem(Properties props) {
        super(props);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack pouch = player.getItemInHand(hand);
        if (level.isClientSide) return InteractionResultHolder.success(pouch);

        if (player.isShiftKeyDown()) {
            // Dump all contents back to player inventory
            List<ItemStack> items = getItems(pouch);
            boolean any = false;
            for (ItemStack st : items) {
                if (!st.isEmpty()) {
                    any = true;
                    if (!player.addItem(st.copy())) {
                        player.drop(st.copy(), true);
                    }
                }
            }
            if (any) {
                clear(pouch);
                player.displayClientMessage(Component.translatable("tooltip.warbornrenewed.pouch_dump").withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResultHolder.sidedSuccess(pouch, false);
        } else {
            // Collect compatible ammo from player's inventory into the pouch
            boolean moved = collectFromInventory(player, pouch);
            if (moved) {
                player.displayClientMessage(Component.translatable("tooltip.warbornrenewed.pouch_collect").withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResultHolder.sidedSuccess(pouch, false);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        List<ItemStack> items = getItems(stack);
        int countStacks = 0;
        int totalItems = 0;
        for (ItemStack st : items) {
            if (!st.isEmpty()) {
                countStacks++;
                totalItems += st.getCount();
            }
        }
        tooltip.add(Component.translatable("tooltip.warbornrenewed.pouch_info", String.valueOf(countStacks), String.valueOf(totalItems))
                .withStyle(ChatFormatting.DARK_GREEN));
        // Show preview of up to first 3 stacks
        int shown = 0;
        for (ItemStack st : items) {
            if (st.isEmpty()) continue;
            tooltip.add(Component.literal(" • " + st.getHoverName().getString() + " x" + st.getCount()).withStyle(ChatFormatting.GRAY));
            if (++shown >= 3) break;
        }
        tooltip.add(Component.translatable("tooltip.warbornrenewed.pouch_hint").withStyle(ChatFormatting.BLUE));
    }

    // --- Storage helpers ---

    public static List<ItemStack> getItems(ItemStack pouch) {
        List<ItemStack> result = new ArrayList<>();
        CompoundTag tag = pouch.getOrCreateTag();
        ListTag list = tag.getList(NBT_ITEMS, Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag it = list.getCompound(i);
            ItemStack st = ItemStack.of(it);
            if (!st.isEmpty()) result.add(st);
        }
        return result;
    }

    public static void setItems(ItemStack pouch, List<ItemStack> stacks) {
        ListTag list = new ListTag();
        for (ItemStack st : stacks) {
            if (st.isEmpty()) continue;
            CompoundTag it = new CompoundTag();
            st.save(it);
            list.add(it);
        }
        pouch.getOrCreateTag().put(NBT_ITEMS, list);
    }

    public static void clear(ItemStack pouch) {
        pouch.getOrCreateTag().put(NBT_ITEMS, new ListTag());
    }

    private static boolean collectFromInventory(Player player, ItemStack pouch) {
        List<ItemStack> current = getItems(pouch);
        boolean changed = false;

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack inv = player.getInventory().getItem(slot);
            if (inv.isEmpty()) continue;
            if (!isAmmo(inv)) continue;

            // Try to merge into existing stacks first
            int remaining = inv.getCount();
            for (ItemStack inside : current) {
                if (remaining <= 0) break;
                if (ItemStack.isSameItemSameTags(inside, inv)) {
                    int canMove = Math.min(remaining, inside.getMaxStackSize() - inside.getCount());
                    if (canMove > 0) {
                        inside.grow(canMove);
                        remaining -= canMove;
                        changed = true;
                    }
                }
            }
            // Then add new stacks if room
            while (remaining > 0 && current.size() < MAX_STACKS) {
                int move = Math.min(remaining, inv.getMaxStackSize());
                ItemStack copy = inv.copy();
                copy.setCount(move);
                current.add(copy);
                remaining -= move;
                changed = true;
            }
            if (changed) {
                inv.shrink(inv.getCount() - remaining);
                player.getInventory().setItem(slot, remaining > 0 ? inv : ItemStack.EMPTY);
            }
            if (current.size() >= MAX_STACKS) break;
        }

        if (changed) setItems(pouch, current);
        return changed;
    }

    public static boolean extractOneStackToInventory(Player player, ItemStack pouch) {
        List<ItemStack> current = getItems(pouch);
        if (current.isEmpty()) return false;
        ItemStack first = current.remove(0);
        boolean added = player.addItem(first);
        if (!added) {
            player.drop(first, true);
        }
        setItems(pouch, current);
        return true;
    }

    // Heuristic: accept items from superbwarfare or tacz namespaces, and any item whose registry path hints ammo/mag/cartridge/round
    public static boolean isAmmo(ItemStack stack) {
        if (stack.isEmpty()) return false;
        var key = stack.getItemHolder().unwrapKey();
        if (key.isPresent()) {
            String ns = key.get().location().getNamespace();
            String path = key.get().location().getPath();
            if ("superbwarfare".equals(ns) || "tacz".equals(ns)) {
                return path.contains("ammo") || path.contains("mag") || path.contains("magazine") || path.contains("cartridge") || path.contains("round");
            }
            // Also allow generic ammo-like names for broader compatibility
            return path.contains("ammo") || path.contains("mag") || path.contains("cartridge") || path.contains("round");
        }
        return false;
    }
}
