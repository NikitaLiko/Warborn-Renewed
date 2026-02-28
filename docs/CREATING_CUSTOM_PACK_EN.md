# 🛡️ Creating a Custom Pack for Warborn Renewed

> This guide describes how to create your own content pack (armor set) for the **Warborn Renewed** mod.

---

## 📋 Table of Contents

- [Pack System Overview](#-pack-system-overview)
- [Required Tools](#-required-tools)
- [Pack Structure](#-pack-structure)
- [Step-by-Step Pack Creation](#-step-by-step-pack-creation)
  - [Step 1: Create Pack Folder](#step-1-create-pack-folder)
  - [Step 2: Create 3D Model](#step-2-create-3d-model)
  - [Step 3: Create Texture](#step-3-create-texture)
  - [Step 4: Create JSON Configuration](#step-4-create-json-configuration)
  - [Step 5: Animations (optional)](#step-5-animations-optional)
  - [Step 6: Localization](#step-6-localization-item-name)
- [Bone Name Reference](#-bone-name-reference)
- [Armor Stats Reference](#-armor-stats-reference)
- [Testing Your Pack](#-testing-your-pack)
- [Examples](#-examples)
- [FAQ](#-faq)

---

## 🔍 Pack System Overview

Warborn Renewed supports a **content pack system** — a way to add new armor to the mod without writing any Java code. Each pack is a set of files including:

- **JSON configuration** — armor parameters (defense, durability, etc.)
- **3D model** — a `.geo.json` file in Bedrock/GeckoLib format
- **Texture** — a `.png` file for rendering the model
- **Animation** (optional) — an `.animation.json` file for GeckoLib animations

Packs are loaded from the `warbornrenewed/packs/` folder in the game's root directory (next to the `mods` folder).

---

## 🛠️ Required Tools

| Tool | Description | Link |
|---|---|---|
| **Blockbench** | Creating 3D models in Bedrock/GeckoLib format | [blockbench.net](https://www.blockbench.net/) |
| **GeckoLib Plugin** | Blockbench plugin — export models and animations for GeckoLib | [blockbench.net/plugins/geckolib](https://www.blockbench.net/plugins/geckolib) |
| **Image Editor** | Any graphics editor (Photoshop, GIMP, Aseprite, paint.net) | — |
| **Text Editor** | For editing JSON files (VS Code, Notepad++, etc.) | — |

---

## 📁 Pack Structure

Each pack is placed in a separate folder inside `packs/` in the repository. Here's the structure:

```
packs/
└── my_cool_armor/             ← Your pack name (snake_case)
    ├── json/
    │   └── armor/
    │       ├── my_helmet.json         ← Helmet configuration
    │       └── my_chestplate.json     ← Chestplate configuration
    ├── models/
    │   └── armor/
    │       ├── my_helmet.geo.json     ← 3D helmet model (GeckoLib)
    │       ├── my_helmet.png          ← Helmet texture
    │       ├── my_chestplate.geo.json ← 3D chestplate model
    │       └── my_chestplate.png      ← Chestplate texture
    └── lang/                          ← (Optional) multilingual support
        ├── en_us.json
        └── ru_ru.json
```

> [!IMPORTANT]
> The pack folder name **must be unique** and use `snake_case` format (lowercase letters, words separated by underscores). Example: `russian_ratnik`, `nato_urban_camo`.

---

## 🚀 Step-by-Step Pack Creation

### Step 1: Create Pack Folder

Create a folder with a unique name for your pack inside `packs/`:

```
packs/
└── my_armor_pack/
    ├── json/
    │   └── armor/
    └── models/
        └── armor/
```

### Step 2: Create 3D Model

Open **Blockbench** and create a new project:

> [!TIP]
> It is recommended to install the **GeckoLib Animation Utils** plugin for Blockbench — it adds convenient tools for creating GeckoLib-compatible models and animations.
> Install directly from Blockbench: **File → Plugins** → search for `GeckoLib`, or visit: [blockbench.net/plugins/geckolib](https://www.blockbench.net/plugins/geckolib)

1. Choose the **Bedrock Entity** format (or **GeckoLib Animated Model** via the plugin if you need animations).
2. Create the armor model.

#### Bone Naming Rules

> [!CAUTION]
> The model **must** contain bones with the correct names! Without this, the armor will not render correctly on the player character.

Depending on the armor type, the model should contain these bones:

| Armor Type | Required Bones |
|---|---|
| **Helmet** | `armorHead` |
| **Chestplate** | `armorBody`, `armorRightArm`*, `armorLeftArm`* |
| **Leggings** | `armorBody`*, `armorRightLeg`, `armorLeftLeg` |
| **Boots** | `armorRightBoot`, `armorLeftBoot` |

> \* — optional bones. If your chestplate model doesn't cover the arms, you can omit the arm bones.

#### Bone Hierarchy in Blockbench

Example hierarchy for a helmet:

```
📦 root
└── 🦴 armorHead
    ├── 🟦 helmet_base (cube)
    ├── 🟦 helmet_visor (cube)
    └── 🟦 helmet_strap (cube)
```

Example hierarchy for a chestplate:

```
📦 root
├── 🦴 armorBody
│   ├── 🟦 vest_front (cube)
│   ├── 🟦 vest_back (cube)
│   └── 🟦 vest_collar (cube)
├── 🦴 armorRightArm
│   └── 🟦 shoulder_pad_right (cube)
└── 🦴 armorLeftArm
    └── 🟦 shoulder_pad_left (cube)
```

#### Pivot Points

Use the following pivot points for root bones to correctly position armor on the player model:

| Bone | Pivot Point (X, Y, Z) |
|---|---|
| `armorHead` | `[0, 24, 0]` |
| `armorBody` | `[0, 24, 0]` |
| `armorRightArm` | `[-5, 22, 0]` |
| `armorLeftArm` | `[5, 22, 0]` |
| `armorRightLeg` | `[-1.9, 12, 0]` |
| `armorLeftLeg` | `[1.9, 12, 0]` |
| `armorRightBoot` | `[-1.9, 12, 0]` |
| `armorLeftBoot` | `[1.9, 12, 0]` |

3. Export the model as **Bedrock Geometry** (`.geo.json`).
4. Save the file to `packs/<your_pack>/models/armor/<name>.geo.json`.

#### Example `.geo.json` Model

```json
{
  "format_version": "1.12.0",
  "minecraft:geometry": [
    {
      "description": {
        "identifier": "geometry.my_helmet",
        "texture_width": 64,
        "texture_height": 64,
        "visible_bounds_width": 3,
        "visible_bounds_height": 3,
        "visible_bounds_offset": [0, 1.5, 0]
      },
      "bones": [
        {
          "name": "armorHead",
          "pivot": [0, 24, 0],
          "cubes": [
            {
              "origin": [-4.5, 23.5, -4.5],
              "size": [9, 9, 9],
              "uv": [0, 0]
            }
          ]
        }
      ]
    }
  ]
}
```

### Step 3: Create Texture

1. In **Blockbench**, create a texture for your model (or draw it in an external image editor).
2. Recommended size: **64×64**, **128×128**, or **256×256** — depending on model complexity.
3. Save the texture as `.png` alongside the model:
   ```
   packs/<your_pack>/models/armor/<name>.png
   ```

> [!TIP]
> For consistency with the rest of the mod's content, military-themed textures should use realistic camouflage patterns (EMR, Multicam, Desert, UCP, etc.).

### Step 4: Create JSON Configuration

Create a JSON file at `packs/<your_pack>/json/armor/<name>.json`. This file describes the armor's properties.

#### Configuration Format

```json
{
  "id": "<pack_name>:<armor_id>",
  "model_id": "<pack_name>:geo/armor/<model_name>",
  "name": "My Helmet",
  "names": {
    "ru_ru": "Мой Шлем",
    "en_us": "My Helmet"
  },
  "defense": 3,
  "toughness": 1.0,
  "knockback_resistance": 0.0,
  "durability": 150
}
```

#### Field Description

| Field | Type | Req. | Description |
|---|---|---|---|
| `id` | `string` | ✅ | **Unique identifier** in format `pack_name:item_id`. Example: `my_pack:tactical_helmet` |
| `model_id` | `string` | ✅ | **Model path** in format `pack_name:geo/armor/file_name` (without `.geo.json` extension). Texture is loaded from the same path with `.png` extension |
| `name` | `string` | ❌ | **Display name** of the item. Used as default name for all languages if `names` is not specified |
| `names` | `object` | ❌ | **Multilingual names**. Key — locale code (`ru_ru`, `en_us`, etc.), value — name in that language |
| `defense` | `int` | ✅ | **Defense points** (like vanilla armor). 0–20 |
| `toughness` | `float` | ✅ | **Armor toughness** (reduces damage from strong attacks). 0.0–4.0 |
| `knockback_resistance` | `float` | ✅ | **Knockback resistance**. 0.0–1.0 (1.0 = full resistance) |
| `durability` | `int` | ✅ | **Durability** (hits before breaking). 0 = infinite |

#### Configuration Example

```json
{
  "id": "my_armor_pack:tactical_helmet",
  "model_id": "my_armor_pack:geo/armor/tactical_helmet",
  "name": "Tactical Helmet",
  "names": {
    "ru_ru": "Тактический шлем",
    "en_us": "Tactical Helmet",
    "de_de": "Taktischer Helm"
  },
  "defense": 4,
  "toughness": 1.5,
  "knockback_resistance": 0.05,
  "durability": 250
}
```

> [!NOTE]
> The `model_id` field defines the path for both the model (appended with `.geo.json`) and the texture (appended with `.png`). Both files must be located at the same path.

### Step 5: Animations (optional)

To add animations, create an `.animation.json` file alongside the model:

```
packs/<your_pack>/models/armor/<name>.animation.json
```

Animations are created in **Blockbench** using the **GeckoLib Animation Utils** plugin:

1. Install the GeckoLib plugin in Blockbench.
2. Create animations for your model.
3. Export as `.animation.json`.

The system will automatically pick up the animation file at the path matching `model_id` (with `.animation.json` extension).

### Step 6: Localization (Item Name)

There are **two ways** to set the item name — choose whichever is more convenient:

---

#### Method 1: Directly in JSON Configuration (recommended ✅)

The simplest way — add `name` and/or `names` fields directly to the armor JSON file:

```json
{
  "id": "my_pack:tactical_helmet",
  "model_id": "my_pack:geo/armor/tactical_helmet",
  "name": "Tactical Helmet",
  "names": {
    "ru_ru": "Тактический шлем",
    "en_us": "Tactical Helmet"
  },
  "defense": 4,
  "toughness": 1.5,
  "knockback_resistance": 0.05,
  "durability": 250
}
```

**Display priority:**
1. `names` → name in the current game language
2. `name` → general name (if language not found in `names`)
3. `id` → last resort fallback

> [!TIP]
> If your pack targets only one language — just specify `name` without `names`.

---

#### Method 2: Via lang files in the pack folder

For advanced users or packs with many items — create a `lang/` folder inside the pack:

```
packs/my_pack/
└── lang/
    ├── en_us.json
    └── ru_ru.json
```

**Key format:**
```
item.warbornrenewed.pack.<pack_name>.<item_id>
```

**`lang/en_us.json`:**
```json
{
  "item.warbornrenewed.pack.my_pack.tactical_helmet": "Tactical Helmet",
  "item.warbornrenewed.pack.my_pack.tactical_vest": "Tactical Vest"
}
```

**`lang/ru_ru.json`:**
```json
{
  "item.warbornrenewed.pack.my_pack.tactical_helmet": "Тактический шлем",
  "item.warbornrenewed.pack.my_pack.tactical_vest": "Тактический жилет"
}
```

> [!NOTE]
> Lang files have the **highest priority** — if a translation is found in a lang file, it will be used instead of `name`/`names` from the JSON config.

---

## 🦴 Bone Name Reference

Complete list of all bones used by the GeckoLib rendering system:

| Bone Name | Body Part | Used In |
|---|---|---|
| `armorHead` | Head | Helmets |
| `armorBody` | Torso | Chestplates, Leggings |
| `armorRightArm` | Right Arm | Chestplates (shoulder pads) |
| `armorLeftArm` | Left Arm | Chestplates (shoulder pads) |
| `armorRightLeg` | Right Leg | Leggings |
| `armorLeftLeg` | Left Leg | Leggings |
| `armorRightBoot` | Right Foot | Boots |
| `armorLeftBoot` | Left Foot | Boots |

---

## 📊 Armor Stats Reference

### Recommended Stat Values

For reference — stats of existing mod armor:

| Type | Item | Defense | Toughness | Bullet Res. | Durability |
|---|---|---|---|---|---|
| 🪖 Helmet (light) | Panama | 0 | 0.0 | 0% | Low |
| 🪖 Helmet (medium) | PASGT | 2 | 0.5 | 30% | 200 |
| 🪖 Helmet (heavy) | 6B47 | 3 | 1.0 | 35% | 300 |
| 🪖 Helmet (advanced) | Ops-Core | 3 | 1.0 | 40% | 350 |
| 🦺 Vest (medium) | IOTV | 6 | 2.0 | 50% | 400 |
| 🦺 Vest (heavy) | 6B45 | 7 | 2.5 | 55% | 450 |
| 🦺 Vest (elite) | Warmor | 8 | 3.0 | 60% | 500 |

> [!WARNING]
> Do not make stats **excessively high** — this will break the mod's balance!

---

## ✅ Testing Your Pack

Before using, test the pack locally:

1. **Copy the pack** to the game folder:
   ```
   .minecraft/warbornrenewed/packs/<your_pack>/
   ```

2. **Launch Minecraft** with the Warborn Renewed mod installed.

3. **Verify:**
   - ✅ Armor loads without errors in the logs
   - ✅ Model renders correctly on the player character
   - ✅ Texture displays properly (no artifacts or "pink-black squares")
   - ✅ Stats (defense, durability) work as expected
   - ✅ Item name displays correctly (not as `item.warbornrenewed.pack...`)
   - ✅ Animations work (if used)

4. **Check logs** for errors:
   ```
   logs/latest.log
   ```
   Look for lines containing `Failed to load armor def` or `WarbornPackManager`.

---

## 📌 Examples

### Example: The `example_pack`

The repository already contains an example pack at `packs/example_pack/`:

**Structure:**
```
packs/example_pack/
├── json/
│   └── armor/
│       └── alfa_helmet.json
└── models/
    └── armor/
        ├── alfa_helmet.geo.json
        └── alfa_helmet.png
```

**Configuration (`alfa_helmet.json`):**
```json
{
  "id": "example_pack:alfa_helmet",
  "model_id": "example_pack:geo/armor/alfa_helmet",
  "name": "Alfa Helmet",
  "names": {
    "ru_ru": "Шлем Альфа",
    "en_us": "Alfa Helmet"
  },
  "defense": 3,
  "toughness": 1.0,
  "knockback_resistance": 0.0,
  "durability": 150
}
```

### Loading Packs (for users)

For client-side use (not for development), packs are placed in:

```
.minecraft/warbornrenewed/packs/<pack_name>/
```

The mod automatically scans this directory at startup and loads all packs found.

---

## ❓ FAQ

### My pack won't load. What should I do?

1. Check the folder structure — it should match the format above
2. Check JSON for syntax errors (extra commas, unclosed brackets)
3. Make sure the `id` is unique and doesn't conflict with other packs
4. Check the logs: `logs/latest.log` — look for `Failed to load armor def`

### What model format is needed?

Use the **Bedrock Entity Geometry** `.geo.json` format (the same used for Bedrock Edition models and GeckoLib).

### The model renders incorrectly (offset, rotated)

Check the **pivot points** of root bones — they should match the table in the ["Pivot Points"](#pivot-points) section.

### Texture shows as pink-black squares

This means the `model_id` path points to a non-existent texture file. Make sure:
- The `.png` file exists alongside the `.geo.json`
- The path in `model_id` is correct

### Can I add multiple items to one pack?

Yes! Create one `.json`, `.geo.json`, and `.png` file per armor item, all within the same pack folder.

### What texture size should I use?

Recommended: **64×64** for simple models and **128×128** or **256×256** for more detailed ones. Textures should be square with dimensions that are a power of two (64, 128, 256). The texture size **must match** what's specified in the `.geo.json` file (`texture_width` / `texture_height`).

---

> **Good luck creating content!** 🎮
