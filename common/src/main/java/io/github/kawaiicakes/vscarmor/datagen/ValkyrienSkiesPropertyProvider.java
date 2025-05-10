package io.github.kawaiicakes.vscarmor.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class ValkyrienSkiesPropertyProvider implements DataProvider {
    public final DataGenerator.PathProvider pathResolver;

    public ValkyrienSkiesPropertyProvider(DataGenerator output) {
        this.pathResolver = output.createPathProvider(DataGenerator.Target.DATA_PACK, "vs_mass");
    }

    @Override
    public void run(@NotNull CachedOutput writer) throws IOException {
        DataProvider.saveStable(
                writer,
                properties(),
                this.pathResolver.json(new ResourceLocation("valkyrienskies", MOD_ID))
        );
    }

    public static JsonArray properties() {
        List<Block> blocks = VSCArmorBlocks.blocks();

        JsonArray toReturn = new JsonArray(blocks.size());

        for (Block block : blocks) {
            double frictionCoefficient = 0.2;
            int priority = 420;

            JsonObject propertyObject = new JsonObject();

            propertyObject.addProperty("block", Registry.BLOCK.getKey(block).toString());
            propertyObject.addProperty("mass", getMass(block));
            propertyObject.addProperty("friction", frictionCoefficient);
            propertyObject.addProperty("priority", priority);

            toReturn.add(propertyObject);
        }

        return toReturn;
    }

    public static double getMass(Block block) {
        double reinforcedMass = 4312;
        double compositeMass = 2744;
        double steelMass = 1176;
        double lightMass = 392;

        String blockPath = Registry.BLOCK.getKey(block).getPath();

        final double glassWeight = 200;
        double multiplier = 1;
        double glassMultiplier = 0;
        final double grade;

        if (blockPath.contains("light_armor")) grade = lightMass;
        else if (blockPath.contains("steel_armor")) grade = steelMass;
        else if (blockPath.contains("composite_armor")) grade = compositeMass;
        else grade = reinforcedMass;

        if (blockPath.contains("porthole")) {
            multiplier = 0.75;
            glassMultiplier = 0.25;
        }
        // looks for vertical and horizontal too instead of just window in prep for full window block
        else if (blockPath.contains("vertical_window") || blockPath.contains("horizontal_window")) {
            multiplier = 0.4375;
            glassMultiplier = 0.5625;
        }

        if (blockPath.contains("slab"))
            multiplier *= 0.5;
        else if (blockPath.contains("stairs"))
            multiplier *= 0.75;
        else if (blockPath.contains("fence"))
            multiplier = 0.0625;
        else if (blockPath.contains("wall"))
            multiplier = 0.25;

        return (grade * multiplier) + (glassWeight * glassMultiplier);
    }

    @Override
    public @NotNull String getName() {
        return "VS2 Block Properties";
    }
}
