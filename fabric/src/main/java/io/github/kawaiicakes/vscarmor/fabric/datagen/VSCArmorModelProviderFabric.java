package io.github.kawaiicakes.vscarmor.fabric.datagen;

import io.github.kawaiicakes.vscarmor.datagen.VSCArmorModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class VSCArmorModelProviderFabric extends FabricModelProvider {
    public VSCArmorModelProviderFabric(FabricDataGenerator output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        VSCArmorModelProvider.createSimpleModels(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {

    }
}
