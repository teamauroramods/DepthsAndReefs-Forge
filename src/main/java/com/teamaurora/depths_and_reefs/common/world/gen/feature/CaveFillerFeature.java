package com.teamaurora.depths_and_reefs.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class CaveFillerFeature extends Feature<BlockStateFeatureConfig> {
    public CaveFillerFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    if (Math.sqrt(x*x + y*y + z*z) <= 3) {
                        if (worldIn.getBlockState(pos.add(x, y, z)).getBlock() == Blocks.CAVE_AIR) {
                            worldIn.setBlockState(pos.add(x, y, z), config.state, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
