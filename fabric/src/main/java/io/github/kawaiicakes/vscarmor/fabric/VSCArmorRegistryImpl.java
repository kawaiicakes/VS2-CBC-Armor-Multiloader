package io.github.kawaiicakes.vscarmor.fabric;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.block.ArmorBlock;
import io.github.kawaiicakes.vscarmor.block.Grades;
import io.github.kawaiicakes.vscarmor.decal.ColorableBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static net.minecraft.core.Registry.*;

public class VSCArmorRegistryImpl {
    @SuppressWarnings("DataFlowIssue")
    public static BlockEntityType<ColorableBlockEntity> COLORABLE_BE_TYPE = register(
            BLOCK_ENTITY_TYPE,
            "colorable",
            BlockEntityType.Builder.of(ColorableBlockEntity::new, VSCArmorRegistry.blocks()).build(null)
    );

    public static ArmorBlock LIGHT = register(
            BLOCK,
            "light_armor",
            new ArmorBlock(Grades.LIGHT.base())
    );

    public static ArmorBlock STEEL = register(
            BLOCK,
            "light_armor",
            new ArmorBlock(Grades.STEEL.base())
    );

    public static ArmorBlock COMPOSITE = register(
            BLOCK,
            "light_armor",
            new ArmorBlock(Grades.COMPOSITE.base())
    );

    public static ArmorBlock REINFORCED = register(
            BLOCK,
            "light_armor",
            new ArmorBlock(Grades.REINFORCED.base())
    );

    public static BlockEntityType<ColorableBlockEntity> colorableBEType() {
        return COLORABLE_BE_TYPE;
    }

    public static ArmorBlock lightArmor() {
        return LIGHT;
    }

    public static ArmorBlock steelArmor() {
        return STEEL;
    }

    public static ArmorBlock compositeArmor() {
        return COMPOSITE;
    }

    public static ArmorBlock reinforcedArmor() {
        return REINFORCED;
    }

    private static <V, T extends V> T register(
            Registry<V> registry, String name, T object
    ) {
        return Registry.register(registry, new ResourceLocation(VSCArmor.MOD_ID, name), object);
    }
}
