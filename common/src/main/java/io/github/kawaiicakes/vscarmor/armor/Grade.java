package io.github.kawaiicakes.vscarmor.armor;

import io.github.kawaiicakes.vscarmor.datagen.VSCArmorModelProvider;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;
import static net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK;

public enum Grade implements StringRepresentable {
	LIGHT(
			"light_armor",
			3.0F, 5.0F, 392.0, 5, 1,
			(patternBaseId) ->
					TextureMapping.cube(patternBaseId)
							.put(ArmorTextureSlots.WATERLINE, waterline("light_armor"))
							.put(TextureSlot.SIDE, patternBaseId)
							.put(TextureSlot.TOP, patternBaseId)
							.put(TextureSlot.BOTTOM, patternBaseId)
							.put(TextureSlot.END, patternBaseId)
							.put(TextureSlot.TEXTURE, patternBaseId)
							.put(TextureSlot.WALL, patternBaseId)
	),
	STEEL(
			"steel_armor",
			10.0F, 7.0F, 1176.0, 7, 1,
			(patternBaseId) ->
					TextureMapping.cube(patternBaseId)
							.put(ArmorTextureSlots.WATERLINE, waterline("steel_armor"))
							.put(TextureSlot.SIDE, patternBaseId)
							.put(TextureSlot.TOP, patternBaseId)
							.put(TextureSlot.BOTTOM, patternBaseId)
							.put(TextureSlot.END, patternBaseId)
							.put(TextureSlot.TEXTURE, patternBaseId)
							.put(TextureSlot.WALL, patternBaseId)
	),
	COMPOSITE(
			"composite_armor",
			28.0F, 8.0F, 2744.0, 8, 1,
			(patternBaseId) ->
					TextureMapping.cube(patternBaseId)
							.put(ArmorTextureSlots.WATERLINE, waterline("composite_armor"))
							.put(TextureSlot.SIDE, patternBaseId)
							.put(TextureSlot.TOP, patternBaseId)
							.put(TextureSlot.BOTTOM, patternBaseId)
							.put(TextureSlot.END, patternBaseId)
							.put(TextureSlot.TEXTURE, patternBaseId)
							.put(TextureSlot.WALL, patternBaseId)
	),
	REINFORCED(
			"reinforced_armor",
			50.0F, 20.0F, 4312.0, 20, 1,
			(patternBaseId) ->
					TextureMapping.cube(patternBaseId)
							.put(ArmorTextureSlots.WATERLINE, waterline("reinforced_armor"))
							.put(TextureSlot.SIDE, patternBaseId)
							.put(TextureSlot.TOP, patternBaseId)
							.put(TextureSlot.BOTTOM, patternBaseId)
							.put(TextureSlot.END, patternBaseId)
							.put(TextureSlot.TEXTURE, patternBaseId)
							.put(TextureSlot.WALL, patternBaseId)
	);

	final String name;
	final float hardness;
	final float blastResistance;
	final double mass;
	final float cbcToughness;
	final float cbcHardness;
	/**
	 * This field allows for adding/changing textures for a Grade when necessary, like if I suddenly decide that I want
	 * different top/bottom textures than the side ones. This way I only need to change code here rather than in model
	 * datagen.
	 */
	final Function<ResourceLocation, TextureMapping> mapping;

	Grade(
			String name,
			float hardness, float blastResistance, double mass, float cbcToughness, float cbcHardness,
			Function<ResourceLocation, TextureMapping> mapping
	) {
		this.name = name;
		this.hardness = hardness;
		this.blastResistance = blastResistance;
		this.mass = mass;
		this.cbcToughness = cbcToughness;
		this.cbcHardness = cbcHardness;
		this.mapping = mapping;
	}

	public BlockBehaviour.Properties properties() {
		return properties(1);
	}

	public BlockBehaviour.Properties properties(float multiplier) {
		return BlockBehaviour.Properties.copy(NETHERITE_BLOCK)
				.destroyTime(this.hardness * multiplier)
				.explosionResistance(this.blastResistance * multiplier);
	}

	@Override
	public @NotNull String getSerializedName() {
		return this.name;
	}

	@SuppressWarnings("deprecation")
    public String getDisplayName() {
		return WordUtils.capitalize(this.getSerializedName().replace("_", " "));
	}

	public double getMass() {
		return this.mass;
	}

	public TextureMapping getTextureMapping(ColorableBlock colorableBlock) {
		return this.mapping.apply(getGradeBaseModelId(colorableBlock));
	}

	public ResourceLocation getGradeBaseModelId(ColorableBlock colorableBlock) {
		return VSCArmorModelProvider.withPrefixedPath(colorableBlock.getPatternBaseTexture(), "block/");
	}

	public ResourceLocation getGradeWlBaseModelId(ColorableBlock colorableBlock) {
		return VSCArmorModelProvider.withSuffixedPath(
				VSCArmorModelProvider.withPrefixedPath(colorableBlock.getPatternBaseTexture(), "block/"),
				"_waterline"
		);
	}

	private static ResourceLocation waterline(String patternBase) {
		return new ResourceLocation(MOD_ID, "block/" + patternBase);
	}
}