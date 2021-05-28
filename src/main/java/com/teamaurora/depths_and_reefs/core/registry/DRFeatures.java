package com.teamaurora.depths_and_reefs.core.registry;

import com.teamaurora.depths_and_reefs.common.world.gen.feature.*;
import com.teamaurora.depths_and_reefs.core.DepthsAndReefs;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DepthsAndReefs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DRFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, DepthsAndReefs.MODID);

    public static final RegistryObject<Feature<NoFeatureConfig>> SEAGRASS_PATCH = FEATURES.register("seagrass_patch", ()->new SeagrassPatchFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> ALGAE_PATCH = FEATURES.register("algae_patch", ()->new AlgaePatchFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> ULVA_PATCH = FEATURES.register("ulva_patch", ()->new UlvaFeature(NoFeatureConfig.field_236558_a_));

    public static final RegistryObject<Feature<NoFeatureConfig>> SMALL_KELP_STALK = FEATURES.register("small_kelp_stalk", ()->new SmallKelpStalkFeature(NoFeatureConfig.field_236558_a_));

    public static final RegistryObject<Feature<BlockStateFeatureConfig>> LARGE_BOULDER = FEATURES.register("large_boulder", ()->new LargeUnderwaterBoulderFeature(BlockStateFeatureConfig.field_236455_a_));
    public static final RegistryObject<Feature<BlockStateFeatureConfig>> LARGE_BOULDER_SURFACE = FEATURES.register("large_boulder_surface", ()->new LargeBoulderFeature(BlockStateFeatureConfig.field_236455_a_));
    public static final RegistryObject<Feature<BlockStateFeatureConfig>> CAVE_FILLER = FEATURES.register("cave_filler", ()->new CaveFillerFeature(BlockStateFeatureConfig.field_236455_a_));

    public static final class BlockStates {
        public static final BlockState SAND = Blocks.SAND.getDefaultState();

        public static final BlockState CONGLOMERATE = DRBlocks.CONGLOMERATE.get().getDefaultState();
    }

    public static final class Configs {
        public static final BlockStateFeatureConfig SAND_CONFIG = new BlockStateFeatureConfig(BlockStates.SAND);

        public static final BlockStateFeatureConfig CONGLOMERATE_CONFIG = new BlockStateFeatureConfig(BlockStates.CONGLOMERATE);
    }

    public static final class Configured {
        //public static final ConfiguredFeature<NoFeatureConfig, ?> SEAGRASS_PATCH = DRFeatures.SEAGRASS_PATCH.get().withConfiguration(NoFeatureConfig.field_236559_b_);
        //public static final ConfiguredFeature<NoFeatureConfig, ?> ALGAE_PATCH = DRFeatures.ALGAE_PATCH.get().withConfiguration(NoFeatureConfig.field_236559_b_);
        //public static final ConfiguredFeature<NoFeatureConfig, ?> ULVA_PATCH = DRFeatures.ULVA_PATCH.get().withConfiguration(NoFeatureConfig.field_236559_b_);

        //public static final ConfiguredFeature<NoFeatureConfig, ?> SMALL_KELP = DRFeatures.SMALL_KELP_STALK.get().withConfiguration(NoFeatureConfig.field_236559_b_);

        public static final ConfiguredFeature<BlockStateFeatureConfig, ?> CONGLOMERATE_BOULDER = LARGE_BOULDER.get().withConfiguration(Configs.CONGLOMERATE_CONFIG);
        public static final ConfiguredFeature<BlockStateFeatureConfig, ?> CONGLOMERATE_BOULDER_SURFACE = LARGE_BOULDER_SURFACE.get().withConfiguration(Configs.CONGLOMERATE_CONFIG);

        public static final ConfiguredFeature<?, ?> SEAGRASS_PATCH_DECORATED = SEAGRASS_PATCH.get().withConfiguration(NoFeatureConfig.field_236559_b_).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 0.35F, 1)));
        public static final ConfiguredFeature<?, ?> ALGAE_PATCH_DECORATED = ALGAE_PATCH.get().withConfiguration(NoFeatureConfig.field_236559_b_).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(3, 0.25F, 1)));
        public static final ConfiguredFeature<?, ?> ULVA_PATCH_DECORATED = ULVA_PATCH.get().withConfiguration(NoFeatureConfig.field_236559_b_).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.35F, 1)));
        public static final ConfiguredFeature<?, ?> SMALL_KELP_DECORATED = SMALL_KELP_STALK.get().withConfiguration(NoFeatureConfig.field_236559_b_).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.75F, 1)));

        public static final ConfiguredFeature<?, ?> CONGLOMERATE_BOULDER_COMMON = CONGLOMERATE_BOULDER.withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
        public static final ConfiguredFeature<?, ?> CONGLOMERATE_BOULDER_SPARSE = CONGLOMERATE_BOULDER.withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(5)));
        public static final ConfiguredFeature<?, ?> CONGLOMERATE_BOULDER_SPARSE_SURFACE = CONGLOMERATE_BOULDER_SURFACE.withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(3)));

        public static final ConfiguredFeature<?, ?> SPARSE_SEAGRASS = Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.6F)).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(8, 0.33F, 4)));

        public static final ConfiguredFeature<?, ?> BEACH_CAVE_FILLER = CAVE_FILLER.get().withConfiguration(Configs.SAND_CONFIG).withPlacement(Placement.HEIGHTMAP.configure(NoPlacementConfig.INSTANCE));

        private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(DepthsAndReefs.MODID, name), configuredFeature);
        }

        public static void registerConfiguredFeatures() {
            //register("seagrass_patch", SEAGRASS_PATCH);
            //register("algae_patch", ALGAE_PATCH);
            //register("ulva_patch", ULVA_PATCH);

            //register("small_kelp", SMALL_KELP);

            register("conglomerate_boulder", CONGLOMERATE_BOULDER);
            register("conglomerate_boulder_surface", CONGLOMERATE_BOULDER_SURFACE);

            register("seagrass_patch_decorated", SEAGRASS_PATCH_DECORATED);
            register("algae_patch_decorated", ALGAE_PATCH_DECORATED);
            register("ulva_patch_decorated", ULVA_PATCH_DECORATED);
            register("small_kelp_decorated", SMALL_KELP_DECORATED);
            register("conglomerate_boulder_common", CONGLOMERATE_BOULDER_COMMON);
            register("conglomerate_boulder_sparse", CONGLOMERATE_BOULDER_SPARSE);
            register("conglomerate_boulder_sparse_surface", CONGLOMERATE_BOULDER_SPARSE_SURFACE);

            register("sparse_seagrass", SPARSE_SEAGRASS);

            register("beach_cave_filler", BEACH_CAVE_FILLER);
        }
    }
}
