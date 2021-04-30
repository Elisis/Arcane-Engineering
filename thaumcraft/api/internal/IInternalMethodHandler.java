package thaumcraft.api.internal;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.aspects.*;

public interface IInternalMethodHandler
{
    void generateVisEffect(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7);
    
    boolean isResearchComplete(final String p0, final String p1);
    
    ItemStack getStackInRowAndColumn(final Object p0, final int p1, final int p2);
    
    AspectList getObjectAspects(final ItemStack p0);
    
    AspectList getBonusObjectTags(final ItemStack p0, final AspectList p1);
    
    AspectList generateTags(final Item p0, final int p1);
    
    boolean consumeVisFromWand(final ItemStack p0, final EntityPlayer p1, final AspectList p2, final boolean p3, final boolean p4);
    
    boolean consumeVisFromWandCrafting(final ItemStack p0, final EntityPlayer p1, final AspectList p2, final boolean p3);
    
    boolean consumeVisFromInventory(final EntityPlayer p0, final AspectList p1);
    
    void addWarpToPlayer(final EntityPlayer p0, final int p1, final boolean p2);
    
    void addStickyWarpToPlayer(final EntityPlayer p0, final int p1);
    
    boolean hasDiscoveredAspect(final String p0, final Aspect p1);
    
    AspectList getDiscoveredAspects(final String p0);
}
