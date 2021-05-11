package com.teamaurora.depths_and_reefs.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.teamaurora.depths_and_reefs.common.block.SeaAlgaeBlock;
import com.teamaurora.depths_and_reefs.common.block.UlvaBushBlock;
import com.teamaurora.depths_and_reefs.common.block.UlvaCarpetBlock;
import com.teamaurora.depths_and_reefs.core.registry.DRBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class UlvaFeature extends Feature<NoFeatureConfig> {
    public UlvaFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos position, NoFeatureConfig config) {
        int count = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos pos = position.add(x, y, z);
                    if (worldIn.getBlockState(pos.down()).isIn(Blocks.SAND) && (worldIn.getBlockState(pos).isIn(Blocks.WATER) || worldIn.getBlockState(pos).isIn(DRBlocks.ULVA_CARPET.get())) && rand.nextFloat() <= 0.4F) {
                        count++;
                        worldIn.setBlockState(pos, DRBlocks.ULVA_BUSH.get().getDefaultState().with(UlvaBushBlock.WATERLOGGED, true), 3);
                        for (int xo = -1; xo <= 1; xo++) {
                            for (int yo = -1; yo <= 1; yo++) {
                                for (int zo = -1; zo <= 1; zo++) {
                                    BlockPos pos2 = pos.add(xo, yo, zo);
                                    if (worldIn.getBlockState(pos2).isIn(Blocks.WATER) && worldIn.getBlockState(pos2.down()).isIn(Blocks.SAND) && (xo == 0 || zo == 0 || rand.nextInt(3) != 0)) {
                                        worldIn.setBlockState(pos2, DRBlocks.ULVA_CARPET.get().getDefaultState().with(UlvaCarpetBlock.WATERLOGGED, true), 3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return count > 0;
    }
}
