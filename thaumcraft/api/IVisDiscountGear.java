package thaumcraft.api;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.aspects.*;

public interface IVisDiscountGear
{
    int getVisDiscount(final ItemStack p0, final EntityPlayer p1, final Aspect p2);
}
