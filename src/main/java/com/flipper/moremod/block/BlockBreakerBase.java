package com.flipper.moremod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
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

    /// 周围有方块更新时调用
    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            this.checkIfExtend(state, level, pos);
        }

    }

    //被放置时触发
    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock()) && !level.isClientSide && level.getBlockEntity(pos) == null) {
            this.checkIfExtend(state,level, pos);
        }

    }


    ///判断是否伸出的辅助函数，便于调用
    private void checkIfExtend(BlockState state, Level level, BlockPos pos) {
        Direction direction = state.getValue(FACING);

        boolean flag = hasSignal(level, pos, direction);
        boolean extended = state.getValue(EXTENDED);

        if (flag && !extended) {
            BlockPos targetPos = pos.relative(direction);
            BlockState targetState = level.getBlockState(targetPos);

            //防御性编程
            if(targetState.is(ModBlocks.BLOCK_BREAKER_HEAD.get())){
                return;
            }

            //破坏并推出头部
            if(canBreak(level, targetPos)) {

                BlockState newBaseState = state.setValue(EXTENDED, true);
                level.setBlock(pos, newBaseState, 3);

                if(!targetState.isAir()){
                    level.levelEvent(2001, targetPos, Block.getId(targetState));
                    level.destroyBlock(targetPos, true);
                }

                BlockState newHeadState = ModBlocks.BLOCK_BREAKER_HEAD.get().defaultBlockState()
                        .setValue(FACING, direction);
                level.setBlock(targetPos, newHeadState, 3);

            }

        }else if (!flag && extended) {
            BlockPos targetPos = pos.relative(direction);
            BlockState targetState = level.getBlockState(targetPos);


            if(targetState.is(ModBlocks.BLOCK_BREAKER_HEAD)) {
                level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
            }

            BlockState newBaseState = state.setValue(EXTENDED, false);
            level.setBlock(pos, newBaseState, 3);

        }
    }

    private boolean hasSignal(Level level, BlockPos pos, Direction p_dir) {
        for(Direction direction : Direction.values()) {
            //忽略前方的信号
            if (direction != p_dir && level.hasSignal(pos.relative(direction), direction)) {
                return true;
            }
        }
        return false;
    }

    private boolean canBreak(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        if(state.is(Blocks.AIR)) {
            return true;
        }

        if(state.getBlock() instanceof LiquidBlock) {
            return true;
        }

        return state.getDestroySpeed(level, pos) >= 0.0f;
    }
}
