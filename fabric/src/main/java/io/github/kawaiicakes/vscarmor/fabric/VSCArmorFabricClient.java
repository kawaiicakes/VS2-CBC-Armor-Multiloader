package io.github.kawaiicakes.vscarmor.fabric;

import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.armor.ColorableBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VSCArmorFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register(
                (BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos blockPos, int i) -> {
                    if (level == null || blockPos == null) return 0xFFFFFF;
                    ColorableBlockEntity colorableBE = level
                            .getBlockEntity(blockPos, VSCArmorRegistry.colorableBEType())
                            .orElse(null);

                    if (colorableBE == null) return 0xFFFFFF;

                    return i == 0 ? colorableBE.getMainColor() : colorableBE.getLayerColor((byte) i);
                },
                VSCArmorRegistry.blocks()
        );
    }
}