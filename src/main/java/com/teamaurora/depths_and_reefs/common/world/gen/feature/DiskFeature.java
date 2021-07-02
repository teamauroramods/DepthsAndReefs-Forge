package com.teamaurora.depths_and_reefs.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SphereReplaceConfig;

import java.util.Random;

public class DiskFeature extends Feature<SphereReplaceConfig> {
    public DiskFeature(Codec<SphereReplaceConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, SphereReplaceConfig config) {
        int i = 0;

        int radius = config.radius.func_242259_a(rand);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (Math.sqrt(x*x+y*y+z*z) < radius && config.targets.contains(reader.getBlockState(pos.add(x, y, z)))) {
                        reader.setBlockState(pos.add(x, y, z), config.state, 3);
                        i++;
                    }
                }
            }
        }

        return i > 0;
    }
}
