package io.github.kawaiicakes.vscarmor;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;

public class VSCArmorExpectPlatform {
    @ExpectPlatform
    public static String platformName() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item.Properties withTab(Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static String tabName() {
        throw new AssertionError();
    }
}
