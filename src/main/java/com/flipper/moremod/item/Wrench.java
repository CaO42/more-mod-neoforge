package com.flipper.moremod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Wrench extends Item {
    public Wrench(Properties properties) {
        super(properties
                .stacksTo(1)
                .durability(2048)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) { return InteractionResult.PASS; }

        if(!level.isClientSide) {
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);

            //判断有无方向
            StateDefinition<Block, BlockState> definition = state.getBlock().getStateDefinition();
            Property<?> property = definition.getProperties().stream()
                    .filter(p -> p instanceof DirectionProperty)
                    .findFirst()
                    .orElse(null);
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
            }



        }else {
            player.displayClientMessage(Component.literal("wrench used"), false);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
