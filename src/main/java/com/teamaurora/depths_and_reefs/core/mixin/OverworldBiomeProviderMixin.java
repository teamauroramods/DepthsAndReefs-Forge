package com.teamaurora.depths_and_reefs.core.mixin;

import com.google.common.collect.ImmutableList;
import com.teamaurora.depths_and_reefs.core.registry.DRBiomes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.layer.Layer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
Differences from Fabric:
VanillaLayeredBiomeSource -> OverworldBiomeProvider
BiomeLayerSampler genBiomes -> Layer genBiomes
Registry<Biome> biomeRegistry -> Registry<Biome> lookupRegistry
PerlinNoiseSampler -> ImprovedNoiseGenerator
getBiomeForNoiseGen(x,y,z) -> getNoiseBiome
 */

@Mixin(OverworldBiomeProvider.class)
public class OverworldBiomeProviderMixin {
    @Shadow @Final private Layer genBiomes;
    @Shadow @Final private Registry<Biome> lookupRegistry;

    @Unique
    private ImprovedNoiseGenerator seagrassBedsNoise;
    @Unique
    private ImprovedNoiseGenerator riverThiccnessNoise;
    @Unique
    private ImprovedNoiseGenerator tidePoolsNoise;
    @Unique
    private ImprovedNoiseGenerator sandyTidePoolsNoise;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void makeNoise(long seed, boolean legacyBiomeInitLayer, boolean largeBiomes, Registry<Biome> biomeRegistry, CallbackInfo ci) {
        this.seagrassBedsNoise = new ImprovedNoiseGenerator(new Random(seed));
        this.riverThiccnessNoise = new ImprovedNoiseGenerator(new Random(seed + 69420));
        this.tidePoolsNoise = new ImprovedNoiseGenerator(new Random(seed + 283402));
        this.sandyTidePoolsNoise = new ImprovedNoiseGenerator(new Random(seed + 948573));
    }

    private Biome getBiomeFromKey(RegistryKey<Biome> key) {
        Biome biome = (Biome)lookupRegistry.getValueForKey(key);
        if (biome == null) {
            //Util.error("Cave biome surface checker biome ID missing");
            // don't know what the Forge equivalent to above is lol
            return (Biome)lookupRegistry.getValueForKey(BiomeRegistry.getKeyFromID(0));
        } else {
            return biome;
        }
    }

    private List<Biome> getBiomesFromKeys(List<RegistryKey<Biome>> keys) {
        List<Biome> ret = new ArrayList<Biome>();
        for (RegistryKey<Biome> key : keys) {
            ret.add(getBiomeFromKey(key));
        }
        return ret;
    }

    // temporarily an overwrite - will make into an inject when this is split into an API mod TODO

    /**
     * @author SuperCoder79 (original cavegen code)
     * @author ex0planetary (adaptation from cave biomes to ocean biomes)
     */
    @Overwrite
    public Biome getNoiseBiome(int biomeX, int biomeY, int biomeZ) {
        Biome defaultBiome = this.genBiomes.func_242936_a(this.lookupRegistry, biomeX, biomeZ);
        boolean isOceanBiome = defaultBiome.getCategory() == Biome.Category.OCEAN;

        double sbFreq = 95.0;
        float sbThres = 0.41F;

        double rivFreq = 700.0F;
        double rivNoiseVal = this.riverThiccnessNoise.func_215456_a(biomeX / rivFreq, 0, biomeZ / rivFreq, 0.0, 0.0);

        double tpFreq = 105.0;
        float tpThres = 0.33F;
        double stpFreq = 130.0;
        float stpThres = 0.36F;

        // 1-2 OATMEAL kirby is a pink guy
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.sqrt(x * x + z * z) <= 1.5 + (rivNoiseVal / 2)) {
                    if (this.genBiomes.func_242936_a(this.lookupRegistry, biomeX + x, biomeZ + z) == getBiomeFromKey(Biomes.RIVER)) {
                        return this.lookupRegistry.getValueForKey(Biomes.RIVER);
                    }
                }
            }
        }
        if (isOceanBiome) {
            for (int x = -6; x <= 6; x++) {
                for (int z = -6; z <= 6; z++) {
                    if (Math.sqrt(x*x + z*z) <= 6) {
                        if (this.genBiomes.func_242936_a(this.lookupRegistry, biomeX + x, biomeZ + z) == getBiomeFromKey(Biomes.RIVER)) {
                            return this.lookupRegistry.getValueForKey(Biomes.RIVER);
                        }
                        if (this.genBiomes.func_242936_a(this.lookupRegistry, biomeX + x, biomeZ + z) == getBiomeFromKey(Biomes.BEACH)) {
                            if (this.tidePoolsNoise.func_215456_a(biomeX / tpFreq, 0, biomeZ / tpFreq, 0.0, 0.0) > tpThres) {
                                return this.lookupRegistry.getValueForKey(DRBiomes.TIDE_POOLS.getKey());
                            } else if (this.sandyTidePoolsNoise.func_215456_a(biomeX / stpFreq, 0, biomeZ / stpFreq, 0.0, 0.0) > stpThres) {
                                return this.lookupRegistry.getValueForKey(DRBiomes.SANDY_TIDE_POOLS.getKey());
                            }
                            return this.lookupRegistry.getValueForKey(Biomes.BEACH);
                        }
                        if (this.genBiomes.func_242936_a(this.lookupRegistry, biomeX + x, biomeZ + z) == getBiomeFromKey(Biomes.SNOWY_BEACH)) {
                            return this.lookupRegistry.getValueForKey(Biomes.SNOWY_BEACH);
                        }
                    }
                }
            }

            if (this.seagrassBedsNoise.func_215456_a(biomeX / sbFreq, 0, biomeZ / sbFreq, 0.0, 0.0) > sbThres && getBiomesFromKeys(ImmutableList.of(Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN)).contains(defaultBiome)) {
                return this.lookupRegistry.getValueForKey(DRBiomes.SEAGRASS_BEDS.getKey());
            }
        }

        if (defaultBiome == getBiomeFromKey(Biomes.BEACH)) {
            if (this.tidePoolsNoise.func_215456_a(biomeX / tpFreq, 0, biomeZ / tpFreq, 0.0, 0.0) > tpThres) {
                return this.lookupRegistry.getValueForKey(DRBiomes.TIDE_POOLS.getKey());
            } else if (this.sandyTidePoolsNoise.func_215456_a(biomeX / stpFreq, 0, biomeZ / stpFreq, 0.0, 0.0) > stpThres) {
                return this.lookupRegistry.getValueForKey(DRBiomes.SANDY_TIDE_POOLS.getKey());
            }
        }

        return defaultBiome;
    }
}
