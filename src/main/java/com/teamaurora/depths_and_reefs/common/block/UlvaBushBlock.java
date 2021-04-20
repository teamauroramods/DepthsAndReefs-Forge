package com.teamaurora.depths_and_reefs.common.block;

import com.teamaurora.depths_and_reefs.core.registry.DRBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class UlvaBushBlock extends BushBlock implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public UlvaBushBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isSolidSide(worldIn, pos, Direction.UP) && !state.isIn(Blocks.MAGMA_BLOCK);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState stateDown = worldIn.getBlockState(pos.down());
        return stateDown.isSolidSide(worldIn, pos, Direction.UP) && !stateDown.isIn(Blocks.MAGMA_BLOCK);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.isValidPosition(worldIn, currentPos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            if (stateIn.get(WATERLOGGED)) {
                worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            }

            return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate1 = this.getDefaultState();
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());

        if (blockstate1.isValidPosition(iworldreader, blockpos)) {
            return blockstate1.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
        }

        return null;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder.add(WATERLOGGED));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
