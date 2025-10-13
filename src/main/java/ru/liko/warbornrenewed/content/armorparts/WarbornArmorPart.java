package ru.liko.warbornrenewed.content.armorparts;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ru.liko.warbornrenewed.registry.ModItems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Система для регистрации частей брони (Parts) для использования через Curios API.
 * Аналогична WarbornArmorSet, но для Curio-слотов.
 */
public final class WarbornArmorPart {
    private final String id;
    private final EnumMap<PartSlotType, RegistryObject<WarbornArmorPartItem>> parts;

    private WarbornArmorPart(String id, EnumMap<PartSlotType, RegistryObject<WarbornArmorPartItem>> parts) {
        this.id = id;
        this.parts = parts;
    }

    public String id() {
        return id;
    }

    public Collection<RegistryObject<WarbornArmorPartItem>> parts() {
        return Collections.unmodifiableCollection(parts.values());
    }

    public Optional<RegistryObject<WarbornArmorPartItem>> part(PartSlotType type) {
        return Optional.ofNullable(parts.get(type));
    }

    static WarbornArmorPart register(DeferredRegister<Item> items, Builder builder) {
        EnumMap<PartSlotType, RegistryObject<WarbornArmorPartItem>> registrations = new EnumMap<>(PartSlotType.class);
        builder.definitions.forEach((type, definition) -> {
            RegistryObject<WarbornArmorPartItem> entry = items.register(
                definition.registryName(),
                () -> definition.create(type)
            );
            registrations.put(type, entry);
            ModItems.trackArmorPart(entry);
        });
        return new WarbornArmorPart(builder.id, registrations);
    }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    /**
     * Типы слотов для частей брони
     */
    public enum PartSlotType {
        NVG("nvg"),           // Night Vision Goggles - Прибор ночного видения
        BACKPACK("backpack"), // Backpack - Рюкзак
        VEST("vest"),         // Tactical Vest - Тактический жилет
        HEADSET("headset"),   // Headset - Гарнитура
        GOGGLES("goggles");   // Goggles - Очки/защитные очки

        private final String slotName;

        PartSlotType(String slotName) {
            this.slotName = slotName;
        }

        public String getSlotName() {
            return slotName;
        }
    }

    public static final class Builder {
        private final String id;
        private final Map<PartSlotType, PartDefinition> definitions = new EnumMap<>(PartSlotType.class);
        private PartDefinition defaults;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id, "id");
        }

        public Builder nvg(Consumer<PartBuilder> consumer) {
            return part(PartSlotType.NVG, consumer);
        }

        public Builder backpack(Consumer<PartBuilder> consumer) {
            return part(PartSlotType.BACKPACK, consumer);
        }

        public Builder vest(Consumer<PartBuilder> consumer) {
            return part(PartSlotType.VEST, consumer);
        }

        public Builder headset(Consumer<PartBuilder> consumer) {
            return part(PartSlotType.HEADSET, consumer);
        }

        public Builder goggles(Consumer<PartBuilder> consumer) {
            return part(PartSlotType.GOGGLES, consumer);
        }

        public WarbornArmorPart register(DeferredRegister<Item> items) {
            if (definitions.isEmpty()) {
                throw new IllegalStateException("No armor parts configured for set " + id);
            }
            definitions.values().forEach(definition -> definition.validate(id));
            return WarbornArmorPart.register(items, this);
        }

        private Builder part(PartSlotType type, Consumer<PartBuilder> consumer) {
            PartBuilder builder = new PartBuilder(id, type, defaults);
            consumer.accept(builder);
            PartDefinition definition = builder.build();
            definitions.put(type, definition);
            return this;
        }

        private PartDefinition ensureDefaults() {
            if (defaults == null) {
                defaults = PartDefinition.defaults();
            }
            return defaults;
        }
    }

    static final class PartDefinition {
        private final String registryName;
        private final ArmorPartVisualSpec visuals;
        private final PropertiesProvider propertiesProvider;
        private final boolean hasNVGToggle;

        PartDefinition(String registryName, ArmorPartVisualSpec visuals, PropertiesProvider propertiesProvider, boolean hasNVGToggle) {
            this.registryName = registryName;
            this.visuals = visuals;
            this.propertiesProvider = propertiesProvider;
            this.hasNVGToggle = hasNVGToggle;
        }

        static PartDefinition defaults() {
            return new PartDefinition(null, null, PropertiesProvider.identity(), false);
        }

        PartDefinition withVisuals(ArmorPartVisualSpec visuals) {
            return new PartDefinition(registryName, visuals, propertiesProvider, hasNVGToggle);
        }

        PartDefinition withProperties(PropertiesProvider propertiesProvider) {
            return new PartDefinition(registryName, visuals, propertiesProvider, hasNVGToggle);
        }

        PartDefinition withRegistryName(String registryName) {
            return new PartDefinition(registryName, visuals, propertiesProvider, hasNVGToggle);
        }

        PartDefinition withNVGToggle(boolean hasNVGToggle) {
            return new PartDefinition(registryName, visuals, propertiesProvider, hasNVGToggle);
        }

        void validate(String setId) {
            if (visuals == null) {
                throw new IllegalStateException("Visuals are not set for armor part in set " + setId);
            }
        }

        String registryName() {
            if (registryName == null) {
                throw new IllegalStateException("Registry name must be provided");
            }
            return registryName;
        }

        WarbornArmorPartItem create(PartSlotType type) {
            String name = registryName();
            return new WarbornArmorPartItem(name, type, propertiesProvider.properties(), visuals, hasNVGToggle);
        }

        public interface PropertiesProvider {
            Item.Properties properties();

            static PropertiesProvider identity() {
                return () -> new Item.Properties().stacksTo(1);
            }
        }
    }

    public static final class PartBuilder {
        private final String setId;
        private final PartSlotType type;
        private ArmorPartVisualSpec visuals;
        private PartDefinition.PropertiesProvider propertiesProvider;
        private String registryName;
        private boolean hasNVGToggle;

        private PartBuilder(String setId, PartSlotType type, PartDefinition defaults) {
            this.setId = setId;
            this.type = type;
            if (defaults != null) {
                this.visuals = defaults.visuals;
                this.propertiesProvider = defaults.propertiesProvider;
                this.registryName = defaults.registryName;
                this.hasNVGToggle = defaults.hasNVGToggle;
            }
        }

        public PartBuilder registryName(String registryName) {
            this.registryName = registryName;
            return this;
        }

        public PartBuilder visuals(Consumer<ArmorPartVisualSpec.Builder> consumer) {
            ArmorPartVisualSpec.Builder builder = ArmorPartVisualSpec.builder();
            consumer.accept(builder);
            this.visuals = builder.build();
            return this;
        }

        public PartBuilder properties(UnaryOperator<Item.Properties> operator) {
            this.propertiesProvider = () -> {
                Item.Properties base = new Item.Properties().stacksTo(1);
                return operator.apply(base);
            };
            return this;
        }

        /**
         * Включает поддержку переключения NVG (подъём/опускание по хоткею)
         */
        public PartBuilder withNVGToggle() {
            this.hasNVGToggle = true;
            return this;
        }

        private PartDefinition build() {
            if (visuals == null) {
                throw new IllegalStateException("Visuals are required for armor part " + setId + " - " + type);
            }
            PartDefinition.PropertiesProvider properties = propertiesProvider != null
                ? propertiesProvider
                : PartDefinition.PropertiesProvider.identity();
            String registry = registryName != null ? registryName : defaultName();
            return new PartDefinition(registry, visuals, properties, hasNVGToggle);
        }

        private String defaultName() {
            String suffix = type.getSlotName();
            return setId + "_" + suffix;
        }
    }
}
