package thaumcraft.api;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public interface IRepairableExtended extends IRepairable
{
    boolean doRepair(final ItemStack p0, final EntityPlayer p1, final int p2);
}
