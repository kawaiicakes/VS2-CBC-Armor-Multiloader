package io.github.kawaiicakes.vscarmor.forge;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.block.ArmorBlock;
import io.github.kawaiicakes.vscarmor.block.Grades;
import io.github.kawaiicakes.vscarmor.decal.ColorableBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VSCArmorRegistryImpl {
    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, VSCArmor.MOD_ID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, VSCArmor.MOD_ID);

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<ColorableBlockEntity>> COLORABLE_BE_TYPE = BLOCK_ENTITY_TYPES
            .register(
                    "colorable",
                    () -> BlockEntityType.Builder
                            .of(ColorableBlockEntity::new, VSCArmorRegistry.blocks())
                            .build(null)
            );

    public static final RegistryObject<ArmorBlock> LIGHT = BLOCKS.register(
            "light_armor", () -> new ArmorBlock(Grades.LIGHT.base())
    );

    public static final RegistryObject<ArmorBlock> STEEL = BLOCKS.register(
            "steel_armor", () -> new ArmorBlock(Grades.STEEL.base())
    );

    public static final RegistryObject<ArmorBlock> COMPOSITE = BLOCKS.register(
            "composite_armor", () -> new ArmorBlock(Grades.COMPOSITE.base())
    );

    public static final RegistryObject<ArmorBlock> REINFORCED = BLOCKS.register(
            "reinforced_armor", () -> new ArmorBlock(Grades.REINFORCED.base())
    );

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
    }

    public static BlockEntityType<ColorableBlockEntity> colorableBEType() {
        return COLORABLE_BE_TYPE.orElse(null);
    }

    public static ArmorBlock lightArmor() {
        return LIGHT.get();
    }

    public static ArmorBlock steelArmor() {
        return STEEL.get();
    }

    public static ArmorBlock compositeArmor() {
        return COMPOSITE.get();
    }

    public static ArmorBlock reinforcedArmor() {
        return REINFORCED.get();
    }
}
