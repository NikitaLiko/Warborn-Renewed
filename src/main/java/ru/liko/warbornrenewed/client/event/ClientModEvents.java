package ru.liko.warbornrenewed.client.event;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.client.renderer.WarbornCuriosArmorLayer;

/**
 * Регистрация клиентских рендер-слоев
 * По образцу ClientModEvents из WARBORN-1.20.1
 */
@Mod.EventBusSubscriber(modid = Warbornrenewed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.AddLayers event) {
        if (event == null) {
            return;
        }

        // Добавляем слой для всех скинов игрока
        for (String skinName : event.getSkins()) {
            @SuppressWarnings("deprecation")
            PlayerRenderer playerRenderer = event.getSkin(skinName);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new WarbornCuriosArmorLayer<>(playerRenderer));
            }
        }
    }
}
