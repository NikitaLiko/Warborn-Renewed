package ru.liko.warbornrenewed.client.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;

/**
 * Manages vision shader effects (NVG, Thermal) for helmets
 * This is a placeholder system - actual shader files need to be created
 */
@OnlyIn(Dist.CLIENT)
public class VisionShaderManager {
    
    // Shader resource locations (PLACEHOLDER - create actual shader files)
    private static final ResourceLocation NVG_SHADER = new ResourceLocation(Warbornrenewed.MODID, "shaders/post/nvg.json");
    private static final ResourceLocation THERMAL_SHADER = new ResourceLocation(Warbornrenewed.MODID, "shaders/post/thermal.json");
    
    @Nullable
    private static PostChain activeShader = null;
    private static VisionMode currentMode = VisionMode.NONE;
    private static int lastWidth = -1;
    private static int lastHeight = -1;
    
    /**
     * Update shader based on equipped helmet
     * Called every render tick
     */
    public static void tick(Minecraft mc) {
        Player player = mc.player;
        if (player == null) {
            disableShader();
            return;
        }
        
        // Check helmet slot
        ItemStack helmet = player.getInventory().getArmor(3); // Helmet slot
        if (helmet.isEmpty() || !(helmet.getItem() instanceof WarbornArmorItem armorItem)) {
            disableShader();
            return;
        }
        
        // Determine which vision mode should be active
        VisionMode newMode = determineVisionMode(armorItem, helmet);
        
        // Update shader if mode changed
        if (newMode != currentMode) {
            switchShader(newMode, mc);
        }
        
        // Update active shader (if any)
        if (activeShader != null && mc.getWindow() != null) {
            ensureSized(mc);
            activeShader.process(mc.getFrameTime());
        }
    }
    
    /**
     * Determine which vision mode should be active
     */
    private static VisionMode determineVisionMode(WarbornArmorItem armorItem, ItemStack helmet) {
        // Check if NVG is down and available
        if (armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG)) {
            boolean nvgDown = WarbornArmorItem.isNVGDown(helmet);
            if (nvgDown) {
                return VisionMode.NVG;
            }
        }
        
        // Check if thermal is available and helmet is closed
        // (In future: add keybind to toggle between NVG and Thermal)
        if (armorItem.hasVisionCapability(WarbornArmorItem.TAG_THERMAL)) {
            boolean helmetOpen = WarbornArmorItem.isHelmetOpen(helmet);
            // Only activate thermal if helmet is closed (for now)
            // TODO: Add proper thermal toggle keybind
            if (!helmetOpen) {
                // Thermal is secondary to NVG for now
                // return VisionMode.THERMAL;
            }
        }
        
        return VisionMode.NONE;
    }
    
    /**
     * Switch to a different shader mode
     */
    private static void switchShader(VisionMode mode, Minecraft mc) {
        // Cleanup old shader
        disableShader();
        
        currentMode = mode;
        
        // Load new shader
        switch (mode) {
            case NVG -> loadShader(NVG_SHADER, mc);
            case THERMAL -> loadShader(THERMAL_SHADER, mc);
            case NONE -> {} // No shader
        }
    }
    
    /**
     * Load a shader from resource location
     */
    private static void loadShader(ResourceLocation shaderLocation, Minecraft mc) {
        try {
            // Create PostChain from shader resource location
            PostChain shader = new PostChain(
                mc.getTextureManager(),
                mc.getResourceManager(),
                mc.getMainRenderTarget(),
                shaderLocation
            );
            if (mc.getWindow() != null) {
                shader.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
                lastWidth = mc.getWindow().getWidth();
                lastHeight = mc.getWindow().getHeight();
            }
            activeShader = shader;
            
            Warbornrenewed.LOGGER.info("Successfully loaded shader: {}", shaderLocation);
            
        } catch (Exception e) {
            Warbornrenewed.LOGGER.error("Failed to load shader: {}", shaderLocation, e);
            activeShader = null;
        }
    }
    
    /**
     * Disable current shader
     */
    public static void disableShader() {
        if (activeShader != null) {
            activeShader.close();
            activeShader = null;
        }
        currentMode = VisionMode.NONE;
        lastWidth = -1;
        lastHeight = -1;
    }
    
    /**
     * Check if a shader is currently active
     */
    public static boolean isShaderActive() {
        return activeShader != null && currentMode != VisionMode.NONE;
    }
    
    /**
     * Get current vision mode
     */
    public static VisionMode getCurrentMode() {
        return currentMode;
    }

    /**
     * Notify the manager that client resources were reloaded
     */
    public static void onResourceReload() {
        disableShader();
    }

    private static void ensureSized(Minecraft mc) {
        if (activeShader == null || mc.getWindow() == null) {
            return;
        }

        int width = mc.getWindow().getWidth();
        int height = mc.getWindow().getHeight();
        if (width != lastWidth || height != lastHeight) {
            activeShader.resize(width, height);
            lastWidth = width;
            lastHeight = height;
        }
    }
    
    /**
     * Vision modes
     */
    public enum VisionMode {
        NONE,     // No shader active
        NVG,      // Night vision shader
        THERMAL   // Thermal vision shader
    }
}
