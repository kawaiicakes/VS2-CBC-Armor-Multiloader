package io.github.kawaiicakes.vscarmor.fabric.datagen;

import io.github.kawaiicakes.vscarmor.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class VSCArmorDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(VSCArmorBlockLootFabric::new);
        fabricDataGenerator.addProvider(VSCArmorModelProviderFabric::new);
        fabricDataGenerator.addProvider(VSCArmorBlockTagProvider::new);
        fabricDataGenerator.addProvider(generator -> new VSCArmorLangProvider(generator, "en_us"));
        fabricDataGenerator.addProvider(ValkyrienSkiesPropertyProvider::new);
    }
}
