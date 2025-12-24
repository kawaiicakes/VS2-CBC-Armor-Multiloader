package io.github.kawaiicakes.vscarmor.armor;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorExpectPlatform;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
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

/**
 * {@link BlockItem} representation of this mod's {@link ColorableBlock}s. Handles tooltip, item category sorting, and
 * (expected to) handle dynamic item model rendering.
 */
public class ColorableBlockItem extends BlockItem {
    public ColorableBlockItem(Properties properties, Block block) {
        super(block, VSCArmorExpectPlatform.withTab(properties, ((ColorableBlock) block).getGrade()));
    }

    // FIXME - random order
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

    // TODO (fix when putting layers into one array)
    public int getLayerColor(ItemStack stack, int layer) {
        final CompoundTag beData = BlockItem.getBlockEntityData(stack);
        if (beData == null) return 0xFFFFFF;

        final CompoundTag beTag = beData.getCompound(VSCArmor.MOD_ID);
        // return beTag.getIntArray("layers")[layer];
        return beTag.getInt("body");
    }
}
