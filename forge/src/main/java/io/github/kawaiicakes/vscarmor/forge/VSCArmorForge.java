package io.github.kawaiicakes.vscarmor.forge;

import io.github.kawaiicakes.vscarmor.*;
import io.github.kawaiicakes.vscarmor.armor.ColorableBlockItem;
import io.github.kawaiicakes.vscarmor.datagen.VSCArmorLangProvider;
import io.github.kawaiicakes.vscarmor.datagen.ValkyrienSkiesPropertyProvider;
import io.github.kawaiicakes.vscarmor.armor.ColorableBlockEntity;
import io.github.kawaiicakes.vscarmor.forge.datagen.VSCArmorBlockLootForge;
import io.github.kawaiicakes.vscarmor.forge.datagen.VSCArmorBlockTagsForge;
import io.github.kawaiicakes.vscarmor.forge.datagen.VSCArmorModelProviderForge;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

@Mod(MOD_ID)
public class VSCArmorForge {
    public static final CreativeModeTab LIGHT_TAB = new CreativeModeTab("vscarmor.light_armor") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return RegistryObject.create(new ResourceLocation(MOD_ID, "light_armor"), ForgeRegistries.ITEMS)
                    .get()
                    .getDefaultInstance();
        }
    };

    public static final CreativeModeTab STEEL_TAB = new CreativeModeTab("vscarmor.steel_armor") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return RegistryObject.create(new ResourceLocation(MOD_ID, "steel_armor"), ForgeRegistries.ITEMS)
                    .get()
                    .getDefaultInstance();
        }
    };

    public static final CreativeModeTab COMPOSITE_TAB = new CreativeModeTab("vscarmor.composite_armor") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return RegistryObject.create(new ResourceLocation(MOD_ID, "composite_armor"), ForgeRegistries.ITEMS)
                    .get()
                    .getDefaultInstance();
        }
    };

    public static final CreativeModeTab REINFORCED_TAB = new CreativeModeTab("vscarmor.reinforced_armor") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return RegistryObject.create(new ResourceLocation(MOD_ID, "reinforced_armor"), ForgeRegistries.ITEMS)
                    .get()
                    .getDefaultInstance();
        }
    };

    public VSCArmorForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        VSCArmorRegistryImpl.register(eventBus);

        VSCArmor.init();

        eventBus.addListener(this::gatherData);
    }

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();

        gen.addProvider(
                event.includeServer(),
                new VSCArmorBlockLootForge(gen)
        );

        gen.addProvider(
                event.includeClient(),
                new VSCArmorModelProviderForge(gen, MOD_ID, efh)
        );

        gen.addProvider(
                event.includeServer(),
                new VSCArmorBlockTagsForge(gen, MOD_ID, efh)
        );

        gen.addProvider(
                event.includeClient(),
                new VSCArmorLangProvider(gen, "en_us")
        );

        gen.addProvider(
                event.includeServer(),
                new ValkyrienSkiesPropertyProvider(gen)
        );
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
            event.register(
                    (BlockState blockState, @Nullable BlockAndTintGetter level, @Nullable BlockPos blockPos, int i) -> {
                        if (level == null || blockPos == null) return 0xFFFFFF;
                        ColorableBlockEntity colorableBE = level
                                .getBlockEntity(blockPos, VSCArmorRegistry.colorableBEType())
                                .orElse(null);

                        if (colorableBE == null) return 0xFFFFFF;

                        return i == 0 ? colorableBE.getMainColor() : colorableBE.getLayerColor((byte) i);
                    },
                    VSCArmorRegistry.blocks()
            );
        }

        @SubscribeEvent
        public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
            event.register(
                    (ItemStack itemStack, int i) -> {
                        if (!(itemStack.getItem() instanceof ColorableBlockItem colorableItem)) return 0xFFFFFF;
                        return colorableItem.getLayerColor(itemStack, i);
                    },
                    VSCArmorRegistry.items()
            );
        }
    }
}
