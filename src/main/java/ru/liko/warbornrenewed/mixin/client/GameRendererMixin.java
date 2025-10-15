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
     * Inject BEFORE post-processing but AFTER all rendering (including hands)
     * This ensures the shader is applied to everything including first-person hands
     */
    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GameRenderer;tryTakeScreenshotIfNeeded()V",
            shift = At.Shift.BEFORE
        )
    )
    private void warbornrenewed$applyVisionShader(
        float partialTick, 
        long nanoTime, 
        boolean renderLevel, 
        CallbackInfo ci
    ) {
        // Update and process shader after all rendering (including hands)
        if (this.minecraft.level != null && this.minecraft.player != null) {
            VisionShaderManager.updateShaderState(this.minecraft);
            VisionShaderManager.processShader(this.minecraft);
        }
    }
}
