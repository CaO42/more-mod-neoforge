package com.flipper.moremod.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class Crusher extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public Crusher(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    // 所有属性都需要在此函数内加入才可调用
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    // 绝大部分有方向方块都可以如此定义
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return  this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


}
