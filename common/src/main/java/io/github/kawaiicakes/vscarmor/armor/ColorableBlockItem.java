package io.github.kawaiicakes.vscarmor.armor;

import io.github.kawaiicakes.vscarmor.VSCArmorExpectPlatform;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ColorableBlockItem extends BlockItem {
    public ColorableBlockItem(Block block) {
        super(block, VSCArmorExpectPlatform.withTab(new Properties()));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void fillItemCategory(CreativeModeTab creativeModeTab, NonNullList<ItemStack> nonNullList) {
        super.fillItemCategory(creativeModeTab, nonNullList);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(
            ItemStack itemStack,
            @Nullable Level level,
            List<Component> list,
            TooltipFlag tooltipFlag
    ) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);


    }
}
