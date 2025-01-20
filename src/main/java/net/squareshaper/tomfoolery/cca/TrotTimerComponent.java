package net.squareshaper.tomfoolery.cca;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class TrotTimerComponent implements AutoSyncedComponent {
    private int trotTimer;

    public int getTrotTimer() {
        return trotTimer;
    }

    public void setTrotTimer(int trotTimer) {
        this.trotTimer = trotTimer;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.trotTimer = nbtCompound.getInt("TrotTimer");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("TrotTimer", this.trotTimer);
    }
}
