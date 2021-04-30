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
        this.func_149663_c("ArcaneEngineering." + name);
        GameRegistry.registerBlock((Block)this, name);
        this.func_149658_d("arcane_engineering:" + name);
        this.func_149647_a(ArcaneEngineering.tabArcaneEngineering);
    }
    
    public int func_149692_a(final int meta) {
        return meta;
    }
    
    public boolean func_149662_c() {
        return false;
    }
    
    public boolean func_149686_d() {
        return false;
    }
    
    public int func_149645_b() {
        return -1;
    }
    
    public abstract TileEntity func_149915_a(final World p0, final int p1);
}
