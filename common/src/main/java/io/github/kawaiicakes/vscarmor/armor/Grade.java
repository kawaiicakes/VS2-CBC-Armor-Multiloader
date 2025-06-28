package io.github.kawaiicakes.vscarmor.armor;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK;

public enum Grade implements StringRepresentable {
	LIGHT("light_armor", 3.0F, 5.0F, 392.0, 5, 1),
	STEEL("steel_armor", 10.0F, 7.0F, 1176.0, 7, 1),
	COMPOSITE("composite_armor", 28.0F, 8.0F, 2744.0, 8, 1),
	REINFORCED("reinforced_armor", 50.0F, 20.0F, 4312.0, 20, 1);

	final String name;
	final float hardness;
	final float blastResistance;
	final double mass;
	final float cbcToughness;
	final float cbcHardness;

	Grade(String name, float hardness, float blastResistance, double mass, float cbcToughness, float cbcHardness) {
		this.name = name;
		this.hardness = hardness;
		this.blastResistance = blastResistance;
		this.mass = mass;
		this.cbcToughness = cbcToughness;
		this.cbcHardness = cbcHardness;
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
}