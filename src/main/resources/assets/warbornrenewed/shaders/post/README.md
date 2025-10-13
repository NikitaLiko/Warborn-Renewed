# Vision Shaders - Placeholders

This directory contains **placeholder shader files** for the vision system.

## Current Files

- **nvg.json** - Night Vision Goggles shader (placeholder)
- **thermal.json** - Thermal vision shader (placeholder)

## Implementation Status

⚠️ **PLACEHOLDER**: These shader files are currently placeholders and need to be implemented with actual shader code.

## How to Implement

1. **Study Minecraft's built-in shaders** for reference:
   - `assets/minecraft/shaders/post/spider.json` - Spider vision effect
   - `assets/minecraft/shaders/post/creeper.json` - Creeper vision effect
   - `assets/minecraft/shaders/post/invert.json` - Color inversion

2. **Create shader program files** in `assets/warbornrenewed/shaders/program/`:
   - `nvg_green.json` - Green night vision filter
   - `thermal_vision.json` - Thermal/heat vision filter

3. **Create fragment shaders** in `assets/warbornrenewed/shaders/program/`:
   - `nvg_green.fsh` - GLSL fragment shader for NVG
   - `thermal_vision.fsh` - GLSL fragment shader for thermal

4. **Update VisionShaderManager.java** to properly load shaders:
   - Uncomment the PostChain loading code
   - Add error handling
   - Test with actual helmet equipped

## Expected Behavior

### NVG (Night Vision Goggles)
- Green-tinted overlay
- Brightness amplification
- Slight scanline/grain effect (optional)
- Vignette darkening at edges

### Thermal Vision
- Heat-based color mapping (red/orange for heat, blue/purple for cold)
- Entity highlighting
- Reduced environmental detail
- Color gradient based on temperature

## Testing

Once implemented, test by:
1. Equipping a helmet with NVG capability
2. Pressing N key to toggle NVG down
3. Verifying shader activates correctly
4. Checking performance impact

## Resources

- [Minecraft Wiki - Shaders](https://minecraft.fandom.com/wiki/Shaders)
- [GLSL Fragment Shader Tutorial](https://thebookofshaders.com/)
- [Minecraft Shader Examples](https://github.com/search?q=minecraft+post+shader)

## Notes

- Shaders run on GPU, ensure good performance
- Test on various graphics cards
- Add config options for shader intensity
- Consider compatibility with OptiFine/Iris shaders
