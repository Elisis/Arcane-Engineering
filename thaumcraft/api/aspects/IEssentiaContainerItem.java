package thaumcraft.api.aspects;

import net.minecraft.item.*;

public interface IEssentiaContainerItem
{
    AspectList getAspects(final ItemStack p0);
    
    void setAspects(final ItemStack p0, final AspectList p1);
}
