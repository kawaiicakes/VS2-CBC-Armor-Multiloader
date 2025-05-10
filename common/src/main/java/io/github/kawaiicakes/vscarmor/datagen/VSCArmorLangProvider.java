package io.github.kawaiicakes.vscarmor.datagen;

import com.google.gson.JsonObject;
import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class VSCArmorLangProvider implements DataProvider {
    protected final DataGenerator dataGenerator;
    private final String languageCode;

    public VSCArmorLangProvider(DataGenerator dataGenerator, String languageCode) {
        this.dataGenerator = dataGenerator;
        this.languageCode = languageCode;
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

    public void generateTranslations(BiConsumer<String, String> translationBuilder) {
        for (Block block : VSCArmorBlocks.blocks()) {
            String name = sanitizeName(block.getDescriptionId());
            translationBuilder.accept(block.getDescriptionId(), name);
        }
    }

    @Override
    public void run(@NotNull CachedOutput cachedOutput) throws IOException {
        TreeMap<String, String> translationEntries = new TreeMap<>();

        generateTranslations((String key, String value) -> {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);

            if (translationEntries.containsKey(key)) {
                throw new RuntimeException("Existing translation key found - " + key + " - Duplicate will be ignored.");
            }

            translationEntries.put(key, value);
        });

        JsonObject langEntryJson = new JsonObject();

        for (Map.Entry<String, String> entry : translationEntries.entrySet()) {
            langEntryJson.addProperty(entry.getKey(), entry.getValue());
        }

        DataProvider.saveStable(cachedOutput, langEntryJson, getLangFilePath(this.languageCode));
    }

    @Override
    public @NotNull String getName() {
        return "Language";
    }

    private Path getLangFilePath(String code) {
        return this.dataGenerator.getOutputFolder()
                .resolve("assets/%s/lang/%s.json".formatted(MOD_ID, code));
    }
}
