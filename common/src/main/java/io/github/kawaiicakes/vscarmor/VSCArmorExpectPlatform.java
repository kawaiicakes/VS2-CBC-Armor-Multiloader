package io.github.kawaiicakes.vscarmor;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.kawaiicakes.vscarmor.armor.Grade;
import net.minecraft.world.item.Item;

public class VSCArmorExpectPlatform {
    @ExpectPlatform
    public static String platformName() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Item.Properties withTab(Item.Properties properties, Grade grade) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static String tabName(Grade grade) {
        throw new AssertionError();
    }
}
