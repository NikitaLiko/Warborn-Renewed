package ru.liko.warbornrenewed.content.armorset;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.compat.TACZIntegration;
import ru.liko.warbornrenewed.config.WarbornConfig;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.CuriosCapability;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class WarbornArmorItem extends ArmorItem implements GeoItem, DyeableLeatherItem {
    // ==================== Vision Capability Tags ====================
    public static final String TAG_NVG = "nvg";                    // Night Vision Goggles
    public static final String TAG_THERMAL = "thermal";            // Thermal Vision
    public static final String TAG_DIGITAL = "digital";            // Digital overlay
    public static final String TAG_SIMPLE_NVG = "simple_nvg";      // Simple night vision (no animation)
    public static final String TAG_GOGGLE = "goggle";              // Protective goggles

    // NBT keys for helmet state
    public static final String NBT_NVG_DOWN = "nvg_down";          // Is NVG flipped down?
    public static final String NBT_HELMET_OPEN = "helmet_top_open"; // Is helmet visor/top open?

    private final String itemId;
    private final ArmorVisualSpec visuals;
    private final ArmorBonesSpec bones;
    private final List<ArmorAttributeSpec> attributes;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    // Vision capabilities for this armor piece (stores capability tags like "nvg", "thermal")
    private final List<String> visionCapabilities = new ArrayList<>();

    public WarbornArmorItem(String itemId, ArmorMaterial material, Type type, Properties properties, ArmorVisualSpec visuals, ArmorBonesSpec bones, List<ArmorAttributeSpec> attributes) {
        super(material, type, properties);
        this.itemId = Objects.requireNonNull(itemId, "itemId");
        this.visuals = Objects.requireNonNull(visuals, "visuals");
        this.bones = Objects.requireNonNull(bones, "bones");
        this.attributes = List.copyOf(attributes);
    }

    /**
     * Add a vision capability to this helmet
     * Called during registration from ArmorPieceDefinition.create()
     */
    public void addVisionCapability(String capability) {
        if (!visionCapabilities.contains(capability)) {
            visionCapabilities.add(capability);
        }
    }

    /**
     * Check if this helmet has a specific vision capability
     */
    public boolean hasVisionCapability(String capability) {
        return visionCapabilities.contains(capability);
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.EMPTY;
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
        // Animation controller for NVG/helmet toggle
        // Only add controller if helmet has animation file
        if (getType() == Type.HELMET && visuals.animation() != null) {
            controllers.add(new AnimationController<>(this, "nvg_toggle", 0, state -> {
                // Get ItemStack and Entity from animation state
                ItemStack stack = state.getData(DataTickets.ITEMSTACK);
                Entity rawEntity = state.getData(DataTickets.ENTITY);

                // Stop if not a living entity or is armor stand
                if (!(rawEntity instanceof LivingEntity entity) || entity instanceof ArmorStand) {
                    return PlayState.STOP;
                }

                // If no stack or no NBT, default to down position
                if (stack == null || !stack.hasTag()) {
                    state.setAnimation(RawAnimation.begin().then("nvg_down", Animation.LoopType.HOLD_ON_LAST_FRAME));
                    return PlayState.CONTINUE;
                }

                // Check NVG state from NBT
                boolean nvgDown = stack.getOrCreateTag().getBoolean(NBT_NVG_DOWN);
                String animationName = nvgDown ? "nvg_down" : "nvg_up";

                // Set animation based on state with HOLD_ON_LAST_FRAME
                state.setAnimation(RawAnimation.begin().then(animationName, Animation.LoopType.HOLD_ON_LAST_FRAME));
                return PlayState.CONTINUE;
            }));
        }
    }

    /**
     * Set NVG up/down state
     */
    public void setNVGUp(ItemStack stack, boolean up) {
        stack.getOrCreateTag().putBoolean("nvg_up", up);
    }

    /**
     * Check if NVG is up
     */
    public boolean isNVGUp(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("nvg_up");
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        // НЕ показываем атрибуты для MAINHAND и OFFHAND - только когда надет на тело
        if (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            return HashMultimap.create();
        }

        Multimap<Attribute, AttributeModifier> modifiers = super.getDefaultAttributeModifiers(slot);
        // Применяем атрибуты если:
        // 1. Слот соответствует типу брони (стандартная броня)
        // 2. ИЛИ слот не является стандартным слотом брони (для Curios предметов)
        // Это позволяет предметам в Curios слотах применять атрибуты
        boolean shouldApplyAttributes = !attributes.isEmpty() &&
            (slot == getType().getSlot() || slot.getType() != EquipmentSlot.Type.ARMOR);

        if (shouldApplyAttributes) {
            modifiers = HashMultimap.create(modifiers);
            // Используем правильный слот для UUID генерации (если это Curios, используем тип брони)
            EquipmentSlot slotForUUID = slot.getType() == EquipmentSlot.Type.ARMOR ? slot : getType().getSlot();
            
            // Получаем атрибут TACZ (если мод загружен)
            Attribute taczBulletResistance = TACZIntegration.getTACZBulletResistance();
            Attribute warbornBulletResistance = ru.liko.warbornrenewed.registry.ModAttributes.BULLET_RESISTANCE.get();
            
            for (int i = 0; i < attributes.size(); i++) {
                ArmorAttributeSpec spec = attributes.get(i);
                Attribute attribute = spec.attribute();
                UUID uuid = UUID.nameUUIDFromBytes((itemId + "/" + slotForUUID.getName() + "#" + i).getBytes(StandardCharsets.UTF_8));
                modifiers.put(attribute, spec.createModifier(uuid, stack, Warbornrenewed.MODID + ":" + itemId, slotForUUID));
                
                // Если это наш bullet_resistance и TACZ загружен - дублируем для TACZ
                if (attribute == warbornBulletResistance && taczBulletResistance != null) {
                    UUID taczUuid = UUID.nameUUIDFromBytes((itemId + "/tacz/" + slotForUUID.getName() + "#" + i).getBytes(StandardCharsets.UTF_8));
                    AttributeModifier taczMod = spec.createModifier(taczUuid, stack, Warbornrenewed.MODID + ":" + itemId + "/tacz", slotForUUID);
                    modifiers.put(taczBulletResistance, taczMod);
                }
            }
        }
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        // НЕ вызываем super, чтобы не показывать стандартные атрибуты "When in Main Hand/Off Hand/Body"

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
        double blastMult = 1.0; // значение по умолчанию — 1.0 (без изменений)
        double moveMod = 0.0;

        Attribute bulletAttr = ru.liko.warbornrenewed.registry.ModAttributes.BULLET_RESISTANCE.get();
        Attribute protAttr = ru.liko.warbornrenewed.registry.ModAttributes.PROTECTION_CLASS.get();
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

        // Защита от взрывов: конвертация множителя в проценты уменьшения урона
        // Пример: 1.0 = 0%, 0.8 = 20% защиты, 1.2 = -20% (увеличение урона)
        int blastPercent = (int) Math.round((1.0 - blastMult) * 100.0);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.blast_resistance", String.valueOf(blastPercent))
                .withStyle(blastPercent >= 0 ? ChatFormatting.DARK_GREEN : ChatFormatting.RED));

        // Скорость: проценты (от -50 до +20 по дизайну атрибута)
        int speedPercent = (int) Math.round(moveMod * 100.0);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.movement_speed", String.valueOf(speedPercent))
                .withStyle(speedPercent >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED));

        // Добавляем информацию о vision capabilities если это шлем
        if (getType() == Type.HELMET && hasAnyVisionCapability()) {
            tooltipComponents.add(Component.empty());
            tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.vision_capabilities")
                    .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));

            if (hasVisionCapability(TAG_NVG)) {
                tooltipComponents.add(Component.literal("  • ")
                        .append(Component.translatable("tooltip.warbornrenewed.vision.nvg"))
                        .withStyle(ChatFormatting.GREEN));
            }
            if (hasVisionCapability(TAG_THERMAL)) {
                tooltipComponents.add(Component.literal("  • ")
                        .append(Component.translatable("tooltip.warbornrenewed.vision.thermal"))
                        .withStyle(ChatFormatting.LIGHT_PURPLE));
            }

            // Показываем состояние NVG если есть
            if (hasVisionCapability(TAG_NVG)) {
                boolean nvgDown = stack.getOrCreateTag().getBoolean(NBT_NVG_DOWN);
                Component statusKey = nvgDown
                        ? Component.translatable("tooltip.warbornrenewed.nvg.down").withStyle(ChatFormatting.GREEN)
                        : Component.translatable("tooltip.warbornrenewed.nvg.up").withStyle(ChatFormatting.GRAY);
                tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.nvg.status", statusKey));
            }
        }
    }

    // ==================== Vision Capability Methods ====================

    /**
     * Check if this helmet has any vision capabilities
     */
    public boolean hasAnyVisionCapability() {
        return !visionCapabilities.isEmpty();
    }

    /**
     * Check if NVG is currently down (active)
     */
    public static boolean isNVGDown(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(NBT_NVG_DOWN);
    }

    /**
     * Toggle NVG state
     */
    public static void toggleNVG(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        boolean current = tag.getBoolean(NBT_NVG_DOWN);
        tag.putBoolean(NBT_NVG_DOWN, !current);
    }

    /**
     * Set NVG state
     */
    public static void setNVGDown(ItemStack stack, boolean down) {
        stack.getOrCreateTag().putBoolean(NBT_NVG_DOWN, down);
    }

    /**
     * Check if helmet visor/top is open
     */
    public static boolean isHelmetOpen(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(NBT_HELMET_OPEN);
    }

    /**
     * Toggle helmet open state
     */
    public static void toggleHelmet(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        boolean current = tag.getBoolean(NBT_HELMET_OPEN);
        tag.putBoolean(NBT_HELMET_OPEN, !current);
    }

    /**
     * Set helmet open state
     */
    public static void setHelmetOpen(ItemStack stack, boolean open) {
        stack.getOrCreateTag().putBoolean(NBT_HELMET_OPEN, open);
    }

    public String getItemId() {
        return itemId;
    }

    public ArmorVisualSpec getVisuals() {
        return visuals;
    }

    @Nullable
    public ResourceLocation getNvgShader() {
        return visuals.nvgShader();
    }

    public ArmorBonesSpec getBones() {
        return bones;
    }

    // ==================== Hide Default Attributes ====================

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        // Скрываем стандартные атрибуты (MODIFIERS) чтобы не дублировался tooltip
        stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        return stack;
    }

    // ==================== Durability Control ====================

    @Override
    public boolean isDamageable(ItemStack stack) {
        // Check config to determine if armor should be damageable
        return WarbornConfig.COMMON.armorIsDamageable.get();
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, java.util.function.Consumer<T> onBroken) {
        // If armor is not damageable according to config, return 0 (no damage)
        if (!WarbornConfig.COMMON.armorIsDamageable.get()) {
            return 0;
        }
        return amount;
    }

    // ==================== Curios Support ====================
    
    /**
     * Проверяет, должен ли этот предмет быть только в Curios слоте
     */
    private boolean isCuriosOnly(ItemStack stack) {
        if (!(stack.getItem() instanceof WarbornArmorItem item)) {
            return false;
        }
        // Повязки и ghillie костюм - только в Curios слотах
        String id = item.itemId;
        if (id == null) return false;
        
        return id.equals("arm_bandage") || id.equals("leg_bandage") ||
               id.startsWith("ghillie-");
    }
    
    /**
     * Получает идентификатор Curios слота для этого предмета
     */
    private String getCuriosSlotId(ItemStack stack) {
        if (!(stack.getItem() instanceof WarbornArmorItem item)) {
            return null;
        }
        String id = item.itemId;
        if (id == null) return null;
        
        // Повязки
        if ("arm_bandage".equals(id)) {
            return "arms";
        } else if ("leg_bandage".equals(id)) {
            return "legs";
        }
        
        // Ghillie костюм
        if (id.startsWith("ghillie-helmet-")) {
            return "ghillie_hood";
        } else if (id.startsWith("ghillie-body-")) {
            return "ghillie_top";
        } else if (id.startsWith("ghillie-legs-")) {
            return "ghillie_pants";
        }
        
        // Резервная проверка по типу брони для повязок
        if (id.contains("bandage")) {
            if (item.getType() == Type.CHESTPLATE) {
                return "arms";
            } else if (item.getType() == Type.LEGGINGS) {
                return "legs";
            }
        }
        
        return null;
    }
    
    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        // Если предмет предназначен только для Curios, запрещаем стандартную экипировку
        if (isCuriosOnly(stack) && slot.getType() == EquipmentSlot.Type.ARMOR) {
            return false;
        }
        return super.canEquip(stack, slot, entity);
    }
    
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        // Скрываем стандартные атрибуты чтобы не дублировался tooltip
        stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        
        // Добавляем поддержку Curios для повязки
        if (!ModList.get().isLoaded("curios")) {
            return super.initCapabilities(stack, nbt);
        }
        
        // Проверяем, является ли этот предмет повязкой для Curios
        if (!isCuriosOnly(stack)) {
            return super.initCapabilities(stack, nbt);
        }
        
        // Сохраняем itemId заранее для использования в анонимных классах
        final String slotId = getCuriosSlotId(stack);
        if (slotId == null) {
            // Если не удалось определить слот, возвращаем стандартную реализацию
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
                    String slotIdentifier = slotContext.identifier();
                    
                    // Нормализуем идентификатор слота (убираем namespace если есть)
                    String normalizedSlotId = slotIdentifier;
                    if (slotIdentifier.contains(":")) {
                        normalizedSlotId = slotIdentifier.substring(slotIdentifier.indexOf(":") + 1);
                    }
                    
                    // Проверяем точное совпадение идентификатора слота
                    return slotId.equals(normalizedSlotId) || slotId.equals(slotIdentifier);
                }

                @Override
                public boolean canEquipFromUse(SlotContext slotContext) {
                    return canEquip(slotContext);
                }

                @Override
                public void curioTick(SlotContext slotContext) {
                    // Логика тика для Curios предмета
                }

                @Override
                public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                    // При экипировке в Curios слот
                }

                @Override
                public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                    // При снятии из Curios слота
                }
            });
            
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
                // Проверяем, что это Curios capability
                if (ModList.get().isLoaded("curios") && cap != null) {
                    // Используем правильный способ проверки capability через CuriosCapability.ITEM
                    if (cap == CuriosCapability.ITEM) {
                        @SuppressWarnings("unchecked")
                        LazyOptional<T> result = (LazyOptional<T>) curio;
                        return result;
                    }
                    // Резервная проверка через имя capability (на случай, если CuriosCapability.ITEM недоступен)
                    String capName = cap.getName();
                    if (capName != null && (capName.contains("curios") || capName.contains("curio"))) {
                        try {
                            // Пробуем cast к ICurio
                            @SuppressWarnings("unchecked")
                            LazyOptional<T> result = (LazyOptional<T>) curio;
                            return result;
                        } catch (Exception e) {
                            // Если cast не сработал, это не ICurio capability
                        }
                    }
                }
                return LazyOptional.empty();
            }
        };
    }

    // ==================== Skin Variant System ====================

    /**
     * Cycle through available texture variants (skins)
     */
    public void cycleVariant(ItemStack stack) {
        if (visuals.variants().isEmpty()) return;

        List<String> keys = new ArrayList<>(visuals.variants().keySet());
        Collections.sort(keys); // Ensure deterministic order

        // Add default (empty string) as the first option
        List<String> allVariants = new ArrayList<>();
        allVariants.add("");
        allVariants.addAll(keys);

        String current = stack.getOrCreateTag().getString("Variant");
        int index = allVariants.indexOf(current);
        if (index == -1) index = 0; // If unknown variant, start from default

        int nextIndex = (index + 1) % allVariants.size();
        String next = allVariants.get(nextIndex);

        if (next.isEmpty()) {
            stack.getOrCreateTag().remove("Variant");
        } else {
            stack.getOrCreateTag().putString("Variant", next);
        }
    }

    /**
     * Get the current variant name
     */
    public String getVariant(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("Variant")) {
            return stack.getTag().getString("Variant");
        }
        return "";
    }
}

