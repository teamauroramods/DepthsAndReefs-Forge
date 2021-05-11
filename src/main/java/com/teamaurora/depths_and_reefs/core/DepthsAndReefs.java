package com.teamaurora.depths_and_reefs.core;

import com.minecraftabnormals.abnormals_core.core.util.registry.RegistryHelper;
import com.teamaurora.depths_and_reefs.core.registry.DRBiomes;
import com.teamaurora.depths_and_reefs.core.registry.DRFeatures;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.teamaurora.depths_and_reefs.core.DepthsAndReefs.MODID;

@Mod(MODID)
public class DepthsAndReefs
{
    public static final String MODID = "depths_and_reefs";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);

    public DepthsAndReefs() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRY_HELPER.register(eventBus);

        DRFeatures.FEATURES.register(eventBus);

        eventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DRFeatures.Configured.registerConfiguredFeatures();
            DRBiomes.addBiomeTypes();
            DRBiomes.addHillBiomes();
        });
    }
}
