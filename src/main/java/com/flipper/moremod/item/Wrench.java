package com.flipper.moremod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Wrench extends Item {
    public Wrench(Properties properties) {
        super(properties
                .stacksTo(1)
                .durability(2048)
        );
    }

    // 红石元件白名单
    private static final Set<String> REDSTONE_COMPONENTS = Set.of(
            "RepeaterBlock",      // 红石中继器
            "ComparatorBlock",   // 红石比较器
            "HopperBlock",       // 漏斗
            "DispenserBlock",    // 发射器
            "DropperBlock",      // 投掷器
            "LeverBlock",        // 拉杆
            "ButtonBlock",       // 按钮
            "RedstoneLampBlock", // 红石灯
            "DaylightDetectorBlock" // 阳光传感器
    );

    private boolean isRedstoneComponent(Block block) {
        String className = block.getClass().getSimpleName();
        return REDSTONE_COMPONENTS.contains(className);
    }




    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) { return InteractionResult.PASS; }


        if(!level.isClientSide) {

            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);

            if(!isRedstoneComponent(state.getBlock())) {
                return InteractionResult.PASS;
            }

            //蹲下拆解方块
            if(player.isShiftKeyDown()) {

                if(level instanceof ServerLevel serverLevel) {
                    BlockEntity blockEntity = level.getBlockEntity(pos);

                    List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, blockEntity);

                    level.levelEvent(2001, pos, Block.getId(state));
                    level.removeBlock(pos, false);

                    for(ItemStack drop : drops) {
                        ItemHandlerHelper.giveItemToPlayer(player, drop);
                    }

                    stack.hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
                    return InteractionResult.SUCCESS;
                }

                return InteractionResult.PASS;
            }


            //判断有无方向
            StateDefinition<Block, BlockState> definition = state.getBlock().getStateDefinition();
            Property<?> property = definition.getProperties().stream()
                    .filter(p -> p instanceof DirectionProperty)
                    .findFirst()
                    .orElse(null);
            /*StateDefinition<Block, BlockState> stateDefinition = state.getBlock().getStateDefinition();
            Property<?> property = stateDefinition.getProperty("facing");
            */

            //旋转逻辑
            if (property instanceof DirectionProperty directionProperty) {

                Direction currentFacing = state.getValue(directionProperty);

                Collection<Direction> possibleDirections = directionProperty.getPossibleValues();

                //切换下一个
                List<Direction> directionList = new ArrayList<>(possibleDirections);
                int nextIndex = (directionList.indexOf(currentFacing) + 1) % directionList.size();
                Direction nextFacing = directionList.get(nextIndex);

                BlockState newState = state.setValue(directionProperty, nextFacing);
                level.setBlock(pos, newState, 3);

                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }


        }else {
            player.displayClientMessage(Component.literal("wrench used"), false);
        }
        return InteractionResult.SUCCESS;

    }
}
