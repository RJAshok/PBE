package net.lophius_.pbe.event;

import net.lophius_.pbe.PBEMain;
import net.lophius_.pbe.entity.ModEntities;
import net.lophius_.pbe.entity.custom.client.DarkstoneRenderer;
import net.lophius_.pbe.entity.custom.client.WhitestoneRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.renderer.entity.EntityRenderers;

@Mod.EventBusSubscriber(modid = PBEMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Register your custom renderer
        EntityRenderers.register(ModEntities.DARKSTONE_ENTITY.get(), DarkstoneRenderer::new);
        EntityRenderers.register(ModEntities.WHITESTONE_ENTITY.get(), WhitestoneRenderer::new);
    }
}
