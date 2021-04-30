package cofh.api.energy;

import net.minecraftforge.common.util.*;

public interface IEnergyProvider extends IEnergyConnection
{
    int extractEnergy(final ForgeDirection p0, final int p1, final boolean p2);
    
    int getEnergyStored(final ForgeDirection p0);
    
    int getMaxEnergyStored(final ForgeDirection p0);
}