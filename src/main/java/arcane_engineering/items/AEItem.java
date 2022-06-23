package arcane_engineering.items;

import net.minecraft.util.*;
import arcane_engineering.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import cpw.mods.fml.relauncher.*;

public class AEItem extends Item
{
    public String itemName;
    public String[] subNames;
    public IIcon[] icons;
    
    public AEItem(final String name, final int stackSize, final String... subNames) {
        this.setHasSubtypes(subNames != null && subNames.length > 0);
        this.setMaxStackSize(stackSize);
        this.itemName = name;
        this.setUnlocalizedName("ArcaneEngineering." + name);
        this.subNames = (String[])((subNames != null && subNames.length < 1) ? null : subNames);
        this.icons = new IIcon[(this.subNames != null) ? this.subNames.length : 1];
        this.setCreativeTab(ArcaneEngineering.tabArcaneEngineering);
        GameRegistry.registerItem((Item)this, name);
    }
    
    public String[] getSubNames() {
        return this.subNames;
    }
    
    public void registerIcons(final IIconRegister ir) {
        if (this.getSubNames() != null) {
            for (int i = 0; i < this.icons.length; ++i) {
                this.icons[i] = ir.registerIcon("arcane_engineering:" + this.itemName + "_" + this.getSubNames()[i]);
            }
        }
        else {
            this.icons[0] = ir.registerIcon("arcane_engineering:" + this.itemName);
        }
    }
    
    public IIcon getIconFromDamage(final int meta) {
        if (this.getSubNames() != null && meta >= 0 && meta < this.icons.length) {
            return this.icons[meta];
        }
        return this.icons[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        if (this.getSubNames() != null) {
            for (int i = 0; i < this.getSubNames().length; ++i) {
                list.add(new ItemStack((Item)this, 1, i));
            }
        }
        else {
            list.add(new ItemStack((Item)this));
        }
    }
    
    public String getUnlocalizedName(final ItemStack stack) {
        if (this.getSubNames() != null) {
            final String subName = (stack.getItemDamage() < this.getSubNames().length) ? this.getSubNames()[stack.getItemDamage()] : "";
            return this.getUnlocalizedName() + "." + subName;
        }
        return this.getUnlocalizedName();
    }
}
