# Vision Shaders

Post-process definitions used by Warborn Renewed’s vision system live here.

## Files

- **nvg.json** – Night-vision pipeline (uses `shaders/program/nvg.*`).
- **thermal.json** – Thermal vision pipeline (uses `shaders/program/thermal.*`).

## Pipeline Overview

Each JSON entry wires Minecraft’s built-in post-processing chain:

1. Declare the render targets to ping-pong between (`targets`).
2. Reference the program (`passes[].name`) that binds our fragment shader.
3. Provide auxiliary targets or default uniform values as needed.
4. Finish with a `blit` pass that copies the processed frame back to `minecraft:main`.

## Adding New Vision Effects

1. Create a shader program definition under `assets/warbornrenewed/shaders/program/`.
2. Author the fragment shader (`*.fsh`) that implements the look.
3. Point a new post-definition at that program and register it via `VisionShaderManager`.
4. Add an activation predicate in `VisionShaderManager` so the effect toggles for the right helmet or item.

## Testing Checklist

- Equip a helmet that advertises the relevant vision capability.
- Toggle the vision mode with the bound key (default: `N`).
- Verify the shader engages/disengages, including in first-person hands (handled by `GameRendererMixin`).
- Check behaviour in bright vs. pitch-black scenes to ensure automatic brightness scaling works as expected.
