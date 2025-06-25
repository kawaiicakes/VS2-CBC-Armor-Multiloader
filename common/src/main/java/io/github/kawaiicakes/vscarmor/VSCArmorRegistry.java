package io.github.kawaiicakes.vscarmor;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.kawaiicakes.vscarmor.block.ArmorBlock;
import io.github.kawaiicakes.vscarmor.decal.ColorableBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Do not make calls to methods in this class unless you are sure registration has completed
 */
public class VSCArmorRegistry {
    public static Block[] blocks() {
        return new Block[] {
                lightArmor(),
                steelArmor(),
                compositeArmor(),
                reinforcedArmor()
        };
    }

    @ExpectPlatform
    public static BlockEntityType<ColorableBlockEntity> colorableBEType() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ArmorBlock lightArmor() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ArmorBlock steelArmor() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ArmorBlock compositeArmor() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ArmorBlock reinforcedArmor() {
        throw new AssertionError();
    }
}
