package ru.liko.warbornrenewed.packs;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import ru.liko.warbornrenewed.platform.Services;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.model.GeoModel;

import java.util.List;
import java.util.function.Consumer;

public class CustomPackArmorItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CustomPackArmorItem(ArmorMaterial material, Type type, Properties properties) {
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
                    renderer = new PackRenderer();
                }
                renderer.prepForRender(livingEntity, stack, slot, defaultModel);
                return renderer;
            }
        });
    }

    private static class PackRenderer extends GeoArmorRenderer<CustomPackArmorItem> {
        public PackRenderer() {
            super(new PackModel());
            ((PackModel) this.getGeoModel()).renderer = this;
        }

        public ItemStack getCurrentItem() {
            return this.currentStack;
        }
    }

    private static class PackModel extends GeoModel<CustomPackArmorItem> {
        public PackRenderer renderer;

        @Override
        public ResourceLocation getModelResource(CustomPackArmorItem animatable) {
            ItemStack stack = renderer != null ? renderer.getCurrentItem() : null;
            if (stack != null) {
                String packId = Services.ITEM_DATA.getArmorPackId(stack);
                if (packId != null && !packId.isEmpty()) {
                    ArmorDef def = WarbornPackManager.getArmorDef(packId);
                    if (def != null && def.getModelId() != null) {
                        return new ResourceLocation(def.getModelId() + ".geo.json");
                    }
                }
            }
            return new ResourceLocation("warbornrenewed", "geo/armor/default.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(CustomPackArmorItem animatable) {
            ItemStack stack = renderer != null ? renderer.getCurrentItem() : null;
            if (stack != null) {
                String packId = Services.ITEM_DATA.getArmorPackId(stack);
                if (packId != null && !packId.isEmpty()) {
                    ArmorDef def = WarbornPackManager.getArmorDef(packId);
                    if (def != null && def.getModelId() != null) {
                        return new ResourceLocation(def.getModelId() + ".png");
                    }
                }
            }
            return new ResourceLocation("warbornrenewed", "textures/armor/default.png");
        }

        @Override
        public ResourceLocation getAnimationResource(CustomPackArmorItem animatable) {
            ItemStack stack = renderer != null ? renderer.getCurrentItem() : null;
            if (stack != null) {
                String packId = Services.ITEM_DATA.getArmorPackId(stack);
                if (packId != null && !packId.isEmpty()) {
                    ArmorDef def = WarbornPackManager.getArmorDef(packId);
                    if (def != null && def.getModelId() != null) {
                        return new ResourceLocation(def.getModelId() + ".animation.json");
                    }
                }
            }
            return null;
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            ArmorDef def = WarbornPackManager.getArmorDef(id);
            if (def != null) {
                String translationKey = "item.warbornrenewed.pack." + id.replace(":", ".");
                return Component.translatableWithFallback(translationKey, def.getDisplayName("en_us"));
            }
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @javax.annotation.Nullable net.minecraft.world.level.Level level,
            List<Component> tooltipComponents, TooltipFlag isAdvanced) {
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

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
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
