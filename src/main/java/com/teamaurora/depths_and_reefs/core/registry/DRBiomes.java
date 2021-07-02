package com.teamaurora.depths_and_reefs.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.BiomeUtil;
import com.minecraftabnormals.abnormals_core.core.util.registry.BiomeSubRegistryHelper;
import com.mojang.datafixers.util.Pair;
import com.teamaurora.depths_and_reefs.core.DepthsAndReefs;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DepthsAndReefs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DRBiomes {
    private static final BiomeSubRegistryHelper HELPER = DepthsAndReefs.REGISTRY_HELPER.getBiomeSubHelper();

    public static final BiomeSubRegistryHelper.KeyedBiome SEAGRASS_BEDS = HELPER.createBiome("seagrass_beds", () -> makeSeagrassBedsBiome());
    public static final BiomeSubRegistryHelper.KeyedBiome TIDE_POOLS = HELPER.createBiome("tide_pools", () -> makeTidePoolsBiome());

    public static void addHillBiomes() {
        //BiomeUtil.addHillBiome(Biomes.LUKEWARM_OCEAN, Pair.of(SEAGRASS_BEDS.getKey(), 1));
    }

    public static void addBiomeTypes() {
        BiomeDictionary.addTypes(SEAGRASS_BEDS.getKey(), BiomeDictionary.Type.WET, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.OVERWORLD);
        BiomeDictionary.addTypes(TIDE_POOLS.getKey(), BiomeDictionary.Type.WET, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.OVERWORLD);
    }

    private static Biome makeSeagrassBedsBiome() {
        return (new Biome.Builder())
                .precipitation(Biome.RainType.RAIN)
                        .category(Biome.Category.OCEAN)
                        .depth(-0.7F)
                        .scale(0.04F)
                        .temperature(0.5F)
                        .downfall(0.5F)
                        .setEffects((new BiomeAmbience.Builder())
                                .setWaterColor(4566514)
                                .setWaterFogColor(267827)
                                .setFogColor(12638463)
                                .withSkyColor(getSkyColorWithTemperatureModifier(0.5F))
                                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
                                .build())
                        .withMobSpawnSettings(new MobSpawnInfo.Builder().copy())
                        .withGenerationSettings((new BiomeGenerationSettings.Builder())
                                .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244185_q)
                                .build()).build();
    }

    private static Biome makeTidePoolsBiome() {
        return (new Biome.Builder())
                .precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.BEACH)
                .depth(0.0F)
                .scale(0.005F)
                .temperature(0.8F)
                .downfall(0.5F)
                .setEffects((new BiomeAmbience.Builder())
                        .setWaterColor(4566514)
                        .setWaterFogColor(267827)
                        .setFogColor(12638463)
                        .withSkyColor(getSkyColorWithTemperatureModifier(0.8F))
                        .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
                        .build())
                .withMobSpawnSettings(new MobSpawnInfo.Builder().copy())
                .withGenerationSettings((new BiomeGenerationSettings.Builder())
                        .withSurfaceBuilder(DRSurfaceBuilders.Configured.TIDE_POOLS)
                        .build()).build();
    }

    // funni vanilla copy pastes

    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
}
