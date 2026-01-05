package ru.liko.warbornrenewed.content.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.liko.warbornrenewed.Warbornrenewed;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;

/**
 * Рюкзак РЭБ (радиоэлектронной борьбы) - носимый предмет,
 * создающий зону глушения дронов вокруг игрока.
 * Совместим с модом WRBDrones.
 */
public class RebBackpackItem extends Item implements GeoItem {
    
    // Тег NBT для состояния включения РЭБ
    public static final String NBT_REB_ENABLED = "reb_enabled";
    
    // Радиус действия РЭБ рюкзака (меньше чем у стационарного)
    public static final double REB_BACKPACK_RADIUS = 30.0;
    
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final String variantId;
    private final ResourceLocation textureLocation;
    private final ResourceLocation itemTextureLocation;

    public RebBackpackItem(Properties properties, String variantId) {
        super(properties);
        this.variantId = variantId;
        this.textureLocation = Warbornrenewed.id("textures/reb-backpack-" + variantId + ".png");
        this.itemTextureLocation = Warbornrenewed.id("textures/item/reb-backpack-" + variantId + ".png");
    }

    /**
     * Получить идентификатор варианта (камуфляжа)
     */
    public String getVariantId() {
        return variantId;
    }

    /**
     * Получить путь к текстуре модели
     */
    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    /**
     * Получить путь к текстуре иконки предмета
     */
    public ResourceLocation getItemTextureLocation() {
        return itemTextureLocation;
    }

    /**
     * Проверить, включен ли РЭБ
     */
    public static boolean isRebEnabled(ItemStack stack) {
        if (!stack.hasTag()) {
            return true; // По умолчанию включен
        }
        CompoundTag tag = stack.getTag();
        return !tag.contains(NBT_REB_ENABLED) || tag.getBoolean(NBT_REB_ENABLED);
    }

    /**
     * Установить состояние РЭБ
     */
    public static void setRebEnabled(ItemStack stack, boolean enabled) {
        stack.getOrCreateTag().putBoolean(NBT_REB_ENABLED, enabled);
    }

    /**
     * Переключить состояние РЭБ
     */
    public static void toggleReb(ItemStack stack) {
        setRebEnabled(stack, !isRebEnabled(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, 
                                List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        
        // Статус РЭБ
        boolean enabled = isRebEnabled(stack);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.reb.status", 
                        enabled 
                                ? Component.translatable("tooltip.warbornrenewed.reb.enabled")
                                        .withStyle(ChatFormatting.GREEN) 
                                : Component.translatable("tooltip.warbornrenewed.reb.disabled")
                                        .withStyle(ChatFormatting.RED))
                .withStyle(ChatFormatting.GRAY));
        
        // Радиус действия
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.reb.radius", 
                        String.format("%.0f", REB_BACKPACK_RADIUS))
                .withStyle(ChatFormatting.DARK_AQUA));
        
        // Подсказка о совместимости с WRBDrones
        if (ModList.get().isLoaded("wrbdrones")) {
            tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.reb.wrbdrones_compat")
                    .withStyle(ChatFormatting.DARK_PURPLE));
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Пока без анимаций, рюкзак статичный
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        // Проверяем, загружен ли Curios
        if (!ModList.get().isLoaded("curios")) {
            return super.initCapabilities(stack, nbt);
        }

        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> curio = LazyOptional.of(() -> new ICurio() {
                @Override
                public ItemStack getStack() {
                    return stack;
                }

                @Override
                public boolean canEquip(SlotContext slotContext) {
                    String slotId = slotContext.identifier();
                    // Нормализуем идентификатор (убираем namespace)
                    if (slotId.contains(":")) {
                        slotId = slotId.substring(slotId.indexOf(":") + 1);
                    }
                    // Используем встроенный слот Curios "back" (спина)
                    return "back".equals(slotId);
                }

                @Override
                public boolean canEquipFromUse(SlotContext slotContext) {
                    return canEquip(slotContext);
                }

                @Override
                public void curioTick(SlotContext slotContext) {
                    // Тик для обновления зоны РЭБ
                    // Логика глушения дронов реализуется через миксин в WRBDrones
                    // или через event handler
                }

                @Override
                public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                    // При надевании рюкзака
                }

                @Override
                public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                    // При снятии рюкзака
                }
            });

            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, 
                                                              @Nullable net.minecraft.core.Direction side) {
                if (ModList.get().isLoaded("curios") && cap == CuriosCapability.ITEM) {
                    @SuppressWarnings("unchecked")
                    LazyOptional<T> result = (LazyOptional<T>) curio;
                    return result;
                }
                return LazyOptional.empty();
            }
        };
    }
}

