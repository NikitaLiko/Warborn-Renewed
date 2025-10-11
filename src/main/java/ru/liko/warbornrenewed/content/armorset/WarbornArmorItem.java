package ru.liko.warbornrenewed.content.armorset;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.item.AmmoPouchItem;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class WarbornArmorItem extends ArmorItem implements GeoItem {
    private final String itemId;
    private final ArmorVisualSpec visuals;
    private final ArmorBonesSpec bones;
    private final List<ArmorAttributeSpec> attributes;
    private final int internalPouchCapacity;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WarbornArmorItem(String itemId, ArmorMaterial material, Type type, Properties properties, ArmorVisualSpec visuals, ArmorBonesSpec bones, List<ArmorAttributeSpec> attributes, int internalPouchCapacity) {
        super(material, type, properties);
        this.itemId = Objects.requireNonNull(itemId, "itemId");
        this.visuals = Objects.requireNonNull(visuals, "visuals");
        this.bones = Objects.requireNonNull(bones, "bones");
        this.attributes = List.copyOf(attributes);
        this.internalPouchCapacity = Math.max(0, internalPouchCapacity);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private WarbornArmorRenderer renderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                if (renderer == null) {
                    renderer = new WarbornArmorRenderer(visuals, bones);
                }
                renderer.prepForRender(livingEntity, stack, slot, defaultModel);
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // No-op: add controllers here when you introduce animation files
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = super.getDefaultAttributeModifiers(slot);
        if (!attributes.isEmpty() && slot == getType().getSlot()) {
            modifiers = HashMultimap.create(modifiers);
            for (int i = 0; i < attributes.size(); i++) {
                ArmorAttributeSpec spec = attributes.get(i);
                Attribute attribute = spec.attribute();
                UUID uuid = UUID.nameUUIDFromBytes((itemId + "/" + slot.getName() + "#" + i).getBytes(StandardCharsets.UTF_8));
                modifiers.put(attribute, spec.createModifier(uuid, stack, Warbornrenewed.MODID + ":" + itemId, slot));
            }
        }
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

        // Материал брони
        String materialName = getMaterial().toString().toLowerCase();
        String materialKey = "material.warbornrenewed." + materialName;
        Component materialDisplayName = Component.translatable(materialKey);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.material", materialDisplayName)
                .withStyle(ChatFormatting.GRAY));

        // Атрибуты брони — читаем модификаторы текущего предмета в соответствующем слоте
        EquipmentSlot slot = getType().getSlot();
        Multimap<Attribute, AttributeModifier> mods = getAttributeModifiers(slot, stack);

        // Собираем значения по нужным атрибутам
        double bulletRes = 0.0;
        int protClass = 0;
        double thickness = 0.0;
        double blastMult = 1.0; // значение по умолчанию — 1.0 (без изменений)
        double moveMod = 0.0;

        Attribute bulletAttr = ru.liko.warbornrenewed.registry.ModAttributes.BULLET_RESISTANCE.get();
        Attribute protAttr = ru.liko.warbornrenewed.registry.ModAttributes.PROTECTION_CLASS.get();
        Attribute thickAttr = ru.liko.warbornrenewed.registry.ModAttributes.EFFECTIVE_THICKNESS.get();
        Attribute blastAttr = ru.liko.warbornrenewed.registry.ModAttributes.BLAST_DAMAGE_MULTIPLIER.get();
        Attribute moveAttr = ru.liko.warbornrenewed.registry.ModAttributes.ARMOR_MOVEMENT_SPEED.get();

        if (mods != null && !mods.isEmpty()) {
            // Суммируем модификаторы для каждого атрибута (ADD/MULTIPLY_BASE и т.п.)
            for (var entry : mods.entries()) {
                Attribute attr = entry.getKey();
                AttributeModifier mod = entry.getValue();
                double amt = mod.getAmount();

                if (attr == bulletAttr) {
                    if (mod.getOperation() == AttributeModifier.Operation.ADDITION) {
                        bulletRes += amt;
                    } else if (mod.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                        bulletRes *= (1.0 + amt);
                    }
                } else if (attr == protAttr) {
                    if (mod.getOperation() == AttributeModifier.Operation.ADDITION) {
                        protClass += (int) Math.round(amt);
                    }
                } else if (attr == thickAttr) {
                    if (mod.getOperation() == AttributeModifier.Operation.ADDITION) {
                        thickness += amt;
                    }
                } else if (attr == blastAttr) {
                    // В ArmorAttributeSpec мы записываем multiplier - 1.0 при MULTIPLY_BASE
                    if (mod.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                        blastMult *= (1.0 + amt);
                    } else if (mod.getOperation() == AttributeModifier.Operation.ADDITION) {
                        blastMult += amt;
                    }
                } else if (attr == moveAttr) {
                    if (mod.getOperation() == AttributeModifier.Operation.ADDITION) {
                        moveMod += amt;
                    } else if (mod.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                        moveMod *= (1.0 + amt);
                    }
                }
            }
        }

        // Ограничим значения в разумных пределах
        bulletRes = Math.max(0.0, Math.min(1.0, bulletRes));
        protClass = Math.max(0, Math.min(6, protClass));
        thickness = Math.max(0.0, thickness);

        // Форматирование и добавление подсказок
        // Защита от пуль: проценты
        int bulletPercent = (int) Math.round(bulletRes * 100.0);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.bullet_resistance", String.valueOf(bulletPercent))
                .withStyle(ChatFormatting.DARK_GREEN));

        // Класс защиты: локализованный ключ уровня
        String pcKey = "protection_class.warbornrenewed." + protClass;
        Component pcText = Component.translatable(pcKey);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.protection_class", pcText)
                .withStyle(ChatFormatting.BLUE));

        // Толщина брони: мм, максимум 1 знак после запятой
        String thicknessStr = thickness % 1.0 == 0.0 ? String.format("%.0f", thickness) : String.format("%.1f", thickness);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.effective_thickness", thicknessStr)
                .withStyle(ChatFormatting.GRAY));

        // Защита от взрывов: конвертация множителя в проценты уменьшения урона
        // Пример: 1.0 = 0%, 0.8 = 20% защиты, 1.2 = -20% (увеличение урона)
        int blastPercent = (int) Math.round((1.0 - blastMult) * 100.0);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.blast_resistance", String.valueOf(blastPercent))
                .withStyle(blastPercent >= 0 ? ChatFormatting.DARK_GREEN : ChatFormatting.RED));

        // Скорость: проценты (от -50 до +20 по дизайну атрибута)
        int speedPercent = (int) Math.round(moveMod * 100.0);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.movement_speed", String.valueOf(speedPercent))
                .withStyle(speedPercent >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED));

        // Internal ammo pouch info (if present)
        if (internalPouchCapacity > 0 && getType() == Type.CHESTPLATE) {
            List<net.minecraft.world.item.ItemStack> items = ru.liko.warbornrenewed.item.AmmoPouchItem.getItems(stack);
            int countStacks = 0;
            int totalItems = 0;
            for (net.minecraft.world.item.ItemStack st : items) {
                if (!st.isEmpty()) {
                    countStacks++;
                    totalItems += st.getCount();
                }
            }
            tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.pouch_info", String.valueOf(countStacks), String.valueOf(totalItems))
                    .withStyle(ChatFormatting.DARK_GREEN));
            int shown = 0;
            for (net.minecraft.world.item.ItemStack st : items) {
                if (st.isEmpty()) continue;
                tooltipComponents.add(Component.literal(" • " + st.getHoverName().getString() + " x" + st.getCount()).withStyle(ChatFormatting.GRAY));
                if (++shown >= 3) break;
            }
            tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.pouch_hint").withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack self = player.getItemInHand(hand);
        if (internalPouchCapacity <= 0 || getType() != Type.CHESTPLATE) {
            return super.use(level, player, hand);
        }
        if (level.isClientSide) return InteractionResultHolder.success(self);

        if (player.isShiftKeyDown()) {
            // Dump all contents to inventory
            java.util.List<ItemStack> items = AmmoPouchItem.getItems(self);
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
                AmmoPouchItem.clear(self);
                player.displayClientMessage(Component.translatable("tooltip.warbornrenewed.pouch_dump").withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResultHolder.sidedSuccess(self, false);
        } else {
            // Collect ammo from inventory into armor pouch
            boolean moved = collectAmmoFromInventory(player, self, internalPouchCapacity);
            if (moved) {
                player.displayClientMessage(Component.translatable("tooltip.warbornrenewed.pouch_collect").withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResultHolder.sidedSuccess(self, false);
        }
    }

    private static boolean collectAmmoFromInventory(Player player, ItemStack container, int capacity) {
        java.util.List<ItemStack> current = AmmoPouchItem.getItems(container);
        boolean changed = false;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack inv = player.getInventory().getItem(slot);
            if (inv.isEmpty()) continue;
            if (!AmmoPouchItem.isAmmo(inv)) continue;

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
            while (remaining > 0 && current.size() < capacity) {
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
            if (current.size() >= capacity) break;
        }
        if (changed) AmmoPouchItem.setItems(container, current);
        return changed;
    }
}
