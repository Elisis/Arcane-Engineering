package arcane_engineering.blocks;

import net.minecraft.block.material.*;
import net.minecraftforge.common.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import arcane_engineering.blocks.tiles.*;
import thaumcraft.api.aspects.*;
import thaumcraft.api.nodes.*;
import net.minecraft.tileentity.*;

public class BlockDestabilizer extends AEBlockContainer
{
    public BlockDestabilizer() {
        super(Material.iron, "destabilizer");
        this.setHardness(5.0f);
    }
    
    public boolean isSideSolid(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection side) {
        return side == ForgeDirection.DOWN;
    }
    
    public boolean canEntityDestroy(final IBlockAccess world, final int x, final int y, final int z, final Entity entity) {
        return false;
    }
    
    public void breakBlock(final World worldObj, final int x, final int y, final int z, final Block b, final int p_149749_6_) {
        if (worldObj.getTileEntity(x, y, z) instanceof TileEntityDestabilizer && worldObj.getTileEntity(x, y + 1, z) instanceof INode && ((TileEntityDestabilizer)worldObj.getTileEntity(x, y, z)).open) {
            final AspectList aspects = new AspectList().add(Aspect.TAINT, 1);
            ((INode)worldObj.getTileEntity(x, y + 1, z)).setAspects(aspects);
            ((INode)worldObj.getTileEntity(x, y + 1, z)).setNodeType(NodeType.TAINTED);
            ((INode)worldObj.getTileEntity(x, y + 1, z)).setNodeModifier(NodeModifier.FADING);
            worldObj.createExplosion((Entity)null, x + 0.5, y + 1.5, z + 0.5, 2.0f, true);
            worldObj.markBlockForUpdate(x, y + 1, z);
        }
        super.breakBlock(worldObj, x, y, z, b, p_149749_6_);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityDestabilizer();
    }
}
