package arcane_engineering.blocks;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import cpw.mods.fml.common.registry.*;
import arcane_engineering.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class AEBlock extends Block
{
    public String itemName;
    public String[] subNames;
    public IIcon[] icons;
    
    protected AEBlock(final Material material, final String name, final String... subNames) {
        super(material);
        this.itemName = name;
        this.subNames = (String[])((subNames != null && subNames.length < 1) ? null : subNames);
        this.icons = new IIcon[(this.subNames != null) ? this.subNames.length : 1];
        this.func_149663_c("ArcaneEngineering." + name);
        GameRegistry.registerBlock((Block)this, name);
        this.func_149647_a(ArcaneEngineering.tabArcaneEngineering);
    }
    
    public int func_149692_a(final int meta) {
        return meta;
    }
    
    public void func_149666_a(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.subNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    public void func_149651_a(final IIconRegister iconRegister) {
        for (int i = 0; i < this.subNames.length; ++i) {
            this.icons[i] = iconRegister.func_94245_a(this.itemName + "_" + this.subNames[i]);
        }
    }
}
