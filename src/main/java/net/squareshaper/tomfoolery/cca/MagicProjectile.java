package net.squareshaper.tomfoolery.cca;


import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class MagicProjectile implements AutoSyncedComponent {
    private int Age;

    public int getAge() {
        return Age;
    }


    public void setAge(int age) {
        this.Age = age;
    }

    public void decrementAge() {this.Age--;}


    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.Age = nbtCompound.getInt("Age");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("Age", this.Age);
    }
}
