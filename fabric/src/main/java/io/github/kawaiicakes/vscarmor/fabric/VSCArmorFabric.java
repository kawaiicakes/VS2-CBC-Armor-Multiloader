package io.github.kawaiicakes.vscarmor.fabric;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.decal.ColorableBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class VSCArmorFabric implements ModInitializer {
    public static CreativeModeTab TAB = FabricItemGroupBuilder
            .create(new ResourceLocation(MOD_ID, "vscarmor_group"))
            .icon(() -> Registry.ITEM.get(new ResourceLocation(MOD_ID, "light_armor")).getDefaultInstance())
            .build();

    @Override
    public void onInitialize() {
        VSCArmor.init();
    }

    public static class Client implements ClientModInitializer {
        @Override
        public void onInitializeClient() {
            Minecraft.getInstance().getBlockColors().register(
                    (BlockState blockState, @Nullable BlockAndTintGetter level, @Nullable BlockPos blockPos, int i) -> {
                        if (level == null || blockPos == null) return 0xFFFFFF;
                        ColorableBlockEntity colorableBE = level
                                .getBlockEntity(blockPos, VSCArmorRegistry.colorableBEType())
                                .orElse(null);

                        if (colorableBE == null) return 0xFFFFFF;

                        return i == 0 ? colorableBE.getMainColor() : colorableBE.getWaterlineColor();
                    },
                    VSCArmorRegistry.blocks()
            );
        }
    }
}
