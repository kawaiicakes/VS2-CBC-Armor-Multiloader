package io.github.kawaiicakes.vscarmor;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class VSCArmorExpectPlatform {
    @ExpectPlatform
    public static String platformName() {
        throw new AssertionError();
    }
}
