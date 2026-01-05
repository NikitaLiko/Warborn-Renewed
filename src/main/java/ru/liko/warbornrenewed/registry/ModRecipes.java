package ru.liko.warbornrenewed.registry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.recipe.DyeArmorRecipe;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Warbornrenewed.MODID);

    public static final RegistryObject<RecipeSerializer<DyeArmorRecipe>> DYE_ARMOR_SERIALIZER =
            RECIPE_SERIALIZERS.register("crafting_special_dyearmor", DyeArmorRecipe.Serializer::new);
}
