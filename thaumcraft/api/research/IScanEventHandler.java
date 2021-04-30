package thaumcraft.api.research;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public interface IScanEventHandler
{
    ScanResult scanPhenomena(final ItemStack p0, final World p1, final EntityPlayer p2);
}
