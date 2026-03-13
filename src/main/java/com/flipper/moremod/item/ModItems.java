package com.flipper.moremod.item;

import com.flipper.moremod.MoreMod;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreMod.MOD_ID);

    public static final DeferredItem<Item> WRENCH = ITEMS.registerItem("wrench", Wrench::new);
    public static final DeferredItem<Item> CREATIVE_WRENCH = ITEMS.registerItem("creative_wrench", CreativeWrench::new);


}
