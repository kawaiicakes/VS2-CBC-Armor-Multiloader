package io.github.kawaiicakes.vscarmor.forge;

import io.github.kawaiicakes.vscarmor.armor.Grade;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import static io.github.kawaiicakes.vscarmor.forge.VSCArmorForge.*;

@SuppressWarnings("unused")
public class VSCArmorExpectPlatformImpl {
	public static String platformName() {
		return "Forge";
	}

    public static Item.Properties withTab(Item.Properties properties, Grade grade) {
        CreativeModeTab tab = switch (grade) {
            case LIGHT -> LIGHT_TAB;
            case STEEL -> STEEL_TAB;
            case COMPOSITE -> COMPOSITE_TAB;
            case REINFORCED -> REINFORCED_TAB;
        };

        return properties.tab(tab);
    }

    public static String tabName(Grade grade) {
        return "itemGroup.vscarmor." + grade.getSerializedName();
    }
}
