package io.github.kawaiicakes.vscarmor.forge;

import net.minecraft.world.item.Item;

import static io.github.kawaiicakes.vscarmor.forge.VSCArmorForge.TAB;

public class VSCArmorExpectPlatformImpl {
	public static String platformName() {
		return "Forge";
	}

    public static Item.Properties withTab(Item.Properties properties) {
		return properties.tab(TAB);
    }
}
