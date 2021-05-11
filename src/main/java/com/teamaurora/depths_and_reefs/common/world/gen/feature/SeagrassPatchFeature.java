package com.teamaurora.depths_and_reefs.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.teamaurora.depths_and_reefs.common.block.SeaAlgaeBlock;
import com.teamaurora.depths_and_reefs.core.registry.DRBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.SeaGrassBlock;
import net.minecraft.block.TallSeaGrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class SeagrassPatchFeature extends Feature<NoFeatureConfig> {
    public SeagrassPatchFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos position, NoFeatureConfig config) {
        int count = 0;
        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    BlockPos pos = position.add(x, y, z);
                    if ((Math.abs(x) != 3 || Math.abs(z) != 3) && !((Math.abs(x) == 3 && Math.abs(z) == 2) || (Math.abs(x) == 2 && Math.abs(z) == 3))) {
                        if (worldIn.getBlockState(pos).isIn(Blocks.SAND)) {
                            count++;
                            worldIn.setBlockState(pos, DRBlocks.SEAGRASS_PATCH.get().getDefaultState(), 3);
                            if (worldIn.getBlockState(pos.up()).isIn(Blocks.WATER) && rand.nextInt(4) != 0) {
                                if (rand.nextInt(12) == 0) {
                                    // sea algae
                                    worldIn.setBlockState(pos.up(), DRBlocks.SEA_ALGAE.get().getDefaultState().with(SeaAlgaeBlock.WATERLOGGED, true), 3);
                                } else if (rand.nextBoolean()) {
                                    // small seagrass
                                    worldIn.setBlockState(pos.up(), Blocks.SEAGRASS.getDefaultState(), 3);
                                } else if (worldIn.getBlockState(pos.up(2)).isIn(Blocks.WATER)) {
                                    // tall seagrass
                                    ((TallSeaGrassBlock) Blocks.TALL_SEAGRASS).placeAt(worldIn, pos.up(), 3);
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