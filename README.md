# 🛡️ Warborn Renewed

<div align="center">

**Military-grade tactical armor mod for Minecraft**

[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1%20%7C%201.21.1-62B47A?style=for-the-badge&logo=mojangstudios&logoColor=white)](https://www.minecraft.net/)
[![Forge](https://img.shields.io/badge/Forge-1.20.1-blue?style=for-the-badge)](https://files.minecraftforge.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-1.21.1-orange?style=for-the-badge)](https://neoforged.net/)
[![License](https://img.shields.io/badge/License-All%20Rights%20Reserved-red?style=for-the-badge)](#license)

</div>

---

## 📖 About

**Warborn Renewed** is a Minecraft mod that adds realistic military tactical armor, equipment, and gear. The mod features highly detailed 3D models powered by [GeckoLib](https://github.com/bernie-g/geckolib), realistic ballistic protection systems, and a modular content pack system for community-created armor sets.

### ✨ Key Features

- 🪖 **Realistic Armor** — Helmets, body armor, and gear modeled after real-world military equipment (6B47, Ops-Core, IOTV, JPC, PASGT, and more)
- 🎯 **Ballistic Protection System** — Realistic armor mechanics with protection classes based on NIJ/ГОСТ standards
- 🔫 **Mod Compatibility** — Integration with gun mods (TACZ, SuperbWarfare) for bullet resistance
- 🌙 **Night Vision & Thermal** — Functional NVG and thermal vision capabilities on supported helmets
- 🎽 **Camouflage Variants** — Multiple camo patterns per armor set (EMR, Multicam, Desert, UCP, ATACS-FG, and more)
- 🎒 **REW Backpack** — Electronic warfare backpack with drone jamming capabilities
- 🔭 **Binoculars** — Functional binoculars for scouting
- 🌿 **Ghillie Suits** — Full ghillie set (helmet, body, legs) in desert, jungle, and winter variants
- 📦 **Content Pack System** — Add custom armor without writing any code
- 🌍 **Multilingual** — English and Russian localization

---

## 🏗️ Project Structure

The mod uses a **multi-platform architecture** with shared code:

```
Warborn-Renewed/
├── common/          → Shared code (armor system, registry, rendering, packs)
├── forge/           → Forge 1.20.1 platform module
├── neoforge/        → NeoForge 1.21.1 platform module
├── packs/           → Example content packs
└── docs/            → Documentation
```

### Modules

| Module | Platform | Minecraft | Description |
|---|---|---|---|
| `common` | — | — | Shared code: armor sets, GeckoLib models, ballistic system, pack loader |
| `forge` | Forge 47.3.0 | 1.20.1 | Forge-specific platform bindings |
| `neoforge` | NeoForge 21.1.172 | 1.21.1 | NeoForge-specific platform bindings |

---

## 🛡️ Armor Sets

### Helmets
| Name | Material | Protection Class | Bullet Resistance |
|---|---|---|---|
| Panama | Leather | 0 | 0% |
| PASGT | Kevlar | 1 | 30% |
| 6B47 | Kevlar | 1 | 35% |
| Ops-Core | Kevlar | 2 | 40% |
| NATO Helmet | Kevlar | 3 | 40% |

### Body Armor
| Name | Material | Protection Class | Bullet Resistance |
|---|---|---|---|
| IOTV | Ceramic | 4 | 50% |
| JPC | AR500 Steel | 4 | 55% |
| 6B45 Ratnik | UHMWPE | 5 | 55% |
| Warmor Gen.3 | Composite | 5 | 60% |
| UWIN | Ceramic | 5 | 60% |

### Special Equipment
| Name | Description |
|---|---|
| NVG Helmets | 6B47, Ops-Core, and NATO helmets with toggleable night/thermal vision |
| Ghillie Suits | Full concealment set in 3 camo variants |
| Press Armor | Journalist protection set (helmet + vest) |
| REW Backpack | Electronic warfare with drone jamming radius |
| Binoculars | Zoom-capable scouting tool |

---

## 📦 Content Pack System

Warborn Renewed supports **custom content packs** — add your own armor without writing Java code!

A content pack consists of:
- **JSON config** — armor stats (`defense`, `toughness`, `durability`, etc.)
- **3D model** — GeckoLib `.geo.json` model
- **Texture** — `.png` texture file
- **Localization** — multilingual names directly in JSON or via `lang/` folder

### Quick Example

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

📖 **Full guide:** [Creating Custom Packs (EN)](docs/CREATING_CUSTOM_PACK_EN.md) | [Создание паков (RU)](docs/CREATING_CUSTOM_PACK.md)

---

## 🔧 Building from Source

### Prerequisites

- **Java 17+** (for Forge 1.20.1) / **Java 21+** (for NeoForge 1.21.1)
- **Gradle 8.8** (included via wrapper)

### Build Commands

```bash
# Build NeoForge (1.21.1)
./gradlew :neoforge:build

# Build Forge (1.20.1)
./gradlew :forge:build

# Build all
./gradlew build
```

Output JARs will be located in `forge/build/libs/` and `neoforge/build/libs/`.

---

## 📚 Documentation

| Document | Language | Description |
|---|---|---|
| [Creating Custom Packs](docs/CREATING_CUSTOM_PACK_EN.md) | English | Full guide to creating content packs |
| [Создание паков](docs/CREATING_CUSTOM_PACK.md) | Русский | Полное руководство по созданию паков |
| [Ballistic System Guide](docs/BALLISTIC_SYSTEM_GUIDE.md) | English | Armor protection mechanics documentation |
| [Localization Guide](docs/LOCALIZATION_GUIDE.md) | English | How to add translations |

---

## 🔌 Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| [GeckoLib](https://github.com/bernie-g/geckolib) | 4.x | 3D model rendering & animations |
| [Curios API](https://github.com/TheIllusiveC4/Curios) | — | Equipment slot system |

### Optional Compatibility

| Mod | Integration |
|---|---|
| **TACZ** | Bullet resistance attributes |
| **SuperbWarfare** | Bullet resistance attributes |
| **WRB Drones** | REW backpack drone jamming |

---

## 📝 License

**All Rights Reserved** © Liko

This mod and its source code are provided for reference purposes. Redistribution, modification, or commercial use without explicit permission is prohibited.

---

<div align="center">

**Made with ❤️ for the Minecraft military community**

</div>
