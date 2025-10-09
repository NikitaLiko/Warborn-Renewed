package ru.liko.warbornrenewed.content.armorset;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import ru.liko.warbornrenewed.Warbornrenewed;
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
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WarbornArmorItem(String itemId, ArmorMaterial material, Type type, Properties properties, ArmorVisualSpec visuals, ArmorBonesSpec bones, List<ArmorAttributeSpec> attributes) {
        super(material, type, properties);
        this.itemId = Objects.requireNonNull(itemId, "itemId");
        this.visuals = Objects.requireNonNull(visuals, "visuals");
        this.bones = Objects.requireNonNull(bones, "bones");
        this.attributes = List.copyOf(attributes);
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
}
