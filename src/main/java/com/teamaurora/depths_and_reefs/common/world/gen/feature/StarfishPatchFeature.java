package com.teamaurora.depths_and_reefs.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.teamaurora.depths_and_reefs.common.block.StarfishBlock;
import com.teamaurora.depths_and_reefs.core.registry.DRBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class StarfishPatchFeature extends Feature<NoFeatureConfig> {
    public StarfishPatchFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int i = 0;

        for (int x = -5; x <= 5; x++) {
            for (int y = -5; y <= 5; y++) {
                for (int z = -5; z <= 5; z++) {
                    if (Math.sqrt(x*x+y*y+z*z) < 5 && rand.nextInt(10) == 0 && reader.getBlockState(pos.add(x, y-1, z)).isSolid()) {
                        StarfishBlock starfish;
                        int variant = rand.nextInt(5);
                        if (variant == 0) {
                            starfish = (StarfishBlock) DRBlocks.YELLOW_STARFISH.get();
                        } else if (variant == 1) {
                            starfish = (StarfishBlock) DRBlocks.RED_STARFISH.get();
                        } else if (variant == 2) {
                            starfish = (StarfishBlock) DRBlocks.BLUE_STARFISH.get();
                        } else if (variant == 3) {
                            starfish = (StarfishBlock) DRBlocks.PINK_STARFISH.get();
                        } else {
                            starfish = (StarfishBlock) DRBlocks.PURPLE_STARFISH.get();
                        }
                        if (reader.isAirBlock(pos.add(x,y,z))) {
                            reader.setBlockState(pos.add(x,y,z), starfish.getDefaultState(), 3);
                            i++;
                        } else if (reader.getBlockState(pos.add(x,y,z)).getBlock() == Blocks.WATER) {
                            reader.setBlockState(pos.add(x,y,z), starfish.getDefaultState().with(StarfishBlock.WATERLOGGED, true), 3);
                            i++;
                        }
                    }
                }
            }
        }

        return i > 0;
    }
}
