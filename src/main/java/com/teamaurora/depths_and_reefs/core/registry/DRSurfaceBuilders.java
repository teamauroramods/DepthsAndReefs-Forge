package com.teamaurora.depths_and_reefs.core.registry;

import com.teamaurora.depths_and_reefs.common.world.gen.surfacebuilders.TidePoolsSurfaceBuilder;
import com.teamaurora.depths_and_reefs.core.DepthsAndReefs;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DepthsAndReefs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DRSurfaceBuilders {
    public static final SurfaceBuilder<SurfaceBuilderConfig> TIDE_POOLS = new TidePoolsSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_);

    @SubscribeEvent
    public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event) {
        event.getRegistry().register(TIDE_POOLS.setRegistryName(DepthsAndReefs.MODID, "tide_pools"));
    }

    public static final class Configs {
        public static final SurfaceBuilderConfig TIDE_POOL_SHALE = new SurfaceBuilderConfig(DRBlocks.SHALE.get().getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.GRAVEL.getDefaultState());
        public static final SurfaceBuilderConfig TIDE_POOL_CONGLOMERATE = new SurfaceBuilderConfig(DRBlocks.CONGLOMERATE.get().getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.GRAVEL.getDefaultState());
    }

    public static final class Configured {
        public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> TIDE_POOLS = DRSurfaceBuilders.TIDE_POOLS.func_242929_a(SurfaceBuilder.GRAVEL_CONFIG);

        private static <SC extends ISurfaceBuilderConfig> void register(String key, ConfiguredSurfaceBuilder<SC> builder) {
            WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(DepthsAndReefs.MODID, key), builder);
        }

        public static void registerConfiguredSurfaceBuilders() {
            register("tide_pools", TIDE_POOLS);
        }
    }
}
