# Armor Parts System (Curios Integration)

## Overview

This system allows you to create and manage armor parts (NVG, backpacks, vests, etc.) using the Curios API. The system is similar to the existing `WarbornArmorSets` but specifically designed for Curios slots.

## Features

- ✅ **Easy Registration**: Similar builder pattern to `WarbornArmorSet`
- ✅ **Curios API Integration**: Full support for Curios slots and rendering
- ✅ **NVG Toggle System**: Built-in hotkey support for toggling NVG up/down with animations
- ✅ **Multiple Slot Types**: Support for NVG, backpacks, vests, headsets, and goggles
- ✅ **GeckoLib Animation**: Ready for custom animations
- ✅ **Shader Support**: Framework for implementing shader-based night vision effects

## Available Slot Types

```java
public enum PartSlotType {
    NVG,       // Night Vision Goggles
    BACKPACK,  // Backpack
    VEST,      // Tactical Vest
    HEADSET,   // Headset/Communications
    GOGGLES    // Protective Goggles
}
```

## Quick Start: Adding a New Armor Part

### 1. Open `WarbornArmorPartsSets.java`

Located at: `src/main/java/ru/liko/warbornrenewed/setup/WarbornArmorPartsSets.java`

### 2. Add Your Part Registration

```java
private static void registerMyNVG() {
    WarbornArmorPartsRegistry.registerPart(
        WarbornArmorPart.builder("my-nvg")
            .nvg(part -> part
                .registryName("my_nvg")
                .visuals(spec -> spec
                    .model("warbornrenewed:geo/my_nvg.geo.json")
                    .texture("warbornrenewed:textures/parts/my_nvg_up.png")
                    .textureDown("warbornrenewed:textures/parts/my_nvg_down.png"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.RARE))
                .withNVGToggle()) // Enable toggle functionality
    );
}
```

### 3. Call Registration in `bootstrap()`

```java
public static void bootstrap() {
    registerExampleNVG();
    registerMyNVG(); // Add your registration here
}
```

### 4. Add Item to Curios Tag

Edit: `src/main/resources/data/curios/tags/items/nvg.json`

```json
{
  "replace": false,
  "values": [
    "warbornrenewed:example_nvg",
    "warbornrenewed:my_nvg"
  ]
}
```

### 5. Add Localization

Edit: `src/main/resources/assets/warbornrenewed/lang/en_us.json`

```json
{
  "item.warbornrenewed.my_nvg": "My Custom NVG"
}
```

## NVG Toggle System

### How It Works

1. **Hotkey**: Press **N** key (default) to toggle NVG up/down
2. **State Storage**: NVG state is stored in item NBT (`nvg_down` tag)
3. **Network Sync**: Client sends packet to server to sync state
4. **Animation Ready**: Hook into GeckoLib animations for visual toggle

### Using NVG Toggle in Your Parts

```java
.nvg(part -> part
    .registryName("my_nvg")
    .visuals(spec -> spec
        .model("warbornrenewed:geo/nvg.geo.json")
        .texture("warbornrenewed:textures/parts/nvg_up.png")      // Up state
        .textureDown("warbornrenewed:textures/parts/nvg_down.png")) // Down state
    .withNVGToggle()) // Must call this to enable toggle
```

### Implementing Animations

GeckoLib animations are automatically set up! The system includes:

1. **WarbornArmorPartModel** - GeckoLib model that loads your .geo.json models
2. **WarbornArmorPartRenderer** - Handles rendering with texture switching
3. **CuriosArmorPartRenderer** - Integrates GeckoLib with Curios rendering
4. **Animation Controllers** - Built into `WarbornArmorPartItem`

#### Animation File Structure

Place your animation files in:
```
src/main/resources/assets/warbornrenewed/animations/item/{item_id}.animation.json
```

Example for `example_nvg`:
```
src/main/resources/assets/warbornrenewed/animations/item/example_nvg.animation.json
```

#### Animation File Format (Blockbench Export)

```json
{
  "format_version": "1.8.0",
  "animations": {
    "idle": {
      "loop": true,
      "animation_length": 1.0,
      "bones": {}
    },
    "nvg_up": {
      "loop": false,
      "animation_length": 0.5,
      "bones": {
        "nvg": {
          "rotation": {
            "0.0": [0, 0, 0],
            "0.5": [-90, 0, 0]
          }
        }
      }
    },
    "nvg_down": {
      "loop": false,
      "animation_length": 0.5,
      "bones": {
        "nvg": {
          "rotation": {
            "0.0": [-90, 0, 0],
            "0.5": [0, 0, 0]
          }
        }
      }
    }
  }
}
```

#### How It Works

1. When you press **N**, the `NVGToggleHandler` triggers
2. NBT tag `nvg_down` is toggled on the ItemStack
3. `CuriosArmorPartRenderer` reads the NBT and switches textures
4. GeckoLib animation controller plays the appropriate animation
5. The animation transitions between up/down states

#### Custom Animation Controller

The default controller is set up in `WarbornArmorPartItem.registerControllers()`. 
You can customize the animation behavior by modifying the `nvgTogglePredicate` method.

### Adding Night Vision Shader Effect

The NVG toggle system provides the framework. You can implement shader effects in `WarbornArmorPartItem.curioTick()`:

```java
@Override
public void curioTick(SlotContext slotContext, ItemStack stack) {
    // Check if NVG is down (active)
    boolean isDown = stack.getOrCreateTag().getBoolean("nvg_down");
    
    if (isDown && slotContext.entity() instanceof Player player) {
        // Apply night vision effect
        player.addEffect(new MobEffectInstance(
            MobEffects.NIGHT_VISION,
            220, // Duration in ticks
            0,   // Amplifier
            true, // Ambient
            false // Show particles
        ));
        
        // You can also implement custom shader effects here
        // See old mod reference: https://github.com/Raiiiden/WARBORN-1.20.1
    }
    
    ICurioItem.super.curioTick(slotContext, stack);
}
```

## Example: Complete NVG Registration

```java
private static void registerTacticalNVG() {
    WarbornArmorPartsRegistry.registerPart(
        WarbornArmorPart.builder("tactical-nvg")
            .nvg(part -> part
                .registryName("tactical_nvg")
                .visuals(spec -> spec
                    .model("warbornrenewed:geo/tactical_nvg.geo.json")
                    .texture("warbornrenewed:textures/parts/tactical_nvg_up.png")
                    .textureDown("warbornrenewed:textures/parts/tactical_nvg_down.png"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant())
                .withNVGToggle())
    );
}
```

## Example: Backpack Registration

```java
private static void registerMilitaryBackpack() {
    WarbornArmorPartsRegistry.registerPart(
        WarbornArmorPart.builder("military-backpack")
            .backpack(part -> part
                .registryName("military_backpack")
                .visuals(spec -> spec
                    .model("warbornrenewed:geo/military_backpack.geo.json")
                    .texture("warbornrenewed:textures/parts/military_backpack.png"))
                .properties(props -> props
                    .stacksTo(1)
                    .rarity(Rarity.COMMON)))
    );
}
```

## Curios Slots Configuration

Slot configurations are defined in: `src/main/resources/data/curios/slots/*.json`

Example (`nvg.json`):
```json
{
  "replace": false,
  "size": 1,
  "operation": "SET",
  "order": 10,
  "icon": "warbornrenewed:slot/nvg",
  "add_cosmetic": false,
  "use_native_gui": true,
  "render_toggle": true
}
```

## Files You May Need to Edit

1. **Part Registration**: `src/main/java/ru/liko/warbornrenewed/setup/WarbornArmorPartsSets.java`
2. **Curios Tags**: `src/main/resources/data/curios/tags/items/*.json`
3. **Localization**: `src/main/resources/assets/warbornrenewed/lang/en_us.json`
4. **Models**: Place GeckoLib models in `src/main/resources/assets/warbornrenewed/geo/`
5. **Textures**: Place textures in `src/main/resources/assets/warbornrenewed/textures/parts/`
6. **Animations**: Place animation files in `src/main/resources/assets/warbornrenewed/animations/item/`

## Key Binding

- **Default Key**: N
- **Localization Key**: `key.warbornrenewed.nvg_toggle`
- **Category**: `key.categories.warbornrenewed`

Players can rebind this key in Minecraft's controls settings.

## References

- **Old Mod Animations**: https://github.com/Raiiiden/WARBORN-1.20.1/tree/master/src/main/resources/assets/warborn/animations
- **Curios API Docs**: https://docs.illusivesoulworks.com/1.20.x
- **Existing Armor System**: See `WarbornArmorSets.java` for similar patterns

## Troubleshooting

### Part Not Appearing in Curios Slot
1. Check that the item is registered in the correct Curios tag file
2. Verify the slot configuration JSON exists
3. Ensure `canEquip()` returns true for the slot

### NVG Toggle Not Working
1. Verify `.withNVGToggle()` is called in part builder
2. Check that keybind is registered (should show in Controls menu)
3. Ensure NetworkHandler is registered in main mod class

### Textures Not Loading
1. Check texture paths match exactly (case-sensitive)
2. Verify texture files exist in the correct directory
3. Review console for resource loading errors

## Next Steps

1. Create your 3D models in Blockbench and export as GeckoLib format
2. Create textures for your armor parts
3. Add animation files for NVG toggle (refer to old mod examples)
4. Implement shader effects for night vision (optional)
5. Test in-game with Curios inventory (default key: G)

Happy modding! 🎮
