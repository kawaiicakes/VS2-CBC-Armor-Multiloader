package io.github.kawaiicakes.vscarmor.block;

import io.github.kawaiicakes.vscarmor.decal.ColorableBlock;
import io.github.kawaiicakes.vscarmor.decal.PatternBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class ArmorBlock extends Block implements ColorableBlock, PatternBlock {
    public ArmorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.createDefaultState(this.defaultBlockState()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        this.addPatternStateDefinition(builder);
    }
}
