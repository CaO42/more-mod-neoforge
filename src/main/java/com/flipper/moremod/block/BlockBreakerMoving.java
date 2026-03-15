package com.flipper.moremod.block;

import com.flipper.moremod.block.entity.BlockBreakerMovingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBreakerMoving extends Block implements EntityBlock {
    public BlockBreakerMoving(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlockBreakerMovingEntity(pos, state);
    }
}
