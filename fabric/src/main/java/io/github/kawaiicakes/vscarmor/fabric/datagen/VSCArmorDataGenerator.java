package io.github.kawaiicakes.vscarmor.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class VSCArmorDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(VSCArmorBlockLootTables::new);
        fabricDataGenerator.addProvider(VSCArmorModelProvider::new);
        fabricDataGenerator.addProvider(VSCArmorBlockTagProvider::new);
        fabricDataGenerator.addProvider(VSCArmorLangProvider::new);
        fabricDataGenerator.addProvider(ValkyrienSkiesPropertyProvider::new);
    }
}
