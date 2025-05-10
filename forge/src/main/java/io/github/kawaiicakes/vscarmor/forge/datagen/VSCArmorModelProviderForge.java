package io.github.kawaiicakes.vscarmor.forge.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.kawaiicakes.vscarmor.datagen.VSCArmorModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static io.github.kawaiicakes.vscarmor.datagen.VSCArmorModelProvider.withPrefixedPath;

public class VSCArmorModelProviderForge extends BlockStateProvider {
    protected final BlockModelGenerators blockDelegate = new BlockModelGenerators(
            (generator) -> this.registeredBlocks.put(generator.getBlock(), () -> generator.get().getAsJsonObject()),
            (modelLoc, serialized) -> {
                BlockModelBuilder builder
                        = new BlockBuilderDelegate(modelLoc, this.models().existingFileHelper, serialized);

                if (this.models().generatedModels.put(modelLoc, builder) != null) {
                    throw new IllegalStateException("Duplicate model definition for " + modelLoc);
                }
            },
            (item) -> {}
    );

    public VSCArmorModelProviderForge(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        VSCArmorModelProvider.createSimpleModels(this.blockDelegate);
        VSCArmorModelProvider.createWaterlineModels(this.blockDelegate);

        // this isn't done automatically on forge for whatever reason
        for (Block block : VSCArmorModelProvider.BASE_BLOCKS) {
            ResourceLocation location = ForgeRegistries.BLOCKS.getKey(block);

            if (location == null) continue;

            this.simpleBlockItem(
                    block,
                    new ModelFile.UncheckedModelFile(withPrefixedPath(location, "block/"))
            );
        }
    }

    public static class BlockBuilderDelegate extends BlockModelBuilder {
        protected final Supplier<JsonElement> jsonElementSupplier;

        public BlockBuilderDelegate(
                ResourceLocation outputLocation, ExistingFileHelper existingFileHelper,
                Supplier<JsonElement> jsonElementSupplier
        ) {
            super(outputLocation, existingFileHelper);
            this.jsonElementSupplier = jsonElementSupplier;
        }

        @Override
        public JsonObject toJson() {
            JsonObject toReturn = new JsonObject();

            JsonObject original = super.toJson();
            for (String key : original.keySet()) {
                toReturn.add(key, original.get(key));
            }

            JsonObject serialized = this.jsonElementSupplier.get().getAsJsonObject();
            for (String key : serialized.keySet()) {
                toReturn.add(key, serialized.get(key));
            }

            return toReturn;
        }
    }
}
