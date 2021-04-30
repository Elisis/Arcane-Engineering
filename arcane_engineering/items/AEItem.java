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
        this.func_77627_a(subNames != null && subNames.length > 0);
        this.func_77625_d(stackSize);
        this.itemName = name;
        this.func_77655_b("ArcaneEngineering." + name);
        this.subNames = (String[])((subNames != null && subNames.length < 1) ? null : subNames);
        this.icons = new IIcon[(this.subNames != null) ? this.subNames.length : 1];
        this.func_77637_a(ArcaneEngineering.tabArcaneEngineering);
        GameRegistry.registerItem((Item)this, name);
    }
    
    public String[] getSubNames() {
        return this.subNames;
    }
    
    public void func_94581_a(final IIconRegister ir) {
        if (this.getSubNames() != null) {
            for (int i = 0; i < this.icons.length; ++i) {
                this.icons[i] = ir.func_94245_a("arcane_engineering:" + this.itemName + "_" + this.getSubNames()[i]);
            }
        }
        else {
            this.icons[0] = ir.func_94245_a("arcane_engineering:" + this.itemName);
        }
    }
    
    public IIcon func_77617_a(final int meta) {
        if (this.getSubNames() != null && meta >= 0 && meta < this.icons.length) {
            return this.icons[meta];
        }
        return this.icons[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void func_150895_a(final Item item, final CreativeTabs tab, final List list) {
        if (this.getSubNames() != null) {
            for (int i = 0; i < this.getSubNames().length; ++i) {
                list.add(new ItemStack((Item)this, 1, i));
            }
        }
        else {
            list.add(new ItemStack((Item)this));
        }
    }
    
    public String func_77667_c(final ItemStack stack) {
        if (this.getSubNames() != null) {
            final String subName = (stack.func_77960_j() < this.getSubNames().length) ? this.getSubNames()[stack.func_77960_j()] : "";
            return this.func_77658_a() + "." + subName;
        }
        return this.func_77658_a();
    }
}
