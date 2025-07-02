package io.github.kawaiicakes.vscarmor.armor;

import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// TODO - Store waterline BlockState as a boolean to allow switching item model accordingly
public class ColorableBlockEntity extends BlockEntity {
    protected int mainColor = 0xFFFFFF;
    protected int waterlineColor = 0xFFFFFF;
    protected List<Integer> layers;
    protected ColorableBlock colorableBlock = null;

    public ColorableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(VSCArmorRegistry.colorableBEType(), blockPos, blockState);

        if (this.getBlockState().getBlock() instanceof ColorableBlock colorable) {
            this.colorableBlock = colorable;

            if (colorable.getPattern().getLayers() > 0) {
                this.layers = NonNullList.createWithCapacity(colorable.getPattern().getLayers());
            }
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        CompoundTag vscarmor = new CompoundTag();
        vscarmor.putInt("body", this.mainColor);
        vscarmor.putInt("wl", this.waterlineColor);
        if (this.layers != null) {
            vscarmor.putIntArray("layers", this.layers);
        }
        compoundTag.put("vscarmor", vscarmor);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        CompoundTag vscarmor = compoundTag.getCompound("vscarmor");
        this.mainColor = vscarmor.getInt("body");
        this.waterlineColor = vscarmor.getInt("wl");

        if (vscarmor.contains("layers", Tag.TAG_INT_ARRAY)) {
            int[] layersArray = vscarmor.getIntArray("layers");
            List<Integer> temp = NonNullList.createWithCapacity(layersArray.length);
            for (int layer : layersArray) {
                temp.add(layer);
            }
            this.layers = temp;
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag toReturn = super.getUpdateTag();

        CompoundTag vscarmor = new CompoundTag();
        vscarmor.putInt("body", this.mainColor);
        vscarmor.putInt("wl", this.waterlineColor);

        toReturn.put("vscarmor", vscarmor);

        return toReturn;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.level == null) return;
        this.level.sendBlockUpdated(
                this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS
        );
    }

    public int getMainColor() {
        return this.mainColor;
    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
        this.setChanged();
    }

    public int getWaterlineColor() {
        return this.waterlineColor;
    }

    public void setWaterlineColor(int waterlineColor) {
        this.waterlineColor = waterlineColor;
        this.setChanged();
    }

    public int getLayerColor(byte layer) {
        if (this.layers == null || this.layers.size() - 1 < layer) throw new IllegalArgumentException();
        return this.layers.get(layer);
    }

    public void setLayerColor(byte layer, int color) {
        if (this.layers == null || this.layers.size() - 1 < layer) throw new IllegalArgumentException();
        this.layers.set(layer, color);
        this.setChanged();
    }
}
