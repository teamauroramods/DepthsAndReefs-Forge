package com.teamaurora.depths_and_reefs.common.block;

import com.teamaurora.depths_and_reefs.common.block.UlvaBushBlock;
import com.teamaurora.depths_and_reefs.core.registry.DRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class UlvaCarpetBlock extends UlvaBushBlock implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public UlvaCarpetBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (worldIn.getBlockState(pos.add(x, 0, z)).isIn(Blocks.WATER)) {
                    worldIn.setBlockState(pos.add(x, 0, z), DRBlocks.ULVA_CARPET.get().getDefaultState());
                }
            }
        }
        worldIn.setBlockState(pos, DRBlocks.ULVA_BUSH.get().getDefaultState());
    }
}
