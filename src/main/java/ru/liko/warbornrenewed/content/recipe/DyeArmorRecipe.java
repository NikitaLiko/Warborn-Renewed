package ru.liko.warbornrenewed.content.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
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

    public DyeArmorRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack armor = ItemStack.EMPTY;
        List<ItemStack> dyes = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
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
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack armor = ItemStack.EMPTY;
        int[] colors = new int[0];
        int colorCount = 0;
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        int maxColorCount = 0;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof WarbornArmorItem armorItem) {
                    armor = stack.copy();
                    armor.setCount(1);

                    // Get existing color if any
                    if (armorItem.hasCustomColor(stack)) {
                        int existingColor = armorItem.getColor(stack);
                        int r = (existingColor >> 16) & 0xFF;
                        int g = (existingColor >> 8) & 0xFF;
                        int b = existingColor & 0xFF;
                        totalRed += r;
                        totalGreen += g;
                        totalBlue += b;
                        colorCount++;
                        maxColorCount = Math.max(maxColorCount, colorCount);
                    }
                } else if (stack.getItem() instanceof DyeItem dyeItem) {
                    // Get dye color - use getFireworkColor for 1.20.1
                    float[] colorComponents = dyeItem.getDyeColor().getTextureDiffuseColors();
                    int r = (int) (colorComponents[0] * 255.0F);
                    int g = (int) (colorComponents[1] * 255.0F);
                    int b = (int) (colorComponents[2] * 255.0F);
                    totalRed += r;
                    totalGreen += g;
                    totalBlue += b;
                    colorCount++;
                    maxColorCount = Math.max(maxColorCount, colorCount);
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

        // Apply color to armor
        if (armor.getItem() instanceof WarbornArmorItem armorItem) {
            armorItem.setColor(armor, newColor);
        }

        return armor;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < remaining.size(); i++) {
            ItemStack stack = container.getItem(i);
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
        @Override
        public DyeArmorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            CraftingBookCategory category = CraftingBookCategory.CODEC.byName(
                json.get("category").getAsString(),
                CraftingBookCategory.MISC
            );
            return new DyeArmorRecipe(recipeId, category);
        }

        @Override
        public DyeArmorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
            return new DyeArmorRecipe(recipeId, category);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DyeArmorRecipe recipe) {
            buffer.writeEnum(recipe.category());
        }
    }
}
