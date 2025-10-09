# Changelog

## [Unreleased]

### Added
- Comprehensive armor template system with Builder API
- Support for GeckoLib models, textures, and animations
- Integration with SuperbWarfare materials and attributes
- 10+ example armor configurations
- Full documentation (ARMOR_TEMPLATE_GUIDE.md, ARMOR_EXAMPLES.java)
- Automatic armor piece registration system

### Fixed
- Made MaterialProvider and PropertiesProvider public to fix visibility issues
- Fixed protected field access in GeoArmorRenderer using reflection
- Suppressed deprecation warnings for ResourceLocation constructors (will be updated in future Minecraft versions)
- Fixed compilation errors in armor template system

### Technical Details
- ArmorBonesSpec now uses reflection to set protected fields in GeoArmorRenderer
- Added @SuppressWarnings("removal") for deprecated API usage
- Updated WarbornArmorSet.ArmorPieceDefinition interfaces to be public
