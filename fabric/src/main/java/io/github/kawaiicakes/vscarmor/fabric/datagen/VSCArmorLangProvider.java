package io.github.kawaiicakes.vscarmor.fabric.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.text.WordUtils;

import java.nio.file.Path;

public class VSCArmorLangProvider extends FabricLanguageProvider {
    public VSCArmorLangProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "en_us");
    }

    // Surely nothing can go horribly wrong here!
    @SuppressWarnings("deprecation")
    private static String sanitizeName(String rawId) {
        String toReturn = WordUtils.capitalize(
                rawId.replace("block.vscarmor.", "").replace("_", " ")
        );

        toReturn = toReturn
                .replaceFirst("Ab ", "Alphabet ")
                .replaceFirst("Wl ", "Waterline ")
                .replaceFirst("29 ", "Blue #29 ")
                .replaceFirst("31 ", "Gray #31 ")
                .replaceFirst("32 ", "Gray #32 ")
                .replaceFirst("33 ", "Blue #33 ")
                .replaceFirst("4b0 ", "Soviet 4B0 Green ");

        if (toReturn.contains("Camo ")) {
            String waterline = toReturn.contains("Waterline ") ? "Waterline " : "";
            toReturn = toReturn.replaceFirst("Waterline ", "");

            toReturn = toReturn.replaceFirst("Camo ", "");

            String[] split = toReturn.split(" ", 2);

            toReturn = waterline + split[0] + " Camo " + split[1];
        }

        return toReturn;
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (Block block : VSCArmorBlocks.blocks()) {
            // Item item = Registry.ITEM.get(Registry.BLOCK.getKey(block));

            String name = sanitizeName(block.getDescriptionId());
            translationBuilder.add(block, name);
            // translationBuilder.add(item, name);
        }

        /*
        try {
            Path existingFilePath = this.dataGenerator
                    .getModContainer()
                    .findPath("common/src/resources/assets/vscarmor/lang/en_us.existing.json")
                    .orElseThrow();

            translationBuilder.add(existingFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }
         */
    }
}
