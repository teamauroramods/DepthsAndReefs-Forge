package com.teamaurora.depths_and_reefs.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class SmallKelpStalkFeature extends Feature<NoFeatureConfig> {
    public SmallKelpStalkFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos position, NoFeatureConfig config) {
        if (!worldIn.getBlockState(position).isIn(Blocks.WATER)) {
            return false;
        }
        int height = rand.nextInt(3) + 4;
        for (int i = 0; i < height; i++) {
            if (worldIn.getBlockState(position.up(i)).isIn(Blocks.WATER)) {
                if (worldIn.getBlockState(position.up(i-1)).isIn(Blocks.KELP)) {
                    worldIn.setBlockState(position.up(i-1), Blocks.KELP_PLANT.getDefaultState(), 3);
                }
                worldIn.setBlockState(position.up(i), Blocks.KELP.getDefaultState(), 3);
            }
        }

        return true;
    }
}
