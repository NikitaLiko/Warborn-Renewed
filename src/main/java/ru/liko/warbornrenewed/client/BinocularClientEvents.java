package ru.liko.warbornrenewed.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.item.BinocularItem;

/**
 * Клиентские события для бинокля - FOV модификатор (zoom)
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Warbornrenewed.MODID, value = Dist.CLIENT)
public class BinocularClientEvents {

    // Коэффициент увеличения бинокля (0.1 = 10x zoom)
    private static final float BINOCULAR_FOV_MODIFIER = 0.1f;
    
    // Скорость интерполяции zoom (увеличено для быстрого зума)
    private static final float ZOOM_SPEED = 3.0f;
    
    private static float currentZoom = 1.0f;

    @SubscribeEvent
    public static void onComputeFov(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        
        if (player == null) return;
        
        // Проверяем использует ли игрок бинокль
        boolean usingBinocular = player.isUsingItem() && 
                                  player.getUseItem().getItem() instanceof BinocularItem;
        
        float targetZoom = usingBinocular ? BINOCULAR_FOV_MODIFIER : 1.0f;
        
        // Плавная интерполяция zoom
        float delta = Minecraft.getInstance().getDeltaFrameTime() * ZOOM_SPEED;
        if (currentZoom < targetZoom) {
            currentZoom = Math.min(currentZoom + delta, targetZoom);
        } else if (currentZoom > targetZoom) {
            currentZoom = Math.max(currentZoom - delta, targetZoom);
        }
        
        if (usingBinocular) {
            // Применяем FOV модификатор
            event.setNewFovModifier(event.getNewFovModifier() * currentZoom);
        } else {
            // Сброс при отпускании
            currentZoom = 1.0f;
        }
    }
}

