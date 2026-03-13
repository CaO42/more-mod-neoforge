package com.flipper.moremod;

import com.flipper.moremod.block.ModBlocks;
import com.flipper.moremod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.List;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreMod.MOD_ID);

    /// 物品的列表
    public static final Collection<DeferredHolder<Item, Item>> MORE_MOD_ITEMS = List.of(
            ModItems.WRENCH,
            ModItems.CREATIVE_WRENCH
    );

    public static final Collection<DeferredHolder<Item, BlockItem>> MORE_MOD_BLOCK_ITEMS = List.of(
            ModBlocks.CRUSHER_ITEM,
            ModBlocks.BLOCK_BREAKER_BASE_ITEM
    );


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MORE_MOD_TAB = CREATIVE_MODE_TABS.register(
            "more_mod_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.moremod.more_mod_tab"))
                    .icon(() -> ModItems.WRENCH.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for(DeferredHolder<Item, Item> itemHolder : MORE_MOD_ITEMS) {
                            output.accept(itemHolder.get());
                        }
                        for(DeferredHolder<Item, BlockItem> itemHolder : MORE_MOD_BLOCK_ITEMS) {
                            output.accept(itemHolder.get());
                        }
                    })
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build()
    );
}
