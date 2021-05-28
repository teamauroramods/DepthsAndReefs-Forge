package com.teamaurora.depths_and_reefs.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class LargeUnderwaterBoulderFeature extends Feature<BlockStateFeatureConfig> {
    public LargeUnderwaterBoulderFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
        int count = 0;

        count += generateSmallBoulder(worldIn, rand, pos, config.state);
        for (int i = 0; i < 4; i++) {
            if (rand.nextBoolean()) {
                count += generateSmallBoulder(worldIn, rand, pos.offset(Direction.byHorizontalIndex(i)), config.state);
            }
        }

        return count > 0;
    }

    private int generateSmallBoulder(ISeedReader worldIn, Random rand, BlockPos pos, BlockState state) {
        int count = 0;
        if (worldIn.getBlockState(pos.up()).isIn(Blocks.WATER) || worldIn.getBlockState(pos.up()).isIn(Blocks.AIR)) {
            worldIn.setBlockState(pos.up(), state, 3);
            count++;
        }
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if ((x == 0 && z == 0) || ((x == 0 || z == 0) && rand.nextInt(4) != 0) || rand.nextInt(4) == 0) {
                    BlockPos newPos = pos.add(x, 0, z);
                    if (worldIn.getBlockState(newPos).isIn(Blocks.WATER) || worldIn.getBlockState(newPos).isIn(Blocks.AIR)) {
                        worldIn.setBlockState(newPos, state, 3);
                        count++;
                    }
                    if (worldIn.getBlockState(newPos.down()).isIn(Blocks.WATER) || worldIn.getBlockState(newPos.down()).isIn(Blocks.AIR)) {
                        worldIn.setBlockState(newPos.down(), state, 3);
                        count++;
                    }
                }
            }
        }
        if (worldIn.getBlockState(pos.down(2)).isIn(Blocks.WATER)) {
            worldIn.setBlockState(pos.down(2), state, 3);
            count++;
        }
        return count;
    }
}
