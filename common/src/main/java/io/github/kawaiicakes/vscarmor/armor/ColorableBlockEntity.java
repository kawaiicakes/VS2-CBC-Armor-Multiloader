package io.github.kawaiicakes.vscarmor.armor;

import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColorableBlockEntity extends BlockEntity {
    protected int mainColor = 0xFFFFFF;
    protected int waterlineColor = 0xFFFFFF;

    public ColorableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(VSCArmorRegistry.colorableBEType(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        CompoundTag vscarmor = new CompoundTag();
        vscarmor.putInt("body", this.mainColor);
        vscarmor.putInt("wl", this.waterlineColor);
        compoundTag.put("vscarmor", vscarmor);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        CompoundTag vscarmor = compoundTag.getCompound("vscarmor");
        this.mainColor = vscarmor.getInt("body");
        this.waterlineColor = vscarmor.getInt("wl");
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
}
