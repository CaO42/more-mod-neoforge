package com.flipper.moremod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.PistonType;

public class BlockBreakerHead extends Block {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockBreakerHead(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }



    private boolean isFittingBase(BlockState state, BlockState backblockstate) {
        if (backblockstate.is(ModBlocks.BLOCK_BREAKER_BASE.get())) {
            boolean flag1 = state.getValue(FACING) == backblockstate.getValue(FACING);
            boolean flag2 = backblockstate.getValue(BlockBreakerBase.EXTENDED);
            return flag1 && flag2;
        }
        return false;
    }
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        //玩家为创造模式
        if (!level.isClientSide && player.getAbilities().instabuild) {
            //获取背后方块位置
            BlockPos blockpos = pos.relative((state.getValue(FACING)).getOpposite());
            BlockState blockstate = level.getBlockState(blockpos);
            //是对应方块则摧毁
            if (this.isFittingBase(state, blockstate)) {
                level.setBlock(blockpos, blockstate.setValue(BlockBreakerBase.EXTENDED, false), 3);
                level.destroyBlock(blockpos, false);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            //onRemove需要双端调用
            super.onRemove(state, level, pos, newState, isMoving);
            if(!level.isClientSide) {
                BlockPos blockpos = pos.relative((state.getValue(FACING)).getOpposite());
                BlockState blockstate = level.getBlockState(blockpos);
                //头被破坏时破坏底座
                if (this.isFittingBase(state, level.getBlockState(blockpos))) {
                    level.setBlock(blockpos, blockstate.setValue(BlockBreakerBase.EXTENDED, false), 3);
                    level.destroyBlock(blockpos, true);
                }
            }
        }
    }

    // 当相邻方块发生变化时调用
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        // 如果发生变化的方位正好是 Head 的背后，并且当前的生存条件（canSurvive）不成立
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, currentPos)) {
            // 返回空气，让方块自我销毁掉落
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos.relative((state.getValue(FACING)).getOpposite()));
        return blockstate.is(ModBlocks.BLOCK_BREAKER_BASE.get()) && this.isFittingBase(state, blockstate);
    }
}
