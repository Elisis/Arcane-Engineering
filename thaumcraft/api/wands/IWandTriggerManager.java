package thaumcraft.api.wands;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public interface IWandTriggerManager
{
    boolean performTrigger(final World p0, final ItemStack p1, final EntityPlayer p2, final int p3, final int p4, final int p5, final int p6, final int p7);
}
