package com.flipper.moremod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBreakerMovingEntity extends BlockEntity {
    private float progress;
    private Direction facing;
    private boolean extending;

    public BlockBreakerMovingEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.BLOCK_BREAKER_MOVING_ENTITY.get(), pos, blockState);
    }
}
