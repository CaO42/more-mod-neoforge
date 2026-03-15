package com.flipper.moremod.block;

import com.flipper.moremod.MoreMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreMod.MOD_ID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(MoreMod.MOD_ID);


    public static final DeferredBlock<Block> CRUSHER = BLOCKS.registerBlock("crusher", Crusher::new);
    public static final DeferredItem<BlockItem> CRUSHER_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(CRUSHER);

    public static final DeferredBlock<Block> BLOCK_BREAKER_BASE = BLOCKS.registerBlock("block_breaker_base", BlockBreakerBase::new);
    public static final DeferredItem<BlockItem> BLOCK_BREAKER_BASE_ITEM = BLOCK_ITEMS.registerSimpleBlockItem(BLOCK_BREAKER_BASE);
    public static final DeferredBlock<Block> BLOCK_BREAKER_HEAD = BLOCKS.registerBlock("block_breaker_head", BlockBreakerHead::new);
    public static final DeferredBlock<Block> BLOCK_BREAKER_MOVING = BLOCKS.registerBlock("block_breaker_moving", BlockBreakerMoving::new);


}
