package com.teamaurora.depths_and_reefs.common.world.biome;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.teamaurora.depths_and_reefs.core.DepthsAndReefs;
import com.teamaurora.depths_and_reefs.core.registry.DRBiomes;
import com.teamaurora.depths_and_reefs.core.registry.DRFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = DepthsAndReefs.MODID)
public class DRBiomeFeatures {
    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event) {
        ResourceLocation biomeName = event.getName();

        if (biomeName == null)
            return;

        if (DataUtil.matchesKeys(biomeName, Biomes.BEACH, Biomes.SNOWY_BEACH)) {
            List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.LAKES);
            if (event.getName() != null) {
                List<Supplier<ConfiguredFeature<?, ?>>> toRemove = new ArrayList<>();
                for (Supplier<ConfiguredFeature<?, ?>> configuredFeatureSupplier : features) {
                    IFeatureConfig config = configuredFeatureSupplier.get().config;
                    if (config instanceof DecoratedFeatureConfig) {
                        IFeatureConfig config1 = ((DecoratedFeatureConfig) config).feature.get().config;
                        if (config1 instanceof BlockStateFeatureConfig) {
                            BlockStateFeatureConfig bsfconfig = (BlockStateFeatureConfig) config1;

                            if (bsfconfig.state.getBlock() == Blocks.WATER) {
                                toRemove.add(configuredFeatureSupplier);
                            }
                        }
                    }
                }
                toRemove.forEach(features::remove);
            }
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DRFeatures.Configured.BEACH_CAVE_FILLER);
            event.getGeneration().withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DRFeatures.Configured.CONGLOMERATE_BOULDER_SPARSE);
            //event.getGeneration().withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DRFeatures.Configured.CONGLOMERATE_BOULDER_SPARSE_SURFACE);
        }

        if (DataUtil.matchesKeys(biomeName, DRBiomes.SEAGRASS_BEDS.getKey())) {
            withSeagrassBedsFeatures(event.getGeneration(), event.getSpawns());
        }

        if (DataUtil.matchesKeys(biomeName, DRBiomes.TIDE_POOLS.getKey())) {
            withTidePoolsFeatures(event.getGeneration(), event.getSpawns());
        }
    }

    public static void withSeagrassBedsFeatures(BiomeGenerationSettingsBuilder builder, MobSpawnInfoBuilder spawns) {
        addBaseOceanFeatures(builder, false, true, false);

        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.ULVA_PATCH_DECORATED);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.SEAGRASS_PATCH_DECORATED);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.ALGAE_PATCH_DECORATED);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.SMALL_KELP_DECORATED);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.SPARSE_SEAGRASS);
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DRFeatures.Configured.CONGLOMERATE_BOULDER_COMMON);

        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEA_PICKLE);

        DefaultBiomeFeatures.withFrozenTopLayer(builder);

        DefaultBiomeFeatures.withOceanMobs(spawns, 5, 2, 10);
        spawns.withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.TROPICAL_FISH, 20, 8, 8))
                .withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.DOLPHIN, 2, 1, 2))
                .withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.TURTLE, 10, 1, 4));
        // TODO: Mixin to WorldEntitySpawner.func_234975_a_ to make it so turtles can spawn underwater as well
    }

    public static void withTidePoolsFeatures(BiomeGenerationSettingsBuilder builder, MobSpawnInfoBuilder spawns) {
        DefaultBiomeFeatures.withBatsAndHostiles(spawns);
        builder.withStructure(StructureFeatures.MINESHAFT);
        builder.withStructure(StructureFeatures.BURIED_TREASURE);
        builder.withStructure(StructureFeatures.SHIPWRECK_BEACHED);

        builder.withFeature(GenerationStage.Decoration.RAW_GENERATION, DRFeatures.Configured.CONGLOMERATE_TIDE_DISK);
        builder.withFeature(GenerationStage.Decoration.RAW_GENERATION, DRFeatures.Configured.SHALE_TIDE_DISK);
        builder.withFeature(GenerationStage.Decoration.RAW_GENERATION, DRFeatures.Configured.SAND_TIDE_DISK);

        builder.withFeature(GenerationStage.Decoration.LAKES, DRFeatures.Configured.TIDE_POOL);

        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DRFeatures.Configured.CONGLOMERATE_BOULDER_COMMON_SURFACE);
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DRFeatures.Configured.SHALE_BOULDER_COMMON_SURFACE);
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DRFeatures.Configured.TIDE_CONGLOMERATE_TIDE_DISK);
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DRFeatures.Configured.TIDE_SHALE_TIDE_DISK);

        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_RIVER);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.STARFISH_PATCH);

        builder.withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withDefaultFlowers(builder);
        DefaultBiomeFeatures.withBadlandsGrass(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);
    }

    private static void addBaseOceanFeatures(BiomeGenerationSettingsBuilder builder, boolean hasOceanMonument, boolean isWarmOcean, boolean isDeepVariant) {
        StructureFeature<?, ?> structurefeature = isWarmOcean ? StructureFeatures.OCEAN_RUIN_WARM : StructureFeatures.OCEAN_RUIN_COLD;
        if (isDeepVariant) {
            if (hasOceanMonument) {
                builder.withStructure(StructureFeatures.MONUMENT);
            }

            DefaultBiomeFeatures.withOceanStructures(builder);
            builder.withStructure(structurefeature);
        } else {
            builder.withStructure(structurefeature);
            if (hasOceanMonument) {
                builder.withStructure(StructureFeatures.MONUMENT);
            }

            DefaultBiomeFeatures.withOceanStructures(builder);
        }

        builder.withStructure(StructureFeatures.RUINED_PORTAL_OCEAN);
        DefaultBiomeFeatures.withOceanCavesAndCanyons(builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withTreesInWater(builder);
        DefaultBiomeFeatures.withDefaultFlowers(builder);
        DefaultBiomeFeatures.withBadlandsGrass(builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(builder);
    }
}
