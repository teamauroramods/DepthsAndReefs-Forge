package com.teamaurora.depths_and_reefs.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.teamaurora.depths_and_reefs.core.registry.DRBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TidePoolFeature extends Feature<NoFeatureConfig> {
    public TidePoolFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int i = 0;

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (rand.nextInt(7) == 0) {
                    int radius = rand.nextInt(3) + 1;
                    for (int x2 = -radius; x2 <= radius; x2++) {
                        for (int z2 = -radius; z2 <= radius; z2++) {
                            if (Math.sqrt(x2*x2+z2*z2) < radius && rand.nextInt(5) <= 2 && safeWaterPlace(world, rand, pos.add(x+x2, -1, z+z2))) {
                                i++;
                            }
                        }
                    }
                }
            }
        }

        return i > 0;
    }

    private boolean safeWaterPlace(ISeedReader world, Random rand, BlockPos pos) {
        List<BlockPos> positions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            positions.add(pos.offset(Direction.byHorizontalIndex(i)));
        }
        positions.add(pos.down());

        for (BlockPos pos2 : positions) {
            if (world.isAirBlock(pos2)) return false;
        }
        if (!world.isAirBlock(pos.up())) return false;

        world.setBlockState(pos.down(), world.getBlockState(pos), 3);
        world.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);

        for (BlockPos pos2 : positions) {
            if (!world.isAirBlock(pos2) && !world.getBlockState(pos2).isIn(Blocks.WATER)) {
                if (rand.nextBoolean()) {
                    world.setBlockState(pos2, DRBlocks.TIDE_CONGLOMERATE.get().getDefaultState(), 3);
                } else {
                    world.setBlockState(pos2, DRBlocks.CONGLOMERATE.get().getDefaultState(), 3);
                }
            }
        }

        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    if (Math.sqrt(x*x+y*y+z*z) < 3) {
                        if (world.getBlockState(pos.add(x,y,z)).getBlock() == DRBlocks.CONGLOMERATE.get() && rand.nextInt(4) == 0) {
                            world.setBlockState(pos.add(x,y,z), DRBlocks.TIDE_CONGLOMERATE.get().getDefaultState(), 3);
                        } else if (world.getBlockState(pos.add(x,y,z)).getBlock() == DRBlocks.SHALE.get() && rand.nextInt(4) == 0) {
                            world.setBlockState(pos.add(x,y,z), DRBlocks.SHALE.get().getDefaultState(), 3);
                        }
                    }
                }
            }
        }

        return true;
    }
}
