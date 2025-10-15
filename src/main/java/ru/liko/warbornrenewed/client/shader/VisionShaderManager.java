package ru.liko.warbornrenewed.client.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public final class VisionShaderManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisionShaderManager.class);

    private static final String NVG_SHADER_ID = "nvg";
    private static final ResourceLocation NVG_SHADER = Warbornrenewed.id("shaders/post/nvg.json");

    private VisionShaderManager() {
    }

    public static void registerShaders() {
        boolean registered = VisionShaderRegistry.getInstance().registerShader(
            NVG_SHADER_ID,
            NVG_SHADER,
            VisionShaderManager::isNightVisionActive,
            VisionShaderManager::configureNightVision
        );

        if (registered) {
            LOGGER.debug("Registered NVG shader with id '{}'", NVG_SHADER_ID);
        } else {
            LOGGER.debug("NVG shader with id '{}' was already registered", NVG_SHADER_ID);
        }
    }

    public static void processShaders(Minecraft minecraft) {
        VisionShaderRegistry.getInstance().processShaders(minecraft);
    }

    public static void disableShader() {
        VisionShaderRegistry.getInstance().shutdown();
    }

    public static boolean isShaderActive() {
        return VisionShaderRegistry.getInstance().isShaderActive();
    }

    public static void onResourceReload() {
        VisionShaderRegistry.getInstance().onResourceReload();
    }

    private static boolean isNightVisionActive(Minecraft minecraft) {
        if (minecraft == null || minecraft.player == null) {
            return false;
        }

        ItemStack helmet = minecraft.player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty() || !(helmet.getItem() instanceof WarbornArmorItem armorItem)) {
            return false;
        }

        if (!armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG)) {
            return false;
        }

        if (!WarbornArmorItem.isNVGDown(helmet)) {
            return false;
        }

        if (WarbornArmorItem.isHelmetOpen(helmet)) {
            return false;
        }

        return true;
    }

    private static void configureNightVision(PostChain postChain) {
        Minecraft minecraft = Minecraft.getInstance();
        float time = 0.0F;
        if (minecraft != null) {
            if (minecraft.level != null) {
                time = (minecraft.level.getGameTime() + minecraft.getFrameTime()) / 20.0F;
            } else {
                time = minecraft.getFrameTime() / 20.0F;
            }
        }

        List<PostPass> passes = VisionShaderRegistry.getPasses(postChain);
        for (PostPass pass : passes) {
            if (pass.getEffect().getUniform("NightVisionEnabled") != null) {
                pass.getEffect().safeGetUniform("NightVisionEnabled").set(1.0F);
            }
            if (pass.getEffect().getUniform("VignetteEnabled") != null) {
                pass.getEffect().safeGetUniform("VignetteEnabled").set(1.0F);
            }
            if (pass.getEffect().getUniform("Time") != null) {
                pass.getEffect().safeGetUniform("Time").set(time);
            }
        }
    }
}
