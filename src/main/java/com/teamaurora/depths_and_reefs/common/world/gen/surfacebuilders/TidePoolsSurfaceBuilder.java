package com.teamaurora.depths_and_reefs.common.world.gen.surfacebuilders;

import com.mojang.serialization.Codec;
import com.teamaurora.depths_and_reefs.core.registry.DRSurfaceBuilders;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class TidePoolsSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public TidePoolsSurfaceBuilder(Codec<SurfaceBuilderConfig> p_i232127_1_) {
        super(p_i232127_1_);
    }

    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        if (noise > 0.35D) {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, DRSurfaceBuilders.Configs.TIDE_POOL_SHALE);
        } else if (noise > -0.35D) {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.GRAVEL_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, DRSurfaceBuilders.Configs.TIDE_POOL_CONGLOMERATE);
        }

    }
}
