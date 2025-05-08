package io.github.kawaiicakes.vscarmor;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class VSCArmorItems {
    public static final List<Pair<String, Item>> ITEMS = new ArrayList<>();

    public static void init(List<Pair<String, Block>> blocks) {
        ITEMS.addAll(
                blocks.stream()
                        .map((block) ->
                                Pair.of(block.left(), (Item) (new BlockItem(block.right(), new Item.Properties())))
                        )
                        .toList()
        );
    }
}
