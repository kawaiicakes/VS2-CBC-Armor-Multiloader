package io.github.kawaiicakes.vscarmor;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class VSCArmorItems {
    public static final List<Pair<String, Supplier<Item>>> ITEMS = new ArrayList<>();

    public static void init(List<Pair<String, Supplier<Block>>> blocks) {
        VSCArmor.LOGGER.info("Registering items for " + VSCArmor.NAME);

        for (Pair<String, Supplier<Block>> pair : blocks) {
            ITEMS.add(
                    Pair.of(
                            pair.left(),
                            () -> new BlockItem(
                                    Registry.BLOCK.get(new ResourceLocation(MOD_ID, pair.left())),
                                    new Item.Properties()
                            )
                    )
            );
        }
    }
}
