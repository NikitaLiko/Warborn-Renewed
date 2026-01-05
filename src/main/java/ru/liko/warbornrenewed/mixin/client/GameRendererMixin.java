package ru.liko.warbornrenewed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import ru.liko.warbornrenewed.client.shader.VisionShaderManager;
import ru.liko.warbornrenewed.client.shader.VisionShaderRegistry;
import ru.liko.warbornrenewed.Warbornrenewed;

/**
 * Mixin to apply vision shaders to all rendering, including first-person hands
 * This fixes the issue where shaders don't apply to player hands
 */
@OnlyIn(Dist.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    private PostChain postEffect;
    
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
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.level != null && mc.player != null) {
            VisionShaderManager.processShaders(mc);
        }
    }

    @Inject(method = "checkEntityPostEffect", at = @At("HEAD"), cancellable = true)
    private void warbornrenewed$keepVisionShader(net.minecraft.world.entity.Entity entity, CallbackInfo ci) {
        if (warbornrenewed$isVisionPostEffectActive() && warbornrenewed$isWarbornEffect(this.postEffect)) {
            ci.cancel();
        }
    }

    @Inject(method = "shutdownEffect", at = @At("HEAD"), cancellable = true)
    private void warbornrenewed$preventExternalShutdown(CallbackInfo ci) {
        if (warbornrenewed$isVisionPostEffectActive()
            && !VisionShaderRegistry.getInstance().isInternalShutdownInProgress()
            && warbornrenewed$isWarbornEffect(this.postEffect)) {
            ci.cancel();
        }
    }

    @Unique
    private boolean warbornrenewed$isVisionPostEffectActive() {
        return VisionShaderRegistry.getInstance().isShaderActive();
    }

    @Unique
    private boolean warbornrenewed$isWarbornEffect(PostChain effect) {
        if (effect == null) {
            return false;
        }
        String effectName = effect.getName();
        if (effectName == null) {
            return false;
        }
        return effectName.contains(Warbornrenewed.MODID);
    }
}
