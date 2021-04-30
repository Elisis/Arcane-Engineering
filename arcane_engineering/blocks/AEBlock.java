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
        this.setUnlocalizedName("ArcaneEngineering." + name);
        GameRegistry.registerBlock((Block)this, name);
        this.setCreativeTab(ArcaneEngineering.tabArcaneEngineering);
    }
    
    public int damageDropped(final int meta) {
        return meta;
    }
    
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.subNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    public void registerIcons(final IIconRegister iconRegister) {
        for (int i = 0; i < this.subNames.length; ++i) {
            this.icons[i] = iconRegister.registerIcon(this.itemName + "_" + this.subNames[i]);
        }
    }
}
