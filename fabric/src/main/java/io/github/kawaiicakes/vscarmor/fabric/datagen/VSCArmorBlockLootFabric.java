package io.github.kawaiicakes.vscarmor.fabric.datagen;

import io.github.kawaiicakes.vscarmor.datagen.VSCArmorBlockLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class VSCArmorBlockLootFabric extends FabricBlockLootTableProvider {
    protected VSCArmorBlockLootFabric(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        VSCArmorBlockLootTables.generate(this);
    }
}
