package ru.liko.warbornrenewed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.liko.warbornrenewed.client.shader.VisionShaderManager;

/**
 * Mixin to apply vision shaders to all rendering, including first-person hands
 * This fixes the issue where shaders don't apply to player hands
 */
@OnlyIn(Dist.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    
    @Shadow
    @Final
    private Minecraft minecraft;
    
    /**
     * Inject at the end of renderLevel to ensure shader is applied after all rendering
     * Uses TAIL to guarantee execution after hand rendering is complete
     * This ensures the shader is applied to everything including first-person hands
     */
    @Inject(
        method = "renderLevel",
        at = @At("TAIL")
    )
    private void warbornrenewed$applyVisionShader(
        float partialTick, 
        long nanoTime, 
        PoseStack poseStack,
        CallbackInfo ci
    ) {
        // Update and process shader after all world rendering (including hands)
        if (this.minecraft.level != null && this.minecraft.player != null) {
            VisionShaderManager.updateShaderState(this.minecraft);
            VisionShaderManager.processShader(this.minecraft);
        }
    }
}
