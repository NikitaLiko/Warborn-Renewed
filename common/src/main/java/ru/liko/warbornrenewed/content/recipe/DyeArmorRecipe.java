package ru.liko.warbornrenewed.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom recipe for dyeing WarbornArmorItem pieces
 * Similar to vanilla leather armor dyeing
 */
public class DyeArmorRecipe extends CustomRecipe {

    public DyeArmorRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        ItemStack armor = ItemStack.EMPTY;
        List<ItemStack> dyes = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof WarbornArmorItem) {
                    if (!armor.isEmpty()) {
                        return false; // Only one armor piece allowed
                    }
                    armor = stack;
                } else if (stack.getItem() instanceof DyeItem) {
                    dyes.add(stack);
                } else {
                    return false; // Invalid item
                }
            }
        }

        return !armor.isEmpty() && !dyes.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack armor = ItemStack.EMPTY;
        int colorCount = 0;
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof WarbornArmorItem armorItem) {
                    armor = stack.copy();
                    armor.setCount(1);
                    // Color system not yet implemented for WarbornArmorItem in 1.21.1
                } else if (stack.getItem() instanceof DyeItem dyeItem) {
                    // Get dye color - use getMapColor for 1.21.1
                    DyeColor dyeColor = dyeItem.getDyeColor();
                    int color = dyeColor.getMapColor().col;
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = color & 0xFF;
                    totalRed += r;
                    totalGreen += g;
                    totalBlue += b;
                    colorCount++;
                }
            }
        }

        if (armor.isEmpty() || colorCount == 0) {
            return ItemStack.EMPTY;
        }

        // Calculate average color
        int avgRed = totalRed / colorCount;
        int avgGreen = totalGreen / colorCount;
        int avgBlue = totalBlue / colorCount;
        int newColor = (avgRed << 16) | (avgGreen << 8) | avgBlue;

        // Apply color to armor - TODO: Implement with DataComponents in 1.21.1
        // For now, return the armor without color modification
        return armor;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < remaining.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.hasCraftingRemainingItem()) {
                remaining.set(i, stack.getCraftingRemainingItem());
            }
        }

        return remaining;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ru.liko.warbornrenewed.registry.ModRecipes.DYE_ARMOR_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<DyeArmorRecipe> {
        private static final MapCodec<DyeArmorRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC)
                        .forGetter(CustomRecipe::category))
                .apply(instance, DyeArmorRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, DyeArmorRecipe> STREAM_CODEC = StreamCodec.of(
                (buffer, recipe) -> buffer.writeEnum(recipe.category()),
                buffer -> new DyeArmorRecipe(buffer.readEnum(CraftingBookCategory.class)));

        @Override
        public MapCodec<DyeArmorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DyeArmorRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
