package thaumcraft.api.internal;

import thaumcraft.api.aspects.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class DummyInternalMethodHandler implements IInternalMethodHandler
{
    @Override
    public void generateVisEffect(final int dim, final int x, final int y, final int z, final int x2, final int y2, final int z2, final int color) {
    }
    
    @Override
    public boolean isResearchComplete(final String username, final String researchkey) {
        return false;
    }
    
    @Override
    public boolean hasDiscoveredAspect(final String username, final Aspect aspect) {
        return false;
    }
    
    @Override
    public AspectList getDiscoveredAspects(final String username) {
        return null;
    }
    
    @Override
    public ItemStack getStackInRowAndColumn(final Object instance, final int row, final int column) {
        return null;
    }
    
    @Override
    public AspectList getObjectAspects(final ItemStack is) {
        return null;
    }
    
    @Override
    public AspectList getBonusObjectTags(final ItemStack is, final AspectList ot) {
        return null;
    }
    
    @Override
    public AspectList generateTags(final Item item, final int meta) {
        return null;
    }
    
    @Override
    public boolean consumeVisFromWand(final ItemStack wand, final EntityPlayer player, final AspectList cost, final boolean doit, final boolean crafting) {
        return false;
    }
    
    @Override
    public boolean consumeVisFromWandCrafting(final ItemStack wand, final EntityPlayer player, final AspectList cost, final boolean doit) {
        return false;
    }
    
    @Override
    public boolean consumeVisFromInventory(final EntityPlayer player, final AspectList cost) {
        return false;
    }
    
    @Override
    public void addWarpToPlayer(final EntityPlayer player, final int amount, final boolean temporary) {
    }
    
    @Override
    public void addStickyWarpToPlayer(final EntityPlayer player, final int amount) {
    }
}
