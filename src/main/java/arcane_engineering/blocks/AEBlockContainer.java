package arcane_engineering.blocks;

import net.minecraft.block.material.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.block.*;
import arcane_engineering.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public abstract class AEBlockContainer extends BlockContainer
{
    public String name;
    
    public AEBlockContainer(final Material material, final String name) {
        super(material);
        this.name = name;
        this.setBlockName("ArcaneEngineering." + name);
        GameRegistry.registerBlock((Block)this, name);
        this.setBlockTextureName("arcane_engineering:" + name);
        this.setCreativeTab(ArcaneEngineering.tabArcaneEngineering);
    }
    
    public int damageDropped(final int meta) {
        return meta;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return -1;
    }
    
    public abstract TileEntity createNewTileEntity(final World p0, final int p1);
}
