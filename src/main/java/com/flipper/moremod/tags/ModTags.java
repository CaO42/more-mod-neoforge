package com.flipper.moremod.tags;

import com.flipper.moremod.MoreMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final class Blocks {
        public static final TagKey<Block> WRENCHABLE = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(MoreMod.MOD_ID, "wrenchable")
        );
    }

    public static void init(){}
}
