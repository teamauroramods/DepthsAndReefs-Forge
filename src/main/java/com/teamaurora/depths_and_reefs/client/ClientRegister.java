package com.teamaurora.depths_and_reefs.client;

import com.teamaurora.depths_and_reefs.core.DepthsAndReefs;
import com.teamaurora.depths_and_reefs.core.registry.DRBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DepthsAndReefs.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegister {
    // one day this will do something
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            setupRenderLayer();
        });
    }

    public static void setupRenderLayer() {
        RenderTypeLookup.setRenderLayer(DRBlocks.SEA_ALGAE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DRBlocks.ULVA_BUSH.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DRBlocks.ULVA_CARPET.get(), RenderType.getCutout());
    }
}
