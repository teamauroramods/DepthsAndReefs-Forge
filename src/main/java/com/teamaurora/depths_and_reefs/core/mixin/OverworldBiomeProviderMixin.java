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

    @Inject(method = "<init>", at = @At("RETURN"))
    private void makeNoise(long seed, boolean legacyBiomeInitLayer, boolean largeBiomes, Registry<Biome> biomeRegistry, CallbackInfo ci) {
        this.seagrassBedsNoise = new ImprovedNoiseGenerator(new Random(seed));
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

        double sbFreq = 70.0;
        float sbThres = 0.44F;

        if (isOceanBiome) {
            for (int x = -6; x <= 6; x++) {
                for (int z = -6; z <= 6; z++) {
                    if (Math.sqrt(x*x + z*z) <= 6) {
                        if (this.genBiomes.func_242936_a(this.lookupRegistry, biomeX + x, biomeZ + z) == getBiomeFromKey(Biomes.RIVER)) {
                            return this.lookupRegistry.getValueForKey(Biomes.RIVER);
                        }
                        if (this.genBiomes.func_242936_a(this.lookupRegistry, biomeX + x, biomeZ + z) == getBiomeFromKey(Biomes.BEACH)) {
                            return this.lookupRegistry.getValueForKey(Biomes.BEACH);
                        }
                        if (this.genBiomes.func_242936_a(this.lookupRegistry, biomeX + x, biomeZ + z) == getBiomeFromKey(Biomes.SNOWY_BEACH)) {
                            return this.lookupRegistry.getValueForKey(Biomes.SNOWY_BEACH);
                        }
                    }
                }
            }

            if (this.seagrassBedsNoise.func_215456_a(biomeX / sbFreq, 0, biomeZ / sbFreq, 0.0, 0.0) > sbThres) {
                return this.lookupRegistry.getValueForKey(DRBiomes.SEAGRASS_BEDS.getKey());
            }
        }

        return defaultBiome;
    }
}
