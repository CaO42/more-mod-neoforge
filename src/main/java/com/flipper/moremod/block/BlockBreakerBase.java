package com.flipper.moremod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class BlockBreakerBase extends Block {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty EXTENDED = BooleanProperty.create("extended");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(EXTENDED);
    }

    public BlockBreakerBase(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(EXTENDED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return  this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            Direction direction = state.getValue(FACING);
            boolean flag = hasSignal(level, pos, direction);
            boolean extended = state.getValue(EXTENDED);
            if (flag && !extended) {

            }else if (!flag && extended) {

            }
        }

    }

    private boolean hasSignal(Level level, BlockPos pos, Direction dir) {
        for(Direction direction : Direction.values()) {
            if (direction != dir && level.hasSignal(pos.relative(dir), dir)) {
                return true;
            }
        }
        return false;
    }
}
