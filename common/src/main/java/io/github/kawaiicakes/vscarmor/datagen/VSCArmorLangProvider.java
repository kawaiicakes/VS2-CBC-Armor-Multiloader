package io.github.kawaiicakes.vscarmor.datagen;

import com.google.gson.JsonObject;
import io.github.kawaiicakes.vscarmor.VSCArmorExpectPlatform;
import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.armor.ColorableBlock;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.level.block.Block;
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

    public void generateTranslations(BiConsumer<String, String> translationBuilder) {
        for (Block block : VSCArmorRegistry.blocks()) {
            ColorableBlock asColorable = ((ColorableBlock) block);
            String pattern = asColorable.getPattern().asPrettyPrefix();
            String grade = asColorable.getGrade().getDisplayName();
            String name = pattern + grade + asColorable.getType().asPrettySuffix();
            translationBuilder.accept(block.getDescriptionId(), name);
        }

        translationBuilder.accept(VSCArmorExpectPlatform.tabName(), "Armor Blocks");
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
