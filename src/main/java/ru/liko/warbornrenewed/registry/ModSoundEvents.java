package ru.liko.warbornrenewed.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.liko.warbornrenewed.Warbornrenewed;

/**
 * Central registration point for all custom sound events used by the mod.
 */
public final class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Warbornrenewed.MODID);

    public static final RegistryObject<SoundEvent> ARMOR_BODY_EQUIP =
        register("armor.body_equip");
    public static final RegistryObject<SoundEvent> ARMOR_BODY_UNEQUIP =
        register("armor.body_unequip");
    public static final RegistryObject<SoundEvent> ARMOR_HELMET_EQUIP =
        register("armor.helmet_equip");
    public static final RegistryObject<SoundEvent> ARMOR_HELMET_UNEQUIP =
        register("armor.helmet_unequip");

    public static final RegistryObject<SoundEvent> VISION_NVG_ENABLE =
        register("vision.nvg_enable");
    public static final RegistryObject<SoundEvent> VISION_NVG_DISABLE =
        register("vision.nvg_disable");
    public static final RegistryObject<SoundEvent> VISION_THERMAL_ENABLE =
        register("vision.thermal_enable");
    public static final RegistryObject<SoundEvent> VISION_THERMAL_DISABLE =
        register("vision.thermal_disable");

    private ModSoundEvents() {
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    private static RegistryObject<SoundEvent> register(String path) {
        ResourceLocation id = Warbornrenewed.id(path);
        return SOUND_EVENTS.register(path, () -> SoundEvent.createVariableRangeEvent(id));
    }
}
