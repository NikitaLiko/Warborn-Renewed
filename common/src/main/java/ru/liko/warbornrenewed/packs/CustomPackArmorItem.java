package ru.liko.warbornrenewed.packs;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import ru.liko.warbornrenewed.platform.Services;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.model.GeoModel;

import java.util.List;
import java.util.function.Consumer;

public class CustomPackArmorItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CustomPackArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Here you can add custom animation controllers based on pack logic if needed
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<CustomPackArmorItem> renderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack stack,
                    EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                if (renderer == null) {
                    renderer = new GeoArmorRenderer<>(new GeoModel<CustomPackArmorItem>() {
                        @Override
                        public ResourceLocation getModelResource(CustomPackArmorItem animatable) {
                            String packId = Services.ITEM_DATA.getArmorPackId(stack);
                            if (packId != null && !packId.isEmpty()) {
                                ArmorDef def = WarbornPackManager.getArmorDef(packId);
                                if (def != null && def.getModelId() != null) {
                                    return ResourceLocation.parse(def.getModelId() + ".geo.json");
                                }
                            }
                            // Default missing model fallback
                            return ResourceLocation.parse("warbornrenewed:geo/armor/default.geo.json");
                        }

                        @Override
                        public ResourceLocation getTextureResource(CustomPackArmorItem animatable) {
                            String packId = Services.ITEM_DATA.getArmorPackId(stack);
                            if (packId != null && !packId.isEmpty()) {
                                ArmorDef def = WarbornPackManager.getArmorDef(packId);
                                if (def != null && def.getModelId() != null) {
                                    return ResourceLocation.parse(def.getModelId() + ".png");
                                }
                            }
                            return ResourceLocation.parse("warbornrenewed:textures/armor/default.png");
                        }

                        @Override
                        public ResourceLocation getAnimationResource(CustomPackArmorItem animatable) {
                            String packId = Services.ITEM_DATA.getArmorPackId(stack);
                            if (packId != null && !packId.isEmpty()) {
                                ArmorDef def = WarbornPackManager.getArmorDef(packId);
                                if (def != null && def.getModelId() != null) {
                                    return ResourceLocation.parse(def.getModelId() + ".animation.json");
                                }
                            }
                            return null;
                        }
                    });
                }
                renderer.prepForRender(livingEntity, stack, slot, defaultModel);
                return renderer;
            }
        });
    }

    @Override
    public Component getName(ItemStack stack) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            ArmorDef def = WarbornPackManager.getArmorDef(id);
            if (def != null) {
                String locale = getClientLocale();
                String displayName = WarbornPackManager.getDisplayName(id, locale);
                return Component.literal(displayName);
            }
        }
        return super.getName(stack);
    }

    /**
     * Получить текущую локаль клиента.
     * На сервере возвращает "en_us" как fallback.
     */
    private static String getClientLocale() {
        try {
            return net.minecraft.client.Minecraft.getInstance()
                    .getLanguageManager().getSelected();
        } catch (Exception e) {
            return "en_us";
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag isAdvanced) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            ArmorDef def = WarbornPackManager.getArmorDef(id);
            if (def != null) {
                tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.pack_id", id)
                        .withStyle(ChatFormatting.DARK_GRAY));
                tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.defense")
                        .append(Component.literal(": " + def.getDefense())).withStyle(ChatFormatting.BLUE));
                tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.toughness")
                        .append(Component.literal(": " + def.getToughness())).withStyle(ChatFormatting.BLUE));
            }
        }

        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
    }

    // Custom stats getters intended to be used by mixins / modifiers events
    public int getDefense(ItemStack stack) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            ArmorDef def = WarbornPackManager.getArmorDef(id);
            if (def != null) {
                return def.getDefense();
            }
        }
        return this.getDefense();
    }

    public float getToughness(ItemStack stack) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            ArmorDef def = WarbornPackManager.getArmorDef(id);
            if (def != null) {
                return def.getToughness();
            }
        }
        return this.getToughness();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            ArmorDef def = WarbornPackManager.getArmorDef(id);
            if (def != null && def.getDurability() > 0) {
                return def.getDurability();
            }
        }
        return super.getMaxDamage(stack);
    }
}
