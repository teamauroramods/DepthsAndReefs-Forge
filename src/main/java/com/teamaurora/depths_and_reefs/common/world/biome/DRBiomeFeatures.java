package com.teamaurora.depths_and_reefs.common.world.biome;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.teamaurora.depths_and_reefs.core.DepthsAndReefs;
import com.teamaurora.depths_and_reefs.core.registry.DRBiomes;
import com.teamaurora.depths_and_reefs.core.registry.DRFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DepthsAndReefs.MODID)
public class DRBiomeFeatures {
    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event) {
        ResourceLocation biomeName = event.getName();

        if (biomeName == null)
            return;

        if (DataUtil.matchesKeys(biomeName, DRBiomes.SEAGRASS_BEDS.getKey())) {
            withSeagrassBedsFeatures(event.getGeneration(), event.getSpawns());
        }
    }

    public static void withSeagrassBedsFeatures(BiomeGenerationSettingsBuilder builder, MobSpawnInfoBuilder spawns) {
        addBaseOceanFeatures(builder, false, true, false);

        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.ULVA_PATCH_DECORATED);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.SEAGRASS_PATCH_DECORATED);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.ALGAE_PATCH_DECORATED);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.SMALL_KELP_DECORATED);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DRFeatures.Configured.SPARSE_SEAGRASS);
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DRFeatures.Configured.CONGLOMERATE_BOULDER_DECORATED);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);

        DefaultBiomeFeatures.withOceanMobs(spawns, 5, 2, 10);
        spawns.withSpawner(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(EntityType.TROPICAL_FISH, 20, 8, 8))
                .withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.DOLPHIN, 2, 1, 2))
                .withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.TURTLE, 10, 1, 4));
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
