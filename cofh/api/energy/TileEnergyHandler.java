package cofh.api.energy;

import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.*;

public class TileEnergyHandler extends TileEntity implements IEnergyHandler
{
    protected EnergyStorage storage;
    
    public TileEnergyHandler() {
        this.storage = new EnergyStorage(32000);
    }
    
    public void func_145839_a(final NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.storage.readFromNBT(nbt);
    }
    
    public void func_145841_b(final NBTTagCompound nbt) {
        super.func_145841_b(nbt);
        this.storage.writeToNBT(nbt);
    }
    
    public boolean canConnectEnergy(final ForgeDirection from) {
        return true;
    }
    
    public int receiveEnergy(final ForgeDirection from, final int maxReceive, final boolean simulate) {
        return this.storage.receiveEnergy(maxReceive, simulate);
    }
    
    public int extractEnergy(final ForgeDirection from, final int maxExtract, final boolean simulate) {
        return this.storage.extractEnergy(maxExtract, simulate);
    }
    
    public int getEnergyStored(final ForgeDirection from) {
        return this.storage.getEnergyStored();
    }
    
    public int getMaxEnergyStored(final ForgeDirection from) {
        return this.storage.getMaxEnergyStored();
    }
}
