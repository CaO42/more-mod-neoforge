package com.flipper.moremod.block.entity;

import com.flipper.moremod.MoreMod;
import com.flipper.moremod.block.BlockBreakerMoving;
import com.flipper.moremod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MoreMod.MOD_ID);

    public static final Supplier<BlockEntityType<BlockBreakerMovingEntity>> BLOCK_BREAKER_MOVING_ENTITY =
            BLOCK_ENTITY_TYPES.register(
                    "block_breaker_moving_entity",
                    () -> BlockEntityType.Builder.of(
                            BlockBreakerMovingEntity::new,
                            ModBlocks.BLOCK_BREAKER_MOVING.get()
                    ).build(null)
            );
}
